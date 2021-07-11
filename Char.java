import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class Char extends Expr {

	public String ch;

	public Char(final String ch) {
		this.ch = ch;
	}
	
	public void codeGen(ClassWriter cw, MethodVisitor method) {
		method.visitLdcInsn(new Integer(this.ch));
	}
	
	public char getValue() {
		return this.ch;
	}

	@Override
	public String toString() {
		return "Char{" +
				"ch=" + this.ch +
				'}';
	}

}
