import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;


public class Assign extends StmtExpr {

	public String name;
	public Expr expr;

	public Assign(final String name, final Expr expr) {
		this.name = name;
		this.expr = expr;
	}

	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVar) {
		int indexOf = 0;
		for (LocalVarDecl varDecl: localVar) {
			if (varDecl.name == this.name) {
				indexOf = localVar.indexOf(varDecl) + 1;
				break;
			}
		}

		String fieldType = Type.INT_TYPE.getClassName();
		if (indexOf == 0) {
			for (Field field :i_class.fields) {
				if (field.name.str == this.name) {
					fieldType = field.type.getType();
					if (field.type.isString) fieldType = "String";
				}
			}
		}

		// name = expr
		if (expr instanceof LocalOrFieldVar) {
			((LocalOrFieldVar) expr).codeGen(cw, method, i_class, localVar);

			int opCode = Opcodes.ISTORE;
			if (fieldType == "String") opCode = Opcodes.ASTORE;

			if (indexOf == 0) {
				method.visitFieldInsn(Opcodes.PUTFIELD, i_class.name, this.name, fieldType);
			} else {
				method.visitVarInsn(opCode, indexOf);
			}

		// name = 4 (expr)
		} else if (expr instanceof Bool || expr instanceof Char || expr instanceof Integer ||
				   expr instanceof Jnull) {

			// not very elegant but as all the above mentioned type have codeGen it should be fine
			((Bool) expr).codeGen(cw, method);
			if (indexOf == 0) {
				method.visitFieldInsn(Opcodes.PUTFIELD, i_class.name, this.name, fieldType);
			} else {
				method.visitVarInsn(Opcodes.ISTORE, indexOf);
			}

		} else if (expr instanceof JString) {
			((JString) expr).codeGen(cw, method);
			if (indexOf == 0) {
				method.visitFieldInsn(Opcodes.PUTFIELD, i_class.name, this.name, fieldType);
			} else {
				// write reference
				method.visitVarInsn(Opcodes.ASTORE, indexOf);
			}
		}
	}

	@Override
	public String toString() {
		return "Assign{" +
				"name='" + this.name + '\'' +
				", exprLeft=" + this.expr +
				'}';
	}

}
