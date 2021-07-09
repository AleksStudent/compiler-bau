public class LocalOrFieldVar extends Expr {

	public String name;

	public LocalOrFieldVar(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "LocalOrFieldVar{" +
				"name='" + this.name + '\'' +
				'}';
	}

}
