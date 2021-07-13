import java.util.Map;

public class This extends Expr {

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        return thisClass.type;
    }
}
