import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Block extends Stmt {

	public Vector<Stmt> statements;

	public Block(final Vector<Stmt> statements) {
		this.statements = statements;
	}
	
	public void codeGen(ClassWriter cw, MethodVisitor method) {
		
	}

	@Override
	public String toString() {
		return "Block{" +
				"statements=" + this.statements +
				'}';
	}

}
