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