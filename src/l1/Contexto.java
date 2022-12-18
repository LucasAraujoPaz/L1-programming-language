package l1;
import java.util.Map;
import java.util.Optional;

class Contexto {
	final Optional<Contexto> pai;
	final Optional<Funcao> funcao;
	final Map<String, Expressao> tabelaDeSimbolos;
	
	Contexto(Optional<Contexto> pai, Optional<Funcao> funcao, Map<String, Expressao> tabelaDeSimbolos) {
		this.pai = pai;
		this.funcao = funcao;
		this.tabelaDeSimbolos = tabelaDeSimbolos;
		if (funcao.isPresent())
			tabelaDeSimbolos.put(funcao.get().getParametro().nome, funcao.get().getParametro());
	}
	
	Declaracao declarar(Token token, Expressao expressao) {
		String nome = token.texto();
		Parser.asseverar( ! tabelaDeSimbolos.containsKey(nome), "Não é possível redeclarar nome " + nome, Optional.ofNullable(token));
		var declaracao = new Declaracao(token, expressao);
		this.tabelaDeSimbolos.put(nome, declaracao);
		return declaracao;
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
