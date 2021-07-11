import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

public class Field {

	public String name;
	public Type type;

	public Field(final String name, final Type type) {
		this.name = name;
		this.type = type;
	}
	
	/**
	 * generate bytecode with classwriter of asm library
	 * 
	 * @param cw
	 */
	public void codeGen(ClassWriter cw) {
		cw.visitField(Opcodes.ACC_PUBLIC, name.str, type.getType(), null, null);
		cw.visitEnd();
		
	}

	@Override
	public String toString() {
		return "Field{" +
				"name='" + this.name + '\'' +
				", type=" + this.type +
				'}';
	}

}
