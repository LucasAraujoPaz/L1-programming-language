public interface Nulo extends Valor {

	public final static Nulo NULO = new Nulo() {

		@Override
		public Nulo obterValorNativo() {
			return null;
		}
	};
}