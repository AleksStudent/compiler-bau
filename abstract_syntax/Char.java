public class Char extends Expr {

	public char ch;

	public Char(final char ch) {
		this.ch = ch;
	}
	
	public char getValue() {
		return this.ch;
	}

	@Override
	public String toString() {
		return "Char{" +
				"ch=" + this.ch +
				'}';
	}

}
