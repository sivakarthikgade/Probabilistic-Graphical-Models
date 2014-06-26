
public class SamplingBasedVariableElimination {
	
	public static void main(String args[]) throws Exception {
		double startTime = System.currentTimeMillis();
		if(args.length < 5) {
			throw new Exception("Please pass network and evidence files' paths, w, N, isAdaptive as input.");
		}
		NetworkManager mgr = new NetworkManagerImpl();
		double z = mgr.computeSamplingBasedPOE(args);
		Util.log("Probability of Evidence: "+z);
 		Util.log("SamplingBasedVariableElimination: Total execution time->"+((double)(System.currentTimeMillis()-startTime)/1000)+" secs.");
	}

}
