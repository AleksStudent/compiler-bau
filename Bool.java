import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Bool extends Expr {

	public String bool;

	public Bool(final String bool) {
		this.bool = bool;
	}
	
	public void codeGen(ClassWriter cw, MethodVisitor method) {
		int codeToWrite = Opcodes.ICONST_0;
		if (this.bool == true) codeToWrite = Opcodes.ICONST_1;
		method.visitInsn(codeToWrite);
	}
	
	public boolean getValue() {
		return this.bool;
	}

	@Override
	public String toString() {
		return "Bool{" +
				"bool=" + this.bool +
				'}';
	}

}