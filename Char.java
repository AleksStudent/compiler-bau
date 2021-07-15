import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class Char extends Expr {

	public char ch;
    public Type type = Type.TYPE_CHAR;

	public Char(final String ch) {
		this.ch = ch.toCharArray()[1];
	}
	
	@Override
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars, Type returnType) {
		method.visitLdcInsn((int) getValue());
		System.out.println("[Char] Writing: " + getValue());
	}

	public char getValue() {
		return ch;
	}

	@Override
	public String toString() {
		return "Char{" +
				"ch=" + this.ch +
				'}';
	}

	@Override
	public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        return type;
	}
}
