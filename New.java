import java.util.List;
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
                //only empty constructor possible
                if(expressions.size()==0){
                    return type;
                }else{
                    throw new MethodParametersMismatchException("New-Error: No Parameters allowed in empty Constructor");
                }
            } else {
                throw new UnexpectedTypeException(String.format("New-Error: Type %s does not equal expected Class-Type %s", type, thisClass.type));
            }
        } else {
            throw new UnexpectedTypeException(String.format("New-Error: Type must be a Class type, found %s", type));
        }
    }
}
