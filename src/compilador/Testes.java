package compilador;
import java.util.List;
import java.util.Set;

public class Testes {

	public static void main(String[] args) {
		testarNumeros();
		testarBooleanos();
		testarFuncoes();
		testarToken();
		System.out.println("Testes rodados com sucesso.");
	}

	private static void testarNumeros() {
	}

	private static void testarBooleanos() {
	}

	private static void testarFuncoes() {
		// Let fatorial := x => x < 2 ? 1 : x * fatorial(x - 1). 
		var x = new Parametro("x");
		var fatorial = new FuncaoLiteral(x, null, Set.of());
		var corpo = 
				new ExpressaoSeSenao(
						List.of(new ExpressaoMenor(x, new NumeroLiteral(2))),
						List.of(new NumeroLiteral(1),
								new ExpressaoMultiplicacao(
										x, 
										new InvocacaoImpl(
												fatorial,
												new ExpressaoSubtracao(x, new NumeroLiteral(1))))));
		
		fatorial.corpo = corpo;
		asseverar(fatorial.aplicar(new NumeroLiteral(6)).obterValorNativo().equals(720d));
		
		// Let f := a => b => a + b.
		// f(1)(2) = 3
		Parametro a = new Parametro("a"), b = new Parametro("b");
		Funcao g = new FuncaoLiteral(b, new ExpressaoSoma(a, b), Set.of(a));
		Funcao f = new FuncaoLiteral(a, g, Set.of());
		var closureG = new InvocacaoImpl(f, new NumeroLiteral(1));
		var retorno = new InvocacaoImpl(closureG, new NumeroLiteral(2));
		asseverar(retorno.avaliar().obterValorNativo().equals(3d));
	}

	private static void testarToken() {
		var codigoFonte = """
Let number := 3.14.
Let boolean := True Or False.
Let string := "String".
Let array := [1, 2, 3].
Let factorial := 
	Function(Number x) -> Number:
		If x < 2 Then
			1
		Else
			x * factorial(x - 1)
		End
	End
.
Let main := Function(String x) -> Number:
	factorial(10)
End.
""";

		var t = Token.processar(codigoFonte);
		asseverar(t.size() == 74);
		
		var a = "Let x¬ := 1.";
		var b = "Let s := \"bla \\\".";
		asseverarQueLancaExcecao(() -> Token.processar(a), "", Throwable.class);
		asseverarQueLancaExcecao(() -> Token.processar(b), "", Throwable.class);
	}
	
	public static void asseverar(boolean condicao) {
		asseverar(condicao, "");
	}

	public static void asseverar(boolean condicao, String mensagem) {
		if (condicao)
			return;

		throw new Erro(mensagem);
	}
	
	public static void asseverarQueLancaExcecao(
			Runnable s, String mensagem, Class<Throwable> c) {
		try {
			s.run();
		} catch(Throwable t) {
			asseverar(c.isInstance(t) , mensagem);
			return;
		}
		asseverar(false, mensagem);
	}
}
