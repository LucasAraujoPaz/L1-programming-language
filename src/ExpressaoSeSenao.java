import java.util.List;

public class ExpressaoSeSenao implements ExpressaoComplexa<Expressao, Object> {
	List<Booleano> condicoes;
	List<Expressao> corpos;
	public ExpressaoSeSenao(List<Booleano> condicoes, List<Expressao> corpos) {
		Testes.asseverar(condicoes.size() == corpos.size() - 1, "É preciso que haja 1 corpo a mais que o número de condições");
		this.condicoes = condicoes;
		this.corpos = corpos;
	}
	@Override
	public ExpressaoSimples obterValorPrimitivo() {
		for (int i = 0; i < condicoes.size(); ++i)
			if (condicoes.get(i).obterValorNativo())
				return corpos.get(i).obterValorPrimitivo();
		
		return corpos.get(condicoes.size()).obterValorPrimitivo();
	}
	@Override
	public Object obterValorNativo() {
		return null;
	}
}
