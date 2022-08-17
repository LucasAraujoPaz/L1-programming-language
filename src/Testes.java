import java.util.List;

public class Testes {

	public static void main(String[] args) {
		testarNumeros();
		testarBooleanos();
	}

	private static void testarNumeros() {
		
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
