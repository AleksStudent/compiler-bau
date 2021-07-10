public class Parameter {

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

}
