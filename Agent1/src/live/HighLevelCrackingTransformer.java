package live;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import javassist.*;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.ClassFile;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.MethodInfo;

import java.io.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.List;
import java.util.logging.Level;

public class HighLevelCrackingTransformer implements ClassFileTransformer {

    private final String className;
    private final String methodName;

    public HighLevelCrackingTransformer(String args) {
        int index = args.indexOf(".");
        className = args.substring(0, index);
        methodName = args.substring(index + 1);
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] data) throws IllegalClassFormatException {
        if (!this.className.equals(className)) {
            return data;
        }
        System.out.println("transforming " +className);
        ClassPool pool = ClassPool.getDefault();
        try (InputStream in = new ByteArrayInputStream(data)) {
            CtClass clazz = pool.makeClass(in);
            for (CtMethod method : clazz.getDeclaredMethods()) {
                System.out.println("checking " + method.getName());
                if (method.getName().equals(methodName)) {
                    System.out.println("transforming " + method.getName());
                    transformMethod(method);
                }
            }
            ClassFile file = clazz.getClassFile2();
            try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                try(DataOutputStream dout = new DataOutputStream(out)) {
                    file.write(dout);
                }
                return out.toByteArray();
            }
//            transformClass(clazz);
        } catch (IOException | CannotCompileException | NotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
            return data;
        }
//        System.err.println(className);
//        return data;
    }

    private void transformMethod(CtMethod method) throws CannotCompileException, NotFoundException {
        final String srcBefore = createSrcBefore(method);
        final String srcAfter = createSrcAfter(method);
        method.insertBefore(srcBefore);
        method.insertAfter(srcAfter);
//        method.insertBefore("live.Util.enter();");
//        method.insertAfter("live.Util.leave();");
    }

    private void transformClass(CtClass clazz) {

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
        src.append(includeResult ? "":"live.Util.enter();");

        src.append(includeResult ? "System.err.print(" : "System.err.println(" );
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

        src.append(includeResult ? "live.Util.leave();":"");
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
