import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface Numero extends Expressao<Double>, NumeroOuOperadorNumerico {

	final static Set<OperadorNumerico> multiplicacaoDivisaoEModulo = Set.of(
			OperadorNumericoBinario.MULTIPLICACAO, OperadorNumericoBinario.DIVISAO, OperadorNumericoBinario.MODULO);
	
	public static Numero processar(final List<NumeroOuOperadorNumerico> lista) {
		
		final List<NumeroOuOperadorNumerico> semUnarios = new ArrayList<>();
		for (int i = 0; i < lista.size(); ++i) {
			if (lista.get(i) instanceof OperadorNumericoUnario unario)
				semUnarios.add(
						unario.obterExpressao(
								(Numero) lista.get(i++ + 1)
						));
			else
				semUnarios.add(lista.get(i));
		}
		
		final List<NumeroOuOperadorNumerico> semExponenciacao = new ArrayList<NumeroOuOperadorNumerico>();
		for (int i = semUnarios.size() - 1; i >= 0; --i) {
			if (semUnarios.get(i) == OperadorNumericoBinario.EXPONENCIACAO)
				semExponenciacao.add(0, 
						((OperadorNumericoBinario) semUnarios.get(i)).obterExpressao(
								(Numero) semUnarios.get(i-- - 1),
								(Numero) semExponenciacao.remove(0)
						));
			else
				semExponenciacao.add(0, semUnarios.get(i));
		}
		
		final List<NumeroOuOperadorNumerico> semMultiplicaoDivisaoNemModulo = new ArrayList<>();
		for (int i = 0; i < semExponenciacao.size(); ++i) {
			if (multiplicacaoDivisaoEModulo.contains(semExponenciacao.get(i)))
				semMultiplicaoDivisaoNemModulo.add(
						((OperadorNumericoBinario) semExponenciacao.get(i)).obterExpressao(
								(Numero) semMultiplicaoDivisaoNemModulo.remove(semMultiplicaoDivisaoNemModulo.size() - 1), 
								(Numero) semExponenciacao.get(i++ + 1)
						));
			else
				semMultiplicaoDivisaoNemModulo.add(semExponenciacao.get(i));
		}

		Numero semSomaNemSubtracao = (Numero) semMultiplicaoDivisaoNemModulo.get(0);
		for (int i = 1; i < semMultiplicaoDivisaoNemModulo.size(); i += 2) {
			semSomaNemSubtracao = ((OperadorNumericoBinario) semMultiplicaoDivisaoNemModulo.get(i)).obterExpressao(
					semSomaNemSubtracao, (Numero) semMultiplicaoDivisaoNemModulo.get(i + 1));
		}

		return semSomaNemSubtracao;
	}
}

interface OperadorNumerico extends NumeroOuOperadorNumerico {
}

interface OperadorNumericoUnario extends OperadorNumerico {
	
	public static final OperadorNumerico
		POSITIVO = new OperadorNumericoUnario() {
			@Override
			public Numero obterExpressao(Numero numero) {
				return numero;
			}},
		NEGATIVO = new OperadorNumericoUnario() {
			@Override
			public Numero obterExpressao(Numero numero) {
				return new ExpressaoNegacaoNumerica(numero);
			}};
		
	public Numero obterExpressao(final Numero numero);
}

interface OperadorNumericoBinario extends OperadorNumerico {
	
	public static final OperadorNumerico
		EXPONENCIACAO = new OperadorNumericoBinario() {
			@Override
			public Numero obterExpressao(Numero esquerda, Numero direita) {
				return new ExpressaoExponenciacao(esquerda, direita);
			}}, 
		MULTIPLICACAO = new OperadorNumericoBinario() {
			@Override
			public Numero obterExpressao(Numero esquerda, Numero direita) {
				return new ExpressaoMultiplicacao(esquerda, direita);
			}}, 
		DIVISAO = new OperadorNumericoBinario() {
			@Override
			public Numero obterExpressao(Numero esquerda, Numero direita) {
				return new ExpressaoDivisao(esquerda, direita);
			}},
		MODULO = new OperadorNumericoBinario() {
			@Override
			public Numero obterExpressao(Numero esquerda, Numero direita) {
				return new ExpressaoModulo(esquerda, direita);
			}},
		SOMA = new OperadorNumericoBinario() {
			@Override
			public Numero obterExpressao(Numero esquerda, Numero direita) {
				return new ExpressaoSoma(esquerda, direita);
			}},
		SUBTRACAO = new OperadorNumericoBinario() {
			@Override
			public Numero obterExpressao(Numero esquerda, Numero direita) {
				return new ExpressaoSubtracao(esquerda, direita);
			}};
	
	public Numero obterExpressao(final Numero esquerda, final Numero direita);	
}

interface NumeroOuOperadorNumerico {
}

class NumeroLiteral implements Numero {

	final double numero;
	
	public NumeroLiteral(final double numero) {
		this.numero = numero;
	}
	
	@Override
	public Double obterValor() {
		return numero;
	}
	
}

class ExpressaoNegacaoNumerica implements Numero {

	final Numero numero;

	ExpressaoNegacaoNumerica(final Numero numero) {
		this.numero = numero;
	}

	@Override
	public Double obterValor() {
		return - numero.obterValor();
	}
}

class ExpressaoExponenciacao implements Numero {

	final Numero esquerda, direita;

	ExpressaoExponenciacao(final Numero esquerda, final Numero direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Double obterValor() {
		return Math.pow(esquerda.obterValor(), direita.obterValor());
	}
}

class ExpressaoMultiplicacao implements Numero {

	final Numero esquerda, direita;

	ExpressaoMultiplicacao(final Numero esquerda, final Numero direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Double obterValor() {
		return esquerda.obterValor() * direita.obterValor();
	}
}

class ExpressaoDivisao implements Numero {

	final Numero esquerda, direita;

	ExpressaoDivisao(final Numero esquerda, final Numero direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Double obterValor() {
		return esquerda.obterValor() / direita.obterValor();
	}
}

class ExpressaoModulo implements Numero {

	final Numero esquerda, direita;

	ExpressaoModulo(final Numero esquerda, final Numero direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Double obterValor() {
		return esquerda.obterValor() % direita.obterValor();
	}
}

class ExpressaoSoma implements Numero {

	final Numero esquerda, direita;

	ExpressaoSoma(final Numero esquerda, final Numero direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Double obterValor() {
		return esquerda.obterValor() + direita.obterValor();
	}
}

class ExpressaoSubtracao implements Numero {

	final Numero esquerda, direita;

	ExpressaoSubtracao(final Numero esquerda, final Numero direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Double obterValor() {
		return esquerda.obterValor() - direita.obterValor();
	}
}