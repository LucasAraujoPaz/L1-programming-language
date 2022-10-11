public interface Funcao
	extends ExpressaoSimples<Funcao, Funcao> {
	
	public ExpressaoSimples aplicar(Expressao parametro);
}

interface InvocacaoDeFuncao
	extends ExpressaoComplexa {
}

class InvocacaoDeFuncaoLiteral
	implements InvocacaoDeFuncao {

	public final Funcao funcao;
	public final Expressao parametro;
	
	public InvocacaoDeFuncaoLiteral(Funcao funcao, Expressao parametro) {
		this.funcao = funcao;
		this.parametro = parametro;
	}
	
	public ExpressaoSimples obterValorPrimitivo() {
		return funcao.aplicar(parametro);
	}

	@Override
	public Object obterValorNativo() {
		return obterValorPrimitivo().obterValorNativo();
	}
}

class FuncaoLiteral
	implements Funcao {

	public final Parametro parametro;
	public Expressao corpo;
	
	public FuncaoLiteral(Parametro parametro, Expressao corpo) {
		this.parametro = parametro;
		this.corpo = corpo;
	}
	
	@Override
	public ExpressaoSimples aplicar(Expressao input) { 
		var previo = this.parametro.valor;
		this.parametro.valor = input.obterValorPrimitivo();
		var resultado = corpo.obterValorPrimitivo();
		this.parametro.valor = previo;
		return resultado;
	}

	@Override
	public Funcao obterValorNativo() {
		return this;
	}
}

class Parametro implements ExpressaoComplexa {
	Expressao valor;
	@Override
	public ExpressaoSimples obterValorPrimitivo() {
		return valor.obterValorPrimitivo();
	}
	@Override
	public Object obterValorNativo() {
		return valor.obterValorPrimitivo().obterValorNativo();
	}
}