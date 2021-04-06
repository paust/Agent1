package at.jku.ssw.mevss.jpmb.parametertracking;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class Agent {
	
	public static void premain(String args, Instrumentation instrumentation) {
		main(instrumentation, args);
	}
	
	public static void agentmain(String args, Instrumentation instrumentation) {
		main(instrumentation, args);
	}
	
	private static void main(Instrumentation instrumentation, String args) {
		//parse argument and setup transformer
		{
			String clazz = args.substring(0, args.lastIndexOf('.'));
			String method = args.substring(clazz.length() + 1);
			ClassFileTransformer transformer;
			String transformerType = System.getProperty("transformer-type");
			switch(transformerType != null ? transformerType : "high") {
				case "low":
					transformer = new LowLevelTransformer(clazz, method);
					break;
				case "high":
					transformer = new HighLevelTransformer(clazz, method);
					break;
				default:				
					throw new IllegalArgumentException("Illegal transformer!");
			}
			instrumentation.addTransformer(transformer, true);
		}
				
		//retransform existing classes
		{
			for(Class<?> clazz : instrumentation.getAllLoadedClasses()) {
				if(!clazz.isArray()) {
					try {
						instrumentation.retransformClasses(clazz);
					} catch (UnmodifiableClassException e) {
						System.err.println(e + ": " + clazz.getName());
					}
				}
			}
		}
	}
	
}
