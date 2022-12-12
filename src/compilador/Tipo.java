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
	}
	static class Funcao implements Tipo {
	}
}