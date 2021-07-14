import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import org.objectweb.asm.*;

public class BytecodeGenerator {
	Class classToCode;
	Program programToCode;

	static void writeClassfile(ClassWriter cw) throws IOException {
		byte[] bytes = cw.toByteArray();
		String className = new ClassReader(bytes).getClassName();
		File outputFile = new File("./", className + ".class");
		FileOutputStream output = new FileOutputStream(outputFile);
		output.write(bytes);
		output.close();
	}

	public BytecodeGenerator(Program i_program) throws IOException {
		this.programToCode = i_program;

		// begin bytecode generation
		this.programToCode.codeGen(i_program);
	}
}