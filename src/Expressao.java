interface Expressao<T, N> extends Nativo<N> {
	public ExpressaoSimples<T, N> obterValorPrimitivo();
}

interface ExpressaoSimples<T, N> extends Expressao<T, N> {
	@Override
	public default ExpressaoSimples<T, N> obterValorPrimitivo() {
		return this;
	}
}

interface ExpressaoComplexa<T, N> extends Expressao<T, N> {
}

interface Nativo<N> {
	public N obterValorNativo();
}