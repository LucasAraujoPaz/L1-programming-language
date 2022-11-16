import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Constantes sem underline por conta do Regex do Java */
enum TipoDeToken {
    NUMERO("\\d+(\\.\\d+)?", Parser::unidade, null, Precedencia.NENHUMA),
    TEXTO("\"(?:\\\\\"|[^\"])*(?<!\\\\)\"", Parser::unidade, null, Precedencia.NENHUMA),
    IDENTIFICADOR("[a-zA-Z_][a-zA-Z_\\d]*", Parser::referencia, null, Precedencia.NENHUMA),

    EXPONENCIACAO("\\*\\*", null, Parser::operadorBinario, Precedencia.EXPONENCIACAO),
    MAIOROUIGUAL(">=", null, Parser::operadorBinario, Precedencia.COMPARACAO),
    MENOROUIGUAL("<=", null, Parser::operadorBinario, Precedencia.COMPARACAO),
    DIFERENTE("!=", null, Parser::operadorBinario, Precedencia.IGUALDADE),
    DEFINIDOCOMO(":=", null, null, Precedencia.NENHUMA),
    SETAFINA("->", null, null, Precedencia.NENHUMA),

    PARENTESEESQUERDO("\\(", Parser::grupo, Parser::invocacao, Precedencia.INVOCACAO),
    PARENTESEDIREITO("\\)", null, null, Precedencia.NENHUMA),
    COLCHETEESQUERDO("\\[", Parser::lista, null, Precedencia.NENHUMA),
    COLCHETEDIREITO("\\]", null, null, Precedencia.NENHUMA),
    NEGACAONUMERICA("-", Parser::operadorUnario, Parser::operadorBinario, Precedencia.SOMA),
    NEGACAOLOGICA("!", Parser::operadorUnario, null, Precedencia.NENHUMA),
    MULTIPLICADO("\\*", null, Parser::operadorBinario, Precedencia.MULTIPLICACAO),
    DIVIDIDO("\\/", null, Parser::operadorBinario, Precedencia.MULTIPLICACAO),
    MODULO("%", null, Parser::operadorBinario, Precedencia.MULTIPLICACAO),
    MAIS("\\+", null, Parser::operadorBinario, Precedencia.SOMA),
    MAIOR(">", null, Parser::operadorBinario, Precedencia.COMPARACAO),
    MENOR("<", null, Parser::operadorBinario, Precedencia.COMPARACAO),
    IGUAL("=", null, Parser::operadorBinario, Precedencia.IGUALDADE),
    VIRGULA(",", null, null, Precedencia.NENHUMA),
    DOISPONTOS(":", null, null, Precedencia.NENHUMA),
    PONTO("\\.", null, null, Precedencia.NENHUMA),
    
    WHITESPACE("\\s+", null, null, Precedencia.NENHUMA),
    /** "Erro" precisa estar nesta posição para separar IDENTIFICADOR das keywords */
    ERRO("\\S+", null, null, Precedencia.NENHUMA),
    
    NUMBER("Number", null, null, Precedencia.NENHUMA),
    BOOLEAN("Boolean", null, null, Precedencia.NENHUMA),
    STRING("String", null, null, Precedencia.NENHUMA),
    ARRAY("Array", null, null, Precedencia.NENHUMA),
    FUNCTION("Function", Parser::funcao, null, Precedencia.NENHUMA),
    ANY("Any", null, null, Precedencia.NENHUMA),
    LET("Let", null, null, Precedencia.NENHUMA),
    TRUE("True", Parser::unidade, null, Precedencia.NENHUMA),
    FALSE("False", Parser::unidade, null, Precedencia.NENHUMA),
    AND("And", null, Parser::operadorBinario, Precedencia.E),
    OR("Or", null, Parser::operadorBinario, Precedencia.OU),
    IF("If", Parser::se, null, Precedencia.NENHUMA),
    THEN("Then", null, null, Precedencia.NENHUMA),
    ELSE("Else", null, null, Precedencia.NENHUMA),
    END("End", null, null, Precedencia.NENHUMA);
	
	final String regex;
	final Optional<Function<Parser, Expressao>> prefix;
	final Optional<BiFunction<Parser, Expressao, Expressao>> infix;
	final Precedencia precedenciaInfix; 
	boolean ehPalavraReservada() {
		return tiposDeTokenReservados.contains(this);
	};

	TipoDeToken(
			String regex, 
			Function<Parser, Expressao> prefix,
			BiFunction<Parser, Expressao, Expressao> infix,
			Precedencia precedenciaInfix) {
		this.regex = regex;
		this.prefix = Optional.ofNullable(prefix);
		this.infix = Optional.ofNullable(infix);
		this.precedenciaInfix = precedenciaInfix;
	}
	
	private static Set<TipoDeToken> tiposDeTokenReservados = 
			Set.of(NUMBER, BOOLEAN, STRING, ARRAY, FUNCTION, ANY, LET, TRUE, FALSE, AND, OR, IF, THEN, ELSE, END);
	
	static TipoDeToken obterPossivelPalavraReservada(TipoDeToken tipoOriginal, String texto) {
		if (tipoOriginal != IDENTIFICADOR)
			return tipoOriginal;
		
		for (var tipoDeToken : tiposDeTokenReservados)
			if (tipoDeToken.regex.equals(texto))
				return tipoDeToken;
		
		return tipoOriginal;
	}
	
}

enum Precedencia {
	NENHUMA, OU, E, IGUALDADE, COMPARACAO, SOMA, MULTIPLICACAO, EXPONENCIACAO, UNARIO, INVOCACAO,
}

public interface Token {
	
	TipoDeToken tipo();
	String texto();
	int linha();
	
	record TokenImpl(
			TipoDeToken tipo,
			String texto, 
			int linha) implements Token {
		
		public TokenImpl(TipoDeToken tipo, String texto, int linha) {
			this.tipo = TipoDeToken.obterPossivelPalavraReservada(tipo, texto);
			this.texto = tipo == TipoDeToken.TEXTO ? texto.substring(1, texto.length() - 1) : texto;
			this.linha = linha;
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