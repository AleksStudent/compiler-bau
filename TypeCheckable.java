import java.util.Map;

public interface TypeCheckable {
	Type typeCheck(Map<String,Type> localVars, Class thisClass);
}
