package at.jku.ssw.mevss.jpmb.parametertracking;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javassist.NotFoundException;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.Bytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Opcode;

public class LowLevelTransformer implements ClassFileTransformer {

	private static final Logger LOGGER = Logger.getLogger(LowLevelTransformer.class.getName());
	
	private final String className;
	private final String methodName;
	
	public LowLevelTransformer(String className, String methodName) {
		this.className = className;
		this.methodName = methodName;
		LOGGER.log(Level.INFO, "initialized for " + className + "." + methodName);
	}
	
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		if(this.className.equals(className.replace('/', '.'))) {
			try(DataInputStream in = new DataInputStream(new ByteArrayInputStream(classfileBuffer))) {
				ClassFile clazz = new ClassFile(in);
				transform(clazz);
				try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
					try(DataOutputStream dataOut = new DataOutputStream(out)) {
						clazz.write(dataOut);
					}
					return out.toByteArray();
				}
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "error occured", e);
				return classfileBuffer;
			} catch (BadBytecode e) {
				LOGGER.log(Level.SEVERE, "error occured", e);
				return classfileBuffer;
			} catch (Throwable t) {
				LOGGER.log(Level.SEVERE, "error occured", t);
				return classfileBuffer;				
			}
		} else {
			return classfileBuffer;
		}
	}

	private void transform(ClassFile clazz) throws BadBytecode, NotFoundException {
		LOGGER.log(Level.INFO, "instrumenting " + clazz.getName());
		@SuppressWarnings("unchecked")
		List<MethodInfo> methods = clazz.getMethods();
		for(MethodInfo method : methods) {
			if(this.methodName.equals(method.getName())) {
				transform(clazz.getName(), method);
			}
		}
	}

	private void transform(String clazz, MethodInfo method) throws BadBytecode, NotFoundException {
		LOGGER.log(Level.INFO, "instrumenting " + method.getName());
		CodeAttribute code = method.getCodeAttribute();
		CodeIterator iterator = code.iterator();
		iterator.insert(getCodeBefore(clazz, method));
		while(iterator.hasNext()) {
			int bci = iterator.next();
			int opcode = iterator.byteAt(bci);
			if(opcode == Opcode.ARETURN || opcode == Opcode.IRETURN || opcode == Opcode.LRETURN || opcode == Opcode.FRETURN || opcode == Opcode.DRETURN) {
				iterator.insert(bci, getCodeAfter(clazz, method));
			}
		}
		code.computeMaxStack();
		LOGGER.log(Level.INFO, "instrumented " + method.getName());
	}
	
	private byte[] getCodeBefore(String clazz, MethodInfo method) throws NotFoundException {
		return emitPrintCall(clazz, method, false);
	}
	
	private byte[] getCodeAfter(String clazz, MethodInfo method) throws NotFoundException {
		return emitPrintCall(clazz, method, true);
	}

	private byte[] emitPrintCall(String clazz, MethodInfo method, boolean assumeReturnValueOnStack) throws NotFoundException {
		boolean isStatic = (method.getAccessFlags() & AccessFlag.STATIC) != 0;
		Bytecode bytecode = new Bytecode(method.getConstPool(), 3, 0);
		
		// build string
		
		emitCreateStringBuilder(bytecode);
		if(isStatic) {
			emitAppendStringToStringBuilder(bytecode, clazz);
		} else {
			String[] wrapper = getTypeSpecificWrappers(clazz);
			emitAppendStringToStringBuilder(bytecode, wrapper[0]);
			emitLoad(bytecode, 0, "L" + clazz.replace('.', '/') + ";");
			emitAppendToStringBuilder(bytecode, clazz);
			emitAppendStringToStringBuilder(bytecode, wrapper[1]);
		}
		emitAppendStringToStringBuilder(bytecode, "." + method.getName() + "(");
		
		String[] parameterSignatures = getParameterSignatures(method.getDescriptor());
		int loc_offset = isStatic ? 0 : 1;
		for(int i = 0; i < parameterSignatures.length; i++) {
			if(i > 0) {
				emitAppendStringToStringBuilder(bytecode, ", ");
			}
			String parameterSignature = parameterSignatures[i];
			String[] wrapper = getTypeSpecificWrappers(getTypeFromSignature(parameterSignature));
			emitAppendStringToStringBuilder(bytecode, wrapper[0]);
			emitLoad(bytecode, loc_offset + i, parameterSignature);
			emitAppendToStringBuilder(bytecode, parameterSignature);
			emitAppendStringToStringBuilder(bytecode, wrapper[1]);
			if(isDoubleSlotType(parameterSignature)) {
				loc_offset++;
			}
		}
		
		emitAppendStringToStringBuilder(bytecode, ")");
		
		if(assumeReturnValueOnStack) {
			emitAppendStringToStringBuilder(bytecode, " => ");
			
			String returnTypeSignature = getReturnSignature(method.getDescriptor());
			String[] wrapper = getTypeSpecificWrappers(getTypeFromSignature(returnTypeSignature));

			emitAppendStringToStringBuilder(bytecode, wrapper[0]);
			// [result, stringbuilder]
			if(isDoubleSlotType(returnTypeSignature)) {
				// [result1, result2, stringbuilder]
				bytecode.addOpcode(Opcode.DUP_X2);
				// [stringbuilder, result1, result2, stringbuilder]
				bytecode.addOpcode(Opcode.POP);
				// [stringbuilder, result1, result2]
				bytecode.addOpcode(Opcode.DUP2_X1);
				// [result1, result2, stringbuilder, result1, result2]				
			} else {
				// [result, stringbuilder]				
				bytecode.addOpcode(Opcode.SWAP);
				// [stringbuilder, result]
				bytecode.addOpcode(Opcode.DUP_X1);
				// [result, stringbuilder, result]				
			}
			// [result, stringbuilder, result]
			emitAppendToStringBuilder(bytecode, returnTypeSignature);
			emitAppendStringToStringBuilder(bytecode, wrapper[1]);
		}
		
		//print
		
		bytecode.addGetstatic("java.lang.System", "err", "Ljava/io/PrintStream;");
		bytecode.addOpcode(Opcode.SWAP);
		bytecode.addInvokevirtual("java.io.PrintStream", "println", "(Ljava/lang/Object;)V");
		
		return bytecode.get();
	}

	private void emitLoad(Bytecode bytecode, int n, String signature) throws NotFoundException {
		switch(signature) {
			case "B": bytecode.addIload(n); break;
			case "S": bytecode.addIload(n); break;
			case "I": bytecode.addIload(n); break;
			case "J": bytecode.addLload(n); break;
			case "F": bytecode.addFload(n); break;
			case "D": bytecode.addDload(n); break;
			case "Z": bytecode.addIload(n); break;
			case "C": bytecode.addIload(n); break;
			default:
				if(signature.charAt(0) == 'L' || signature.charAt(0) == '[') {
					bytecode.addAload(n);
				} else {
					assert false;
					throw new NotFoundException("Unknown signature: " + signature);
				}
				break;
		}
	}
	
	private void emitCreateStringBuilder(Bytecode bytecode) {
		// []
		bytecode.addNew("java.lang.StringBuilder");
		// [stringbuilder]
		bytecode.addOpcode(Opcode.DUP);
		// [stringbuilder, stringbuilder]
		bytecode.addInvokespecial("java.lang.StringBuilder", "<init>", "()V");		
		// [stringbuilder]
	}
	
	private void emitAppendStringToStringBuilder(Bytecode bytecode, String string) {
		if(!string.isEmpty()) {
			bytecode.addLdc(string);
			emitAppendToStringBuilder(bytecode, "Ljava/lang/CharSequence;");
		}
	}
	
	private void emitAppendToStringBuilder(Bytecode bytecode, String type) {
		// [stringbuilder, value]
		switch(type) {
			case "B": case "S":
				type = "I";
				break;
			default:
				if (type.startsWith("[")) {
					if(type.startsWith("[L") || type.startsWith("[[")) {
						type = "[Ljava/lang/Object;";
					}
					bytecode.addInvokestatic("java.util.Arrays", "toString", "(" + type + ")Ljava/lang/String;");
					type = "Ljava/lang/CharSequence;";
				} else if(type.length() > 1 && !type.equals("Ljava/lang/CharSequence;")) {
					type = "Ljava/lang/Object;";
				}
		}
		// [stringbuilder, value]
		bytecode.addInvokevirtual("java.lang.StringBuilder", "append", "(" + type + ")Ljava/lang/StringBuilder;");
		// [stringbuilder]
	}
	
	private String[] getTypeSpecificWrappers(String type) {
		switch(type) {
			case "byte": return new String[] { "", "B" };
			case "short": return new String[] { "", "S" };
			case "int": return new String[] { "", "" };
			case "long": return new String[] { "", "L" };
			case "float": return new String[] { "", "F" };
			case "double": return new String[] { "", "D" };
			case "boolean": return new String[] { "", "" };
			case "char": return new String[] { "'", "'" };
			case "java.lang.String": return new String[] { "\"", "\"" };
			default: 
				if(type.endsWith("[]")) return new String[] {"", ""};
				else return new String[] { "{" + type + ": ", "}" };
		}
	}

	private String[] getParameterSignatures(String methodSignature) {
		List<String> parameterSignatures = new ArrayList<>();
		methodSignature = methodSignature.substring(1, methodSignature.indexOf(')'));
		int cursor = 0;
		while(cursor < methodSignature.length()) {
			char t = methodSignature.charAt(cursor);
			int rank = 0;
			while(t == '[') {
				rank++;
				t = methodSignature.charAt(++cursor);
			}
			String type;
			if(t == 'L') {
				type = methodSignature.substring(cursor, methodSignature.indexOf(';', cursor) + 1);
				cursor = cursor + type.length();
			} else {
				type = String.valueOf(t);
				cursor++;
			}
			for(int i = 0; i < rank; i++) {
				type = "[" + type;
			}
			parameterSignatures.add(type);
		}
		return parameterSignatures.toArray(new String[parameterSignatures.size()]);
	}
	
	private String getReturnSignature(String methodSignature) {
		return methodSignature.substring(methodSignature.indexOf(')') + 1);
	}
	
	private String getTypeFromSignature(String signature) throws NotFoundException {
		switch(signature) {
			case "B": return "byte";
			case "S": return "short";
			case "I": return "int";
			case "J": return "long";
			case "F": return "float";
			case "D": return "double";
			case "Z": return "boolean";
			case "C": return "char";
			default: 
				if(signature.charAt(0) == 'L') {
					return signature.substring(1, signature.length() - 1).replace('/', '.');
				} else if(signature.charAt(0) == '[') {
					return getTypeFromSignature(signature.substring(1))+ "[]";
				} else {
					assert false;
					throw new NotFoundException("Unknown signature: " + signature);
				}
		}
	}
	
	private boolean isDoubleSlotType(String signature) {
		return signature.equals("J") || signature.equals("D");
	}

	/*
	private void emitConcatStrings(Bytecode bytecode) {
		emitConcatStrings(bytecode, "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;");
	}
	
	private void emitConcatStrings(Bytecode bytecode, String type1, String type2) {
		// [value1, value2]
		bytecode.addOpcode(Opcode.SWAP);
		// [value2, value1]
		bytecode.addNew("java.lang.StringBuilder");
		// [value2, value1, stringbuilder]
		bytecode.addOpcode(Opcode.DUP);
		// [value2, value1, stringbuilder, stringbuilder]
		bytecode.addInvokespecial("java.lang.StringBuilder", "<init>", "()V");
		// [value2, value1, stringbuilder]
		bytecode.addOpcode(Opcode.SWAP);
		// [value2, stringbuilder, value1]
		bytecode.addInvokevirtual("java.lang.StringBuilder", "append", "(" + type1 + ")Ljava/lang/StringBuilder;");
		// [value2, stringbuilder]
		bytecode.addOpcode(Opcode.SWAP);
		// [stringbuilder, value2]
		bytecode.addInvokevirtual("java.lang.StringBuilder", "append", "(" + type2 + ")Ljava/lang/StringBuilder;");
		// [stringbuilder]
		bytecode.addInvokevirtual("java.lang.Object", "toString", "()Ljava/lang/String;");
		// [string]
	}
	*/

}
