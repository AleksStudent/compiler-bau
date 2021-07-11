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
	}

	@Override
	public String toString() {
		return "LocalVarDecl{" +
				"type=" + type +
				", name='" + name + '\'' +
				'}';
	}
	
}
