import java.util.Vector;

public class MethodCall extends StmtExpr {

	public Expr expr;
	public String name;
	public Vector<Expr> expressions;

	public MethodCall(final Expr expr, final String name, final Vector<Expr> expressions) {
		this.expr = expr;
		this.name = name;
		this.expressions = expressions;
	}

	@Override
	public String toString() {
		return "MethodCall{" +
				"expr=" + this.expr +
				", name='" + this.name + '\'' +
				", expressions=" + this.expressions +
				'}';
	}

}
