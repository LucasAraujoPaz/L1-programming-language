public interface Nulo extends Expressao<Nulo> {

	public final static Nulo NULO = new Nulo() {

		@Override
		public Nulo obterValor() {
			return this;
		}
	};
}