import java.util.ArrayList;
import java.util.List;

public interface Booleano extends Expressao {
	
	@Override
	ValorBooleano avaliar();
	
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

class BooleanoLiteral implements Booleano {
	public static final BooleanoLiteral VERDADEIRO = new BooleanoLiteral(true) {};
	public static final BooleanoLiteral FALSO = new BooleanoLiteral(false) {};
	
	boolean valor;
	public BooleanoLiteral(boolean valor) {
		this.valor = valor;
	}
	@Override
	public ValorBooleano avaliar() {
		return new ValorBooleano(valor);
	}
}

class ValorBooleano implements Valor {
	public static final ValorBooleano VERDADEIRO = new ValorBooleano(true) {};
	public static final ValorBooleano FALSO = new ValorBooleano(false) {};
	
	boolean valor;
	public ValorBooleano(boolean valor) {
		this.valor = valor;
	}
	@Override
	public Boolean obterValorNativo() {
		return valor;
	}
}

class ExpressaoIgual implements Booleano {

	final Expressao esquerda, direita;

	ExpressaoIgual(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public ValorBooleano avaliar() {
		return esquerda.avaliar().obterValorNativo().equals(direita.avaliar().obterValorNativo()) ?
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}

class ExpressaoDiferente implements Booleano {

	final Expressao esquerda, direita;

	ExpressaoDiferente(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}
	
	@Override
	public ValorBooleano avaliar() {
		return ! esquerda.avaliar().obterValorNativo().equals(direita.avaliar().obterValorNativo()) ?
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}

class ExpressaoMaior implements Booleano {

	final Expressao esquerda, direita;

	ExpressaoMaior(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public ValorBooleano avaliar() {
		return (Double) esquerda.avaliar().obterValorNativo() > (Double) direita.avaliar().obterValorNativo() ? 
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}

class ExpressaoMenor implements Booleano {

	final Expressao esquerda, direita;

	ExpressaoMenor(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public ValorBooleano avaliar() {
		return (Double) esquerda.avaliar().obterValorNativo() < (Double) direita.avaliar().obterValorNativo() ? 
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}

class ExpressaoMaiorOuIgual implements Booleano {

	final Expressao esquerda, direita;

	ExpressaoMaiorOuIgual(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public ValorBooleano avaliar() {
		return (Double) esquerda.avaliar().obterValorNativo() >= (Double) direita.avaliar().obterValorNativo() ? 
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}

class ExpressaoMenorOuIgual implements Booleano {

	final Expressao esquerda, direita;

	ExpressaoMenorOuIgual(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public ValorBooleano avaliar() {
		return (Double) esquerda.avaliar().obterValorNativo() <= (Double) direita.avaliar().obterValorNativo() ? 
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}

class ExpressaoNao implements Booleano {

	final Expressao negado;

	ExpressaoNao(final Expressao negado) {
		this.negado = negado;
	}

	@Override
	public ValorBooleano avaliar() {
		return ! (Boolean) negado.avaliar().obterValorNativo() ? 
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}

class ExpressaoE implements Booleano {

	final Expressao esquerda, direita;

	ExpressaoE(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public ValorBooleano avaliar() {
		return (Boolean) esquerda.avaliar().obterValorNativo() && (Boolean) direita.avaliar().obterValorNativo() ? 
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}

class ExpressaoOu implements Booleano {

	final Expressao esquerda, direita;

	ExpressaoOu(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}
	
	@Override
	public ValorBooleano avaliar() {
		return (Boolean) esquerda.avaliar().obterValorNativo() || (Boolean) direita.avaliar().obterValorNativo() ? 
				ValorBooleano.VERDADEIRO : ValorBooleano.FALSO;
	}
}