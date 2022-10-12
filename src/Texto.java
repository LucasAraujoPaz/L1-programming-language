public interface Texto extends Expressao {
	public String obterValorNativo();
}

class TextoLiteral implements Texto, ExpressaoSimples {
	
	final String texto;
	
	public TextoLiteral(final String texto) {
		this.texto = texto;
	}

	@Override
	public String obterValorNativo() {
		return texto;
	}
}