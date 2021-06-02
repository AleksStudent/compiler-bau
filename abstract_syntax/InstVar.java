public class InstVar extends Expr {

	public Expr expr;
	public String name;

	public InstVar(final Expr expr, final String name) {
		this.expr = expr;
		this.name = name;
	}

	@Override
	public String toString() {
		return "InstVar{" +
				"expr=" + this.expr +
				", name='" + this.name + '\'' +
				'}';
	}

}
