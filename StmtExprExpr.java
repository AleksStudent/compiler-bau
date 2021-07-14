import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class StmtExprExpr extends Expr {

	public StmtExpr expr;

	public StmtExprExpr(final StmtExpr expr) {
		this.expr = expr;
	}
	
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {
		expr.codeGen(cw, method, i_class, localVars);
	}

	@Override
	public String toString() {
		return "StmtExprExpr{" +
				"expr=" + this.expr +
				'}';
	}

	@Override
	public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
		return expr.typeCheck(localVars, thisClass);
	}
}
