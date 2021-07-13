import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Field implements TypeCheckable {

    public String name;
    public Type type;

    public Field(final String name, final Type type) {
        this.name = name;
        this.type = type;
    }

	/**
	 * generate bytecode with classwriter of asm library
	 *
	 * @param cw
	 */
	public void codeGen(ClassWriter cw) {
		cw.visitField(Opcodes.ACC_PUBLIC, name, type.getASMType(), null, null);
		cw.visitEnd();
		System.out.println("[Field] Created Field: " + name + ", " + type.getType());

	}

    @Override
    public String toString() {
        return "Field{" +
                "name='" + this.name + '\'' +
                ", type=" + this.type +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        if (!Type.VALID_VAR_TYPES.contains(type)) {
            throw new UnexpectedTypeException(String.format("Field-Error: Field %s with Type %s has invalid Type", name, type));
        }
        return type;
    }

	@Override
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {
		// TODO Auto-generated method stub
		
	}
}
