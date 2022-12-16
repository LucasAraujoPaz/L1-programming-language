package compilador;

import java.util.List;

interface Tipo {
	static Tipo QUALQUER = new Tipo() {
		@Override
		public boolean ehSubtipoDe(Tipo tipo) {
			return tipo.equals(Tipo.QUALQUER);
		}
	};
	static Tipo NUMERO = new Tipo() {
		@Override
		public boolean ehSubtipoDe(Tipo tipo) {
			return tipo.equals(NUMERO) || tipo.equals(Tipo.QUALQUER);
		}
	};
	static Tipo BOOLEANO = new Tipo() {
		@Override
		public boolean ehSubtipoDe(Tipo tipo) {
			return tipo.equals(BOOLEANO) || tipo.equals(Tipo.QUALQUER);
		}
	};
	static Tipo TEXTO = new Tipo() {
		@Override
		public boolean ehSubtipoDe(Tipo tipo) {
			return tipo.equals(TEXTO) || tipo.equals(Tipo.QUALQUER);
		}
	};
	static class Lista implements Tipo {
		final Tipo interno;
		public Lista(Tipo interno) {
			this.interno = interno;
		}
		@Override
		public boolean ehSubtipoDe(Tipo tipo) {
			return tipo.equals(Tipo.QUALQUER) || 
					tipo instanceof Tipo.Lista l && this.interno.ehSubtipoDe(l.interno);
		}
	}
	static class Funcao implements Tipo {
		final Tipo tipoDoParametro;
		final Tipo tipoDoRetorno;
		public Funcao(Tipo tipoDoParametro, Tipo tipoDoRetorno) {
			this.tipoDoParametro = tipoDoParametro;
			this.tipoDoRetorno = tipoDoRetorno;
		}
		@Override
		public boolean ehSubtipoDe(Tipo tipo) {
			return tipo.equals(Tipo.QUALQUER) ||
					tipo instanceof Tipo.Funcao f && 
					f.tipoDoParametro.ehSubtipoDe(this.tipoDoParametro) &&
					this.tipoDoRetorno.ehSubtipoDe(f.tipoDoRetorno);
		}
	}
	
	boolean ehSubtipoDe(Tipo tipo);
	
	static Tipo obterTipoFinal(List<Expressao> lista) {
		Tipo tipoInicial = lista.isEmpty() ? Tipo.QUALQUER : lista.get(0).obterTipo();
		
		Tipo tipoFinal = lista.stream()
				.map(Expressao::obterTipo)
				.reduce(tipoInicial, 
					(tipoAcumulado, tipo) -> tipo.ehSubtipoDe(tipoAcumulado) ? tipoAcumulado : 
						tipoAcumulado.ehSubtipoDe(tipo) ? tipo : 
							Tipo.QUALQUER);
		return tipoFinal;
	}
}