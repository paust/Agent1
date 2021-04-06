package live;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.management.ManagementFactory;

public class Agent {
    public static void premain(String args, Instrumentation instrumentation)  {
        Util.do1();
        String clazz = args.substring(0, args.lastIndexOf('.'));
        String method = args.substring(clazz.length() + 1);

//        instrumentation.addTransformer(new HighLevelCrackingTransformer(args), true);
        instrumentation.addTransformer(new LowLevelLoggingTransformer(clazz, method), true);
//        System.out.println("all loades classes");
        try {
        for (Class<?> clasz : instrumentation.getAllLoadedClasses()) {
            if(instrumentation.isModifiableClass(clasz)) {
                instrumentation.retransformClasses(clasz  );

            }

//            else {
//                System.err.println("Cannot retransform:" + clasz.getName());
//            }
//            System.out.println(clasz.getName());
        }
//        Util.getT1();
//            instrumentation.retransformClasses();
        } catch (UnmodifiableClassException e) {
            e.printStackTrace();
        }

    }
}
