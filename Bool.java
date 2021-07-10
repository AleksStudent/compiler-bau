import java.util.Map;

public class Bool extends Expr {

	public String bool;

	public Bool(final String bool) {
		this.bool = bool;
	}

	@Override
	public String toString() {
		return "Bool{" +
				"bool=" + this.bool +
				'}';
	}

	@Override
	public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
		return Type.TYPE_BOOL;
	}
}
