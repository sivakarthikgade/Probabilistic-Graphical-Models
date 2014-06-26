
public class LikelihoodWeightingParallel {

	public static void main(String args[]) throws Exception {
		double startTime = System.currentTimeMillis();
		if(args.length < 5) {
			throw new Exception("Please pass Network file, Evidence file, Alpha, I, N as input.");
		}
		NetworkManager mgr = new NetworkManagerImpl();
		Network P = mgr.createNetwork(args[0]);
		Network Q = P.clone(true, -1);
		mgr.clampEvidence(Q, args[1]);
		double z1 = mgr.computeLikelihoodWeightingBasedPOEParallel(P, Q, Double.parseDouble(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
		Util.log("LikelihoodWeighting: POE-> z1: "+z1);
		Util.log("LikelihoodWeighting: Total execution time-> "+((double)(System.currentTimeMillis()-startTime)/1000)+" secs.");
	}
}
