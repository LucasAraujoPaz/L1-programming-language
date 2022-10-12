interface Nativo {
	public Object obterValorNativo();
}

interface Expressao extends Nativo {
	public ExpressaoSimples obterValorPrimitivo();
}

interface ExpressaoSimples extends Expressao {
	@Override
	public default ExpressaoSimples obterValorPrimitivo() {
		return this;
	}
}

interface ExpressaoComplexa extends Expressao {
}