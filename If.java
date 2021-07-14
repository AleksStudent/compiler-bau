import java.util.Map;

import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Label;

public class If extends Stmt {

    public Expr cond;
    public Stmt ifStmt;
    public Stmt optionalElseStmt;
    public Type type;

    public If(final Expr cond, final Stmt ifStmt, final Stmt optionalElseStmt) {
        this.cond = cond;
        this.ifStmt = ifStmt;
        this.optionalElseStmt = optionalElseStmt;
    }

    public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars, Type returnType) {
        System.out.println("[If] Creating Construct");
        ((Binary) cond).codeGen(cw, method, i_class, localVars, ifStmt, optionalElseStmt, returnType);
    }

    @Override
    public String toString() {
        return "If{" +
                "cond=" + this.cond +
                ", stmt=" + this.ifStmt +
                ", optional=" + this.optionalElseStmt +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        Type condType = cond.typeCheck(localVars, thisClass);
        if (condType.equals(Type.TYPE_BOOL)) {
            Type ifType = ifStmt.typeCheck(localVars, thisClass);
            if (optionalElseStmt == null) {
                type = ifType;
                return type;
            } else {
                Type elseType = ifStmt.typeCheck(localVars, thisClass);
                if (ifType.equals(elseType)) {
                    type = ifType;
                    return type;
                } else if (ifType.equals(Type.TYPE_VOID) || elseType.equals(Type.TYPE_VOID)) {
                    type = Type.TYPE_VOID;
                    return type;
                } else {
                    throw new UnexpectedTypeException(String.format("If-Error: Expected if-Type and else-Type to be equal or void but found if-Type: %s else-Type: %s", ifType, elseType));
                }
            }
        } else {
            throw new UnexpectedTypeException(String.format("If-Error: Expected Condition of Type boolean but found %s", condType));
        }
    }
}
