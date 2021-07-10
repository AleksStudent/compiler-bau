import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

public class MethodCall extends StmtExpr {

    public Expr expr;
    public String name;
    public Vector<Expr> expressions;

    public MethodCall(final Expr expr, final String name, final Vector<Expr> expressions) {
        this.expr = expr;
        this.name = name;
        this.expressions = expressions;
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
