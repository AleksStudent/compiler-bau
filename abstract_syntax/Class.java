import java.util.Vector;

public class Class {

	public String name;
	public Vector<Field> fields;
	public Vector<Method> methods;

	public Class(final Vector<Field> fields, final Vector<Method> methods, final String name) {
		this.fields = fields;
		this.methods = methods;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Class{" +
				"fields=" + this.fields +
				", methods=" + this.methods +
				", name=" + this.name +
				'}';
	}

}
