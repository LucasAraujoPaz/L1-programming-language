package l1;

public interface Booleano extends Expressao {
	
	@Override
	ValorBooleano avaliar();
	@Override
	default Tipo obterTipo() {
		return Tipo.BOOLEANO;
	}
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