public interface Texto {
	public String obterValor();
}

class TextoLiteral implements Texto {
	
	final String texto;
	
	public TextoLiteral(final String texto) {
		this.texto = texto;
	}

	@Override
	public String obterValor() {
		return texto;
	}
}