import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

public class New extends StmtExpr {

    public Type type;
    public Vector<Expr> expressions;

    public New(final Type type, final Vector<Expr> expressions) {
        this.type = type;
        this.expressions = expressions;
    }

    @Override
    public String toString() {
        return "New{" +
                "type=" + this.type +
                ", expressions=" + this.expressions +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        if (!type.equals(Type.TYPE_VOID) && !type.equals(Type.TYPE_NULL) && !Type.PRIMITIVE_TYPES.contains(type)) {
            //Only possible Class is the class itself
            if (type.equals(thisClass.type)) {
                //check if constructor exists
                String constructorMethodName = thisClass.type.getType();
                if (thisClass.methods.stream().anyMatch(method -> method.name.equals(constructorMethodName))) {
                    Method constructor = thisClass.methods.stream().filter(method -> method.name.equals(constructorMethodName)).collect(Collectors.toList()).get(0);
                    if (expressions.size() != constructor.parameters.size()) {
                        throw new MethodParametersMismatchException(String.format("New-Error: Expected %s Parameters in Constructor, found %s", constructor.parameters.size(), expressions.size()));
                    }
                    for (int i = 0; i < expressions.size(); i++) {
                        Type expressionType = expressions.get(i).typeCheck(localVars, thisClass);
                        Type parameterType = constructor.parameters.get(i).type;
                        if (!expressionType.equals(parameterType)) {
                            throw new UnexpectedTypeException(String.format("New-Error: Given Expression %s with Type %s does not match expected type %s of parameter %s", expressions.get(i), expressionType, parameterType, constructor.parameters.get(i).name));
                        }
                    }
                    return type;
                } else {
                    throw new NotFoundException(String.format("New-Error: Constructor of Type %s not found", type));
                }
            } else {
                throw new UnexpectedTypeException(String.format("New-Error: Type %s does not equal expected Class-Type %s", type, thisClass.type));
            }
        } else {
            throw new UnexpectedTypeException(String.format("New-Error: Type must be a Class type, found %s", type));
        }
    }
}
