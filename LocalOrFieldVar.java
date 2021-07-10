public class LocalOrFieldVar extends Expr {

	public String name;
	public boolean local;

	public LocalOrFieldVar(final String name) {
		this.name = name;
	}

	public boolean isLocal() {
		return this.local;
	}

	@Override
	public String toString() {
		return "LocalOrFieldVar{" +
				"name='" + this.name + '\'' +
				'}';
	}

}
