import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


import java.util.Map;
import java.util.stream.Collectors;

public class Assign extends StmtExpr {

    public String name;
    public Expr expr;
    public Type type;

    public Assign(final String name, final Expr expr) {
        this.name = name;
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "Assign{" +
                "name='" + this.name + '\'' +
                ", exprLeft=" + this.expr +
                '}';
    }
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVar) {
		System.out.println("[Assign] Start Assing");
		int indexOf = 0;
		for (LocalVarDecl varDecl: localVar) {
			if (varDecl.name.equals(this.name)) {
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
				if (field.name.equals(this.name)) {
					fieldType = field.type.getASMType();
					System.out.println("[Assign] Found Field Var: " + this.name + ", " + fieldType);
					break;
				}
			}
		}

		// name = expr
		if (expr instanceof LocalOrFieldVar) {
			System.out.println("[Assign] Name = Expr");

			int opCode = Opcodes.ISTORE;
			if (fieldType.equals(Type.getASMType(Type.TYPE_STRING))) {
				opCode = Opcodes.ASTORE;
				System.out.println("[Assign] Selected Reference");
			} else {
				System.out.println("[Assign] Direct Assignment of Value");
			}

			if (indexOf == 0) {
				method.visitVarInsn(Opcodes.ALOAD, 0);
				((LocalOrFieldVar) expr).codeGen(cw, method, i_class, localVar);
				method.visitFieldInsn(Opcodes.PUTFIELD, i_class.name, this.name, fieldType);
				System.out.println("[Assign] Writing to Field Var...");
			} else {
				expr.codeGen(cw, method, i_class, localVar);
				method.visitVarInsn(opCode, indexOf);
				System.out.println("[Assign] Writing to Local Var...");
			}

		// name = 4 (expr)
		} else if (expr instanceof Bool || expr instanceof Char || expr instanceof Integer ||
				   expr instanceof Jnull) {
			System.out.println("[Assign] name = " + expr.toString());

			if (indexOf == 0) {
				method.visitVarInsn(Opcodes.ALOAD, 0);
				expr.codeGen(cw, method, i_class, localVar);
				method.visitFieldInsn(Opcodes.PUTFIELD, i_class.name, this.name, fieldType);
				System.out.println("[Assign] Writing to Field Var...");
			} else {
				expr.codeGen(cw, method, i_class, localVar);
				method.visitVarInsn(Opcodes.ISTORE, indexOf);
				System.out.println("[Assign] Writing to Local Var...");
			}

		} else {
			System.out.println("[Assign] Writing Nested Expression...");
			System.out.println("[Assign] name = " + expr.toString());
			if (indexOf == 0) {
				method.visitVarInsn(Opcodes.ALOAD, 0);
				expr.codeGen(cw, method, i_class, localVar);
				method.visitFieldInsn(Opcodes.PUTFIELD, i_class.name, this.name, fieldType);
				System.out.println("[Assign] Writing to Field Var...");
			} else {
				// write reference
				expr.codeGen(cw, method, i_class, localVar);
				method.visitVarInsn(Opcodes.ASTORE, indexOf);
				System.out.println("[Assign] Writing to Local Var...");
			}
		}
	}

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        boolean localVariable = localVars.containsKey(name);
        boolean classVariable = thisClass.fields.stream().anyMatch(field -> field.name.equals(name));
        if (localVariable || classVariable) {
            Type exprType = expr.typeCheck(localVars, thisClass);
            Type variableType = localVariable ? localVars.get(name) : thisClass.fields.stream().filter(field -> field.name.equals(name)).collect(Collectors.toList()).get(0).type;
            if (variableType.equals(exprType) || (!Type.PRIMITIVE_TYPES.contains(variableType) && exprType.equals(Type.TYPE_NULL))) {
                type = variableType;
                return type;
            } else {
                throw new UnexpectedTypeException(String.format("Assign-Error: The Type %s of Expression %s does not match the Type %s of Variable %s", exprType, expr, variableType, name));
            }
        } else {
            throw new NotFoundException(String.format("Assign-Error: The Variable %s does not exist", name));
        }
    }
}
