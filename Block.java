import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import org.objectweb.asm.Label;

public class Block extends Stmt {

	public Vector<Stmt> statements;

	public Block(final Vector<Stmt> statements) {
		this.statements = statements;
	}
	
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {
		Label newBlock = new Label();
		method.visitLabel(newBlock);
		System.out.println("[Block] Reading Statements...");
		
		for (Stmt statement: statements) {
			statement.codeGen(cw, method, i_class, localVars);
		}
	}

	@Override
	public String toString() {
		return "Block{" +
				"statements=" + this.statements +
				'}';
	}

}
