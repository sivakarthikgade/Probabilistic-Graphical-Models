import java.io.BufferedWriter;
import java.io.FileWriter;


public class ExperimentLikelihoodWeighting {

	double alpha[] = {0, 0.02, 0.05, 0.2, 0.5, 1};
	int I[] = {350, 750};
	int N[] = {20, 50, 100};
	String uaiFile, evidFile;
	double poe = 0;
	double factor = Math.pow(10, 3);
	
	public static void main(String args[]) throws Exception {
		ExperimentLikelihoodWeighting exp = new ExperimentLikelihoodWeighting();
		
		exp.uaiFile = args[0];
		exp.evidFile = args[1];
		exp.poe = Double.parseDouble(args[2]);
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(exp.uaiFile.substring(0, exp.uaiFile.lastIndexOf(".")) + "-result2.csv.log"));
		bw.write("Alpha\\(I;N),,(350;20),(350;50),(350;100),(750;20),(750;50),(750;100)");
		bw.newLine();
		bw.flush();
		for(int a = 0; a < exp.alpha.length; a++) {
			String timeLine = ""+exp.alpha[a]+",Time";
			String errorLine = ""+exp.alpha[a]+",Error";

			for(int i = 0; i < exp.I.length; i++) {
				for(int n = 0; n < exp.N.length; n++) {
					double tt[] = new double[10];
					double error[] = new double[10];
					for(int itr = 0; itr < 10; itr++) {
						double startTime = System.currentTimeMillis();
						NetworkManager mgr = new NetworkManagerImpl();
						Network P = mgr.createNetwork(exp.uaiFile);
//						mgr.computeMinDegreeOrdering(P);
						Network Q = P.clone(true, -1);
						mgr.clampEvidence(Q, exp.evidFile);
						double z = mgr.computeLikelihoodWeightingBasedPOE(P, Q, exp.alpha[a], exp.I[i], exp.N[n]);
						tt[itr] = ((double)(System.currentTimeMillis()-startTime)/1000);
						error[itr] = (Math.log10(exp.poe) - Math.log10(z))/Math.log10(exp.poe);
					}

					double ttMean = 0, ttSD = 0, eMean = 0, eSD = 0;
					
					for(double tti: tt) {
						ttMean = ttMean + tti;
					}
					ttMean = ttMean/tt.length;
					for(double tti: tt) {
						ttSD = ttSD + Math.pow(tti - ttMean, 2);
					}
					ttSD = ttSD/tt.length;
					ttSD = Math.sqrt(ttSD);

					for(double ei: error) {
						eMean = eMean + ei;
					}
					eMean = eMean/error.length;
					for(double ei: error) {
						eSD = eSD + Math.pow(ei - eMean, 2);
					}
					eSD = eSD/error.length;
					eSD = Math.sqrt(eSD);
					
					timeLine = timeLine + "," + (Math.round(ttMean*exp.factor)/exp.factor)+" +/- "+(Math.round(ttSD*exp.factor)/exp.factor);
					errorLine = errorLine + "," + (Math.round(eMean*exp.factor)/exp.factor)+" +/- "+(Math.round(eSD*exp.factor)/exp.factor);
				}
			}
			bw.write(timeLine);
			bw.newLine();
			bw.write(errorLine);
			bw.newLine();
			bw.flush();
		}
		bw.close();
	}
}
