public class Integer extends Expr {

	public int num;

	public Integer(final int num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "Integer{" +
				"num=" + this.num +
				'}';
	}

}
