import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class Integer extends Expr {

	public String num;

	public Integer(final String num) {
		this.num = num;
	}

	public void codeGen(ClassWriter cw, MethodVisitor method) {
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
		return Type.TYPE_INT;
	}

	@Override
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {
		// TODO Auto-generated method stub
		
	}
}
