import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class StmtExprStmt extends Stmt {

	public StmtExpr expr;

	public StmtExprStmt(final StmtExpr expr) {
		this.expr = expr;
	}
	
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {
		expr.codeGen(cw, method, i_class, localVars);
	}

	@Override
	public String toString() {
		return "StmtExprStmt{" +
				"expr=" + this.expr +
				'}';
	}

	@Override
	public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
		expr.typeCheck(localVars, thisClass);
		return Type.TYPE_VOID;
	}
}
