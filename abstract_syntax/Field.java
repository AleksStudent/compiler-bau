import org.objectweb.asm.ClassWriter;

public class Field {

	public JString name;
	public Type type;

	public Field(final JString name, final Type type) {
		this.name = name;
		this.type = type;
	}
	
	/**
	 * generate bytecode with classwriter of asm library
	 * 
	 * @param cw
	 */
	public void codeGen(ClassWriter cw) {

	}

	@Override
	public String toString() {
		return "Field{" +
				"name='" + this.name + '\'' +
				", type=" + this.type +
				'}';
	}

}
