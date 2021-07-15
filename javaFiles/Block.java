import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import org.objectweb.asm.Label;

public class Block extends Stmt {

    public Vector<Stmt> statements;
    public Type type;

    public Block(final Vector<Stmt> statements) {
        this.statements = statements;
    }

	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars, Type returnType) {
		Label newBlock = new Label();
		method.visitLabel(newBlock);

		for (Stmt statement: statements) {
            System.out.println("[Block] Reading Statement: " + statement.toString());
			statement.codeGen(cw, method, i_class, localVars, returnType);
		}
	}

    @Override
    public String toString() {
        return "Block{" +
                "statements=" + this.statements +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        Type blockType = Type.TYPE_VOID;
        for (Stmt statement : statements) {
            Type statementType = statement.typeCheck(localVars, thisClass);
            if (!statementType.equals(Type.TYPE_VOID)) {
                if (blockType.equals(Type.TYPE_VOID) || blockType.equals(statementType)) {
                    blockType = statementType;
                } else {
                    throw new UnexpectedTypeException(String.format("Block-Error: Block with Type %s cannot have second Type %s of Statement %s", blockType, statementType, statement));
                }
            }
        }
        type = blockType;
        return type;
    }
}
