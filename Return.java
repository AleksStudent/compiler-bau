public class Return extends Stmt {

	public Expr expr;

	public Return(final Expr expr) {
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "Return{" +
				"expr=" + this.expr +
				'}';
	}

}
