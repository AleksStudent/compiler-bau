public class Bool extends Expr {

	public boolean bool;

	public Bool(final boolean bool) {
		this.bool = bool;
	}

	@Override
	public String toString() {
		return "Bool{" +
				"bool=" + this.bool +
				'}';
	}

}