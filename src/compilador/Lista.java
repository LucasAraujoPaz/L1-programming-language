package compilador;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface Lista extends Expressao {
}

class ListaLiteral implements Lista {

	final List<Expressao> lista;
	public ListaLiteral(List<Expressao> lista) {
		this.lista = lista;
	}
	@Override
	public Valor avaliar() {
		return new ListaAvaliada(lista.stream().map(Expressao::avaliar).toList());
	}
}
class ListaAvaliada implements Valor {

	final Optional<No> cabeca;
	public ListaAvaliada(List<Valor> lista) {
		Optional<No> no = Optional.empty();
		for (int i = lista.size() - 1; i >= 0; --i) {
			no = Optional.of( new No(lista.get(i), no) );
		}
		cabeca = no;
	}
	@Override
	public Object obterValorNativo() {
		List<Object> lista = new LinkedList<>();
		Optional<No> no = cabeca;
		while (no.isPresent()) {
			lista.add(no.get().valor.obterValorNativo());
			no = no.get().proximo;
		}
		return lista;
	}
}

class No {
	final Valor valor;
	final Optional<No> proximo;
	public No(Valor valor, Optional<No> proximo) {
		this.valor = valor;
		this.proximo = proximo;
	}
}