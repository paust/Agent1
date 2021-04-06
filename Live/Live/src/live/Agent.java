package live;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class Agent {

	public static void premain(String args, Instrumentation instrumentation) {
		instrumentation.addTransformer(new HighLevelLicencingCrackingTransformer(args), true);
		instrumentation.addTransformer(new LowLevelLoggingTransformer(), true);
		try {
			for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
				if(instrumentation.isModifiableClass(clazz)) {
					instrumentation.retransformClasses(clazz);
				}
			}
		} catch (UnmodifiableClassException e) {
			e.printStackTrace();
		}
	}
	
	
}
