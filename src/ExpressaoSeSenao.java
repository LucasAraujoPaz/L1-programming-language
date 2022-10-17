import java.util.List;

public class ExpressaoSeSenao implements Expressao {
	List<Booleano> condicoes;
	List<Expressao> corpos;
	public ExpressaoSeSenao(List<Booleano> condicoes, List<Expressao> corpos) {
		Testes.asseverar(condicoes.size() == corpos.size() - 1, 
				"É preciso que haja 1 corpo a mais que o número de condições");
		this.condicoes = condicoes;
		this.corpos = corpos;
	}
	@Override
	public Valor avaliar() {
		for (int i = 0; i < condicoes.size(); ++i)
			if ((Boolean) condicoes.get(i).avaliar().obterValorNativo())
				return corpos.get(i).avaliar();
		
		return corpos.get(condicoes.size()).avaliar();
	}
}
