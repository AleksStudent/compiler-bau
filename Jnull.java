import java.util.Map;

public class Jnull extends Expr {

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        return Type.TYPE_NULL;
    }

    @Override
    public String toString() {
        return "null";
    }
}
