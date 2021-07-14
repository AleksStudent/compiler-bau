import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Bool extends Expr {

	public String bool;

	public Bool(final String bool) {
		this.bool = bool;
	}

	@Override
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {
		int codeToWrite = Opcodes.ICONST_0;
		if (this.bool.equals("true")) codeToWrite = Opcodes.ICONST_1;
		method.visitInsn(codeToWrite);
		System.out.println("[Bool] Writing: " + this.bool);
	}

	public boolean getValue() {
		return this.bool == "true" ? true : false;
	}

	@Override
	public String toString() {
		return "Bool{" +
				"bool=" + this.bool +
				'}';
	}

	@Override
	public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
		return Type.TYPE_BOOL;
	}
}
