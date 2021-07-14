import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Class {

    public Type type;
    public Vector<Field> fields;
    public Vector<Method> methods;
    public String name;

    public Class(final Type type, final Vector<Field> fields, final Vector<Method> methods) {
        this.type = type;
        this.fields = fields;
        this.methods = methods;
        this.name = "Test";
    }

	public void codeGen(ClassWriter cw, Class i_class) {
		cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, this.name, null, "java/lang/Object", null);
		
		// go through fields
		for (Field field : this.fields)
			field.codeGen(cw);
		
		System.out.println("[Class] Creating Constructor");
		// create constructor
		MethodVisitor constructor = cw.visitMethod(0, "<init>", "()V", null, null);
		constructor.visitCode();
		// load "this"
		constructor.visitVarInsn(Opcodes.ALOAD, 0);
		constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		constructor.visitInsn(Opcodes.RETURN);
		// end constructor
		constructor.visitMaxs(0, 0);
		System.out.println("[Class] Constructor Set");

		for (Method method : this.methods) 
			method.codeGen(cw, i_class);
		
	}

    @Override
    public String toString() {
        return "Class{" +
                "fields=" + this.fields +
                ", methods=" + this.methods +
                '}';
    }

    public Type typeCheck() {
        Class thisClass = this;
        for (Field field : fields) {
            field.typeCheck(new HashMap<>(), thisClass);
        }
        for (Method method : methods) {
            method.typeCheck(new HashMap<>(), thisClass);
        }
        Type.VALID_VAR_TYPES.add(type);
        return type;
    }
}
