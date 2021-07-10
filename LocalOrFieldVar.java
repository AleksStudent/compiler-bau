import java.util.Map;
import java.util.stream.Collectors;

public class LocalOrFieldVar extends Expr {

    public String name;
    public boolean local;

    public LocalOrFieldVar(final String name) {
        this.name = name;
    }

    public boolean isLocal() {
        return this.local;
    }

    @Override
    public String toString() {
        return "LocalOrFieldVar{" +
                "name='" + this.name + '\'' +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        if (localVars.containsKey(name)) {
            local = true;
            return localVars.get(name);
        } else if (thisClass.fields.stream().anyMatch(field -> field.name.equals(name))) {
            return thisClass.fields.stream().filter(field -> field.name.equals(name)).collect(Collectors.toList()).get(0).type;
        } else {
            throw new NotFoundException(String.format("LocalOrFieldVar-Error: Variable with name %s does not exist in this context", name));
        }
    }
}
