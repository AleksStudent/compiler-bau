import java.util.Map;

public class Return extends Stmt {

    public Expr expr;

    public Return(final Expr expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "Return{" +
                "expr=" + this.expr +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        return expr.typeCheck(localVars, thisClass);
    }
}
