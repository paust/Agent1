package at.jku.ssw.mevss.jpmb.parametertracking;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.ClassFile;

public class HighLevelTransformer implements ClassFileTransformer {

	private static final Logger LOGGER = Logger.getLogger(HighLevelTransformer.class.getName());
	
	private final String className;
	private final String methodName;
	
	public HighLevelTransformer(String className, String methodName) {
		this.className = className;
		this.methodName = methodName;
		LOGGER.log(Level.INFO, "initialized for " + className + "." + methodName);
	}
	
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		if(this.className.equals(className.replace('/', '.'))) {
			try(DataInputStream in = new DataInputStream(new ByteArrayInputStream(classfileBuffer))) {
				ClassPool pool = ClassPool.getDefault();
				CtClass clazz = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
				transform(clazz);
				ClassFile file = clazz.getClassFile2();
				try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
					try(DataOutputStream dataOut = new DataOutputStream(out)) {
						file.write(dataOut);
					}
					return out.toByteArray();
				}
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "error occured", e);
				return classfileBuffer;
			} catch (CannotCompileException e) {
				LOGGER.log(Level.SEVERE, "error occured", e);
				assert false : e;
				return classfileBuffer;
			} catch (NotFoundException e) {
				LOGGER.log(Level.SEVERE, "error occured", e);
				assert false : e;
				return classfileBuffer;
			}
		} else {
			return classfileBuffer;
		}
	}

	private void transform(CtClass clazz) throws CannotCompileException, NotFoundException {
		LOGGER.log(Level.INFO, "instrumenting " + clazz.getName());
		for(CtMethod method : clazz.getDeclaredMethods()) {
			if(this.methodName.equals(method.getName())) {
				transform(method);
			}
		}
	}

	private void transform(CtMethod method) throws CannotCompileException, NotFoundException {
		LOGGER.log(Level.INFO, "instrumenting " + method.getName());
		final String srcBefore = createSrcBefore(method);
		final String srcAfter = createSrcAfter(method);
		LOGGER.log(Level.INFO, "inserting \"" + srcBefore + "\" at method start");
		LOGGER.log(Level.INFO, "inserting \"" + srcAfter + "\" at method end");
		method.insertBefore(srcBefore);
		method.insertAfter(srcAfter);
	}

	private String createSrcBefore(CtMethod method) throws NotFoundException {
		return createSrc(method, false);
	}

	private String createSrcAfter(CtMethod method) throws NotFoundException {
		return createSrc(method, true);
	}

	private String createSrc(CtMethod method, boolean includeResult) throws NotFoundException {
		boolean isStatic = (method.getModifiers() & Modifier.STATIC) != 0;
		StringBuilder src = new StringBuilder();
		src.append("System.err.println(");
		if(isStatic) {
			src.append("\"");
			src.append(method.getDeclaringClass().getName());
		} else {
			src.append(toTypeSpecificString("$0", method.getDeclaringClass().getName()));
			src.append(" + \"");
		}
		src.append('.');
		src.append(method.getName());
		src.append("\"");
		src.append(" + \"(\"");
		CtClass[] parameterTypes = method.getParameterTypes();
		for(int i = 0; i < parameterTypes.length; i++) {
			if(i > 0) {
				src.append(" + \", \"");
			}
			src.append(" + ");
			src.append(toTypeSpecificString("$" + (i + 1), parameterTypes[i].getName()));
		}
		src.append(" + \")\"");
		if(includeResult) {
			src.append(" + \" => \" + ");
			src.append(toTypeSpecificString("$_", method.getReturnType().getName()));
		}
		src.append(");");
		return src.toString();
	}
	
	private String toTypeSpecificString(String value, String type) {
		switch(type) {
			case "byte": return value + " + 'B'";
			case "short": return value + " + 'S'";
			case "int": return value;
			case "long": return value + " + 'L'";
			case "float": return value + " + 'F'";
			case "double": return value + " + 'D'";
			case "boolean": return value;
			case "char": return "\"'\" + " + value + " + \"'\"";
			case "java.lang.String": return "\"\\\"\" + " + value + " + \"\\\"\"";
			default:
				if(type.endsWith("[]")) {
					return "java.util.Arrays.toString(" + value + ")";
				} else {
					return "\"{" + type + ": \" + " + value + " + \"}\"";
				}
		}
	}

}
