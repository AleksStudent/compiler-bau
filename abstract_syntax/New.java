import java.util.Vector;

public class New extends StmtExpr {

	public Type type;
	public Vector<Expr> expressions;

	public New(final Type type, final Vector<Expr> expressions) {
		this.type = type;
		this.expressions = expressions;
	}

	@Override
	public String toString() {
		return "New{" +
				"type=" + this.type +
				", expressions=" + this.expressions +
				'}';
	}

}
