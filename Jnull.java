import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Jnull extends Expr {
	
	public void codeGen(ClassWriter cw, MethodVisitor method) {
		method.visitInsn(Opcodes.ACONST_NULL);
	}
	
	public Object getValue() {
		return null;
	}

}
