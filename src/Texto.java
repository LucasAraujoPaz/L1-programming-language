public interface Texto extends Expressao<Texto, String> {
}

class TextoLiteral implements Texto, ExpressaoSimples<Texto, String> {
	
	final String texto;
	
	public TextoLiteral(final String texto) {
		this.texto = texto;
	}

	@Override
	public String obterValorNativo() {
		return texto;
	}
}