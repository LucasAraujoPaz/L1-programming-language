public interface Texto extends Expressao {
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
	public Object obterValorNativo() {
		return texto;
	}
}