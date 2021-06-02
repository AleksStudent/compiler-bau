public class JString extends Expr {

	public String str;

	public JString(final String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return "JString{" +
				"str='" + this.str + '\'' +
				'}';
	}

}
