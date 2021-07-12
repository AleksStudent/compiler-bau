import java.util.Map;
import java.util.stream.Collectors;

import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LocalOrFieldVar extends Expr {

    public String name;
    public boolean local;

	public LocalOrFieldVar(final String name) {
		this.name = name;
	}

	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {
		if (isFieldVar(name, i_class.fields)) {
			Field foundField = getFieldVar(name, i_class.fields);
			System.out.println("[LocalOrFieldVar] Field Var: " + this.name + ", Type: " + foundField.type.getType());
            method.visitVarInsn(Opcodes.ALOAD, 0);

            method.visitFieldInsn(Opcodes.GETFIELD, "", this.name, foundField.type.getType());
		} else {
			int indexOfVar = 0;
			for (LocalVarDecl varDecl: localVars) {
				if (varDecl.name == this.name) {
					indexOfVar = localVars.indexOf(varDecl) + 1;
				}
			}
			if (indexOfVar == 0) System.out.println("[LocalOrFieldVar] ERROR: Variable on Stack not found!!!");

			System.out.println("[LocalOrFieldVar] Local Var: " + this.name + ", Type: " + localVars.get(indexOfVar - 1).type.getType());
			int opCode = Opcodes.ILOAD;
			if (localVars.get(indexOfVar - 1).type.isString) opCode = Opcodes.ALOAD;
			method.visitVarInsn(opCode, indexOfVar);
		}
	}

	public boolean isFieldVar(String name, Vector<Field> fields) {
		for (Field field: fields) {
			if (field.name == name) return true;
		}
		return false;
	}

	public Field getFieldVar(String name, Vector<Field> fields) {
		for (Field field: fields) {
			if (field.name == name) return field;
		}
		return null;
	}

	public LocalVarDecl getLocalVar(String name, Vector<LocalVarDecl> localVar) {
		for (LocalVarDecl var: localVar) {
			if (var.name == name) return var;
		}
		return null;
	}

	public Type getVariableType(String name, Class i_class, Vector<LocalVarDecl> localVar) {
		if (isFieldVar(name, i_class.fields)) {
			return getFieldVar(name, i_class.fields).type;
		} else {
			LocalVarDecl foundVar = getLocalVar(name, localVar);
			if (foundVar != null) {
				return foundVar.type;
			}
			else {
				return null;
			}
		}
	}

	public boolean isLocal() {
		return this.local;
	}

    @Override
    public String toString() {
        return "LocalOrFieldVar{" +
                "name='" + this.name + '\'' +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        if (localVars.containsKey(name)) {
            local = true;
            return localVars.get(name);
        } else if (thisClass.fields.stream().anyMatch(field -> field.name.equals(name))) {
            return thisClass.fields.stream().filter(field -> field.name.equals(name)).collect(Collectors.toList()).get(0).type;
        } else {
            throw new NotFoundException(String.format("LocalOrFieldVar-Error: Variable with name %s does not exist in this context", name));
        }
    }
}
