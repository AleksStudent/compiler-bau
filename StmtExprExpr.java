public class StmtExprExpr extends Expr {

	public StmtExpr expr;

	public StmtExprExpr(final StmtExpr expr) {
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "StmtExprExpr{" +
				"expr=" + this.expr +
				'}';
	}

}
