import java.util.Map;
import java.util.stream.Collectors;

import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class InstVar extends Expr {

    public Expr expr;
    public String name;
    public Type type;

    public InstVar(final Expr expr, final String name) {
        this.expr = expr;
        this.name = name;
    }

    public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVar) {
        System.out.println("[InstVar] Getting Variable...");
        this.expr.codeGen(cw, method, i_class, localVar);
        Type typeOfAccessedObject;

        if (((LocalOrFieldVar) expr).isFieldVar(((LocalOrFieldVar) expr).name, i_class.fields)) {
            typeOfAccessedObject = ((LocalOrFieldVar) expr).getFieldVar(((LocalOrFieldVar) expr).name, i_class.fields).type;
            System.out.println("[InstVar] Accessing Field Variable of Type: " + typeOfAccessedObject.getType());
        } else {
            typeOfAccessedObject = ((LocalOrFieldVar) expr).getLocalVar(((LocalOrFieldVar) expr).name, localVar).type;
            System.out.println("[InstVar] Accessing Local Variable of Type: " + typeOfAccessedObject.getType());
        }
        System.out.println("[InstVar] (" + expr.toString() + ")." + name);
        method.visitFieldInsn(Opcodes.GETFIELD, typeOfAccessedObject.getType(), this.name, null);
    }

    @Override
    public String toString() {
        return "InstVar{" +
                "expr=" + this.expr +
                ", name='" + this.name + '\'' +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        Type exprType = expr.typeCheck(localVars, thisClass);
        if (!exprType.equals(Type.TYPE_VOID) && !exprType.equals(Type.TYPE_NULL) && !Type.PRIMITIVE_TYPES.contains(exprType)) {
            //Only possible Class is the class itself
            if (exprType.equals(thisClass.type)) {
                if (thisClass.fields.stream().anyMatch(field -> field.name.equals(name))) {
                    type = thisClass.fields.stream().filter(field -> field.name.equals(name)).collect(Collectors.toList()).get(0).type;
                    return type;
                } else {
                    throw new UnexpectedTypeException(String.format("InstVar-Error: Class with Type %s does not contain a variable named %s", thisClass.type, name));
                }
            } else {
                throw new UnexpectedTypeException(String.format("InstVar-Error: Expression %s with Type %s does not equal expected Class-Type %s", expr, exprType, thisClass.type));
            }
        } else {
            throw new UnexpectedTypeException(String.format("InstVar-Error: The Expression must be a valid Class type, found %s", exprType));
        }
    }
}
