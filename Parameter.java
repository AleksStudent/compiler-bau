import java.util.Map;

public class Parameter implements TypeCheckable{

	public String name;
	public Type type;

	public Parameter(final String name, final Type type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public String toString() {
		return "Parameter{" +
				"name='" + this.name + '\'' +
				", type=" + this.type +
				'}';
	}

	@Override
	public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
		if(!Type.VALID_VAR_TYPES.contains(type)){
			throw new UnexpectedTypeException(String.format("Parameter-Error: The Parameter %s with Type %s has invalid Type", name, type));
		}
		return type;
	}
}
