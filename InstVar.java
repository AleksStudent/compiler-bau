import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class InstVar extends Expr {

	public Expr expr;
	public String name;

	public InstVar(final Expr expr, final String name) {
		this.expr = expr;
		this.name = name;
	}
	
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVar) {
		System.out.println("[InstVar] Getting Variable...");
		this.expr.codeGen(cw, method, i_class, localVar);
		Type typeOfAccessedObject;
		
		if (((LocalOrFieldVar) expr).isFieldVar(((LocalOrFieldVar) expr).name, i_class.fields)) {
			typeOfAccessedObject = ((LocalOrFieldVar) expr).getFieldVar(((LocalOrFieldVar) expr).name, i_class.fields).type;
			System.out.println("[InstVar] Accessing Field Variable of Type: " + typeOfAccessedObject.getType());
		} else {
			typeOfAccessedObject = ((LocalOrFieldVar) expr).getLocalVar(((LocalOrFieldVar) expr).name, localVar).type;
			System.out.println("[InstVar] Accessing Local Variable of Type: " + typeOfAccessedObject.getType());
		}
		System.out.println("[InstVar] (" + expr.toString() + ")." + name);
		method.visitFieldInsn(Opcodes.GETFIELD, typeOfAccessedObject.getType(), this.name, null);
	}

	@Override
	public String toString() {
		return "InstVar{" +
				"expr=" + this.expr +
				", name='" + this.name + '\'' +
				'}';
	}

}
