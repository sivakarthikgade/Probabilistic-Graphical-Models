import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Test2 {
	public static void main(String args[]) throws Exception {
		
		int pCtr = 0, nCtr = 0;
		
		File froot = new File("root");
		if(!froot.exists()) {
			froot.mkdir();
		}
		
		File fp = new File("root\\+");
		if(!fp.exists()) {
			fp.mkdir();
		}
		
		File fn = new File("root\\-");
		if(!fn.exists()) {
			fn.mkdir();
		}
		
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		String str = br.readLine();
		while((str=br.readLine()) != null) {
			String[] tokens = str.split(",");
			if(tokens[1].trim().equals("+")) {
				pCtr++;
				BufferedWriter bwp = new BufferedWriter(new FileWriter("root\\+\\"+pCtr));
				bwp.write(tokens[0]);
				bwp.flush();
				bwp.close();
			} else if(tokens[1].trim().equals("-")) {
				nCtr++;
				BufferedWriter bwn = new BufferedWriter(new FileWriter("root\\-\\"+nCtr));
				bwn.write(tokens[0]);
				bwn.flush();
				bwn.close();
			}
		}
		br.close();
	}
}
