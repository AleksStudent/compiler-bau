import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Class {

	public String name;
	public Vector<Field> fields;
	public Vector<Method> methods;

	public Class(final Vector<Field> fields, final Vector<Method> methods, final String name) {
		this.fields = fields;
		this.methods = methods;
		this.name = name;
	}
	
	public void codeGen(ClassWriter cw) {
		cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, this.name, null, "java/lang/Object", null);
		
		// create constructor
		MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		constructor.visitCode();
		constructor.visitVarInsn(Opcodes.ALOAD, 0);
		constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		// end constructor
		constructor.visitEnd();

		for (Field field : this.fields)
			field.codeGen(cw);

		for (Method method : this.methods)
			method.codeGen(cw);

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
