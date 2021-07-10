public class If extends Stmt {

	public Expr cond;
	public Stmt stmt;
	public Stmt optional;

	public If(final Expr cond, final Stmt stmt, final Stmt optional) {
		this.cond = cond;
		this.stmt = stmt;
		this.optional = optional;
	}

	@Override
	public String toString() {
		return "If{" +
				"cond=" + this.cond +
				", stmt=" + this.stmt +
				", optional=" + this.optional +
				'}';
	}

}
