public class LocalVarDecl extends Stmt {

	public Type type;
	public String name;

	public LocalVarDecl(Type type, String name) {
		this.type = type;
		this.name = name;
	}

	@Override
	public String toString() {
		return "LocalVarDecl{" +
				"type=" + type +
				", name='" + name + '\'' +
				'}';
	}
	
}
