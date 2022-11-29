package compilador;

public interface Booleano extends Expressao {
	
	@Override
	ValorBooleano avaliar();	
}

class BooleanoLiteral implements Booleano {
	public static final BooleanoLiteral VERDADEIRO = new BooleanoLiteral() {};
	public static final BooleanoLiteral FALSO = new BooleanoLiteral() {};
	
	@Override
	public ValorBooleano avaliar() {
		return this.equals(VERDADEIRO) ? 
			ValorBooleano.VERDADEIRO : ValorBooleano.FALSO; 
	}
}

class ValorBooleano implements Valor {
	public static final ValorBooleano VERDADEIRO = new ValorBooleano(true) {};
	public static final ValorBooleano FALSO = new ValorBooleano(false) {};
	
	boolean valor;
	private ValorBooleano(boolean valor) {
		this.valor = valor;
	}
	@Override
	public Boolean obterValorNativo() {
		return valor;
	}
}

class ExpressaoIgual implements Booleano {

	final Expressao esquerda, direita;

	ExpressaoIgual(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public ValorBooleano avaliar() {
		return esquerda.avaliar().obterValorNativo().equals(direita.avaliar().obterValorNativo()) ?
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}

class ExpressaoDiferente implements Booleano {

	final Expressao esquerda, direita;

	ExpressaoDiferente(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}
	
	@Override
	public ValorBooleano avaliar() {
		return ! esquerda.avaliar().obterValorNativo().equals(direita.avaliar().obterValorNativo()) ?
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}

class ExpressaoMaior implements Booleano {

	final Expressao esquerda, direita;

	ExpressaoMaior(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public ValorBooleano avaliar() {
		return (Double) esquerda.avaliar().obterValorNativo() > (Double) direita.avaliar().obterValorNativo() ? 
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}

class ExpressaoMenor implements Booleano {

	final Expressao esquerda, direita;

	ExpressaoMenor(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public ValorBooleano avaliar() {
		return (Double) esquerda.avaliar().obterValorNativo() < (Double) direita.avaliar().obterValorNativo() ? 
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}

class ExpressaoMaiorOuIgual implements Booleano {

	final Expressao esquerda, direita;

	ExpressaoMaiorOuIgual(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public ValorBooleano avaliar() {
		return (Double) esquerda.avaliar().obterValorNativo() >= (Double) direita.avaliar().obterValorNativo() ? 
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}

class ExpressaoMenorOuIgual implements Booleano {

	final Expressao esquerda, direita;

	ExpressaoMenorOuIgual(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public ValorBooleano avaliar() {
		return (Double) esquerda.avaliar().obterValorNativo() <= (Double) direita.avaliar().obterValorNativo() ? 
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}

class ExpressaoNao implements Booleano {

	final Expressao negado;

	ExpressaoNao(final Expressao negado) {
		this.negado = negado;
	}

	@Override
	public ValorBooleano avaliar() {
		return ! (Boolean) negado.avaliar().obterValorNativo() ? 
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}

class ExpressaoE implements Booleano {

	final Expressao esquerda, direita;

	ExpressaoE(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public ValorBooleano avaliar() {
		return (Boolean) esquerda.avaliar().obterValorNativo() && (Boolean) direita.avaliar().obterValorNativo() ? 
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}

class ExpressaoOu implements Booleano {

	final Expressao esquerda, direita;

	ExpressaoOu(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}
	
	@Override
	public ValorBooleano avaliar() {
		return (Boolean) esquerda.avaliar().obterValorNativo() || (Boolean) direita.avaliar().obterValorNativo() ? 
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}