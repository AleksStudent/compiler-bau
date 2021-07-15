import java.io.IOException;

import org.objectweb.asm.ClassWriter;

public class Program {

	public Class clazz;

	public Program(final Class clazz) {
		this.clazz = clazz;
	}
	
	public void codeGen(Program i_program) {
		// define class writer
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		
		i_program.clazz.codeGen(cw, i_program.clazz);
		
		try {
			BytecodeGenerator.writeClassfile(cw, i_program.clazz.type.getType());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public String toString() {
		return "Program{" +
				"clazz=" + this.clazz +
				'}';
	}

	public void typeCheck(){
		clazz.typeCheck();
	}
}
