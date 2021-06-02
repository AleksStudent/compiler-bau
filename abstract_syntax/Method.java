import java.util.Vector;

public class Method {

	public JString name;
	public Type returnType;
	public Vector<Parameter> parameters;
	public Block block;

	public Method(final JString name, final Type returnType, final Vector<Parameter> parameters, final Block block) {
		this.name = name;
		this.returnType = returnType;
		this.parameters = parameters;
		this.block = block;
	}

	@Override
	public String toString() {
		return "Method{" +
				"name='" + this.name + '\'' +
				", returnType=" + this.returnType +
				", parameters=" + this.parameters +
				", block=" + this.block +
				'}';
	}

}
