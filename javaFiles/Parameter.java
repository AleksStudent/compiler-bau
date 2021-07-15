import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class Parameter implements TypeCheckable{

	public String name;
	public Type type;

	public Parameter(final String name, final Type type) {
		this.name = name;
		this.type = type;
	}
	
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVar, Type returnType) {
		localVar.add(new LocalVarDecl(type, name));
	}

	@Override
	public String toString() {
		return "Parameter{" +
				"name='" + this.name + '\'' +
				", type=" + this.type +
				'}';
	}

	@Override
	public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
		if(!Type.VALID_VAR_TYPES.contains(type)){
			throw new UnexpectedTypeException(String.format("Parameter-Error: The Parameter %s with Type %s has invalid Type", name, type));
		}
		return type;
	}
}
