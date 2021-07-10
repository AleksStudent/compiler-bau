public class Bool extends Expr {

	public String bool;

	public Bool(final String bool) {
		this.bool = bool;
	}

	@Override
	public String toString() {
		return "Bool{" +
				"bool=" + this.bool +
				'}';
	}

}
