package l1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BibliotecaPadrao {

	public static final Map<String, Expressao> bibliotecaPadrao() { 
		return new HashMap<>( Map.of("Standard~concatenate", concatenar()) ); 
	};

	private static Expressao concatenar() {
		var a = new Parametro(Tipo.TEXTO, "a");
		var b = new Parametro(Tipo.TEXTO, "b");
		FuncaoLiteral f = new FuncaoLiteral(new Tipo.Funcao(Tipo.TEXTO, Tipo.TEXTO), a, null, List.of());
		FuncaoLiteral g = new FuncaoLiteral(Tipo.TEXTO, b, 
				new ExpressaoNativa(Tipo.TEXTO,
					() -> new TextoAvaliado( (String) a.valor.get().obterValorNativo() + (String) b.valor.get().obterValorNativo() ) 
				),
				List.of(a));
		f.setCorpo(g);
		return f;
	}
}

class ExpressaoNativa implements Expressao {
	final Tipo tipo;
	final Supplier<Valor> s;
	public ExpressaoNativa(Tipo tipo, Supplier<Valor> s) {
		this.tipo = tipo;
		this.s = s; 
	}
	@Override
	public Valor avaliar() {
		return s.get();
	}
	@Override
	public Tipo obterTipo() {
		return tipo;
	}
}