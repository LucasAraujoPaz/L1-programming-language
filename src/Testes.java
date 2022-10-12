import java.util.List;
import java.util.Set;

public class Testes {

	public static void main(String[] args) {
		testarNumeros();
		testarBooleanos();
		testarFuncoes();
		System.out.println("Testes rodados.");
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
				OperadorNumericoBinario.DIVISAO, OperadorNumericoUnario.POSITIVO, new NumeroLiteral(13),
				OperadorNumericoBinario.MULTIPLICACAO, OperadorNumericoUnario.NEGATIVO, new NumeroLiteral(1),
				OperadorNumericoBinario.MODULO, new NumeroLiteral(9), OperadorNumericoBinario.SUBTRACAO,
				new NumeroLiteral(3.6))).obterValorNativo().equals(23667.2));
	}

	private static void testarBooleanos() {

		asseverar(Booleano.processar(List.of(Booleano.VERDADEIRO)).obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(Booleano.FALSO)).obterValorNativo().equals(false));

		asseverar(Booleano.processar(List.of(OperadorBooleano.NAO, Booleano.VERDADEIRO)).obterValorNativo().equals(false));

		asseverar(Booleano.processar(List.of(OperadorBooleano.NAO, Booleano.FALSO)).obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.E, Booleano.VERDADEIRO))
				.obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.E, Booleano.FALSO))
				.obterValorNativo().equals(false));

		asseverar(Booleano.processar(List.of(Booleano.FALSO, OperadorBooleanoBinario.E, Booleano.VERDADEIRO))
				.obterValorNativo().equals(false));

		asseverar(Booleano.processar(List.of(Booleano.FALSO, OperadorBooleanoBinario.E, Booleano.FALSO))
				.obterValorNativo().equals(false));

		asseverar(Booleano.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, Booleano.VERDADEIRO))
				.obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, Booleano.FALSO))
				.obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(Booleano.FALSO, OperadorBooleanoBinario.OU, Booleano.VERDADEIRO))
				.obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(Booleano.FALSO, OperadorBooleanoBinario.OU, Booleano.FALSO))
				.obterValorNativo().equals(false));

		asseverar(Booleano.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.E, Booleano.VERDADEIRO,
				OperadorBooleanoBinario.E, Booleano.VERDADEIRO)).obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.E, Booleano.FALSO,
				OperadorBooleanoBinario.E, Booleano.VERDADEIRO)).obterValorNativo().equals(false));

		asseverar(Booleano.processar(
				List.of(Booleano.FALSO, OperadorBooleanoBinario.OU, Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, Booleano.FALSO))
				.obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, Booleano.FALSO,
				OperadorBooleanoBinario.OU, Booleano.VERDADEIRO)).obterValorNativo().equals(true));

		asseverar(Booleano.processar(
				List.of(Booleano.FALSO, OperadorBooleanoBinario.OU, Booleano.FALSO, OperadorBooleanoBinario.OU, Booleano.FALSO))
				.obterValorNativo().equals(false));

		asseverar(Booleano
				.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.E, OperadorBooleano.NAO, Booleano.VERDADEIRO))
				.obterValorNativo().equals(false));

		asseverar(Booleano
				.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.E, OperadorBooleano.NAO, Booleano.FALSO))
				.obterValorNativo().equals(true));

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, Booleano.VERDADEIRO, OperadorBooleanoBinario.E, Booleano.VERDADEIRO))
				.obterValorNativo().equals(false));

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, Booleano.VERDADEIRO, OperadorBooleanoBinario.E, Booleano.FALSO))
				.obterValorNativo().equals(false));

		asseverar(Booleano
				.processar(List.of(Booleano.FALSO, OperadorBooleanoBinario.E, OperadorBooleano.NAO, Booleano.VERDADEIRO))
				.obterValorNativo().equals(false));

		asseverar(Booleano.processar(List.of(Booleano.FALSO, OperadorBooleanoBinario.E, OperadorBooleano.NAO, Booleano.FALSO))
				.obterValorNativo().equals(false));

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, Booleano.FALSO, OperadorBooleanoBinario.E, Booleano.VERDADEIRO))
				.obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(OperadorBooleano.NAO, Booleano.FALSO, OperadorBooleanoBinario.E, Booleano.FALSO))
				.obterValorNativo().equals(false));

		asseverar(Booleano
				.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO, Booleano.VERDADEIRO))
				.obterValorNativo().equals(true));

		asseverar(Booleano
				.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO, Booleano.FALSO))
				.obterValorNativo().equals(true));

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, Booleano.VERDADEIRO))
				.obterValorNativo().equals(true));

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, Booleano.FALSO))
				.obterValorNativo().equals(false));

		asseverar(Booleano
				.processar(List.of(Booleano.FALSO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO, Booleano.VERDADEIRO))
				.obterValorNativo().equals(false));

		asseverar(Booleano.processar(List.of(Booleano.FALSO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO, Booleano.FALSO))
				.obterValorNativo().equals(true));

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, Booleano.FALSO, OperadorBooleanoBinario.OU, Booleano.VERDADEIRO))
				.obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(OperadorBooleano.NAO, Booleano.FALSO, OperadorBooleanoBinario.OU, Booleano.FALSO))
				.obterValorNativo().equals(true));

		asseverar(Booleano.processar(List.of(// true && false || ! true || ! false && true && false || ! false == true
				Booleano.VERDADEIRO, OperadorBooleanoBinario.E, Booleano.FALSO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO,
				Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO, Booleano.FALSO, OperadorBooleanoBinario.E,
				Booleano.VERDADEIRO, OperadorBooleanoBinario.E, Booleano.FALSO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO,
				Booleano.FALSO)).obterValorNativo().equals(true));
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
										new InvocacaoDeClosureLiteral(
												fatorial,
												new ExpressaoSubtracao(x, new NumeroLiteral(1))))));
		
		fatorial.corpo = corpo;
		asseverar(fatorial.aplicar(new NumeroLiteral(6)).obterValorNativo().equals(720d));
		
		// Let f := a => b => a + b.
		// f(1)(2) = 3
		Parametro a = new Parametro(), b = new Parametro();
		Funcao g = new FuncaoLiteral(b, new ExpressaoSoma(a, b), Set.of(a));
		Funcao f = new FuncaoLiteral(a, g, Set.of());
		var closureG = new InvocacaoDeClosureLiteral(f, new NumeroLiteral(1)).obterValorPrimitivo();
		var retorno = new InvocacaoDeClosureLiteral(closureG, new NumeroLiteral(2));
		asseverar(retorno.obterValorNativo().equals(3d));
	}

	public static void asseverar(boolean condicao) {
		asseverar(condicao, "");
	}

	public static void asseverar(boolean condicao, String mensagem) {
		if (condicao)
			return;

		throw new AssertionError(mensagem);
	}
}
