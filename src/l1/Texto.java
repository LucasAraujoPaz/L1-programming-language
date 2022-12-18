package l1;
public interface Texto extends Expressao {
	@Override
	TextoAvaliado avaliar();
	@Override
	default Tipo obterTipo() {
		return Tipo.TEXTO;
	}
}

class TextoLiteral implements Texto {
	
	final String texto;
	
	public TextoLiteral(final String texto) {
		this.texto = texto;
	}

	@Override
	public TextoAvaliado avaliar() {
		return new TextoAvaliado(texto);
	}
}

class TextoAvaliado implements Valor {
	
	final String texto;
	
	public TextoAvaliado(final String texto) {
		this.texto = texto;
	}

	@Override
	public String obterValorNativo() {
		return texto;
	}
}