import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class While extends Stmt {

	public Expr cond;
	public Stmt stmt;

	public While(Expr cond, Stmt stmt) {
		this.cond = cond;
		this.stmt = stmt;
	}
	
	@Override
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars, Type returnType) {
		if (cond instanceof Binary) {
			((Binary) cond).codeGenWhile(cw, method, i_class, localVars, stmt, returnType);	
		} if (cond instanceof Bool) {
        	// questionable workaround :/
        	(new Binary("||", cond, new Bool("false"))).codeGenWhile(cw, method, i_class, localVars, stmt, returnType);
		}
	}

	@Override
	public String toString() {
		return "While{" +
				"cond=" + cond +
				", stmt=" + stmt +
				'}';
	}

	@Override
	public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
		Type condType=cond.typeCheck(localVars,thisClass);
		if(!condType.equals(Type.TYPE_BOOL)){
			throw new UnexpectedTypeException(String.format("While-Error: Expected Condition of Type boolean but found %s", condType));
		}
		return stmt.typeCheck(localVars,thisClass);
	}
}
