import java.util.List;

public class Testes {

	public static void main(String[] args) {
		testarNumeros();
		testarBooleanos();
		testarFuncoes();
	}

	private static void testarNumeros() {

		asseverar(Numero.processar(List.of(new NumeroLiteral(0))).obterValorNativo() == 0);

		asseverar(Numero.processar(List.of(new NumeroLiteral(1), OperadorNumericoBinario.SOMA, new NumeroLiteral(1)))
				.obterValorNativo() == 2);

		asseverar(Numero.processar(List.of( // 234.8 + 23428 + - 8762 * 23 ** 3 ** 4 / + 13 * - 1 % 9 - 3.6 == 23667.2
				new NumeroLiteral(234.8), OperadorNumericoBinario.SOMA, new NumeroLiteral(23428),
				OperadorNumericoBinario.SOMA, OperadorNumericoUnario.NEGATIVO, new NumeroLiteral(8762),
				OperadorNumericoBinario.MULTIPLICACAO, new NumeroLiteral(23), OperadorNumericoBinario.EXPONENCIACAO,
				new NumeroLiteral(3), OperadorNumericoBinario.EXPONENCIACAO, new NumeroLiteral(4),
				OperadorNumericoBinario.DIVISAO, OperadorNumericoUnario.POSITIVO, new NumeroLiteral(13),
				OperadorNumericoBinario.MULTIPLICACAO, OperadorNumericoUnario.NEGATIVO, new NumeroLiteral(1),
				OperadorNumericoBinario.MODULO, new NumeroLiteral(9), OperadorNumericoBinario.SUBTRACAO,
				new NumeroLiteral(3.6))).obterValorNativo() == 23667.2);
	}

	private static void testarBooleanos() {

		asseverar(Booleano.processar(List.of(Booleano.VERDADEIRO)).obterValorNativo() == true);

		asseverar(Booleano.processar(List.of(Booleano.FALSO)).obterValorNativo() == false);

		asseverar(Booleano.processar(List.of(OperadorBooleano.NAO, Booleano.VERDADEIRO)).obterValorNativo() == false);

		asseverar(Booleano.processar(List.of(OperadorBooleano.NAO, Booleano.FALSO)).obterValorNativo() == true);

		asseverar(Booleano.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.E, Booleano.VERDADEIRO))
				.obterValorNativo() == true);

		asseverar(Booleano.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.E, Booleano.FALSO))
				.obterValorNativo() == false);

		asseverar(Booleano.processar(List.of(Booleano.FALSO, OperadorBooleanoBinario.E, Booleano.VERDADEIRO))
				.obterValorNativo() == false);

		asseverar(Booleano.processar(List.of(Booleano.FALSO, OperadorBooleanoBinario.E, Booleano.FALSO))
				.obterValorNativo() == false);

		asseverar(Booleano.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, Booleano.VERDADEIRO))
				.obterValorNativo() == true);

		asseverar(Booleano.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, Booleano.FALSO))
				.obterValorNativo() == true);

		asseverar(Booleano.processar(List.of(Booleano.FALSO, OperadorBooleanoBinario.OU, Booleano.VERDADEIRO))
				.obterValorNativo() == true);

		asseverar(Booleano.processar(List.of(Booleano.FALSO, OperadorBooleanoBinario.OU, Booleano.FALSO))
				.obterValorNativo() == false);

		asseverar(Booleano.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.E, Booleano.VERDADEIRO,
				OperadorBooleanoBinario.E, Booleano.VERDADEIRO)).obterValorNativo() == true);

		asseverar(Booleano.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.E, Booleano.FALSO,
				OperadorBooleanoBinario.E, Booleano.VERDADEIRO)).obterValorNativo() == false);

		asseverar(Booleano.processar(
				List.of(Booleano.FALSO, OperadorBooleanoBinario.OU, Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, Booleano.FALSO))
				.obterValorNativo() == true);

		asseverar(Booleano.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, Booleano.FALSO,
				OperadorBooleanoBinario.OU, Booleano.VERDADEIRO)).obterValorNativo() == true);

		asseverar(Booleano.processar(
				List.of(Booleano.FALSO, OperadorBooleanoBinario.OU, Booleano.FALSO, OperadorBooleanoBinario.OU, Booleano.FALSO))
				.obterValorNativo() == false);

		asseverar(Booleano
				.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.E, OperadorBooleano.NAO, Booleano.VERDADEIRO))
				.obterValorNativo() == false);

		asseverar(Booleano
				.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.E, OperadorBooleano.NAO, Booleano.FALSO))
				.obterValorNativo() == true);

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, Booleano.VERDADEIRO, OperadorBooleanoBinario.E, Booleano.VERDADEIRO))
				.obterValorNativo() == false);

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, Booleano.VERDADEIRO, OperadorBooleanoBinario.E, Booleano.FALSO))
				.obterValorNativo() == false);

		asseverar(Booleano
				.processar(List.of(Booleano.FALSO, OperadorBooleanoBinario.E, OperadorBooleano.NAO, Booleano.VERDADEIRO))
				.obterValorNativo() == false);

		asseverar(Booleano.processar(List.of(Booleano.FALSO, OperadorBooleanoBinario.E, OperadorBooleano.NAO, Booleano.FALSO))
				.obterValorNativo() == false);

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, Booleano.FALSO, OperadorBooleanoBinario.E, Booleano.VERDADEIRO))
				.obterValorNativo() == true);

		asseverar(Booleano.processar(List.of(OperadorBooleano.NAO, Booleano.FALSO, OperadorBooleanoBinario.E, Booleano.FALSO))
				.obterValorNativo() == false);

		asseverar(Booleano
				.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO, Booleano.VERDADEIRO))
				.obterValorNativo() == true);

		asseverar(Booleano
				.processar(List.of(Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO, Booleano.FALSO))
				.obterValorNativo() == true);

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, Booleano.VERDADEIRO))
				.obterValorNativo() == true);

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, Booleano.FALSO))
				.obterValorNativo() == false);

		asseverar(Booleano
				.processar(List.of(Booleano.FALSO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO, Booleano.VERDADEIRO))
				.obterValorNativo() == false);

		asseverar(Booleano.processar(List.of(Booleano.FALSO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO, Booleano.FALSO))
				.obterValorNativo() == true);

		asseverar(Booleano
				.processar(List.of(OperadorBooleano.NAO, Booleano.FALSO, OperadorBooleanoBinario.OU, Booleano.VERDADEIRO))
				.obterValorNativo() == true);

		asseverar(Booleano.processar(List.of(OperadorBooleano.NAO, Booleano.FALSO, OperadorBooleanoBinario.OU, Booleano.FALSO))
				.obterValorNativo() == true);

		asseverar(Booleano.processar(List.of(// true && false || ! true || ! false && true && false || ! false == true
				Booleano.VERDADEIRO, OperadorBooleanoBinario.E, Booleano.FALSO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO,
				Booleano.VERDADEIRO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO, Booleano.FALSO, OperadorBooleanoBinario.E,
				Booleano.VERDADEIRO, OperadorBooleanoBinario.E, Booleano.FALSO, OperadorBooleanoBinario.OU, OperadorBooleano.NAO,
				Booleano.FALSO)).obterValorNativo() == true);
	}

	private static void testarFuncoes() {
		// fatorial = x => x < 2 ? 1 : x * fatorial(x - 1). 
		var x = new Parametro();
		var fatorial = new FuncaoLiteral(x, null);
		var corpo = 
				new ExpressaoSeSenao(
						List.of(new ExpressaoMenor(new ReferenciaANumero(x), new NumeroLiteral(2))),
						List.of(new NumeroLiteral(1),
								new ExpressaoMultiplicacao(
										new ReferenciaANumero(x),
										new ReferenciaANumero(
												new InvocacaoDeFuncaoLiteral(
														fatorial,
														new ExpressaoSubtracao(new ReferenciaANumero(x), new NumeroLiteral(1)))))));
		
		fatorial.corpo = corpo;
		asseverar(fatorial.aplicar(new NumeroLiteral(6)).obterValorNativo().equals(720d));
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
