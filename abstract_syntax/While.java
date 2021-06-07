public class While extends Stmt {

	public Expr cond;
	public Stmt stmt;

	public While(Expr cond, Stmt stmt) {
		this.cond = cond;
		this.stmt = stmt;
	}

	@Override
	public String toString() {
		return "While{" +
				"cond=" + cond +
				", stmt=" + stmt +
				'}';
	}
	
}
