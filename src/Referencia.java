import java.util.Collection;

class ReferenciaABooleano implements Booleano {

	public final Expressao referenciado;
	public ReferenciaABooleano(Expressao referenciado) {
		this.referenciado = referenciado;
	}
	
	@Override
	public ExpressaoSimples<Booleano, Boolean> obterValorPrimitivo() {
		return ((Booleano) referenciado.obterValorPrimitivo()).obterValorPrimitivo();
	}

	@Override
	public Boolean obterValorNativo() {
		return ((Booleano) referenciado.obterValorPrimitivo()).obterValorNativo();
	}
}
class ReferenciaAFuncao implements Funcao {

	public final Expressao referenciado;
	public ReferenciaAFuncao(Expressao referenciado) {
		this.referenciado = referenciado;
	}
	
	@Override
	public Funcao obterValorNativo() {
		return ((Funcao) referenciado.obterValorPrimitivo()).obterValorNativo();
	}

	@Override
	public ExpressaoSimples aplicar(Expressao parametro) {
		return ((Funcao) referenciado.obterValorPrimitivo()).aplicar(parametro);
	}
}
class ReferenciaALista implements Lista {

	public final Expressao referenciado;
	public ReferenciaALista(Expressao referenciado) {
		this.referenciado = referenciado;
	}
	
	@Override
	public Collection<?> obterValorNativo() {
		return ((Lista) referenciado.obterValorPrimitivo()).obterValorNativo();
	}
}
class ReferenciaANulo implements Nulo {
	
	public final Expressao referenciado;
	public ReferenciaANulo(Expressao referenciado) {
		this.referenciado = referenciado;
	}
	
	@Override
	public Nulo obterValorNativo() {
		return ((Nulo) referenciado.obterValorPrimitivo()).obterValorNativo();
	}
}
class ReferenciaANumero implements Numero {

	public final Expressao referenciado;
	public ReferenciaANumero(Expressao referenciado) {
		this.referenciado = referenciado;
	}

	@Override
	public Double obterValorNativo() {
		return ((Numero) referenciado.obterValorPrimitivo()).obterValorNativo();
	}

	@Override
	public ExpressaoSimples<Numero, Double> obterValorPrimitivo() {
		return ((Numero) referenciado.obterValorPrimitivo()).obterValorPrimitivo();
	}
}
class ReferenciaATexto implements Texto {

	public final Expressao referenciado;
	public ReferenciaATexto(Expressao referenciado) {
		this.referenciado = referenciado;
	}
	
	@Override
	public ExpressaoSimples<Texto, String> obterValorPrimitivo() {
		return ((Texto) referenciado.obterValorPrimitivo()).obterValorPrimitivo();
	}

	@Override
	public String obterValorNativo() {
		return ((Texto) referenciado.obterValorPrimitivo()).obterValorNativo();
	}
}