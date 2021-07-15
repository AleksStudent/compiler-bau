import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import org.objectweb.asm.*;

public class BytecodeGenerator {

	static void writeClassfile(ClassWriter cw, String classname) throws IOException {
		byte[] bytes = cw.toByteArray();
		String className = new ClassReader(bytes).getClassName();
		System.out.println(classname);
		File outputFile = new File("./", classname + ".class");
		FileOutputStream output = new FileOutputStream(outputFile);
		output.write(bytes);
		output.close();
	}

	static void codeGen(Program i_program) throws IOException {
		Program programToCode = i_program;

		// begin bytecode generation
		programToCode.codeGen(i_program);
	}
}