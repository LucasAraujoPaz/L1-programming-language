package compilador;
interface Expressao {
	public Valor avaliar();
}

interface Valor {
	public Object obterValorNativo();
}