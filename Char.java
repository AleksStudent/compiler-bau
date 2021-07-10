import java.util.Map;

public class Char extends Expr {

	public String ch;

	public Char(final String ch) {
		this.ch = ch;
	}

	@Override
	public String toString() {
		return "Char{" +
				"ch=" + this.ch +
				'}';
	}

	@Override
	public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
		return Type.TYPE_CHAR;
	}
}
