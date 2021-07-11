import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Method {

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
		String parameterInput = "";
		Vector<LocalVarDecl> localVars = new Vector<LocalVarDecl>();
		
		for (Parameter parameter: parameters) {
			parameterInput += parameter.type.getType();
			localVars.add(new LocalVarDecl(parameter.type, parameter.name.str));
		}
		String methodSignature = "(" + parameterInput + ")" + this.returnType.getType();
		
		MethodVisitor method = cw.visitMethod(Opcodes.ACC_PUBLIC, this.name.str, methodSignature, null, null);	
		
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

}
