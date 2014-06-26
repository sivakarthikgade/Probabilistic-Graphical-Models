import java.io.*;
import java.util.*;

public class Test {

	public static void main(String args[]) throws Exception {
		List<String> output = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader("temp.csv"));
		String str = null;
		while((str = br.readLine()) != null) {
			String tokens[] = str.split(",");
			for(int i = 0; i < tokens.length; i++) {
				if(tokens[i].contains("+/-")) {
					tokens[i] = tokens[i].substring(0, tokens[i].indexOf("+"));
				}
			}
			str = "";
			for(int i = 0; i < tokens.length; i++) {
				str = str + tokens[i]+",";
			}
			output.add(str);
		}
		br.close();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("temp-op6.csv"));
		for(int i = 0; i < output.size(); i++) {
			bw.write(output.get(i));
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
}
