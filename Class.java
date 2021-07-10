import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Class {

    public Type type;
    public Vector<Field> fields;
    public Vector<Method> methods;

    public Class(final Type type, final Vector<Field> fields, final Vector<Method> methods) {
        this.type = type;
        this.fields = fields;
        this.methods = methods;
    }

    @Override
    public String toString() {
        return "Class{" +
                "fields=" + this.fields +
                ", methods=" + this.methods +
                '}';
    }

    public Type typeCheck() {
        Class thisClass = this;
        for (Field field : fields) {
            field.typeCheck(new HashMap<>(), thisClass);
        }
        for (Method method : methods) {
            method.typeCheck(new HashMap<>(), thisClass);
        }
        Type.VALID_VAR_TYPES.add(type);
        return type;
    }
}
