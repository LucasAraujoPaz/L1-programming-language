import java.util.ArrayList;
import java.util.List;

public interface Booleano extends Expressao {
	
	public static final Verdadeiro VERDADEIRO = new Verdadeiro() {};
	public static final Falso FALSO = new Falso() {};
	public Boolean obterValorNativo();
	
	public static Booleano processar(final List<Object> lista) {

		final List<Object> semNegacao = new ArrayList<>();
		for (int i = 0; i < lista.size(); ++i) {
			if (lista.get(i) == OperadorBooleano.NAO)
				semNegacao.add(new ExpressaoNao((Booleano) lista.get(i++ + 1)));
			else
				semNegacao.add(lista.get(i));
		}

		final List<Object> semE = new ArrayList<>();
		for (int i = 0; i < semNegacao.size(); ++i) {
			if (semNegacao.get(i) == OperadorBooleanoBinario.E)
				semE.add(new ExpressaoE((Booleano) semE.remove(semE.size() - 1), (Booleano) semNegacao.get(i++ + 1)));
			else
				semE.add(semNegacao.get(i));
		}

		Booleano semOu = (Booleano) semE.get(0);
		for (int i = 1; i < semE.size(); i += 2) {
			semOu = new ExpressaoOu(semOu, (Booleano) semE.get(i + 1));
		}

		return semOu;
	}	
}

interface OperadorBooleano {

	public static final OperadorBooleano
		NAO = new OperadorBooleano() {};
}

interface OperadorBooleanoBinario extends OperadorBooleano {

	public static final OperadorBooleanoBinario
		IGUAL = new OperadorBooleanoBinario() {
			@Override
			public Booleano obterExpressao(Expressao esquerda, Expressao direita) {
				return new ExpressaoIgual(esquerda, direita);
			}
		}, 
		DIFERENTE = new OperadorBooleanoBinario() {
			@Override
			public Booleano obterExpressao(Expressao esquerda, Expressao direita) {
				return new ExpressaoDiferente(esquerda, direita);
			}
		}, 
		MAIOR = new OperadorBooleanoBinario() {
			@Override
			public Booleano obterExpressao(Expressao esquerda, Expressao direita) {
				return new ExpressaoMaior((Numero) esquerda, (Numero) direita);
			}
		},
		MENOR = new OperadorBooleanoBinario() {
			@Override
			public Booleano obterExpressao(Expressao esquerda, Expressao direita) {
				return new ExpressaoMenor((Numero) esquerda, (Numero) direita);
			}
		},
		MAIOR_OU_IGUAL = new OperadorBooleanoBinario() {
			@Override
			public Booleano obterExpressao(Expressao esquerda, Expressao direita) {
				return new ExpressaoMaiorOuIgual((Numero) esquerda, (Numero) direita);
			}
		},
		MENOR_OU_IGUAL = new OperadorBooleanoBinario() {
			@Override
			public Booleano obterExpressao(Expressao esquerda, Expressao direita) {
				return new ExpressaoMenorOuIgual((Numero) esquerda, (Numero) direita);
			}
		},
		E = new OperadorBooleanoBinario() {
			@Override
			public Booleano obterExpressao(Expressao esquerda, Expressao direita) {
				return new ExpressaoE((Booleano) esquerda, (Booleano) direita);
			}
		}, 
		OU = new OperadorBooleanoBinario() {
			@Override
			public Booleano obterExpressao(Expressao esquerda, Expressao direita) {
				return new ExpressaoDiferente(esquerda, direita);
			}
		};
	
	public Booleano obterExpressao(final Expressao esquerda, final Expressao direita);
}

class Verdadeiro implements Booleano, ExpressaoSimples {
	@Override
	public Boolean obterValorNativo() {
		return true;
	}
}

class Falso implements Booleano, ExpressaoSimples {
	@Override
	public Boolean obterValorNativo() {
		return false;
	}
}

class ExpressaoIgual implements Booleano, ExpressaoComplexa {

	final Expressao esquerda, direita;

	ExpressaoIgual(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Boolean obterValorNativo() {
		return esquerda.obterValorNativo() == direita.obterValorNativo();
	}
	
	@Override
	public ExpressaoSimples obterValorPrimitivo() {
		return obterValorNativo() ? Booleano.VERDADEIRO : Booleano.FALSO;
	}
}

class ExpressaoDiferente implements Booleano, ExpressaoComplexa {

	final Expressao esquerda, direita;

	ExpressaoDiferente(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Boolean obterValorNativo() {
		return esquerda.obterValorNativo() != direita.obterValorNativo();
	}
	
	@Override
	public ExpressaoSimples obterValorPrimitivo() {
		return obterValorNativo() ? Booleano.VERDADEIRO : Booleano.FALSO;
	}
}

class ExpressaoMaior implements Booleano, ExpressaoComplexa {

	final Expressao esquerda, direita;

	ExpressaoMaior(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Boolean obterValorNativo() {
		return (Double) esquerda.obterValorNativo() > (Double) direita.obterValorNativo();
	}
	
	@Override
	public ExpressaoSimples obterValorPrimitivo() {
		return obterValorNativo() ? Booleano.VERDADEIRO : Booleano.FALSO;
	}
}

class ExpressaoMenor implements Booleano, ExpressaoComplexa {

	final Expressao esquerda, direita;

	ExpressaoMenor(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Boolean obterValorNativo() {
		return (Double) esquerda.obterValorNativo() < (Double) direita.obterValorNativo();
	}
	
	@Override
	public ExpressaoSimples obterValorPrimitivo() {
		return obterValorNativo() ? Booleano.VERDADEIRO : Booleano.FALSO;
	}
}

class ExpressaoMaiorOuIgual implements Booleano, ExpressaoComplexa {

	final Expressao esquerda, direita;

	ExpressaoMaiorOuIgual(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Boolean obterValorNativo() {
		return (Double) esquerda.obterValorNativo() >= (Double) direita.obterValorNativo();
	}
	
	@Override
	public ExpressaoSimples obterValorPrimitivo() {
		return obterValorNativo() ? Booleano.VERDADEIRO : Booleano.FALSO;
	}
}

class ExpressaoMenorOuIgual implements Booleano, ExpressaoComplexa {

	final Expressao esquerda, direita;

	ExpressaoMenorOuIgual(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Boolean obterValorNativo() {
		return (Double) esquerda.obterValorNativo() <= (Double) direita.obterValorNativo();
	}
	
	@Override
	public ExpressaoSimples obterValorPrimitivo() {
		return obterValorNativo() ? Booleano.VERDADEIRO : Booleano.FALSO;
	}
}

class ExpressaoNao implements Booleano, ExpressaoComplexa {

	final Expressao negado;

	ExpressaoNao(final Expressao negado) {
		this.negado = negado;
	}

	@Override
	public Boolean obterValorNativo() {
		return ! (Boolean) negado.obterValorNativo();
	}

	@Override
	public ExpressaoSimples obterValorPrimitivo() {
		return obterValorNativo() ? Booleano.VERDADEIRO : Booleano.FALSO;
	}
}

class ExpressaoE implements Booleano, ExpressaoComplexa {

	final Expressao esquerda, direita;

	ExpressaoE(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Boolean obterValorNativo() {
		return (Boolean) esquerda.obterValorNativo() && (Boolean) direita.obterValorNativo();
	}
	
	@Override
	public ExpressaoSimples obterValorPrimitivo() {
		return obterValorNativo() ? Booleano.VERDADEIRO : Booleano.FALSO;
	}
}

class ExpressaoOu implements Booleano, ExpressaoComplexa {

	final Expressao esquerda, direita;

	ExpressaoOu(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Boolean obterValorNativo() {
		return (Boolean) esquerda.obterValorNativo() || (Boolean) direita.obterValorNativo();
	}
	
	@Override
	public ExpressaoSimples obterValorPrimitivo() {
		return obterValorNativo() ? Booleano.VERDADEIRO : Booleano.FALSO;
	}
}