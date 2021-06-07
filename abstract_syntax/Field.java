public class Field {

	public JString name;
	public Type type;

	public Field(final JString name, final Type type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public String toString() {
		return "Field{" +
				"name='" + this.name + '\'' +
				", type=" + this.type +
				'}';
	}

}
