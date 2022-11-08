import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Constantes sem underline por conta do Regex do Java */
enum TipoDeToken {
    NUMERO("\\d+(\\.\\d+)?"),
    TEXTO("\"(?:\\\\\"|[^\"])*(?<!\\\\)\""),
    IDENTIFICADOR("[a-zA-Z_][a-zA-Z_\\d]*"),

    DEFINIDOCOMO(":="),
    MAIOROUIGUAL(">="),
    MENOROUIGUAL("<="),
    DIFERENTE("!="),
    EXPONENCIACAO("\\*\\*"),
    SETAFINA("->"),

    PARENTESEESQUERDO("\\("),
    PARENTESEDIREITO("\\)"),
    COLCHETEESQUERDO("\\["),
    COLCHETEDIREITO("\\]"),
    NEGACAONUMERICA("-"),
    NEGACAOLOGICA("!"),
    MULTIPLICADO("\\*"),
    DIVIDIDO("\\/"),
    MODULO("%"),
    MAIS("\\+"),
    MAIOR(">"),
    MENOR("<"),
    IGUAL("="),
    VIRGULA(","),
    DOISPONTOS(":"),
    PONTO("\\."),
    WHITESPACE("\\s+"),
    ERRO("\\S+");
	
	final String regex;
	
	TipoDeToken(String regex) {
		this.regex = regex;
	}
}

enum PalavraReservada {
    NUMBER_TYPE("Number"),
    BOOLEAN_TYPE("Boolean"),
    STRING_TYPE("String"),
    ARRAY_TYPE("Array"),
    FUNCTION_TYPE("Function"),
    ANY_TYPE("Any"),
    LET("Let"),
    TRUE("True"),
    FALSE("False"),
    AND("And"),
    OR("Or"),
    IF("If"),
    THEN("Then"),
    ELSE("Else"),
    END("End");
	
	final String keyword;
	
	private static final Map<String, PalavraReservada> stringParaEnum = 
		Stream.of(PalavraReservada.values())
		.collect(Collectors.toUnmodifiableMap(v -> v.keyword , Function.identity())
	);
	
	static Optional<PalavraReservada> obterPalavraReservada(String s) {
		return Optional.ofNullable(stringParaEnum.get(s));
	}
	
	PalavraReservada(String keyword) {
		this.keyword = keyword;
	}
}

public interface Token {
	
	TipoDeToken tipo();
	String texto();
	int linha();
	Optional<PalavraReservada> palavraReservada();
	
	record TokenImpl(
			TipoDeToken tipo,
			String texto, 
			int linha, 
			Optional<PalavraReservada> palavraReservada) implements Token {
		
		public TokenImpl(TipoDeToken tipo, String texto, int linha) {
			this(
					tipo,
					tipo == TipoDeToken.TEXTO ? texto.substring(1, texto.length() - 1) : texto, 
					linha, 
					PalavraReservada.obterPalavraReservada(texto)
			);
			Testes.asseverar(tipo != TipoDeToken.ERRO, 
					"Símbolo inesperado na linha " + linha + ": \"" + texto + "\"");
		}		
	}
	
	final Pattern pattern = Pattern.compile(
			Stream.of(TipoDeToken.values())
			.map(tipoDeToken -> "(?<" + tipoDeToken.name() + ">" + tipoDeToken.regex + ")")
			.collect(Collectors.joining("|")));
	
	public static ArrayList<Token> processar(final String codigoFonte) {
		
		final var lg = new LineGetter(codigoFonte);

		final ArrayList<Token> tokens = new ArrayList<>();
		final var m = pattern.matcher(codigoFonte);

		while (m.find()) {
			Optional<Token> token = obterToken(m, lg);
			if (token.isPresent())
				tokens.add(token.get());
		}
		
		return tokens;
	}
	
	private static Optional<Token> obterToken(final Matcher m, final LineGetter lg) {
		for (final var tipoDeToken : TipoDeToken.values()) {
			final String group = m.group(tipoDeToken.name());
			if (group == null) 
				continue;
			if (tipoDeToken.name().equals(TipoDeToken.WHITESPACE.name()))
				return Optional.empty();
			final int linha = lg.obterLinha(m.start());
			final Token token = new TokenImpl(tipoDeToken, group, linha);
			return Optional.of(token);
		}
		Testes.asseverar(false, "Erro processando símbolo no índice " + m.start());
		return Optional.empty();
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
			if (codigoFonte.charAt(indice) == '\n')
				++linha;
			++indice;
		}
		return linha;
	} 
}