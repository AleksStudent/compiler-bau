import java.util.Map;
import java.util.stream.Collectors;

public class Assign extends StmtExpr {

    public String name;
    public Expr expr;

    public Assign(final String name, final Expr expr) {
        this.name = name;
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "Assign{" +
                "name='" + this.name + '\'' +
                ", exprLeft=" + this.expr +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        boolean localVariable = localVars.containsKey(name);
        boolean classVariable = thisClass.fields.stream().anyMatch(field -> field.name.equals(name));
        if (localVariable || classVariable) {
            Type exprType = expr.typeCheck(localVars, thisClass);
            Type variableType = localVariable ? localVars.get(name) : thisClass.fields.stream().filter(field -> field.name.equals(name)).collect(Collectors.toList()).get(0).type;
            if (variableType.equals(exprType) || !Type.PRIMITIVE_TYPES.contains(variableType) && exprType.equals(Type.TYPE_NULL)) {
                return Type.TYPE_VOID;
            } else{
                throw new UnexpectedTypeException(String.format("Assign-Error: The Type %s of Expression %s does not match the Type %s of Variable %s",exprType,expr,variableType,name));
            }
        } else {
            throw new NotFoundException(String.format("Assign-Error: The Variable %s does not exist", name));
        }
    }
}
