import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class MethodCall extends StmtExpr {

	public Expr expr;
	public String name;
	public Vector<Expr> expressions;

	public MethodCall(final Expr expr, final String name, final Vector<Expr> expressions) {
		this.expr = expr;
		this.name = name;
		this.expressions = expressions;
	}
	
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVar) {
		for (Expr expression: expressions) {
			((LocalOrFieldVar) expression).codeGen(cw, method, i_class, localVar);
		}
		String parameterInput = "";
		
		for (Expr parameter: expressions) {
			parameterInput += ((LocalOrFieldVar) parameter).getVariableType(((LocalOrFieldVar) parameter).name, i_class, localVar);
		}
		
		Method foundMethod = null;
		for (Method methodIt: i_class.methods) {
			if (methodIt.name == this.name) foundMethod = methodIt;
		}
		
		String methodSignature = "()V";
		
		if (foundMethod != null) {
			methodSignature = "(" + parameterInput + ")" + foundMethod.returnType.getType();
		} else {
			methodSignature = "(" + parameterInput + ")" + org.objectweb.asm.Type.VOID_TYPE.getClassName();
		}
		
		System.out.println("[MethodCall] Calling: " + this.name + " with Parameters: " + methodSignature);
		method.visitMethodInsn(Opcodes.INVOKEVIRTUAL, i_class.name, this.name, methodSignature, false);
	}

	@Override
	public String toString() {
		return "MethodCall{" +
				"expr=" + this.expr +
				", name='" + this.name + '\'' +
				", expressions=" + this.expressions +
				'}';
	}

	@Override
	public Type typeCheck(Map map, Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

}
