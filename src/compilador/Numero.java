package compilador;

public interface Numero extends Expressao {

	@Override
	NumeroReal avaliar();
}

class NumeroReal implements Valor {
	final double numero;
	public NumeroReal(double numero) {
		this.numero = numero;
	}
	@Override
	public Double obterValorNativo() {
		return numero;
	}
}

class NumeroLiteral implements Numero {

	final double numero;
	
	public NumeroLiteral(final double numero) {
		this.numero = numero;
	}
	
	@Override
	public NumeroReal avaliar() {
		return new NumeroReal(numero);
	}
}

class ExpressaoNegacaoNumerica implements Numero {

	final Expressao numero;

	ExpressaoNegacaoNumerica(final Expressao numero) {
		this.numero = numero;
	}

	@Override
	public NumeroReal avaliar() {
		return new NumeroReal( - (Double) numero.avaliar().obterValorNativo());
	}
}

class ExpressaoExponenciacao implements Numero {

	final Expressao esquerda, direita;

	ExpressaoExponenciacao(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public NumeroReal avaliar() {
		return new NumeroReal(Math.pow(
				(Double) esquerda.avaliar().obterValorNativo(),
				(Double) direita.avaliar().obterValorNativo())
		);
	}
}

class ExpressaoMultiplicacao implements Numero {

	final Expressao esquerda, direita;

	ExpressaoMultiplicacao(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}
	
	@Override
	public NumeroReal avaliar() {
		return new NumeroReal(
				(Double) esquerda.avaliar().obterValorNativo() * 
				(Double) direita.avaliar().obterValorNativo()
		);
	}
}

class ExpressaoDivisao implements Numero {

	final Expressao esquerda, direita;

	ExpressaoDivisao(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public NumeroReal avaliar() {
		return new NumeroReal(
				(Double) esquerda.avaliar().obterValorNativo() / 
				(Double) direita.avaliar().obterValorNativo()
		);
	}
}

class ExpressaoModulo implements Numero {

	final Expressao esquerda, direita;

	ExpressaoModulo(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public NumeroReal avaliar() {
		return new NumeroReal(
				(Double) esquerda.avaliar().obterValorNativo() % 
				(Double) direita.avaliar().obterValorNativo()
		);
	}
}

class ExpressaoSoma implements Numero {

	final Expressao esquerda, direita;

	ExpressaoSoma(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public NumeroReal avaliar() {
		return new NumeroReal(
				(Double) esquerda.avaliar().obterValorNativo() + 
				(Double) direita.avaliar().obterValorNativo()
		);
	}
}

class ExpressaoSubtracao implements Numero {

	final Expressao esquerda, direita;

	ExpressaoSubtracao(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public NumeroReal avaliar() {
		return new NumeroReal(
				(Double) esquerda.avaliar().obterValorNativo() - 
				(Double) direita.avaliar().obterValorNativo()
		);
	}
}