import java.util.Vector;

public class Block extends Stmt {

	public Vector<Stmt> statements;

	public Block(final Vector<Stmt> statements) {
		this.statements = statements;
	}

	@Override
	public String toString() {
		return "Block{" +
				"statements=" + this.statements +
				'}';
	}

}
