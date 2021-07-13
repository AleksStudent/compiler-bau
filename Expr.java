import java.util.Map;
import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class Expr implements TypeCheckable {

	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVar) {
	}

	@Override
	public Type typeCheck(Map<String, Type> map, Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

}
