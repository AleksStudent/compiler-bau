import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.objectweb.asm.*;

public class BytecodeGenerator {
	Class classToCode;

	static void writeClassfile(ClassWriter cw) throws IOException {
		byte[] bytes = cw.toByteArray();
		String className = new ClassReader(bytes).getClassName();
		File outputFile = new File("./", className + ".class");
		FileOutputStream output = new FileOutputStream(outputFile);
		output.write(bytes);
		output.close();
	}

	public BytecodeGenerator(Class i_class) throws IOException {
		this.classToCode = i_class;

		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
		cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, this.classToCode.name, null, "java/lang/Object", null);

		for (Field field : classToCode.fields)
			field.codeGen(cw);

		for (Method method : classToCode.methods)
			method.codeGen(cw);

		writeClassfile(cw);

	}
}