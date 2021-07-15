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

	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars, Type returnType) {
		if (!local) {
			Field foundField = getFieldVar(name, i_class.fields);
			System.out.println("[LocalOrFieldVar] Field Var: " + this.name + ", Type: " + foundField.type.getType());
			
			// load reference for "this"
			// could happen that double load happens
            method.visitVarInsn(Opcodes.ALOAD, 0);
            method.visitFieldInsn(Opcodes.GETFIELD, i_class.name, this.name, foundField.type.getASMType());
		} else {
			int indexOfVar = 0;
			for (LocalVarDecl varDecl: localVars) {
				if (varDecl.name.equals(this.name)) {
					indexOfVar = localVars.indexOf(varDecl) + 1;
				}
			}
			if (indexOfVar == 0) System.out.println("[LocalOrFieldVar] ERROR: Variable on Stack not found!!!");

			System.out.println("[LocalOrFieldVar] Local Var: " + this.name + ", Type: " + localVars.get(indexOfVar - 1).type.getType());
			int opCode = Opcodes.ILOAD;
			if (localVars.get(indexOfVar - 1).type.equals(Type.TYPE_STRING) || localVars.get(indexOfVar - 1).type.equals(Type.TYPE_OBJECT)) {
				opCode = Opcodes.ALOAD;				
			}
			method.visitVarInsn(opCode, indexOfVar);
		}
	}

	public boolean isFieldVar(String name, Vector<Field> fields) {
		// could be done with the "local" variable but this was later introduced
		for (Field field: fields) {
			if (field.name.equals(name) && this.local) return true;
		}
		return false;
	}


	public Field getFieldVar(String name, Vector<Field> fields) {
		for (Field field: fields) {
			if (field.name.equals(name)) return field;
		}
		return null;
	}

	public LocalVarDecl getLocalVar(String name, Vector<LocalVarDecl> localVar) {
		for (LocalVarDecl var: localVar) {
			if (var.name.equals(name) && this.local) return var;
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
