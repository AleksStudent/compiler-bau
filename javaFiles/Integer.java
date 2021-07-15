import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class Integer extends Expr {

	public String num;
    public Type type = Type.TYPE_INT;

	public Integer(final String num) {
		java.lang.Integer.parseInt(num);
		this.num = num;
	}

	@Override
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars, Type returnType) {
		method.visitLdcInsn(this.getValue());
		System.out.println("[Integer] Writing: " + this.num);
	}

	public int getValue() {
		return java.lang.Integer.parseInt(this.num);
	}

	@Override
	public String toString() {
		return "Integer{" +
				"num=" + this.num +
				'}';
	}

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        return type;
    }

}
