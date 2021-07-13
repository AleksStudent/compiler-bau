import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import java.util.Map;

public abstract class StmtExpr implements TypeCheckable {
	
	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {
	}

	public Type typeCheck(Map map, Class clazz) {
		return null;
	}

}
