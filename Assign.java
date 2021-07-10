public class Assign extends StmtExpr {

	public String name;
	public Expr expr;

	public Assign(final String name, final Expr expr) {
		this.name = name;
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "Assign{" +
				"name='" + this.name + '\'' +
				", exprLeft=" + this.expr +
				'}';
	}

}
