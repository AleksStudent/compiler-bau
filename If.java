import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Label;

public class If extends Stmt {

	public Expr cond;
	public Stmt stmt;
	public Stmt optional;

	public If(final Expr cond, final Stmt stmt, final Stmt optional) {
		this.cond = cond;
		this.stmt = stmt;
		this.optional = optional;
	}
	
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {
		System.out.println("[If] Creating Construct");
		((Binary) cond).codeGen(cw, method, i_class, localVars, stmt, optional);
	}

	@Override
	public String toString() {
		return "If{" +
				"cond=" + this.cond +
				", stmt=" + this.stmt +
				", optional=" + this.optional +
				'}';
	}

}
