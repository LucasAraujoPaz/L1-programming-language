import java.util.ArrayList;
import java.util.List;

public interface Booleano extends BooleanoOuOperadorBooleano {

	public static final Booleano VERDADEIRO = new Booleano() {
		@Override
		public boolean obterValor() {
			return true;
		}
	};

	public static final Booleano FALSO = new Booleano() {
		@Override
		public boolean obterValor() {
			return false;
		}
	};

	public static Booleano processar(final List<BooleanoOuOperadorBooleano> lista) {

		final List<BooleanoOuOperadorBooleano> semNegacao = new ArrayList<>();
		for (int i = 0; i < lista.size(); ++i) {
			if (lista.get(i) == OperadorBooleano.NAO)
				semNegacao.add(new ExpressaoNao((Booleano) lista.get(i++ + 1)));
			else
				semNegacao.add(lista.get(i));
		}

		final List<BooleanoOuOperadorBooleano> semE = new ArrayList<>();
		for (int i = 0; i < semNegacao.size(); ++i) {
			if (semNegacao.get(i) == OperadorBooleano.E)
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
	
	public boolean obterValor();
}

interface OperadorBooleano extends BooleanoOuOperadorBooleano {

	public static final OperadorBooleano 
		NAO = new OperadorBooleano() {}, 
		E = new OperadorBooleano() {}, 
		OU = new OperadorBooleano() {};
}

interface BooleanoOuOperadorBooleano {
}

class ExpressaoNao implements Booleano {

	final Booleano negado;

	ExpressaoNao(final Booleano negado) {
		this.negado = negado;
	}

	@Override
	public boolean obterValor() {
		return !negado.obterValor();
	}
}

class ExpressaoE implements Booleano {

	final Booleano esquerda, direita;

	ExpressaoE(final Booleano esquerda, final Booleano direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public boolean obterValor() {
		return esquerda.obterValor() && direita.obterValor();
	}
}

class ExpressaoOu implements Booleano {

	final Booleano esquerda, direita;

	ExpressaoOu(final Booleano esquerda, final Booleano direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public boolean obterValor() {
		return esquerda.obterValor() || direita.obterValor();
	}
}