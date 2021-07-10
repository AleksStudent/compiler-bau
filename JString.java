import java.util.Map;

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

	@Override
	public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
		return Type.TYPE_STRING;
	}
}
