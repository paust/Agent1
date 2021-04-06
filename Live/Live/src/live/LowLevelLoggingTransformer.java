package live;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.List;

import javassist.bytecode.BadBytecode;
import javassist.bytecode.Bytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Opcode;

public class LowLevelLoggingTransformer implements ClassFileTransformer {

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> __, ProtectionDomain ___, byte[] data) throws IllegalClassFormatException {
		if (!className.equals("Fib")) {
			return data;
		}
		try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(data))) {
			ClassFile file = new ClassFile(in);
			transform(file);
			try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
				try (DataOutputStream dout = new DataOutputStream(out)) {
					file.write(dout);
				}
				return out.toByteArray();
			}
		} catch (IOException | BadBytecode e) {
			e.printStackTrace();
			return data;
		}
	}

	private void transform(ClassFile file) throws BadBytecode {
		@SuppressWarnings("unchecked")
		List<MethodInfo> methods = file.getMethods();
		for (MethodInfo method : methods) {
			if (method.getName().equals("fib")) {
				transform(method);
			}
		}
		
	}

	// "foo" + "bar"
	// new StringBuilder("foo").append("bar").toString()
	
	private void transform(MethodInfo method) throws BadBytecode {
		CodeAttribute code = method.getCodeAttribute();
		CodeIterator iterator = code.iterator();

		// Goal: insert System.err.println("fib(" + n + ")");
		Bytecode bytecode = new Bytecode(method.getConstPool(), 0, 0);
		
		// Lclass/name;
		// [Lclass/name;
		// B byte
		// S short
		// I int
		// J long
		// C char
		// Z boolean
		// F float
		// D double
		// V void
		
		// wikipedia java bytecode
		
		bytecode.addGetstatic("java.lang.System", "err", "Ljava/io/PrintStream;");
		// System.err
		//bytecode.addLdc("Hello world");
		bytecode.addLdc(method.getName() + "(");		
		// System.err, "fib("
		bytecode.addNew("java.lang.StringBuilder");
		// System.err, "fib(", StringBuilder
		bytecode.add(Opcode.DUP_X1);
		// System.err, StringBuilder, "fib(", StringBuilder
		bytecode.add(Opcode.SWAP);
		// System.err, StringBuilder, StringBuilder, "fib("		
		bytecode.addInvokespecial("java.lang.StringBuilder", "<init>", "(Ljava/lang/String;)V");
		// System.err, StringBuilder("fib(")
		bytecode.addIload(0);
		// System.err, StringBuilder("fib("), n
		bytecode.addInvokevirtual("java.lang.StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
		// System.err, StringBuilder("fib(" + n)
		bytecode.addLdc(")");
		// System.err, StringBuilder("fib(" + n), ")"
		bytecode.addInvokevirtual("java.lang.StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;");
		// System.err, StringBuilder("fib(" + n + ")")
		bytecode.addInvokevirtual("java.lang.Object", "toString", "()Ljava/lang/String;");
		// System.err, "fib(" + n + ")"
		bytecode.addInvokevirtual("java.io.PrintStream", "println", "(Ljava/lang/String;)V");
		
		iterator.insert(bytecode.get());
	}

}
