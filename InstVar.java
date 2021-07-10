import java.util.Map;
import java.util.stream.Collectors;

public class InstVar extends Expr {

    public Expr expr;
    public String name;

    public InstVar(final Expr expr, final String name) {
        this.expr = expr;
        this.name = name;
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
                    return thisClass.fields.stream().filter(field -> field.name.equals(name)).collect(Collectors.toList()).get(0).type;
                } else {
                    throw new UnexpectedTypeException(String.format("InstVar-Error: Class with Type %s does not contain a variable named %s", thisClass.type, name));
                }
            } else {
                throw new UnexpectedTypeException(String.format("InstVar-Error: Expression %s with Type %s does not equal expected Class-Type %s", expr, exprType, thisClass.type));
            }
        } else {
            throw new UnexpectedTypeException(String.format("InstVar-Error: The Expression must be a Class type, found %s", exprType));
        }
    }
}
