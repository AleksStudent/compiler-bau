import java.util.Map;

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

	@Override
	public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
		return Type.TYPE_INT;
	}
}
