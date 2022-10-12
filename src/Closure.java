import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Closure
	extends ExpressaoSimples {

	public ExpressaoSimples aplicar(Expressao parametro);
}

interface InvocacaoDeClosure
	extends ExpressaoComplexa {
}

class ClosureLiteral
	implements Closure {
	
	public final Funcao funcao;
	public final Map<Parametro, ExpressaoSimples> escopo;
	
	public ClosureLiteral(Funcao funcao) {
		this.funcao = funcao;
		this.escopo = funcao.getUpvalues().stream()
				.collect(Collectors.toMap(Function.identity(), Parametro::obterValorPrimitivo));
	}
	
	private void inverterEscopo() {
		for (var entry : escopo.entrySet()) {
			var valorOriginalDoParametro =  entry.getKey().valor.map(v ->  v.obterValorPrimitivo());
			var valorCapturado = Optional.ofNullable(entry.getValue());
			entry.getKey().valor = valorCapturado;
			entry.setValue(valorOriginalDoParametro.orElse(null));
		}
	}
	
	@Override
	public ExpressaoSimples aplicar(Expressao input) {
		
		inverterEscopo();
		var retorno = funcao.aplicar(input);
		inverterEscopo();
		
		return retorno;
	}
	
	@Override
	public Closure obterValorNativo() {
		return this;
	}
}

class InvocacaoDeClosureLiteral
	implements InvocacaoDeClosure {
	
	public final Expressao closure;
	public final Expressao input;
	
	public InvocacaoDeClosureLiteral(Expressao closure, Expressao parametro) {
		this.closure = closure;
		this.input = parametro;
	}
	
	public ExpressaoSimples obterValorPrimitivo() {
		return (closure instanceof Closure c ? c : (Closure) closure.obterValorPrimitivo())
				.aplicar(input);
	}
	
	@Override
	public Object obterValorNativo() {
		return obterValorPrimitivo().obterValorNativo();
	}
}

interface Funcao
	extends ExpressaoSimples {

	public ExpressaoSimples aplicar(Expressao parametro);
	public Collection<Parametro> getUpvalues();
	public Closure obterValorPrimitivo();

}

class FuncaoLiteral
	implements Funcao {

	public final Parametro parametro;
	public Expressao corpo;
	public final Collection<Parametro> upvalues;
	
	public FuncaoLiteral(Parametro parametro, Expressao corpo, Collection<Parametro> upvalues) {
		this.parametro = parametro;
		this.corpo = corpo;
		this.upvalues = upvalues;
	}
	
	@Override
	public Collection<Parametro> getUpvalues() {
		return upvalues;
	}
	
	@Override
	public ExpressaoSimples aplicar(Expressao input) { 
		var previo = this.parametro.valor;
		this.parametro.valor = Optional.of(input.obterValorPrimitivo());
		var resultado = corpo.obterValorPrimitivo();
		this.parametro.valor = previo;
		return resultado;
	}

	@Override
	public Closure obterValorPrimitivo() {
		return new ClosureLiteral(this); 
	}
	
	@Override
	public Funcao obterValorNativo() {
		return this;
	}
}

class Parametro implements ExpressaoComplexa {
	protected Optional<? extends Expressao> valor = Optional.empty();
	
	@Override
	public ExpressaoSimples obterValorPrimitivo() {
		return valor.get().obterValorPrimitivo();
	}
	@Override
	public Object obterValorNativo() {
		return valor.get().obterValorNativo();
	}
}