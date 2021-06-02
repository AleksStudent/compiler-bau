public class Char extends Expr {

	public char ch;

	public Char(final char ch) {
		this.ch = ch;
	}

	@Override
	public String toString() {
		return "Char{" +
				"ch=" + this.ch +
				'}';
	}

}
