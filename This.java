import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class This extends Expr {
	
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {
		method.visitVarInsn(Opcodes.ALOAD, 0);
	}

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        return thisClass.type;
    }
}
