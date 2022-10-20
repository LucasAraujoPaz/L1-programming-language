import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

enum TipoDeToken {
    NUMERO("\\d+(\\.\\d+)?", Token.Numero::new),
    TEXTO("\"(?:\\\\\"|[^\"])*\"", Token.Texto::new),
    IDENTIFICADOR("[a-zA-Z_][a-zA-Z_\\d]*", Token.Identificador::new),

    DEFINIDO_COMO(":=", Token.DefinidoComo::new),
    MAIOR_OU_IGUAL(">=", Token.MaiorOuIgual::new),
    MENOR_OU_IGUAL("<=", Token.MenorOuIgual::new),
    DIFERENTE("!=", Token.Diferente::new),
    EXPONENCIACAO("\\*\\*", Token.Exponenciacao::new),
    SETA_FINA("->", Token.SetaFina::new),

    PARENTESE_ESQUERDO("\\(", Token.ParenteseEsquerdo::new),
    PARENTESE_DIREITO("\\)", Token.ParenteseDireito::new),
    COLCHETE_ESQUERDO("\\[", Token.ColcheteEsquerdo::new),
    COLCHETE_DIREITO("\\]", Token.ColcheteDireito::new),
    NEGACAO_NUMERICA("-", Token.NegacaoNumerica::new),
    NEGACAO_LOGICA("!", Token.NegacaoLogica::new),
    MULTIPLICADO("\\*", Token.Multiplicado::new),
    DIVIDIDO("/", Token.Dividido::new),
    MODULO("%", Token.Modulo::new),
    MAIS("\\+", Token.Mais::new),
    MAIOR(">", Token.Maior::new),
    MENOR("<", Token.Menor::new),
    IGUAL("=", Token.Igual::new),
    VIRGULA(",", Token.Virgula::new),
    DOIS_PONTOS(":", Token.DoisPontos::new),
    PONTO("\\.", Token.Ponto::new),
    WHITESPACE("\\s+", Token.Whitespace::new),
    ERRO("\\S+", Token.Erro::new);
	
	String regex;
	BiFunction<String, Integer, Token> gerarToken;
	
	private TipoDeToken(String regex, BiFunction<String, Integer, Token> getToken) {
		this.regex = regex;
		this.gerarToken = getToken;
	}
}

public interface Token {
	record Numero(String texto, int linha) implements Token {}
	record Texto(String texto, int linha) implements Token {}
	record Identificador(String texto, int linha) implements Token {}
	record DefinidoComo(String texto, int linha) implements Token {}
	record MaiorOuIgual(String texto, int linha) implements Token {}
	record MenorOuIgual(String texto, int linha) implements Token {}
	record Diferente(String texto, int linha) implements Token {}
	record Exponenciacao(String texto, int linha) implements Token {}
	record SetaFina(String texto, int linha) implements Token {}
	record ParenteseEsquerdo(String texto, int linha) implements Token {}
	record ParenteseDireito(String texto, int linha) implements Token {}
	record ColcheteEsquerdo(String texto, int linha) implements Token {}
	record ColcheteDireito(String texto, int linha) implements Token {}
	record NegacaoNumerica(String texto, int linha) implements Token {}
	record NegacaoLogica(String texto, int linha) implements Token {}
	record Multiplicado(String texto, int linha) implements Token {}
	record Dividido(String texto, int linha) implements Token {}
	record Modulo(String texto, int linha) implements Token {}
	record Mais(String texto, int linha) implements Token {}
	record Maior(String texto, int linha) implements Token {}
	record Menor(String texto, int linha) implements Token {}
	record Igual(String texto, int linha) implements Token {}
	record Virgula(String texto, int linha) implements Token {}
	record DoisPontos(String texto, int linha) implements Token {}
	record Ponto(String texto, int linha) implements Token {}
	record Whitespace(String texto, int linha) implements Token {}
	record Erro(String texto, int linha) implements Token {
		public Erro(String texto, int linha) {
			throw new RuntimeException("Símbolo inesperado na linha " + linha + ": \"" + texto + "\"");
		}
	}
	
	String texto();
	int linha();
	
	final Pattern pattern = Pattern.compile(Stream.of(TipoDeToken.values())
			.map(tipoDeToken -> "(?<" + tipoDeToken.name() + ">" + tipoDeToken.regex + ")")
			.collect(Collectors.joining("|")));
	
	static ArrayList<Token> processar(final String codigoFonte) {
		
		final var lg = new LineGetter(codigoFonte);

		final ArrayList<Token> tokens = new ArrayList<>();
		final var m = pattern.matcher(codigoFonte);

		find:
		while (m.find()) {
			for (final var tipoDeToken : TipoDeToken.values()) {
				final String group = m.group(tipoDeToken.name());
				if (group == null) 
					continue;
				if (group.equals(TipoDeToken.WHITESPACE.name()))
					continue find;
				final int linha = lg.obterLinha(m.start());
				final Token token = tipoDeToken.gerarToken.apply(group, linha);
				tokens.add(token);
				continue find;
			}
			throw new RuntimeException("Erro processando símbolo no índice " + m.start());
		}
		
		return tokens;
	}
}

class LineGetter { 
	private final String codigoFonte; 
	private int linha = 1, indice = 0;
	
	public LineGetter(final String codigoFonte) {
		this.codigoFonte = codigoFonte;
	}
	
	int obterLinha(final int start) {
		final int limite = Math.min(start, codigoFonte.length());
		while (indice < limite) {
			if (codigoFonte.charAt(indice) == Character.LINE_SEPARATOR)
				++linha;
			++indice;
		}
		return linha;
	} 
}