package compilador;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;

public class Parser {

	private final ArrayList<Token> tokens;
	private int indice = 0;
	private Contexto contexto = new Contexto(Optional.empty(), Optional.empty());
	
	private Parser(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}
	
	public static void main(String[] args) {
		rodar("Teste",
"""
Let number := 3.14.
Let boolean := True Or False.
Let string := "String".
Let array := [1, 2, 3].
Let factorial := 
	Function(Number x) -> Number:
		If x < 2 Then
			1
		Else
			x * factorial(x - 1)
		End
	End
.

Let main := Function(String x) -> Number:
	factorial(10)
End.
""");
	}

	public static Declaracao checar(final String codigoFonte) {
		var tokens = Token.processar(codigoFonte);
		var parser = new Parser(tokens);
		var programa = parser.programa();
		asseverar(programa.size() > 0, "É necessário declarar a função main", Optional.empty());
		var declaracaoMain = programa.get(programa.size() - 1);
		var funcaoMain = declaracaoMain.expressao;
		asseverar(declaracaoMain.token.texto().equals("main") && funcaoMain instanceof Funcao, 
				"A última declaração do arquivo deve ser a função \"main\"", Optional.empty());
		return declaracaoMain;
	}
	
	public static String rodar(final String input, final String codigoFonte) {
		var declaracaoMain = checar(codigoFonte);
		var closureMain = (Closure) declaracaoMain.avaliar();
		var resultado = closureMain.aplicar(new TextoLiteral(input));
		var textualizado = resultado.obterValorNativo().toString();
		System.out.println(textualizado);
		return textualizado;
	}
	
	private ArrayList<Declaracao> programa() {
		var declaracoes = new ArrayList<Declaracao>();
		
		while (atual().isPresent())
			declaracoes.add(declaracao());
		
		return declaracoes;
	}
	
	private Declaracao declaracao() {
		consumir(TipoDeToken.LET, "Declarações começam com Let");
		
		var identificador = consumir(TipoDeToken.IDENTIFICADOR, "Declaração necessita de identificador");
		asseverar( ! identificador.tipo().ehPalavraReservada(), "Nome declarado não pode ser palavra reservada", Optional.of(identificador));

		consumir(TipoDeToken.DEFINIDO_COMO, "Declaração necessita de :=");
		
		final Expressao expressao;
		final Declaracao declaracao;
		if (atual().isPresent() && atual().get().tipo() == TipoDeToken.FUNCTION) {
			consumir();
			var funcao = cabecalhoDeFuncao();
			declaracao = contexto.declarar(identificador, funcao);
			funcao.corpo = corpoDeFuncao(funcao);
			expressao = funcao;
		} else {
			expressao = expressao(Precedencia.NENHUMA);
			declaracao = contexto.declarar(identificador, expressao);
		}
		
		consumir(TipoDeToken.PONTO, "Declaração encerra com .");
		
		return declaracao;
	}

	private Expressao expressao(Precedencia precedencia) {
		
		asseverar(atual().map(t -> t.tipo().prefix.isPresent()).orElse(false), "Expressão esperada", atual());
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
		return contexto.obter(anterior().get());
	}
	
	Expressao grupo() {
		var agrupado = expressao(Precedencia.NENHUMA);
		consumir(TipoDeToken.PARENTESE_DIREITO, "Parêntese direito esperado");
		
		return agrupado;
	}
	
	Expressao lista() {
		final var lista = new LinkedList<Expressao>();
		while (atual().map(token -> token.tipo() != TipoDeToken.COLCHETE_DIREITO).orElse(false)) {
			var expressao = expressao(Precedencia.NENHUMA);
			lista.add(expressao);
			if (atual().map(token -> token.tipo() == TipoDeToken.VIRGULA).orElse(false))
				consumir();
		}
		consumir(TipoDeToken.COLCHETE_DIREITO, "Colchete direito esperado para fechar lista");
		return new ListaLiteral(lista);
	}
	
	Expressao operadorUnario() {
		var token = anterior().get();
		return switch (token.tipo()) {
			case NEGACAO_NUMERICA -> new ExpressaoNegacaoNumerica(expressao(Precedencia.EXPONENCIACAO)); 
			case NEGACAO_LOGICA -> new ExpressaoNao(expressao(Precedencia.EXPONENCIACAO));
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
			case NEGACAO_NUMERICA -> new ExpressaoSubtracao(esquerda, expressao(Precedencia.SOMA));
			case MAIOR -> new ExpressaoMaior(esquerda, expressao(Precedencia.COMPARACAO));
			case MENOR -> new ExpressaoMenor(esquerda, expressao(Precedencia.COMPARACAO));
			case MAIOR_OU_IGUAL -> new ExpressaoMaiorOuIgual(esquerda, expressao(Precedencia.COMPARACAO)); 
			case MENOR_OU_IGUAL -> new ExpressaoMenorOuIgual(esquerda, expressao(Precedencia.COMPARACAO));
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
			consumir(TipoDeToken.THEN, "Está faltando o Then");
			corpos.add(expressao(Precedencia.NENHUMA));
			consumir(TipoDeToken.ELSE, "Está faltando o Else");
		};
		
		r.run();
		
		while (atual().isPresent() && atual().get().tipo() == TipoDeToken.IF) {
			consumir();
			r.run();
		}

		corpos.add(expressao(Precedencia.NENHUMA));
		
		consumir(TipoDeToken.END, "End esperado após expressão condicional");
		
		return new ExpressaoSeSenao(condicoes, corpos);
	}

	private FuncaoLiteral cabecalhoDeFuncao() {
		consumir(TipoDeToken.PARENTESE_ESQUERDO, "Parêntese esquerdo necessário depois de Function");
		var tipoDoParametro = tipo();
		var parametro = consumir(TipoDeToken.IDENTIFICADOR, "Nome do parâmetro esperado");
		consumir(TipoDeToken.PARENTESE_DIREITO, "Parêntese direito necessário depois do parâmetro");
		consumir(TipoDeToken.SETA_FINA, "Use seta para indicar o retorno da função");
		var tipoDoRetorno = tipo();
		consumir(TipoDeToken.DOIS_PONTOS, "Use dois pontos \":\" antes de começar o corpo da função");
		
		return new FuncaoLiteral(new Parametro(parametro.texto()), null, new ArrayList<>());
	}
	
	private Expressao corpoDeFuncao(Funcao funcao) {
		this.contexto = new Contexto(Optional.ofNullable(this.contexto), Optional.ofNullable(funcao));
		var corpo = expressao(Precedencia.NENHUMA);
		this.contexto = this.contexto.pai.get();
		consumir(TipoDeToken.END, "End esperado como término da função");
		return corpo;
	}
	
	Expressao funcao() {
		var funcao = cabecalhoDeFuncao();
		funcao.corpo = corpoDeFuncao(funcao);
		return funcao; 
	}

	// TODO
	private Tipo tipo() { 
		asseverar(atual().isPresent(), "Tipo esperado", atual());
		final var token = consumir();
		var tipos = Set.of("Number", "Boolean", "String", "Array", "Function", "Any");
		asseverar(tipos.contains(token.texto()), "Tipo inválido", Optional.ofNullable(token));
		
		Tipo retorno = switch (token.texto()) {
			case "Number" -> Tipo.NUMERO;
			case "Boolean" -> Tipo.BOOLEANO;
			case "String" -> Tipo.TEXTO;
			case "Array" -> new Tipo.Lista();
			case "Function" -> new Tipo.Funcao();
			case "Any" -> Tipo.QUALQUER;
			default -> throw new IllegalArgumentException();
		};
		
		return retorno;
	}

	Expressao invocacao(Expressao esquerda) {
		var argumento = expressao(Precedencia.NENHUMA);
		consumir(TipoDeToken.PARENTESE_DIREITO, "Use parêntese direito após argumento da invocação");
		return new InvocacaoImpl(esquerda, argumento);
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
	
	static void asseverar(boolean condicao, String mensagem, Optional<Token> token) {
		
		if (condicao)
			return;
		
		var complemento = token.isPresent() ? "Linha " + token.get().linha() + ": " : "Fim do arquivo: ";
		
		Testes.asseverar(condicao, complemento + mensagem);
	}
}