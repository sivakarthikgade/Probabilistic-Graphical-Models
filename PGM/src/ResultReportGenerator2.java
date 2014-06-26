import java.io.BufferedWriter;
import java.io.FileWriter;


public class ResultReportGenerator2 {

	static String[] pgms = {"2_28_s.binary"};
	static double[] pgmlogresults = {-10.2872};
	static int[] ws = {1, 2, 3, 4, 5};
	static int[] ns = {100, 1000, 10000, 20000};
	static boolean[] adaptives = {false, true};
	static String[][] results = new String[7][10];
	static double factor = Math.pow(10, 3);
	static BufferedWriter bw;
	
	public static void main(String args[]) throws Exception {
		bw = new BufferedWriter(new FileWriter("util2.log"));
		for(int p = 0; p < pgms.length; p++) {
			bw.write("Input Model: "+pgms[p]);
			bw.newLine();
			bw.write(",N ->,100,1K,10K,20K,100,1K,10K,20K");
			bw.newLine();
			for(int w = 0; w < ws.length; w++) {
				results[p][2*w] = "w="+(w+1)+",Time";
				results[p][2*w+1] = "w="+(w+1)+",Error";
				for(int a = 0; a < adaptives.length; a++) {
					for(int n = 0; n < ns.length; n++) {
						double[] error = new double[10];
						double[] timetaken = new double[10];
						for(int atmp = 0; atmp < 10; atmp++) {
							NetworkManager mgr = new NetworkManagerImpl();
							double startTimeMillis = System.currentTimeMillis();
							double z = mgr.computeSamplingBasedPOE(pgms[p]+".uai", pgms[p]+".uai.evid", ws[w], ns[n], adaptives[a]);
							timetaken[atmp] = ((double)(System.currentTimeMillis() - startTimeMillis))/((double)1000);
							z = Math.log10(z);
							error[atmp] = (pgmlogresults[p] - z)/pgmlogresults[p];
						}
						double meanTimeTaken = 0.0, sdTimeTaken = 0.0, meanError = 0.0, sdError = 0.0;

						for(double t: timetaken) {
							meanTimeTaken = meanTimeTaken + t;
						}
						meanTimeTaken=meanTimeTaken/timetaken.length;

						for(double t: timetaken) {
							sdTimeTaken = sdTimeTaken + Math.pow(t-meanTimeTaken, 2);
						}
						sdTimeTaken = sdTimeTaken/timetaken.length;
						sdTimeTaken = Math.sqrt(sdTimeTaken);
						results[p][2*w] = results[p][2*w] + "," + (Math.round(meanTimeTaken*factor)/factor)+" +/- "+(Math.round(sdTimeTaken*factor)/factor);

						for(double e: error) {
							meanError = meanError + e;
						}
						meanError = meanError/error.length;
						
						for(double e: error) {
							sdError = sdError + Math.pow(e-meanError, 2);
						}
						sdError = sdError/error.length;
						sdError = Math.sqrt(sdError);
						results[p][2*w + 1] = results[p][2*w + 1] + "," + (Math.round(meanError*factor)/factor)+" +/- "+(Math.round(sdError*factor)/factor);
						System.out.println("Done iteration: p-"+pgms[p]+", w-"+ws[w]+", a-"+adaptives[a]+", n-"+ns[n]+".");
					}
				}
				bw.write(results[p][2*w]);
				bw.newLine();
				bw.write(results[p][2*w + 1]);
				bw.newLine();
				bw.flush();
			}
		}
		bw.close();
	}
}
