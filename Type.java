public class Type {
	org.objectweb.asm.Type type = org.objectweb.asm.Type.VOID_TYPE;
	boolean isString = false;
	
	String getType() {
		return this.type.getClassName();
	}
}
