public class Integer extends Expr {

	public String num;

	public Integer(final String num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "Integer{" +
				"num=" + this.num +
				'}';
	}

}
