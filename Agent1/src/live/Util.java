package live;


import java.lang.management.*;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Util {

    private final static Stack<Long> wallTime  = new Stack<>();
    private final static Stack<Long> cpuTime  = new Stack<>();
    private final static Stack<Long> gcCount  = new Stack<>();
    private final static Stack<Long> gcTime  = new Stack<>();

    private static final List<GarbageCollectorMXBean> gcps = (List<GarbageCollectorMXBean>) ManagementFactory.getGarbageCollectorMXBeans();
//    private static final OperatingSystemMXBean mxbean = ManagementFactory.getOperatingSystemMXBean();
    private static final  ThreadMXBean tmx = ManagementFactory.getThreadMXBean();
    private static final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

    public static void enter() {
        cpuTime.push(tmx.getCurrentThreadCpuTime());
        wallTime.push(runtimeMXBean.getUptime());
        gcCount.push(gcps.get(0).getCollectionCount()); // todo sum all
        gcTime.push(gcps.get(0).getCollectionTime());
        StackTraceElement[] traces = tmx.getThreadInfo(Thread.currentThread().getId(),100).getStackTrace();
        int i = 0;
//        if (traces.length > 5) {
//            System.err.print( "> " +traces[5].getClassName() + "::" + traces[5].getMethodName() + "-> ");
////            System.err.print(" -> " + traces[4].getClassName() + "::" + traces[4].getMethodName());
//        }
//        for (StackTraceElement trace : traces) {
//            if (i > 4) {
//                System.err.print (" -> " + trace.getClassName() + "::" + trace.getMethodName());
//
//            }
//            i++;
//        }
//        System.err.println();
    }

    public static void leave() {
        long dcpuTime = (tmx.getCurrentThreadCpuTime() - cpuTime.pop())/1000000000;
        long dwallTime = (runtimeMXBean.getUptime() - wallTime.pop());
        long dgcCount = (gcps.get(0).getCollectionCount() - gcCount.pop());
        long dgcTime = (gcps.get(0).getCollectionTime() - gcTime.pop());
        System.err.printf(" wall time %s ms, cpu time %sms, gcs: %s, gc time: %sms\n", dwallTime, dcpuTime, dgcCount, dgcTime);
    }
    public static void do1() {
       System.err.println(Thread.currentThread().getStackTrace());
    }

    public static void getT1() {

// Get the number of processors
//        int numProcessors = mxbean.getAvailableProcessors();

// Get the Oracle JDK-specific attribute Process CPU time
//        long cpuTime = mxbean.getProcessCpuTime();
//        System.out.println("proc " + numProcessors + " cpu " + cpuTime);

        int i = 0;
        for(GarbageCollectorMXBean gcp : gcps) {
//            System.out.println("gcp " + i + " " +gcp.getCollectionCount() + " " + gcp.getCollectionTime());
            i++;
        }

//        System.out.println("Threads " + tmx.getAllThreadIds().length );
//        System.out.println("Cpu Time " + tmx.getCurrentThreadCpuTime() +  "  tid " + Thread.currentThread().getId());
//        System.nanoTime();
//        tmx.getThreadCpuTime()
    }
}
