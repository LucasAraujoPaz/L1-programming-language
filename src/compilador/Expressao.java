package compilador;
interface Expressao {
	public Valor avaliar();
	public default Object obterValorNativo() {
		return avaliar().obterValorNativo();
	};
}

interface Valor {
	public Object obterValorNativo();
}