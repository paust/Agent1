
import java.io.*;
import java.util.*;

public class DataFileCreator {

	public static void main(String[] args) throws IOException {
		Map<Integer, long[]> data = new HashMap<>();
		String[] headers = null;
		//read
		for(File child : new File(".").listFiles()) {
			String name = child.getName();
			if(name.endsWith(".csv")) {
				int n = Integer.parseInt(name.substring(name.indexOf('_') + 1, name.length() - ".csv".length()));
				try(BufferedReader in = new BufferedReader(new FileReader(child))) {
					headers = in.readLine().split(";");
					long[] localData = new long[headers.length];
					data.put(n, localData);
					for(String line = in.readLine(); line != null; line = in.readLine()) {
						String[] values = line.split(";");
						for(int i = 0; i < values.length; i++) {
							String value = values[i];
							String header = headers[i];
							if(header.length() > 0) {
								localData[i] = Long.parseLong(value);
							}
						}
					}
				}
			}
		}
		//write
		try(BufferedWriter out = new BufferedWriter(new FileWriter("overhead.data"))) {
			out.write("\"n\" ");
			for(String header : headers) {
				out.write("\"");
				out.write(header);
				out.write("\" ");
			}
			out.write("\n");
			int processed = 0;
			int n = 0;
			while(processed < data.size()) {
				long[] values = data.get(n);
				if(values != null) {
					out.write(String.valueOf(n));
					out.write(" ");
					for(long value : values) {
						out.write(String.valueOf(value));
						out.write(" ");
					}
					out.write("\n");
					processed++;
				}
				n++;
			}
		}
	}

}
