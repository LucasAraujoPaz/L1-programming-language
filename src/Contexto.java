import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class Contexto {
	Optional<Contexto> pai;
	Optional<Funcao> funcao;
	Map<String, Expressao> tabelaDeSimbolos = new HashMap<>();
	
	Contexto(Optional<Contexto> pai, Optional<Funcao> funcao) {
		this.pai = pai;
		this.funcao = funcao;
		if (funcao.isPresent())
			tabelaDeSimbolos.put(funcao.get().obterParametro().nome, funcao.get().obterParametro());
	}
	
	void declarar(Token token, Expressao expressao) {
		String nome = token.texto();
		Parser.asseverar( ! tabelaDeSimbolos.containsKey(nome), "Não é possível redeclarar nome " + nome, Optional.ofNullable(token));
		this.tabelaDeSimbolos.put(nome, expressao);
	}
	
	Expressao obter(Token token) {
		String nome = token.texto();
		final boolean possui = tabelaDeSimbolos.containsKey(nome);
		
		if ( ! possui && pai.isPresent()) {
			var vindoDoPai = pai.get().obter(token);
			if (funcao.isPresent() && vindoDoPai instanceof Parametro p) {
				funcao.get().putUpvalue(p);
			}
			return vindoDoPai;
		}
		
		Parser.asseverar(possui, "Nome não declarado: " + nome, Optional.ofNullable(token));
		return tabelaDeSimbolos.get(nome);
	}
}
