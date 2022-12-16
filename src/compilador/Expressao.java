package compilador;

import java.util.Optional;

interface Expressao {
	public Valor avaliar();
	public Tipo obterTipo();
}

interface Valor {
	public Object obterValorNativo();
}

class OperadorUnario implements Expressao {
	final Token token;
	final Expressao expressao;

	OperadorUnario(final Token token, final Expressao expressao) {
		this.token = token;
		this.expressao = expressao;
		switch(token.tipo()) {
			case NOT -> Parser.asseverar(expressao.obterTipo().equals(Tipo.BOOLEANO), 
					"Só booleanos aceitam negação lógica", Optional.ofNullable(token));
			case MENOS -> Parser.asseverar(expressao.obterTipo().equals(Tipo.NUMERO), 
					"Só números aceitam negação numérica", Optional.ofNullable(token));
			default -> throw new IllegalArgumentException();
		};
	}

	@Override
	public Valor avaliar() {
		return switch(token.tipo()) {
			case NOT -> ! (Boolean) expressao.avaliar().obterValorNativo() ? ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
			case MENOS -> new NumeroReal( - (Double) expressao.avaliar().obterValorNativo());
			default -> throw new IllegalArgumentException();
		};
	}

	@Override
	public Tipo obterTipo() {
		return switch(token.tipo()) {
			case NOT -> Tipo.BOOLEANO;
			case MENOS -> Tipo.NUMERO;
			default -> throw new IllegalArgumentException();
		};
	}
}

class OperadorBinario implements Expressao {
	final Expressao esquerda;
	final Token token;
	final Expressao direita;

	OperadorBinario(final Expressao esquerda, final Token token, final Expressao direita) {
		this.token = token;
		this.esquerda = esquerda;
		this.direita = direita;
		switch (token.tipo()) {
			case EXPONENCIACAO, MULTIPLICADO, DIVIDIDO, MODULO, MAIS, MENOS, MAIOR, MENOR, MAIOR_OU_IGUAL, MENOR_OU_IGUAL -> 
				Parser.asseverar(esquerda.obterTipo().equals(Tipo.NUMERO) && direita.obterTipo().equals(Tipo.NUMERO), 
						"Só números aceitam operadores aritméticos", Optional.ofNullable(token));
			case IGUAL, DIFERENTE -> {}
			case AND, OR -> 
				Parser.asseverar(esquerda.obterTipo().equals(Tipo.BOOLEANO) && direita.obterTipo().equals(Tipo.BOOLEANO), 
						"Só booleanos aceitam operadores lógicos", Optional.ofNullable(token));
			default -> throw new IllegalArgumentException();
		};
	}

	@Override
	public Valor avaliar() {
		return switch (token.tipo()) {
			
			case EXPONENCIACAO -> new NumeroReal(Math.pow(
					(Double) esquerda.avaliar().obterValorNativo(), (Double) direita.avaliar().obterValorNativo()));
			
			case MULTIPLICADO -> new NumeroReal((Double) esquerda.avaliar().obterValorNativo() * (Double) direita.avaliar().obterValorNativo());
			case DIVIDIDO -> new NumeroReal((Double) esquerda.avaliar().obterValorNativo() / (Double) direita.avaliar().obterValorNativo());
			case MODULO -> new NumeroReal((Double) esquerda.avaliar().obterValorNativo() % (Double) direita.avaliar().obterValorNativo());
			case MAIS -> new NumeroReal((Double) esquerda.avaliar().obterValorNativo() + (Double) direita.avaliar().obterValorNativo());
			case MENOS -> new NumeroReal((Double) esquerda.avaliar().obterValorNativo() - (Double) direita.avaliar().obterValorNativo());
			case MAIOR -> (Double) esquerda.avaliar().obterValorNativo() > (Double) direita.avaliar().obterValorNativo() ? 
					ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
			case MENOR -> (Double) esquerda.avaliar().obterValorNativo() < (Double) direita.avaliar().obterValorNativo() ? 
					ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
			case MAIOR_OU_IGUAL -> (Double) esquerda.avaliar().obterValorNativo() >= (Double) direita.avaliar().obterValorNativo() ? 
					ValorBooleano.VERDADEIRO : ValorBooleano.FALSO; 
			case MENOR_OU_IGUAL -> (Double) esquerda.avaliar().obterValorNativo() <= (Double) direita.avaliar().obterValorNativo() ? 
					ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
			case IGUAL -> esquerda.avaliar().obterValorNativo().equals(direita.avaliar().obterValorNativo()) ?
					ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
			case DIFERENTE -> ! esquerda.avaliar().obterValorNativo().equals(direita.avaliar().obterValorNativo()) ?
					ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
			case AND -> (Boolean) esquerda.avaliar().obterValorNativo() && (Boolean) direita.avaliar().obterValorNativo() ? 
					ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
			case OR -> (Boolean) esquerda.avaliar().obterValorNativo() || (Boolean) direita.avaliar().obterValorNativo() ? 
					ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
			default -> throw new IllegalArgumentException();
		};
	}

	@Override
	public Tipo obterTipo() {
		return switch (token.tipo()) {
			case EXPONENCIACAO, MULTIPLICADO, DIVIDIDO, MODULO, MAIS, MENOS -> Tipo.NUMERO;
			case MAIOR, MENOR, MAIOR_OU_IGUAL, MENOR_OU_IGUAL, IGUAL, DIFERENTE, AND, OR -> Tipo.BOOLEANO;
			default -> throw new IllegalArgumentException();
		};
	}
}