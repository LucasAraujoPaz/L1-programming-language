public interface Qualquer extends Expressao<Object, Object> {
}

class QualquerImpl implements Qualquer {

	Expressao<Object, Object> interna;
	QualquerImpl(Expressao<Object, Object> interna) {
		this.interna = this;
	}
	
	@Override
	public ExpressaoSimples<Object, Object> obterValorPrimitivo() {
		return interna.obterValorPrimitivo();
	}

	@Override
	public Object obterValorNativo() {
		return interna.obterValorNativo();
	}
	
}