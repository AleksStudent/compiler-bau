import java.util.Vector;

public class Class {

	public Vector<Field> fields;
	public Vector<Method> methods;

	public Class(final Vector<Field> fields, final Vector<Method> methods) {
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
