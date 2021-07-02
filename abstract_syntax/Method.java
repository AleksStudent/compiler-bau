import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

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
	
	/**
	 * generate bytecode with classwriter of asm library
	 * 
	 * @param cw
	 */
	public void codeGen(ClassWriter cw) {		
		MethodVisitor method = cw.visitMethod(Opcodes.ACC_PUBLIC, this.name.str, "()V", null, null);
		method.visitCode();
		// generate code from inside the method
		this.block.codeGen(cw, method);
		// end method
		method.visitEnd();
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
