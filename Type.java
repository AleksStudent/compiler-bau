import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Type {

    static public final Type TYPE_VOID=new Type("void");
    static public final Type TYPE_NULL=new Type("null");
    static public final Type TYPE_INT=new Type("int");
    static public final Type TYPE_CHAR=new Type("char");
    static public final Type TYPE_BOOL=new Type("bool");
    static public final Type TYPE_STRING=new Type("string");
    static public final Set<Type> PRIMITIVE_TYPES=new HashSet<>(Arrays.asList(TYPE_INT,TYPE_CHAR,TYPE_BOOL));
    static public final Set<Type> VALID_VAR_TYPES=new HashSet<>(Arrays.asList(TYPE_INT,TYPE_CHAR,TYPE_BOOL,TYPE_STRING));
    private final String type;

    public Type(final String type){
        this.type=type;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type1 = (Type) o;
        return Objects.equals(type, type1.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
