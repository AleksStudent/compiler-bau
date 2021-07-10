import java.util.Map;
import java.util.Vector;

public class Method implements TypeCheckable {

    public String name;
    public Type returnType;
    public Vector<Parameter> parameters;
    public Block block;

    public Method(final String name, final Type returnType, final Vector<Parameter> parameters, final Block block) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
        this.block = block;
    }

    @Override
    public String toString() {
        return "Method{" +
                "name='" + this.name + '\'' +
                ", returnType=" + this.returnType +
                ", parameters=" + this.parameters +
                ", block=" + this.block +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        if (thisClass.methods.stream().anyMatch(method -> method.name.equals(name))) {
            throw new DuplicateException(String.format("Method-Error: Method with name %s already exists", name));
        }
        if (!Type.VALID_VAR_TYPES.contains(returnType)) {
            throw new UnexpectedTypeException(String.format("Method-Error: Method Return-Type %s is invalid", returnType));
        }
        for (Parameter parameter : parameters) {
            parameter.typeCheck(localVars, thisClass);
        }
        Type blockType = block.typeCheck(localVars, thisClass);
        if (!blockType.equals(returnType)) {
            throw new UnexpectedTypeException(String.format("Method-Error: Method Return-Type %s does not equal Block Type %s", returnType, blockType));
        }
        return returnType;
    }
}
