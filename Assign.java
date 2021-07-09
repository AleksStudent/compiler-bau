public class Assign extends StmtExpr {

	public String name;
	public Expr exprLeft;
	public Expr exprRight;

	public Assign(final String name, final Expr exprLeft, final Expr exprRight) {
		this.name = name;
		this.exprLeft = exprLeft;
		this.exprRight = exprRight;
	}

	@Override
	public String toString() {
		return "Assign{" +
				"name='" + this.name + '\'' +
				", exprLeft=" + this.exprLeft +
				", exprRight=" + this.exprRight +
				'}';
	}

}
