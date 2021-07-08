import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;


public class Assign extends StmtExpr {

	public String name;
	public Expr exprLeft;
	public Expr exprRight;

	public Assign(final String name, final Expr exprLeft, final Expr exprRight) {
		this.name = name;
		this.exprLeft = exprLeft;
		this.exprRight = exprRight;
	}
	
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVar) {
		int indexOf = 0;
		for (LocalVarDecl varDecl: localVar) {
			if (varDecl.name == ((LocalOrFieldVar) exprLeft).name) {
				indexOf = localVar.indexOf(varDecl) + 1;
				break;
			}
		}
		
		String fieldType = Type.INT_TYPE.getClassName();
		if (indexOf == 0) {
			for (Field field :i_class.fields) {
				if (field.name.str == ((LocalOrFieldVar) exprLeft).name) {
					fieldType = field.type.getType();
					if (field.type.isString) fieldType = "String";
				}
			}
		}
		
		// exprLeft = exprRight
		if (exprRight instanceof LocalOrFieldVar) {
			((LocalOrFieldVar) exprRight).codeGen(cw, method, i_class, localVar);

			int opCode = Opcodes.ISTORE;
			if (fieldType == "String") opCode = Opcodes.ASTORE;

			if (indexOf == 0) {
				method.visitFieldInsn(Opcodes.PUTFIELD, i_class.name, ((LocalOrFieldVar) exprLeft).name, fieldType);
			} else {
				method.visitVarInsn(opCode, indexOf);	
			}

		// exprLeft = 4 (exprRight)
		} else if (exprRight instanceof Bool || exprRight instanceof Char || exprRight instanceof Integer ||
				   exprRight instanceof Jnull) {

			// not very elegant but as all the above mentioned type have codeGen it should be fine
			((Bool) exprRight).codeGen(cw, method);
			if (indexOf == 0) {
				method.visitFieldInsn(Opcodes.PUTFIELD, i_class.name, ((LocalOrFieldVar) exprLeft).name, fieldType);
			} else {
				method.visitVarInsn(Opcodes.ISTORE, indexOf);				
			}

		} else if (exprRight instanceof JString) {
			((JString) exprRight).codeGen(cw, method);
			if (indexOf == 0) {
				method.visitFieldInsn(Opcodes.PUTFIELD, i_class.name, ((LocalOrFieldVar) exprLeft).name, fieldType);
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
				", exprLeft=" + this.exprLeft +
				", exprRight=" + this.exprRight +
				'}';
	}

}
