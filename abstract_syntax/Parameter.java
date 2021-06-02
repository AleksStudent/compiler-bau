public class Parameter {

	public JString name;
	public Type type;

	public Parameter(final JString name, final Type type) {
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

}
