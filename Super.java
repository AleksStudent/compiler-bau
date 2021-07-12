import java.util.Map;

public class Super extends Expr {

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        return new Type("object");
    }
}
