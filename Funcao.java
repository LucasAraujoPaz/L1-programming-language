public interface Funcao
	<TParametro extends Expressao<?>, TRetorno extends Expressao<?>> 
	extends Expressao<Funcao<TParametro, TRetorno>> {
	
	public Expressao<TRetorno> aplicar(Expressao<TParametro> parametro);
}

interface InvocacaoDeFuncao<TParametro, TRetorno> extends Expressao<Expressao<TRetorno>> {
}

class InvocacaoDeFuncaoLiteral
	<TParametro extends Expressao<?>, TRetorno extends Expressao<?>> 
	implements InvocacaoDeFuncao<TParametro, TRetorno> {

	public final Funcao<TParametro, TRetorno> funcao;
	public final Expressao<TParametro> parametro;
	
	public InvocacaoDeFuncaoLiteral(Funcao<TParametro, TRetorno> funcao, Expressao<TParametro> parametro) {
		this.funcao = funcao;
		this.parametro = parametro;
	}
	
	@Override
	public Expressao<TRetorno> obterValor() {
		return funcao.aplicar(parametro);
	}
}

class FuncaoLiteral
	<TParametro extends Expressao<?>, TRetorno extends Expressao<?>>
	implements Funcao<TParametro, TRetorno> {

	public final Expressao<TRetorno> corpo;
	
	public FuncaoLiteral(Expressao<TRetorno> corpo) {
		this.corpo = corpo;
	}
	
	@Override
	public Expressao<TRetorno> aplicar(Expressao<TParametro> parametro) {
		return corpo;
	}

	@Override
	public Funcao<TParametro, TRetorno> obterValor() {
		return this;
	}
}
/*
class CorpoDeFuncao<T> {
	final Expressao<T> expressao;
	public CorpoDeFuncao(Expressao<T> expressao) {
		this.expressao = expressao;
	}
	public Expressao<T> obterValor() {
		return expressao;
	}
}*/