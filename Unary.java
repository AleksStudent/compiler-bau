import java.util.*;

public class Unary extends Expr {

    public String operator;
    public Expr expr;

    public static Map<String, List<Type>> VALID_OPERATORS = new HashMap<>() {{
        put("+", Collections.singletonList(Type.TYPE_INT));
        put("-", Collections.singletonList(Type.TYPE_INT));
        put("!", Collections.singletonList(Type.TYPE_BOOL));
    }};

    public static Map<String, Type> OPERATOR_RETURN_TYPE = new HashMap<>() {{
        put("+", Type.TYPE_INT);
        put("-", Type.TYPE_INT);
        put("!", Type.TYPE_BOOL);
    }};

    public Unary(final String operator, final Expr expr) {
        this.operator = operator;
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "Unary{" +
                "operator='" + this.operator + '\'' +
                ", expr=" + this.expr +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        Type exprType = expr.typeCheck(localVars, thisClass);
        if (VALID_OPERATORS.containsKey(operator)) {
            if (VALID_OPERATORS.get(operator).contains(exprType)) {
                return OPERATOR_RETURN_TYPE.get(operator);
            } else {
                throw new UnexpectedTypeException(String.format("Unary-Error: The Operator %s does not support Expressions of Type %s", operator, exprType));
            }
        } else {
            //This should never happen if the scanner works correctly
            throw new UnknownOperatorException(String.format("Unary-Error: The Operator %s is not supported", operator));
        }
    }
}
