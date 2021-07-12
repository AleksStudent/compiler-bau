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

}
