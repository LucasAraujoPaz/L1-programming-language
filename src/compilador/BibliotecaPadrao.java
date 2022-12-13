package compilador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BibliotecaPadrao {

	public static final Map<String, Expressao> bibliotecaPadrao() { 
		return new HashMap<>( Map.of("Standard~concatenate", concatenar()) ); 
	};

	private static Expressao concatenar() {
		var a = new Parametro("a");
		var b = new Parametro("b");
		FuncaoLiteral f = new FuncaoLiteral(a, null, List.of());
		FuncaoLiteral g = new FuncaoLiteral(b, 
				() -> new TextoAvaliado( (String) a.valor.get().obterValorNativo() + (String) b.valor.get().obterValorNativo() ),
				List.of(a));
		f.setCorpo(g);
		return f;
	}
}

class ExpressaoNativa implements Expressao {
	final Supplier<Valor> s;
	public ExpressaoNativa(Supplier<Valor> s) {
		this.s = s; 
	}
	@Override
	public Valor avaliar() {
		return s.get();
	}
}