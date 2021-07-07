public class JString extends Expr {

	public String str;

	public JString(final String str) {
		this.str = str;
	}
	
	public String getValue() {
		return this.str;
	}

	@Override
	public String toString() {
		return "JString{" +
				"str='" + this.str + '\'' +
				'}';
	}

}
