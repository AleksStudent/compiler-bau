import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class New extends StmtExpr {

	public Type type;
	public Vector<Expr> expressions;

	public New(final Type type, final Vector<Expr> expressions) {
		this.type = type;
		this.expressions = expressions;
	}
	
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVar) {
		//method.visitTypeInsn(Opcodes.NEW, type.name);
		method.visitInsn(Opcodes.DUP);
		
		for (Expr expression: expressions) {
			expression.codeGen(cw, method, i_class, localVar);
		}
		
		// String(String): String, Object(): void
		String objectOperand = "()V";
		String stringOperand = "(String)String";
		String selectedOperand;
		
		/*
		switch(type.name) {
			case "String": selectedOperand = stringOperand; break;
			case "Object": selectedOperand = objectOperand; break;
		}

		method.visitMethodInsn(Opcodes.INVOKESPECIAL, type.name, "<init>", selectedOperand, false);
		*/
	}

	@Override
	public String toString() {
		return "New{" +
				"type=" + this.type +
				", expressions=" + this.expressions +
				'}';
	}

	@Override
	public Type typeCheck(Map map, Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

}
