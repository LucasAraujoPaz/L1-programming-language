public interface Nulo extends ExpressaoSimples {

	public final static Nulo NULO = new Nulo() {

		@Override
		public Nulo obterValorNativo() {
			return null;
		}
	};
}