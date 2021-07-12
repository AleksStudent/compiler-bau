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

}
