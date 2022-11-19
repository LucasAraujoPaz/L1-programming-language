import java.util.ArrayList;
import java.util.Optional;

public class Parser {

	private final ArrayList<Token> tokens;
	private int indice = 0;
	
	private Parser(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}
	
	public ArrayList<Declaracao> programa() {
		var declaracoes = new ArrayList<Declaracao>();
		
		while (atual().isPresent())
			declaracoes.add(declaracao());
		
		return declaracoes;
	}
	
	private Declaracao declaracao() {
		consumir(TipoDeToken.LET, "Declara��es come�am com Let");
		
		var nome = consumir(TipoDeToken.IDENTIFICADOR, "Declara��o necessita de identificador");
		asseverar( ! nome.tipo().ehPalavraReservada(), "Nome declarado n�o pode ser palavra reservada", Optional.of(nome));
		
		consumir(TipoDeToken.DEFINIDOCOMO, "Declara��o necessita de :=");
		var expressao = expressao(Precedencia.NENHUMA);
		
		consumir(TipoDeToken.PONTO, "Declara��o encerra com .");
		
		return new Declaracao(nome, expressao);
	}
	
	private Expressao expressao(Precedencia precedencia) {
		
		asseverar(atual().map(t -> t.tipo().prefix.isPresent()).orElse(false), "Express�o esperada", atual());
		Token tokenEsquerdo = consumir();
		
		Expressao esquerda = tokenEsquerdo.tipo().prefix.get().apply(this);
		
		while (precedencia.ordinal() < precedencia().ordinal()) {
			
			asseverar(atual().map(t -> t.tipo().infix.isPresent()).orElse(false), "Operador esperado", atual());
			var operador = consumir();
			
			esquerda = operador.tipo().infix.get().apply(this, esquerda);
		}
		
		return esquerda;
	}
	
	Expressao unidade() {
		var token = anterior().get();
		return switch (token.tipo()) {
			case NUMERO -> new NumeroLiteral(Double.valueOf(token.texto()));
			case TEXTO -> new TextoLiteral(token.texto());
			case TRUE -> BooleanoLiteral.VERDADEIRO;
			case FALSE -> BooleanoLiteral.FALSO;
			default -> throw new IllegalArgumentException();
		};
	}
	
	Expressao referencia() {
		throw new UnsupportedOperationException();
	}
	
	Expressao grupo() {
		var agrupado = expressao(Precedencia.NENHUMA);
		consumir(TipoDeToken.PARENTESEDIREITO, "Par�ntese direito esperado");
		
		return agrupado;
	}
	
	Expressao lista() {
		throw new UnsupportedOperationException();
	}
	
	Expressao operadorUnario() {
		var token = anterior().get();
		return switch (token.tipo()) {
			case NEGACAONUMERICA -> new ExpressaoNegacaoNumerica(expressao(Precedencia.EXPONENCIACAO)); 
			case NEGACAOLOGICA -> new ExpressaoNao(expressao(Precedencia.EXPONENCIACAO));
			default -> throw new IllegalArgumentException();
		};
	}
	
	Expressao operadorBinario(Expressao esquerda) {
		var token = anterior().get();
		return switch (token.tipo()) {
			
			case EXPONENCIACAO -> new ExpressaoExponenciacao(esquerda, expressao(Precedencia.MULTIPLICACAO));
			
			case MULTIPLICADO -> new ExpressaoMultiplicacao(esquerda, expressao(Precedencia.MULTIPLICACAO));
			case DIVIDIDO -> new ExpressaoDivisao(esquerda, expressao(Precedencia.MULTIPLICACAO));
			case MODULO -> new ExpressaoModulo(esquerda, expressao(Precedencia.MULTIPLICACAO));
			case MAIS -> new ExpressaoSoma(esquerda, expressao(Precedencia.SOMA));
			case NEGACAONUMERICA -> new ExpressaoSubtracao(esquerda, expressao(Precedencia.SOMA));
			case MAIOR -> new ExpressaoMaior(esquerda, expressao(Precedencia.COMPARACAO));
			case MENOR -> new ExpressaoMenor(esquerda, expressao(Precedencia.COMPARACAO));
			case MAIOROUIGUAL -> new ExpressaoMaiorOuIgual(esquerda, expressao(Precedencia.COMPARACAO)); 
			case MENOROUIGUAL -> new ExpressaoMenorOuIgual(esquerda, expressao(Precedencia.COMPARACAO));
			case IGUAL -> new ExpressaoIgual(esquerda, expressao(Precedencia.IGUALDADE));
			case DIFERENTE -> new ExpressaoDiferente(esquerda, expressao(Precedencia.IGUALDADE));
			case AND -> new ExpressaoE(esquerda, expressao(Precedencia.E));
			case OR -> new ExpressaoOu(esquerda, expressao(Precedencia.OU));
			default -> throw new IllegalArgumentException();
		};
	}

	Expressao se() {
		var condicoes = new ArrayList<Expressao>();
		var corpos = new ArrayList<Expressao>();
		
		final Runnable r = () -> {
			condicoes.add(expressao(Precedencia.NENHUMA));
			consumir(TipoDeToken.THEN, "Est� faltando o Then");
			corpos.add(expressao(Precedencia.NENHUMA));
			consumir(TipoDeToken.ELSE, "Est� faltando o Else");
		};
		
		r.run();
		
		while (atual().isPresent() && atual().get().tipo() == TipoDeToken.IF) {
			consumir();
			r.run();
		}

		corpos.add(expressao(Precedencia.NENHUMA));
		
		return new ExpressaoSeSenao(condicoes, corpos);
	}
	
	Expressao funcao() {
		throw new UnsupportedOperationException();
	}
	
	Expressao invocacao(Expressao esquerda) {
		throw new UnsupportedOperationException();
	}
	
	public static void main(String[] args) {
		var e = new Parser(Token.processar("If 1 = 3 Then 10 Else If 1 = 2 Then 20 Else If 1 = 1 Then 30 Else 40")).expressao(Precedencia.NENHUMA);
		var a = e.avaliar();
		var n = a.obterValorNativo();
		System.out.println(n);
	}
	
	private Optional<Token> atual() {
		if (indice >= tokens.size())
			return Optional.empty();
		
		return Optional.ofNullable(tokens.get(indice));
	}
	
	private Optional<Token> anterior() {
		if (indice == 0)
			return Optional.empty();
		
		return Optional.ofNullable(tokens.get(indice - 1));
	}
	
	private Token consumir() {
		var token = atual();
		asseverar(token.isPresent(), "", token);
		indice++;
		return token.get();
	}
	
	private Token consumir(TipoDeToken tipo, String erro) {
		var token = atual();
		asseverar(token.isPresent() && token.get().tipo() == tipo, erro, token);
		return consumir();
	}
	
	private Precedencia precedencia() {
		return atual().map(token -> token.tipo().precedenciaInfix).orElse(Precedencia.NENHUMA);
	}
	
	private static void asseverar(boolean condicao, String mensagem, Optional<Token> token) {
		
		if (condicao)
			return;
		
		var complemento = token.isPresent() ? "Linha: " + token.get().linha() + ": " : "Fim do arquivo: ";
		
		Testes.asseverar(condicao, complemento + mensagem);
	}
}