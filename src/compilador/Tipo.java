package compilador;

interface Tipo {
	static Tipo QUALQUER = new Tipo() {
	};
	static Tipo NUMERO = new Tipo() {
	};
	static Tipo BOOLEANO = new Tipo() {
	};
	static Tipo TEXTO = new Tipo() {
	};
	static class Lista implements Tipo {
		final Tipo interno;
		public Lista(Tipo interno) {
			this.interno = interno;
		}
	}
	static class Funcao implements Tipo {
		final Tipo tipoDoParametro;
		final Tipo tipoDoRetorno;
		public Funcao(Tipo tipoDoParametro, Tipo tipoDoRetorno) {
			this.tipoDoParametro = tipoDoParametro;
			this.tipoDoRetorno = tipoDoRetorno;
		}
	}
}