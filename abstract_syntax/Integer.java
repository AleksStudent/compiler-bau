import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class Integer extends Expr {

	public int num;

	public Integer(final int num) {
		this.num = num;
	}
	
	public void codeGen(ClassWriter cw, MethodVisitor method) {
		method.visitLdcInsn(new Integer(this.num));
	}
	
	public int getValue() {
		return this.num;
	}

	@Override
	public String toString() {
		return "Integer{" +
				"num=" + this.num +
				'}';
	}

}
