import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Suite {

	public static void main(String[] args) throws IOException {
		int n = Integer.valueOf(args[0]);
		String file = args[1];
		long duration = Long.MAX_VALUE;
		for(int i = 0; i < 10; i++) {
			long time = System.currentTimeMillis();
			Fib.fib(n);
			duration = Math.min(duration, System.currentTimeMillis() - time);
			System.out.println(duration + "ms");
		}
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(String.valueOf(duration));
		}
	}
	
}
