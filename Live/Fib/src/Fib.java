
public class Fib {

	public static void main(String[] args) {
		if (!hasLicence()) {
			System.exit(1);
		}
		System.out.println(fib(Integer.valueOf(args[0])));
	}
	
	private static boolean hasLicence() {
		return false;
	}
	
	public static long fib(int n) {
		if(0 <= n && n <= 1) {
			return n;
		} else {
			return fib(n - 2) + fib(n - 1);
		}
	}
}
