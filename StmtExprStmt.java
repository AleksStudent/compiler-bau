import java.util.Map;

public class StmtExprStmt extends Stmt {

	public StmtExpr expr;

	public StmtExprStmt(final StmtExpr expr) {
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "StmtExprStmt{" +
				"expr=" + this.expr +
				'}';
	}

	@Override
	public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
		return expr.typeCheck(localVars, thisClass);
	}
}
