package compilador;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Closure extends Valor {

	public Valor aplicar(Expressao input);
}

class ClosureImpl implements Closure {
	
	public final Funcao funcao;
	public final Map<Parametro, Optional<Valor>> escopo;
	
	public ClosureImpl(Funcao funcao) {
		this.funcao = funcao;
		this.escopo = funcao.getUpvalues().stream()
				.collect(Collectors.toMap(Function.identity(), p -> p.valor));
	}
	
	private void inverterEscopo() {
		for (var entry : escopo.entrySet()) {
			var valorOriginalDoParametro = entry.getKey().valor;
			var valorCapturado = entry.getValue();
			entry.getKey().valor = valorCapturado;
			entry.setValue(valorOriginalDoParametro);
		}
	}
	
	@Override
	public Valor aplicar(Expressao input) {
		
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

interface Invocacao extends Expressao {
}

class InvocacaoImpl implements Invocacao {
	
	public final Expressao invocavel;
	public final Expressao input;
	
	public InvocacaoImpl(Expressao invocavel, Expressao input) {
		this.invocavel = invocavel;
		this.input = input;
	}
	
	public Valor avaliar() {
		return ((Closure) invocavel.avaliar())
				.aplicar(input);
	}

	@Override
	public Tipo obterTipo() {
		return ((Tipo.Funcao) invocavel.obterTipo()).tipoDoRetorno;
	}
}

interface Funcao extends Expressao {

	public Valor aplicar(Expressao input);
	public Collection<Parametro> getUpvalues();
	public void putUpvalue(Parametro parametro);
	public Closure avaliar();
	Parametro getParametro();
	Tipo.Funcao obterTipo();
}

class FuncaoLiteral implements Funcao {

	final Tipo tipoDoRetorno;
	public final Parametro parametro;
	private Expressao corpo;
	public final Collection<Parametro> upvalues;
	
	public FuncaoLiteral(Tipo tipoDoRetorno, Parametro parametro, Expressao corpo, Collection<Parametro> upvalues) {
		this.tipoDoRetorno = tipoDoRetorno;
		this.parametro = parametro;
		this.corpo = corpo;
		this.upvalues = upvalues;
	}
	
	@Override
	public Collection<Parametro> getUpvalues() {
		return upvalues;
	}

	@Override
	public void putUpvalue(Parametro parametro) {
		if (upvalues.contains(parametro))
			return;
		upvalues.add(parametro);
	}

	@Override
	public Valor aplicar(Expressao input) { 
		var previo = this.parametro.valor;
		this.parametro.valor = Optional.of(input.avaliar());
		var resultado = corpo.avaliar();
		this.parametro.valor = previo;
		return resultado;
	}

	@Override
	public Closure avaliar() {
		return new ClosureImpl(this); 
	}

	@Override
	public Parametro getParametro() {
		return this.parametro;
	}
	
	public void setCorpo(final Expressao corpo) {
		this.corpo = corpo;
	}

	@Override
	public Tipo.Funcao obterTipo() {
		return new Tipo.Funcao(parametro.obterTipo(), tipoDoRetorno);
	}
}

class Parametro implements Expressao {
	final Tipo tipo;
	Optional<Valor> valor = Optional.empty();
	final String nome;
	public Parametro(Tipo tipo, String nome) {
		this.tipo = tipo;
		this.nome = nome;
	}
	@Override
	public Valor avaliar() {
		return valor.get();
	}
	@Override
	public Tipo obterTipo() {
		return tipo;
	}
}