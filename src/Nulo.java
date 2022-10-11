public interface Nulo extends ExpressaoSimples<Nulo, Nulo> {

	public final static Nulo NULO = new Nulo() {

		@Override
		public Nulo obterValorNativo() {
			return null;
		}
	};
}