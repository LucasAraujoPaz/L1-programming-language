package compilador;
import java.util.Optional;

public class Declaracao implements Expressao {

	final Token token;
	final Expressao expressao;
	private Optional<Valor> valor = Optional.empty();
	
	public Declaracao(Token token, Expressao expressao) {
		this.token = token;
		this.expressao = expressao;
	}
	@Override
	public Valor avaliar() {
		if (valor.isPresent())
			return valor.get();
		
		valor = Optional.ofNullable(expressao.avaliar());
		return valor.get();
	}
	@Override
	public Tipo obterTipo() {
		return expressao.obterTipo();
	}
}
