import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface Numero extends Expressao {

	final static Set<OperadorNumerico> multiplicacaoDivisaoEModulo = Set.of(
			OperadorNumericoBinario.MULTIPLICACAO, OperadorNumericoBinario.DIVISAO, OperadorNumericoBinario.MODULO);
	
	public static Numero processar(final List<?> lista) {
		
		final List<Object> semUnarios = new ArrayList<>();
		for (int i = 0; i < lista.size(); ++i) {
			if (lista.get(i) instanceof OperadorNumericoUnario unario)
				semUnarios.add(
						unario.obterExpressao(
								(Numero) lista.get(i++ + 1)
						));
			else
				semUnarios.add(lista.get(i));
		}
		
		final List<Object> semExponenciacao = new ArrayList<>();
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
		
		final List<Object> semMultiplicaoDivisaoNemModulo = new ArrayList<>();
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
			semSomaNemSubtracao = (Numero) ((OperadorNumericoBinario) semMultiplicaoDivisaoNemModulo.get(i)).obterExpressao(
					semSomaNemSubtracao, (Numero) semMultiplicaoDivisaoNemModulo.get(i + 1));
		}

		return semSomaNemSubtracao;
	}
}

interface OperadorNumerico {
}

interface OperadorNumericoUnario extends OperadorNumerico {
	
	public static final OperadorNumerico
		POSITIVO = new OperadorNumericoUnario() {
			@Override
			public Expressao obterExpressao(Expressao numero) {
				return numero;
			}},
		NEGATIVO = new OperadorNumericoUnario() {
			@Override
			public Expressao obterExpressao(Expressao numero) {
				return new ExpressaoNegacaoNumerica(numero);
			}};
		
	public Expressao obterExpressao(final Expressao numero);
}

interface OperadorNumericoBinario extends OperadorNumerico {
	
	public static final OperadorNumerico
		EXPONENCIACAO = new OperadorNumericoBinario() {
			@Override
			public Expressao obterExpressao(Expressao esquerda, Expressao direita) {
				return new ExpressaoExponenciacao(esquerda, direita);
			}}, 
		MULTIPLICACAO = new OperadorNumericoBinario() {
			@Override
			public Expressao obterExpressao(Expressao esquerda, Expressao direita) {
				return new ExpressaoMultiplicacao(esquerda, direita);
			}}, 
		DIVISAO = new OperadorNumericoBinario() {
			@Override
			public Expressao obterExpressao(Expressao esquerda, Expressao direita) {
				return new ExpressaoDivisao(esquerda, direita);
			}},
		MODULO = new OperadorNumericoBinario() {
			@Override
			public Expressao obterExpressao(Expressao esquerda, Expressao direita) {
				return new ExpressaoModulo(esquerda, direita);
			}},
		SOMA = new OperadorNumericoBinario() {
			@Override
			public Expressao obterExpressao(Expressao esquerda, Expressao direita) {
				return new ExpressaoSoma(esquerda, direita);
			}},
		SUBTRACAO = new OperadorNumericoBinario() {
			@Override
			public Expressao obterExpressao(Expressao esquerda, Expressao direita) {
				return new ExpressaoSubtracao(esquerda, direita);
			}};
	
	public Expressao obterExpressao(final Expressao esquerda, final Expressao direita);	
}

class NumeroReal implements Valor {
	final double numero;
	public NumeroReal(double numero) {
		this.numero = numero;
	}
	@Override
	public Object obterValorNativo() {
		return numero;
	}
}

class NumeroLiteral implements Numero {

	final double numero;
	
	public NumeroLiteral(final double numero) {
		this.numero = numero;
	}
	
	@Override
	public Valor avaliar() {
		return new NumeroReal(numero);
	}
}

class ExpressaoNegacaoNumerica implements Numero {

	final Expressao numero;

	ExpressaoNegacaoNumerica(final Expressao numero) {
		this.numero = numero;
	}

	@Override
	public Valor avaliar() {
		return new NumeroReal( - (Double) numero.avaliar().obterValorNativo());
	}
}

class ExpressaoExponenciacao implements Numero {

	final Expressao esquerda, direita;

	ExpressaoExponenciacao(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Valor avaliar() {
		return new NumeroReal(Math.pow(
				(Double) esquerda.avaliar().obterValorNativo(),
				(Double) direita.avaliar().obterValorNativo())
		);
	}
}

class ExpressaoMultiplicacao implements Numero {

	final Expressao esquerda, direita;

	ExpressaoMultiplicacao(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}
	
	@Override
	public Valor avaliar() {
		return new NumeroReal(
				(Double) esquerda.avaliar().obterValorNativo() * 
				(Double) direita.avaliar().obterValorNativo()
		);
	}
}

class ExpressaoDivisao implements Numero {

	final Expressao esquerda, direita;

	ExpressaoDivisao(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Valor avaliar() {
		return new NumeroReal(
				(Double) esquerda.avaliar().obterValorNativo() / 
				(Double) direita.avaliar().obterValorNativo()
		);
	}
}

class ExpressaoModulo implements Numero {

	final Expressao esquerda, direita;

	ExpressaoModulo(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Valor avaliar() {
		return new NumeroReal(
				(Double) esquerda.avaliar().obterValorNativo() % 
				(Double) direita.avaliar().obterValorNativo()
		);
	}
}

class ExpressaoSoma implements Numero {

	final Expressao esquerda, direita;

	ExpressaoSoma(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Valor avaliar() {
		return new NumeroReal(
				(Double) esquerda.avaliar().obterValorNativo() + 
				(Double) direita.avaliar().obterValorNativo()
		);
	}
}

class ExpressaoSubtracao implements Numero {

	final Expressao esquerda, direita;

	ExpressaoSubtracao(final Expressao esquerda, final Expressao direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	@Override
	public Valor avaliar() {
		return new NumeroReal(
				(Double) esquerda.avaliar().obterValorNativo() - 
				(Double) direita.avaliar().obterValorNativo()
		);
	}
}