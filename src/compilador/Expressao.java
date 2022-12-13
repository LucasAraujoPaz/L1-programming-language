package compilador;
interface Expressao {
	public Valor avaliar();
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
	}

	@Override
	public Valor avaliar() {
		return switch(token.tipo()) {
			case NOT -> ! (Boolean) expressao.avaliar().obterValorNativo() ? ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
			case MENOS -> new NumeroReal( - (Double) expressao.avaliar().obterValorNativo());
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
}