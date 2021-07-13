import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JString extends Expr {

	public String str;

	public JString(final String str) {
		this.str = str;
	}

	public void codeGen(ClassWriter cw, MethodVisitor method) {
		method.visitLdcInsn(this.str);
		System.out.println("[String] Writing: " + this.str);
	}

	public String getValue() {
		return this.str;
	}

	@Override
	public String toString() {
		return "JString{" +
				"str='" + this.str + '\'' +
				'}';
	}

	@Override
	public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
		return Type.TYPE_STRING;
	}

	@Override
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {
		// TODO Auto-generated method stub
		
	}
}
