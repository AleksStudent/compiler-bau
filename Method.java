import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Method implements TypeCheckable {

    public String name;
    public Type returnType;
    public Vector<Parameter> parameters;
    public Block block;

	public Method(final String name, final Type returnType, final Vector<Parameter> parameters, final Block block) {
		this.name = name;
		this.returnType = returnType;
		this.parameters = parameters;
		this.block = block;
	}

	/**
	 * generate bytecode with classwriter of asm library
	 *
	 * @param cw
	 */
	public void codeGen(ClassWriter cw, Class i_class) {
		System.out.println("[Method] Creating " + this.name);
		String parameterInput = "";
		Vector<LocalVarDecl> localVars = new Vector<LocalVarDecl>();

		for (Parameter parameter: parameters) {
			parameterInput += parameter.type.getASMType();
		}
		String methodSignature = "(" + parameterInput + ")" + this.returnType.getASMType();
		System.out.println("[Method] Parameter Input: " + methodSignature);

		MethodVisitor method = cw.visitMethod(Opcodes.ACC_PUBLIC, this.name, methodSignature, null, null);
		
		// 2nd parameter loop as the first was needed for type declarations
		for (Parameter parameter: parameters) {
			parameter.codeGen(cw, method, i_class, localVars);
		}

		method.visitCode();
		// generate code from inside the method
		this.block.codeGen(cw, method, i_class, localVars);
		// end method
		method.visitMaxs(1, localVars.size() + 1);
		method.visitEnd();
	}

    @Override
    public String toString() {
        return "Method{" +
                "name='" + this.name + '\'' +
                ", returnType=" + this.returnType +
                ", parameters=" + this.parameters +
                ", block=" + this.block +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        if (thisClass.methods.stream().anyMatch(method -> method.name.equals(name))) {
            throw new DuplicateException(String.format("Method-Error: Method with name %s already exists", name));
        }
        if (!Type.VALID_VAR_TYPES.contains(returnType)) {
            throw new UnexpectedTypeException(String.format("Method-Error: Method Return-Type %s is invalid", returnType));
        }
        for (Parameter parameter : parameters) {
            parameter.typeCheck(localVars, thisClass);
        }
        Type blockType = block.typeCheck(localVars, thisClass);
        if (!blockType.equals(returnType)) {
            throw new UnexpectedTypeException(String.format("Method-Error: Method Return-Type %s does not equal Block Type %s", returnType, blockType));
        }
        return returnType;
    }

	@Override
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {
		// TODO Auto-generated method stub
		
	}
}
