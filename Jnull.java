import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Jnull extends Expr {

	@Override
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {
		method.visitInsn(Opcodes.ACONST_NULL);
		System.out.println("[Null] Writing: null");
	}

	public Object getValue() {
		return null;
	}

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        return Type.TYPE_NULL;
    }

    @Override
    public String toString() {
        return "null";
    }
}
