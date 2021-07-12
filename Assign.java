import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class Assign extends StmtExpr {

	public String name;
	public Expr expr;

	public Assign(final String name, final Expr expr) {
		this.name = name;
		this.expr = expr;
	}

	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVar) {
		System.out.println("[Assign] Start Assing");
		int indexOf = 0;
		for (LocalVarDecl varDecl: localVar) {
			if (varDecl.name == this.name) {
				indexOf = localVar.indexOf(varDecl) + 1;
				System.out.println("[Assign] Found Local Var at Index: " + indexOf + " with Content: ");
				System.out.println(localVar.get(indexOf - 1).toString());
				break;
			}
		}

		String fieldType = org.objectweb.asm.Type.INT_TYPE.getClassName();
		if (indexOf == 0) {
			System.out.println("[Assign] No Local Var found... Trying Fields");
			for (Field field :i_class.fields) {
				if (field.name == this.name) {
					fieldType = field.type.getType();
					if (field.type.isString) fieldType = "String";
					System.out.println("[Assign] Found Field Var");
					break;
				}
			}
		}

		// name = expr
		if (expr instanceof LocalOrFieldVar) {
			System.out.println("[Assign] Name = Expr");
			((LocalOrFieldVar) expr).codeGen(cw, method, i_class, localVar);

			int opCode = Opcodes.ISTORE;
			if (fieldType == "String") {
				opCode = Opcodes.ASTORE;
				System.out.println("[Assign] Selected Reference");
			} else {
				System.out.println("[Assign] Direct Assignment of Value");
			}

			if (indexOf == 0) {
				method.visitFieldInsn(Opcodes.PUTFIELD, i_class.name, this.name, fieldType);
				System.out.println("[Assign] Writing to Field Var...");
			} else {
				method.visitVarInsn(opCode, indexOf);
				System.out.println("[Assign] Writing to Local Var...");
			}

		// name = 4 (expr)
		} else if (expr instanceof Bool || expr instanceof Char || expr instanceof Integer ||
				   expr instanceof Jnull) {
			System.out.println("[Assign] name = someValue");
			// not very elegant but as all the above mentioned type have codeGen it should be fine
			((Bool) expr).codeGen(cw, method);
			if (indexOf == 0) {
				method.visitFieldInsn(Opcodes.PUTFIELD, i_class.name, this.name, fieldType);
				System.out.println("[Assign] Writing to Field Var...");
			} else {
				method.visitVarInsn(Opcodes.ISTORE, indexOf);
				System.out.println("[Assign] Writing to Local Var...");
			}

		} else if (expr instanceof JString) {
			((JString) expr).codeGen(cw, method);
			if (indexOf == 0) {
				method.visitFieldInsn(Opcodes.PUTFIELD, i_class.name, this.name, fieldType);
				System.out.println("[Assign] Writing to Field Var...");
			} else {
				// write reference
				method.visitVarInsn(Opcodes.ASTORE, indexOf);
				System.out.println("[Assign] Writing to Local Var...");
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

	@Override
	public Type typeCheck(Map map, Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

}
