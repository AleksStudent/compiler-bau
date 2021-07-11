public class Unary extends Expr {

	public String operator;
	public Expr expr;

	public Unary(final String operator, final Expr expr) {
		this.operator = operator;
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "Unary{" +
				"operator='" + this.operator + '\'' +
				", expr=" + this.expr +
				'}';
	}

}
