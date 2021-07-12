import java.util.Map;

import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class LocalVarDecl extends Stmt {

    public Type type;
    public String name;

    public LocalVarDecl(Type type, String name) {
        this.type = type;
        this.name = name;
    }

	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {
		localVars.add(new LocalVarDecl(this.type, this.name));
		System.out.println("[LocalVarDecl] Pushed Variable to Stack at Position: " + localVars.size());
	}

    @Override
    public String toString() {
        return "LocalVarDecl{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        if (localVars.containsKey(name)) {
            throw new DuplicateException(String.format("LocalVarDecl-Error: The Local Variable %s already exists in this context", name));
        }
        if (!Type.VALID_VAR_TYPES.contains(type)) {
            throw new UnexpectedTypeException(String.format("LocalVarDecl-Error: The Local Variable %s with Type %s has invalid Type", name, type));
        }
        return type;
    }
}
