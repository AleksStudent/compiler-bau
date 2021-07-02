import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Constructor extends Method {

	public Constructor(JString name, Type returnType, Vector<Parameter> parameters, Block block) {
		super(name, returnType, parameters, block);
	}
	
	@Override
	public void codeGen(ClassWriter cw) {
		// visit constructor of class
		MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		constructor.visitCode();
		constructor.visitVarInsn(Opcodes.ALOAD, 0);
		constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		
		// generate code from inside the method
		this.block.codeGen(cw, constructor);

		// end constructor
		constructor.visitEnd();
	}
	
	@Override
	public String toString() {
		return "Constructor{" +
				"name='" + this.name + '\'' +
				", returnType=" + this.returnType +
				", parameters=" + this.parameters +
				", block=" + this.block +
				'}';
	}

}
