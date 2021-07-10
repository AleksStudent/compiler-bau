import java.util.Vector;

public class Class {

	public Type type;
	public Vector<Field> fields;
	public Vector<Method> methods;

	public Class(final Type type, final Vector<Field> fields, final Vector<Method> methods) {
		this.type = type;
		this.fields = fields;
		this.methods = methods;
	}

	@Override
	public String toString() {
		return "Class{" +
				"fields=" + this.fields +
				", methods=" + this.methods +
				'}';
	}

}
