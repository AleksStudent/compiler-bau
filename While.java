import java.util.Map;

public class While extends Stmt {

	public Expr cond;
	public Stmt stmt;

	public While(Expr cond, Stmt stmt) {
		this.cond = cond;
		this.stmt = stmt;
	}

	@Override
	public String toString() {
		return "While{" +
				"cond=" + cond +
				", stmt=" + stmt +
				'}';
	}

	@Override
	public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
		Type condType=cond.typeCheck(localVars,thisClass);
		if(!condType.equals(Type.TYPE_BOOL)){
			throw new UnexpectedTypeException(String.format("While-Error: Expected Condition of Type boolean but found %s", condType));
		}
		return stmt.typeCheck(localVars,thisClass);
	}
}
