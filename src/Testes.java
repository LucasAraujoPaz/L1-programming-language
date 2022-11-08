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

		asseverar(Numero.processar(List.of(new NumeroLiteral(0))).obterValorNativo().equals(0d));

		asseverar(Numero.processar(List.of(new NumeroLiteral(1), OperadorNumericoBinario.SOMA, new NumeroLiteral(1)))
				.obterValorNativo().equals(2d));

		asseverar(Numero.processar(List.of( // 234.8 + 23428 + - 8762 * 23 ** 3 ** 4 / + 13 * - 1 % 9 - 3.6 == 23667.2
				new NumeroLiteral(234.8), OperadorNumericoBinario.SOMA, new NumeroLiteral(23428),
				OperadorNumericoBinario.SOMA, OperadorNumericoUnario.NEGATIVO, new NumeroLiteral(8762),
				OperadorNumericoBinario.MULTIPLICACAO, new NumeroLiteral(23), OperadorNumericoBinario.EXPONENCIACAO,
				new NumeroLiteral(3), OperadorNumericoBinario.EXPONENCIACAO, new NumeroLiteral(4),
				OperadorNumericoBinario.DIVISAO, new NumeroLiteral(13),
				OperadorNumericoBinario.MULTIPLICACAO, OperadorNumericoUnario.NEGATIVO, new NumeroLiteral(1),
				OperadorNumericoBinario.MODULO, new NumeroLiteral(9), OperadorNumericoBinario.SUBTRACAO,
				new NumeroLiteral(3.6))).obterValorNativo().equals(23667.2));
	}

	private static void testarBooleanos() {

		asseverar(Booleano.processar(List.of(BooleanoLiteral.VERDADEIRO)).obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(BooleanoLiteral.FALSO)).obterValorNativo().equals(false));

		asseverar(Booleano.processar(List.of(OperadorBooleano.NAO, BooleanoLiteral.VERDADEIRO)).obterValorNativo().equals(false));

		asseverar(Booleano.processar(List.of(OperadorBooleano.NAO, BooleanoLiteral.FALSO)).obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.E, BooleanoLiteral.VERDADEIRO))
				.obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.E, BooleanoLiteral.FALSO))
				.obterValorNativo().equals(false));

		asseverar(Booleano.processar(List.of(BooleanoLiteral.FALSO, OperadorBooleanoBinario.E, BooleanoLiteral.VERDADEIRO))
				.obterValorNativo().equals(false));

		asseverar(Booleano.processar(List.of(BooleanoLiteral.FALSO, OperadorBooleanoBinario.E, BooleanoLiteral.FALSO))
				.obterValorNativo().equals(false));

		asseverar(Booleano.processar(List.of(BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.OU, BooleanoLiteral.VERDADEIRO))
				.obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.OU, BooleanoLiteral.FALSO))
				.obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(BooleanoLiteral.FALSO, OperadorBooleanoBinario.OU, BooleanoLiteral.VERDADEIRO))
				.obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(BooleanoLiteral.FALSO, OperadorBooleanoBinario.OU, BooleanoLiteral.FALSO))
				.obterValorNativo().equals(false));

		asseverar(Booleano.processar(List.of(BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.E, BooleanoLiteral.VERDADEIRO,
				OperadorBooleanoBinario.E, BooleanoLiteral.VERDADEIRO)).obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.E, BooleanoLiteral.FALSO,
				OperadorBooleanoBinario.E, BooleanoLiteral.VERDADEIRO)).obterValorNativo().equals(false));

		asseverar(Booleano.processar(
				List.of(BooleanoLiteral.FALSO, OperadorBooleanoBinario.OU, BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.OU, BooleanoLiteral.FALSO))
				.obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.OU, BooleanoLiteral.FALSO,
				OperadorBooleanoBinario.OU, BooleanoLiteral.VERDADEIRO)).obterValorNativo().equals(true));

		asseverar(Booleano.processar(
				List.of(BooleanoLiteral.FALSO, OperadorBooleanoBinario.OU, BooleanoLiteral.FALSO, OperadorBooleanoBinario.OU, BooleanoLiteral.FALSO))
				.obterValorNativo().equals(false));

		asseverar(Booleano
				.processar(List.of(BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.E, OperadorBooleano.NAO, BooleanoLiteral.VERDADEIRO))
				.obterValorNativo().equals(false));

		asseverar(Booleano
				.processar(List.of(BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.E, OperadorBooleano.NAO, BooleanoLiteral.FALSO))
				.obterValorNativo().equals(true));

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.E, BooleanoLiteral.VERDADEIRO))
				.obterValorNativo().equals(false));

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.E, BooleanoLiteral.FALSO))
				.obterValorNativo().equals(false));

		asseverar(Booleano
				.processar(List.of(BooleanoLiteral.FALSO, OperadorBooleanoBinario.E, OperadorBooleano.NAO, BooleanoLiteral.VERDADEIRO))
				.obterValorNativo().equals(false));

		asseverar(Booleano.processar(List.of(BooleanoLiteral.FALSO, OperadorBooleanoBinario.E, OperadorBooleano.NAO, BooleanoLiteral.FALSO))
				.obterValorNativo().equals(false));

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, BooleanoLiteral.FALSO, OperadorBooleanoBinario.E, BooleanoLiteral.VERDADEIRO))
				.obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(OperadorBooleano.NAO, BooleanoLiteral.FALSO, OperadorBooleanoBinario.E, BooleanoLiteral.FALSO))
				.obterValorNativo().equals(false));

		asseverar(Booleano
				.processar(List.of(BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO, BooleanoLiteral.VERDADEIRO))
				.obterValorNativo().equals(true));

		asseverar(Booleano
				.processar(List.of(BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO, BooleanoLiteral.FALSO))
				.obterValorNativo().equals(true));

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.OU, BooleanoLiteral.VERDADEIRO))
				.obterValorNativo().equals(true));

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.OU, BooleanoLiteral.FALSO))
				.obterValorNativo().equals(false));

		asseverar(Booleano
				.processar(List.of(BooleanoLiteral.FALSO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO, BooleanoLiteral.VERDADEIRO))
				.obterValorNativo().equals(false));

		asseverar(Booleano.processar(List.of(BooleanoLiteral.FALSO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO, BooleanoLiteral.FALSO))
				.obterValorNativo().equals(true));

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, BooleanoLiteral.FALSO, OperadorBooleanoBinario.OU, BooleanoLiteral.VERDADEIRO))
				.obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(OperadorBooleano.NAO, BooleanoLiteral.FALSO, OperadorBooleanoBinario.OU, BooleanoLiteral.FALSO))
				.obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(// true && false || ! true || ! false && true && false || ! false == true
				BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.E, BooleanoLiteral.FALSO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO,
				BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO, BooleanoLiteral.FALSO, OperadorBooleanoBinario.E,
				BooleanoLiteral.VERDADEIRO, OperadorBooleanoBinario.E, BooleanoLiteral.FALSO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO,
				BooleanoLiteral.FALSO)).obterValorNativo().equals(true));
	}

	private static void testarFuncoes() {
		// Let fatorial := x => x < 2 ? 1 : x * fatorial(x - 1). 
		var x = new Parametro();
		var fatorial = new FuncaoLiteral(x, null, Set.of());
		var corpo = 
				new ExpressaoSeSenao(
						List.of(new ExpressaoMenor(x, new NumeroLiteral(2))),
						List.of(new NumeroLiteral(1),
								new ExpressaoMultiplicacao(
										x, 
										new InvocacaoDeClosureImpl(
												fatorial,
												new ExpressaoSubtracao(x, new NumeroLiteral(1))))));
		
		fatorial.corpo = corpo;
		asseverar(fatorial.aplicar(new NumeroLiteral(6)).obterValorNativo().equals(720d));
		
		// Let f := a => b => a + b.
		// f(1)(2) = 3
		Parametro a = new Parametro(), b = new Parametro();
		Funcao g = new FuncaoLiteral(b, new ExpressaoSoma(a, b), Set.of(a));
		Funcao f = new FuncaoLiteral(a, g, Set.of());
		var closureG = new InvocacaoDeClosureImpl(f, new NumeroLiteral(1));
		var retorno = new InvocacaoDeClosureImpl(closureG, new NumeroLiteral(2));
		asseverar(retorno.obterValorNativo().equals(3d));
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
		asseverarQueLancaExcecao(() -> Token.processar(a), null, Throwable.class);
		asseverarQueLancaExcecao(() -> Token.processar(b), null, Throwable.class);
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
