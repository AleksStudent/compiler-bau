import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class Stmt implements TypeCheckable {

	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {

	}

	@Override
	public Type typeCheck(Map map, Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

}
