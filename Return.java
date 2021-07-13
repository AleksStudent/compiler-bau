import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

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

	@Override
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {
		// TODO Auto-generated method stub
		
	}
}
