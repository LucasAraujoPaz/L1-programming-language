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
		consumir(TipoDeToken.LET, "Declarações começam com Let");
		
		var nome = consumir(TipoDeToken.IDENTIFICADOR, "Declaração necessita de identificador");
		asseverar( ! nome.tipo().ehPalavraReservada(), "Nome declarado não pode ser palavra reservada", Optional.of(nome));
		
		consumir(TipoDeToken.DEFINIDOCOMO, "Declaração necessita de :=");
		var expressao = expressao(0);
		
		consumir(TipoDeToken.PONTO, "Declaração encerra com .");
		
		return new Declaracao(nome, expressao);
	}
	
	private Expressao expressao(int precedencia) {
		
		Token tokenEsquerdo = consumir("Expressão esperada");
		asseverar(tokenEsquerdo.tipo().prefix.isPresent(), "Expressão esperada", Optional.ofNullable(tokenEsquerdo));
		
		Expressao esquerda = tokenEsquerdo.tipo().prefix.get().apply(this);
		
		while (precedencia < precedencia()) {
			var operador = consumir("Operador esperado");
			asseverar(operador.tipo().infix.isPresent(), "Operador infixo esperado", Optional.ofNullable(operador));
			esquerda = operador.tipo().infix.get().apply(this, esquerda);
		}
		
		return esquerda;
	}
	
	Expressao unidade() {
		throw new UnsupportedOperationException();
	}
	
	Expressao referencia() {
		throw new UnsupportedOperationException();
	}
	
	Expressao grupo() {
		throw new UnsupportedOperationException();
	}
	
	Expressao lista() {
		throw new UnsupportedOperationException();
	}
	
	Expressao operadorUnario() {
		throw new UnsupportedOperationException();
	}
	
	Expressao operadorBinario(Expressao esquerda) {
		throw new UnsupportedOperationException();
	}

	Expressao se() {
		throw new UnsupportedOperationException();
	}
	
	Expressao funcao() {
		throw new UnsupportedOperationException();
	}
	
	Expressao invocacao(Expressao esquerda) {
		throw new UnsupportedOperationException();
	}
	
	private Optional<Token> atual() {
		if (indice >= tokens.size())
			return Optional.empty();
		
		return Optional.ofNullable(tokens.get(indice));
	}
	
	private Token consumir(String erro) {
		
		asseverar(atual().isPresent(), erro, atual());
		
		var token = atual().get();
		indice++;
		return token;
	}
	
	private Token consumir(TipoDeToken tipo, String erro) {
		
		asseverar(atual().isPresent() && atual().get().tipo() == tipo, erro, atual());
		
		return consumir(erro);
	}
	
	private int precedencia() {
		return atual().map(token -> token.tipo().precedencia.ordinal()).orElse(Precedencia.NENHUMA.ordinal());
	}
	
	private static void asseverar(boolean condicao, String mensagem, Optional<Token> token) {
		
		if (condicao)
			return;
		
		var complemento = token.isPresent() ? "Linha: " + token.get().linha() + ": " : "Fim do arquivo: ";
		
		Testes.asseverar(condicao, complemento + mensagem);
	}
}