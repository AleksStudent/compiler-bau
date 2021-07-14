import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Return extends Stmt {

    public Expr expr;

    public Return(final Expr expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "Return{" +
                "expr=" + this.expr +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        return expr.typeCheck(localVars, thisClass);
    }

	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars, Type returnType) {
		System.out.println("[RETURN] Returning: " + expr.toString());
		
		expr.codeGen(cw, method, i_class, localVars, returnType);
		if (returnType.equals(Type.TYPE_STRING) || returnType.equals(Type.TYPE_OBJECT)) {
			System.out.println("[RETURN] Returning Reference");
			method.visitInsn(Opcodes.ARETURN);
		} else if (returnType.equals(Type.TYPE_VOID)) {
			// if void then ignore it as it will be set at the end of the method visit
			System.out.println("[RETURN] Ignoring");
		} else {
			method.visitInsn(Opcodes.IRETURN);
			System.out.println("[RETURN] Returning Value");
		}
	}

}
