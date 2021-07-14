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
    static public final Type TYPE_OBJECT=new Type("object");
    static public final Set<Type> PRIMITIVE_TYPES=new HashSet<>(Arrays.asList(TYPE_INT,TYPE_CHAR,TYPE_BOOL));
    static public final Set<Type> VALID_VAR_TYPES=new HashSet<>(Arrays.asList(TYPE_INT,TYPE_CHAR,TYPE_BOOL,TYPE_STRING));
    private final String type;

    public Type(final String type){
        this.type=type;
    }

    public String getType() {
        return type;
    }
    
    public static String getASMType(Type i_type) {
    	switch (i_type.type) {
    	case "void": return org.objectweb.asm.Type.VOID_TYPE.toString();
    	case "null": return org.objectweb.asm.Type.VOID_TYPE.toString();
    	case "int": return org.objectweb.asm.Type.INT_TYPE.toString();
    	case "char": return org.objectweb.asm.Type.CHAR_TYPE.toString();
    	case "bool": return org.objectweb.asm.Type.BOOLEAN_TYPE.toString();
    	case "boolean": return org.objectweb.asm.Type.BOOLEAN_TYPE.toString();
    	case "Boolean": return org.objectweb.asm.Type.BOOLEAN_TYPE.toString();
    	case "string": return "Ljava/lang/String;";
    	case "String": return "Ljava/lang/String;";
    	default: return org.objectweb.asm.Type.VOID_TYPE.toString();
    	}
    }
    
    public String getASMType() {
    	return Type.getASMType(new Type(this.type));
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
