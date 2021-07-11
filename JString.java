import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JString extends Expr {

	public String str;

	public JString(final String str) {
		this.str = str;
	}
	
	public void codeGen(ClassWriter cw, MethodVisitor method) {
		method.visitLdcInsn(this.str);
	}
	
	public String getValue() {
		return this.str;
	}

	@Override
	public String toString() {
		return "JString{" +
				"str='" + this.str + '\'' +
				'}';
	}

}
