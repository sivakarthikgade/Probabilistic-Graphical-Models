import java.util.Map;

public class MLEParameterLearning {
	
	public static void main(String args[]) throws Exception {
		double startTime = System.currentTimeMillis();
		if(args.length < 4) {
			throw new Exception("Please pass input network, train, test and output network files as program arguments.");
		}
		NetworkManager mgr = new NetworkManagerImpl();
		Map<int[], Double> dataSet = mgr.gatherSamplesFromDataSet(args[1]);
		Network origNetwork = mgr.createNetwork(args[0]);
		Network lrndNetwork = origNetwork.clone(false, 1);
		mgr.learnNetworkParametersFODMLE(lrndNetwork, dataSet);
		lrndNetwork.writeToFile(args[3]);
		double lld = mgr.getLogLikelihoodDifference(origNetwork, lrndNetwork, args[2]);

		Util.log("Log Likelihood Difference: "+lld);
 		Util.log("MLEParameterLearning: Total execution time->"+((double)(System.currentTimeMillis()-startTime)/1000)+" secs.");
	}

}
