import java.util.Map;

public class If extends Stmt {

    public Expr cond;
    public Stmt ifStmt;
    public Stmt optionalElseStmt;

    public If(final Expr cond, final Stmt ifStmt, final Stmt optionalElseStmt) {
        this.cond = cond;
        this.ifStmt = ifStmt;
        this.optionalElseStmt = optionalElseStmt;
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
                return ifType;
            } else {
                Type elseType = ifStmt.typeCheck(localVars, thisClass);
                if (ifType.equals(elseType)) {
                    return ifType;
                } else if (ifType.equals(Type.TYPE_VOID) || elseType.equals(Type.TYPE_VOID)) {
                    return Type.TYPE_VOID;
                } else {
                    throw new UnexpectedTypeException(String.format("If-Error: Expected if-Type and else-Type to be equal or void but found if-Type: %s else-Type: %s", ifType, elseType));
                }
            }
        } else {
            throw new UnexpectedTypeException(String.format("If-Error: Expected Condition of Type boolean but found %s", condType));
        }
    }
}
