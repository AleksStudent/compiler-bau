import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import org.objectweb.asm.Label;

public class Binary extends Expr {

	public String operator;
	public Expr exprLeft;
	public Expr exprRight;

	public Binary(final String operator, final Expr exprLeft, final Expr exprRight) {
		this.operator = operator;
		this.exprLeft = exprLeft;
		this.exprRight = exprRight;
	}
	
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVar,
			Stmt failedTask, Stmt successTask) {
		int selectedCode = 0;

		switch (operator) {
		case "==": selectedCode = Opcodes.IF_ICMPNE; break;
		case "!=": selectedCode = Opcodes.IF_ICMPEQ; break;
		case ">": selectedCode = Opcodes.IF_ICMPLT; break;
		case ">=": selectedCode = Opcodes.IF_ICMPLE; break;
		case "<": selectedCode = Opcodes.IF_ICMPGT; break;
		case "<=": selectedCode = Opcodes.IF_ICMPGE; break;
		}

		if (selectedCode != 0) {
			this.exprLeft.codeGen(cw, method, i_class, localVar);
			this.exprRight.codeGen(cw, method, i_class, localVar);
			Label successLabel = new Label();
			Label failedLabel = new Label();
			
			method.visitJumpInsn(selectedCode, failedLabel);
			method.visitJumpInsn(Opcodes.GOTO, successLabel);
			
			method.visitLabel(successLabel);
			successTask.codeGen(cw, method, i_class, localVar);
			
			method.visitLabel(failedLabel);
			failedTask.codeGen(cw, method, i_class, localVar);
			return;
		}
		
		switch (operator) {
		case "&&": selectedCode = Opcodes.IFEQ; break;
		case "||": selectedCode = Opcodes.IFNE; break;
		}
		
		if (selectedCode != 0) {
			this.exprLeft.codeGen(cw, method, i_class, localVar);
			
			Label successLabel = new Label();
			Label deciderLabel = new Label();
			Label failedLabel = new Label();
			
			method.visitJumpInsn(selectedCode, failedLabel);
			this.exprRight.codeGen(cw, method, i_class, localVar);

			if (selectedCode == Opcodes.IFEQ) {
				method.visitJumpInsn(selectedCode, failedLabel);
				
				method.visitLabel(successLabel);
				successTask.codeGen(cw, method, i_class, localVar);
				
				method.visitJumpInsn(Opcodes.GOTO, deciderLabel);
				
				method.visitLabel(failedLabel);
				method.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				failedTask.codeGen(cw, method, i_class, localVar);

				method.visitLabel(deciderLabel);
				method.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			} else {
				method.visitJumpInsn(Opcodes.IFEQ, successLabel);
				
				method.visitLabel(failedLabel);
				method.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				failedTask.codeGen(cw, method, i_class, localVar);

				method.visitJumpInsn(Opcodes.GOTO, deciderLabel);
				
				method.visitLabel(successLabel);
				method.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				successTask.codeGen(cw, method, i_class, localVar);
				
				method.visitLabel(deciderLabel);
				method.visitFrame(Opcodes.F_SAME1, 0, null, 1, null);
			}
			return;
		}
		
		switch (operator) {
		case "+": selectedCode = Opcodes.IADD; break;
		case "-": selectedCode = Opcodes.ISUB; break;
		case "*": selectedCode = Opcodes.IMUL; break;
		case "/": selectedCode = Opcodes.IDIV; break;
		case "%": selectedCode = Opcodes.IREM; break;
		}
		
		if (selectedCode != 0) {
			this.exprLeft.codeGen(cw, method, i_class, localVar);
			this.exprRight.codeGen(cw, method, i_class, localVar);
			method.visitInsn(selectedCode);
			return;
		}
		
		System.out.println("ERROR: No Operator selected");
		
	}

	@Override
	public String toString() {
		return "Binary{" +
				"operator='" + this.operator + '\'' +
				", exprLeft=" + this.exprLeft +
				", exprRight=" + this.exprRight +
				'}';
	}

}
