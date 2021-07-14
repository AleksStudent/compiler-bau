import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class MethodCall extends StmtExpr {

    public Expr expr;
    public String name;
    public Vector<Expr> expressions;

	public MethodCall(final Expr expr, final String name, final Vector<Expr> expressions) {
		this.expr = expr;
		this.name = name;
		this.expressions = expressions;
	}

	public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVar, Type returnType) {
		for (Expr expression: expressions) {
			expr.codeGen(cw, method, i_class, localVar, returnType);
		}
		String parameterInput = "";


		Method foundMethod = null;
		for (Method methodIt: i_class.methods) {
			if (methodIt.name.equals(this.name)) foundMethod = methodIt;
			for (Parameter param: methodIt.parameters) {
				parameterInput += param.type.getASMType();
			}
		}

		String methodSignature = "()V";

		if (foundMethod != null) {
			methodSignature = "(" + parameterInput + ")" + foundMethod.returnType.getASMType();
		} else {
			methodSignature = "(" + parameterInput + ")" + org.objectweb.asm.Type.VOID_TYPE.toString();
		}
		
		// define extra methods
		if (this.name.equals("toString")) {
			method.visitVarInsn(Opcodes.ALOAD, 0);
			methodSignature = "()Ljava/lang/String;";
			System.out.println("[MethodCall] Calling: " + this.name + " with Parameters: " + methodSignature);
			method.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", this.name, methodSignature, false);	
		} else {
			System.out.println("[MethodCall] Calling: " + this.name + " with Parameters: " + methodSignature);
			method.visitVarInsn(Opcodes.ALOAD, 0);
			method.visitMethodInsn(Opcodes.INVOKEVIRTUAL, i_class.name, this.name, methodSignature, false);	
		}
	}

    @Override
    public String toString() {
        return "MethodCall{" +
                "expr=" + this.expr +
                ", name='" + this.name + '\'' +
                ", expressions=" + this.expressions +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        if (thisClass.methods.stream().noneMatch(method -> method.name.equals(name))) {
            throw new NotFoundException(String.format("MethodCall-Error: Method with name %s does not exist in this Context", name));
        }
        Type exprType=expr.typeCheck(localVars, thisClass);
        //only allowed class is the class itself
        if(!exprType.equals(thisClass.type)){
            throw new UnexpectedTypeException(String.format("MethodCall-Error: Expected Receiver of Type %s, got Receiver of Type %s",thisClass.type,exprType));
        }
        Method currentMethod = thisClass.methods.stream().filter(method -> method.name.equals(name)).collect(Collectors.toList()).get(0);
        if(expressions.size()!=currentMethod.parameters.size()){
            throw new MethodParametersMismatchException(String.format("MethodCall-Error: Expected %s Parameters, found %s",currentMethod.parameters.size(),expressions.size()));
        }
        for (int i = 0; i < expressions.size(); i++) {
            Type expressionType = expressions.get(i).typeCheck(localVars, thisClass);
            Type parameterType = currentMethod.parameters.get(i).type;
            if (!expressionType.equals(parameterType)) {
                throw new UnexpectedTypeException(String.format("MethodCall-Error: Given Expression %s with Type %s does not match expected type %s of parameter %s", expressions.get(i), expressionType, parameterType, currentMethod.parameters.get(i).name));
            }
        }
        return currentMethod.returnType;
    }
}
