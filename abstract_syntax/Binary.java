public class Binary extends Expr {

	public String operator;
	public Expr exprLeft;
	public Expr exprRight;

	public Binary(final String operator, final Expr exprLeft, final Expr exprRight) {
		this.operator = operator;
		this.exprLeft = exprLeft;
		this.exprRight = exprRight;
	}

	@Override
	public String toString() {
		return "Binary{" +
				"operator='" + this.operator + '\'' +
				", exprLeft=" + this.exprLeft +
				", exprRight=" + this.exprRight +
				'}';
	}

}
