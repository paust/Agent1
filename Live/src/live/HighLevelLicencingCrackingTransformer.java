package live;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.ClassFile;

public class HighLevelLicencingCrackingTransformer implements ClassFileTransformer {

	private final String className;
	private final String methodName;
	
	public HighLevelLicencingCrackingTransformer(String args) {
		int index = args.lastIndexOf('.');
		className = args.substring(0, index);
		methodName = args.substring(index + 1);
	}

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> __, ProtectionDomain ___, byte[] data) throws IllegalClassFormatException {
		if (!this.className.equals(className)) {
			return data;
		}
		System.out.println("transforming " + className);
		try (InputStream in = new ByteArrayInputStream(data)) {
			ClassPool pool = ClassPool.getDefault();
			CtClass clazz = pool.makeClass(in);
			for (CtMethod method : clazz.getDeclaredMethods()) {
				System.out.println("checking " + method.getName());
				if (method.getName().equals(methodName)) {
					System.out.println("transforming " + method.getName());
					transformMethod(method);
				}
			}
			ClassFile file = clazz.getClassFile2();
			try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
				try (DataOutputStream dout = new DataOutputStream(out)) {
					file.write(dout);
				}
				System.out.println("transformed " + className);
				return out.toByteArray();
			}
		} catch (IOException | RuntimeException | CannotCompileException | NotFoundException e) {
			e.printStackTrace();
			return data;
		}
	}

	private void transformMethod(CtMethod method) throws CannotCompileException, NotFoundException {
		if (method.getReturnType() != CtClass.booleanType) {
			System.out.println(method.getName() + " does not return a boolean");
			return;
		}
		method.insertBefore("return true;"); // special syntax, "return n;" does not work, $1 first parameter, $0 this, $$ method name
	}

	
	
	
}
