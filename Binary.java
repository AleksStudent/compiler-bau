import java.util.*;

public class Binary extends Expr {

    public String operator;
    public Expr exprLeft;
    public Expr exprRight;


    public static Map<String, List<Type>> VALID_OPERATORS = new HashMap<>() {{
        put("+", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put("-", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put("/", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put("*", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put("%", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put("<", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put("<=", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put(">", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put(">=", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put("&&", Collections.singletonList(Type.TYPE_BOOL));
        put("||", Collections.singletonList(Type.TYPE_BOOL));
    }};

    public static List<String> SPECIAL_OPERATORS = Arrays.asList("==", "!=");

    public static Map<String, Type> OPERATOR_RETURN_TYPE = new HashMap<>() {{
        put("+", Type.TYPE_INT);
        put("-", Type.TYPE_INT);
        put("/", Type.TYPE_INT);
        put("*", Type.TYPE_INT);
        put("%", Type.TYPE_INT);
        put("<", Type.TYPE_BOOL);
        put("<=", Type.TYPE_BOOL);
        put(">", Type.TYPE_BOOL);
        put(">=", Type.TYPE_BOOL);
        put("&&", Type.TYPE_BOOL);
        put("||", Type.TYPE_BOOL);
    }};

    public Binary(final String operator, final Expr exprLeft, final Expr exprRight) {
        this.operator = operator;
        this.exprLeft = exprLeft;
        this.exprRight = exprRight;
    }

    @Override
    public String toString() {
        return "Binary{" +
                "operator='" + this.operator + '\'' +
                ", exprLeft=" + this.exprLeft +
                ", exprRight=" + this.exprRight +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        Type exprLeftType = exprLeft.typeCheck(localVars, thisClass);
        Type exprRightType = exprRight.typeCheck(localVars, thisClass);
        if (VALID_OPERATORS.containsKey(operator)) {
            if (exprLeftType.equals(exprRightType)) {
                if (VALID_OPERATORS.get(operator).contains(exprLeftType)) {
                    return OPERATOR_RETURN_TYPE.get(operator);
                } else {
                    throw new UnexpectedTypeException(String.format("Binary-Error: The Operator %s does not support Expressions of Type %s", operator, exprLeftType));
                }
            } else {
                throw new UnexpectedTypeException(String.format("Binary-Error: The Type %s of the Left-Expression %s does not match the Type %s of the Right-Expression %s", exprLeftType, exprLeft, exprRightType, exprRight));
            }
        } else if (SPECIAL_OPERATORS.contains(operator)) {
            if (!(exprLeftType.equals(Type.TYPE_VOID) || exprRightType.equals(Type.TYPE_VOID))) {
                if (exprLeftType.equals(exprRightType) || exprLeftType.equals(Type.TYPE_NULL) || exprRightType.equals(Type.TYPE_NULL)) {
                    return Type.TYPE_BOOL;
                }else{
                    throw new UnexpectedTypeException(String.format("Binary-Error: The Left-Expression %s of Type %s does not have the same Type as the Right-Expression %s of Type %s",exprLeft,exprLeftType,exprRight,exprRightType));
                }
            }else{
                throw new UnexpectedTypeException("Binary-Error: One of the Expressions has type void");
            }
        } else {
            //This should never happen if the scanner works correctly
            throw new UnknownOperatorException(String.format("Binary-Error: The Operator %s is not supported", operator));
        }
    }
}

