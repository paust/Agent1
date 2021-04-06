import java.lang.management.ThreadMXBean;

public class Fib {

	public static void main(String[] args) {
		if (!hasLicence()) {
			System.exit(1);
		}
		fib3(Integer.valueOf(args[0]),"x");
		System.out.println(fib(Integer.valueOf(args[0])));
	}
	
	private static boolean hasLicence() {
		return true;
	}
	
	public static long fib(int n) {
//		long n2= 0L;
//		n2 += fib2(n);
//		n2 += fib2(n);
//		return n2;
//		Test1.foo();
//		Test1 mist = new Test1();
//		System.out.println("Threadid " + Thread.currentThread().getId());
//		System.out.println(mist.getCounter());

		if(0 <= n && n <= 1) {
			return n;
		} else {
			return fib(n - 1) + fib(n - 2);
		}
	}

	public static long fib2(int n){
		return n + n;
	}

	public static long fib3(int n, String mm ) {
		if(0 <= n && n <= 1) {
			return n;
		} else {
			return fib3(n - 1,mm) + fib3 (n - 2,mm);
		}
	}
}
