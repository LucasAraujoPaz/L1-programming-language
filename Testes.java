import java.util.List;

public class Testes {

	public static void main(String[] args) {
		testarNumeros();
		testarBooleanos();
	}

	private static void testarNumeros() {
		
		asseverar(Numero.processar(List.of(
				new NumeroLiteral(0)
				)).obterValor() == 0);
		
		asseverar(Numero.processar(List.of(
				new NumeroLiteral(1), OperadorNumericoBinario.SOMA, new NumeroLiteral(1)
				)).obterValor() == 2);

		asseverar(Numero.processar(List.of( //234.8 + 23428 + - 8762 * 23 ** 3 ** 4 / + 13 * - 1 % 9 - 3.6 === 23667.2
				new NumeroLiteral(234.8), 
				OperadorNumericoBinario.SOMA, 
				new NumeroLiteral(23428),
				OperadorNumericoBinario.SOMA,
				OperadorNumericoUnario.NEGATIVO,
				new NumeroLiteral(8762),
				OperadorNumericoBinario.MULTIPLICACAO,
				new NumeroLiteral(23),
				OperadorNumericoBinario.EXPONENCIACAO,
				new NumeroLiteral(3),
				OperadorNumericoBinario.EXPONENCIACAO,
				new NumeroLiteral(4),
				OperadorNumericoBinario.DIVISAO,
				OperadorNumericoUnario.POSITIVO,
				new NumeroLiteral(13),
				OperadorNumericoBinario.MULTIPLICACAO,
				OperadorNumericoUnario.NEGATIVO,
				new NumeroLiteral(1),
				OperadorNumericoBinario.MODULO,
				new NumeroLiteral(9),
				OperadorNumericoBinario.SUBTRACAO,
				new NumeroLiteral(3.6)
				)).obterValor() == 23667.2);
	}
	
	private static void testarBooleanos() {
		
		asseverar(Booleano.processar(List.of(
				Booleano.VERDADEIRO
				)).obterValor() == true);

		asseverar(Booleano.processar(List.of(
				Booleano.FALSO
				)).obterValor() == false);

		
		asseverar(Booleano.processar(List.of(
				OperadorBooleano.NAO, Booleano.VERDADEIRO
				)).obterValor() == false);

		asseverar(Booleano.processar(List.of(
				OperadorBooleano.NAO, Booleano.FALSO
				)).obterValor() == true);
		
		
		asseverar(Booleano.processar(List.of(
				Booleano.VERDADEIRO, OperadorBooleano.E, Booleano.VERDADEIRO 
				)).obterValor() == true);
		
		asseverar(Booleano.processar(List.of(
				Booleano.VERDADEIRO, OperadorBooleano.E, Booleano.FALSO 
				)).obterValor() == false);
		
		asseverar(Booleano.processar(List.of(
				Booleano.FALSO, OperadorBooleano.E, Booleano.VERDADEIRO 
				)).obterValor() == false);
		
		asseverar(Booleano.processar(List.of(
				Booleano.FALSO, OperadorBooleano.E, Booleano.FALSO 
				)).obterValor() == false);
		
		
		asseverar(Booleano.processar(List.of(
				Booleano.VERDADEIRO, OperadorBooleano.OU, Booleano.VERDADEIRO 
				)).obterValor() == true);
		
		asseverar(Booleano.processar(List.of(
				Booleano.VERDADEIRO, OperadorBooleano.OU, Booleano.FALSO 
				)).obterValor() == true);
		
		asseverar(Booleano.processar(List.of(
				Booleano.FALSO, OperadorBooleano.OU, Booleano.VERDADEIRO 
				)).obterValor() == true);
		
		asseverar(Booleano.processar(List.of(
				Booleano.FALSO, OperadorBooleano.OU, Booleano.FALSO 
				)).obterValor() == false);
		
		
		asseverar(Booleano.processar(List.of(
				Booleano.VERDADEIRO, OperadorBooleano.E, Booleano.VERDADEIRO, OperadorBooleano.E, Booleano.VERDADEIRO
				)).obterValor() == true);
		
		asseverar(Booleano.processar(List.of(
				Booleano.VERDADEIRO, OperadorBooleano.E, Booleano.FALSO, OperadorBooleano.E, Booleano.VERDADEIRO
				)).obterValor() == false);
		
		asseverar(Booleano.processar(List.of(
				Booleano.FALSO, OperadorBooleano.OU, Booleano.VERDADEIRO, OperadorBooleano.OU, Booleano.FALSO
				)).obterValor() == true);
		
		asseverar(Booleano.processar(List.of(
				Booleano.VERDADEIRO, OperadorBooleano.OU, Booleano.FALSO, OperadorBooleano.OU, Booleano.VERDADEIRO
				)).obterValor() == true);
		
		asseverar(Booleano.processar(List.of(
				Booleano.FALSO, OperadorBooleano.OU, Booleano.FALSO, OperadorBooleano.OU, Booleano.FALSO
				)).obterValor() == false);
		
		
		asseverar(Booleano.processar(List.of(
				Booleano.VERDADEIRO, OperadorBooleano.E, OperadorBooleano.NAO, Booleano.VERDADEIRO 
				)).obterValor() == false);
		
		asseverar(Booleano.processar(List.of(
				Booleano.VERDADEIRO, OperadorBooleano.E, OperadorBooleano.NAO, Booleano.FALSO 
				)).obterValor() == true);
		
		asseverar(Booleano.processar(List.of(
				OperadorBooleano.NAO, Booleano.VERDADEIRO, OperadorBooleano.E, Booleano.VERDADEIRO 
				)).obterValor() == false);
		
		asseverar(Booleano.processar(List.of(
				OperadorBooleano.NAO, Booleano.VERDADEIRO, OperadorBooleano.E, Booleano.FALSO 
				)).obterValor() == false);
		
		
		asseverar(Booleano.processar(List.of(
				Booleano.FALSO, OperadorBooleano.E, OperadorBooleano.NAO, Booleano.VERDADEIRO 
				)).obterValor() == false);
		
		asseverar(Booleano.processar(List.of(
				Booleano.FALSO, OperadorBooleano.E, OperadorBooleano.NAO, Booleano.FALSO 
				)).obterValor() == false);
		
		asseverar(Booleano.processar(List.of(
				OperadorBooleano.NAO, Booleano.FALSO, OperadorBooleano.E, Booleano.VERDADEIRO 
				)).obterValor() == true);
		
		asseverar(Booleano.processar(List.of(
				OperadorBooleano.NAO, Booleano.FALSO, OperadorBooleano.E, Booleano.FALSO 
				)).obterValor() == false);
		
		
		asseverar(Booleano.processar(List.of(
				Booleano.VERDADEIRO, OperadorBooleano.OU, OperadorBooleano.NAO, Booleano.VERDADEIRO 
				)).obterValor() == true);
		
		asseverar(Booleano.processar(List.of(
				Booleano.VERDADEIRO, OperadorBooleano.OU, OperadorBooleano.NAO, Booleano.FALSO 
				)).obterValor() == true);
		
		asseverar(Booleano.processar(List.of(
				OperadorBooleano.NAO, Booleano.VERDADEIRO, OperadorBooleano.OU, Booleano.VERDADEIRO 
				)).obterValor() == true);
		
		asseverar(Booleano.processar(List.of(
				OperadorBooleano.NAO, Booleano.VERDADEIRO, OperadorBooleano.OU, Booleano.FALSO 
				)).obterValor() == false);
		
		
		asseverar(Booleano.processar(List.of(
				Booleano.FALSO, OperadorBooleano.OU, OperadorBooleano.NAO, Booleano.VERDADEIRO 
				)).obterValor() == false);
		
		asseverar(Booleano.processar(List.of(
				Booleano.FALSO, OperadorBooleano.OU, OperadorBooleano.NAO, Booleano.FALSO 
				)).obterValor() == true);
		
		asseverar(Booleano.processar(List.of(
				OperadorBooleano.NAO, Booleano.FALSO, OperadorBooleano.OU, Booleano.VERDADEIRO 
				)).obterValor() == true);
		
		asseverar(Booleano.processar(List.of(
				OperadorBooleano.NAO, Booleano.FALSO, OperadorBooleano.OU, Booleano.FALSO 
				)).obterValor() == true);
		
		asseverar(Booleano.processar(List.of(// true && false || ! true || ! false && true && false || ! false === true
				Booleano.VERDADEIRO, 
				OperadorBooleano.E, 
				Booleano.FALSO,
				OperadorBooleano.OU,
				OperadorBooleano.NAO,
				Booleano.VERDADEIRO,
				OperadorBooleano.OU,
				OperadorBooleano.NAO,
				Booleano.FALSO,
				OperadorBooleano.E,
				Booleano.VERDADEIRO,
				OperadorBooleano.E,
				Booleano.FALSO,
				OperadorBooleano.OU,
				OperadorBooleano.NAO,
				Booleano.FALSO
				)).obterValor() == true);
	}
	
	private static void asseverar(boolean condicao) {
		asseverar(condicao, "");
	}

	private static void asseverar(boolean condicao, String mensagem) {
		if (condicao)
			return;
		
		throw new AssertionError(mensagem);
	}
}
