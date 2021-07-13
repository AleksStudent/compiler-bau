import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class Char extends Expr {

	public String ch;

	public Char(final String ch) {
		this.ch = ch;
	}

	public void codeGen(ClassWriter cw, MethodVisitor method) {
		method.visitLdcInsn(java.lang.Integer.parseInt(Character.toString(getValue())));
		System.out.println("[Char] Writing: " + getValue());
	}

	public char getValue() {
		return ch.charAt(0);
	}

	@Override
	public String toString() {
		return "Char{" +
				"ch=" + this.ch +
				'}';
	}

	@Override
	public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
		return Type.TYPE_CHAR;
	}

	@Override
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {
		// TODO Auto-generated method stub
		
	}
}
