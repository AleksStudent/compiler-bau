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
	
	public void codeGen(ClassWriter cw, MethodVisitor method) {
		Label conditionExpression = new Label();
		Label innerIfExpression = new Label();
		
		int selectedCode = 0;
		switch (((Unary) cond).operator) {
		case "==": selectedCode = Opcodes.IF_ICMPEQ; break;
		case "!=": selectedCode = Opcodes.IF_ICMPNE; break;
		case ">": selectedCode = Opcodes.IF_ICMPGT; break;
		case ">=": selectedCode = Opcodes.IF_ICMPGE; break;
		case "<": selectedCode = Opcodes.IF_ICMPLT; break;
		case "<=": selectedCode = Opcodes.IF_ICMPLE; break;
		}
		this.stmt.codeGen(cw, method);

		if (selectedCode == 0) {
			if (((Unary) cond).operator == "&&") {
				method.visitJumpInsn(Opcodes.IFEQ, conditionExpression);
				this.optional.codeGen(cw, method);
				method.visitJumpInsn(Opcodes.IFEQ, conditionExpression);
				method.visitLabel(innerIfExpression);
			} else if (((Unary) cond).operator == "||") {
				method.visitJumpInsn(Opcodes.IFNE, conditionExpression);
				this.optional.codeGen(cw, method);
				method.visitJumpInsn(Opcodes.IFEQ, innerIfExpression);
				method.visitLabel(conditionExpression);
			}
		} else {
			method.visitJumpInsn(selectedCode, conditionExpression);
			method.visitLabel(innerIfExpression);
		}
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
