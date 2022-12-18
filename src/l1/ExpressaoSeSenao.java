package l1;
import java.util.List;

public class ExpressaoSeSenao implements Expressao {
	List<Expressao> condicoes;
	List<Expressao> corpos;
	final Tipo tipo;
	public ExpressaoSeSenao(List<Expressao> condicoes, List<Expressao> corpos) {
		Testes.asseverar(condicoes.size() == corpos.size() - 1, 
				"É preciso que haja 1 corpo a mais que o número de condições");
		this.condicoes = condicoes;
		this.corpos = corpos;
		this.tipo = Tipo.obterTipoFinal(corpos);
	}
	@Override
	public Valor avaliar() {
		for (int i = 0; i < condicoes.size(); ++i)
			if ((Boolean) condicoes.get(i).avaliar().obterValorNativo())
				return corpos.get(i).avaliar();
		
		return corpos.get(condicoes.size()).avaliar();
	}
	@Override
	public Tipo obterTipo() {
		return this.tipo;
	}
}
