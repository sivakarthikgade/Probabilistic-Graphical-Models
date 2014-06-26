
public class VariableElimination {

	public VariableElimination() throws Exception {
	}

	public static void main(String args[]) throws Exception {
		double startTime = System.currentTimeMillis();
		if(args.length < 2) {
			throw new Exception("Please pass network and evidence files' paths as input.");
		}
		NetworkManager mgr = new NetworkManagerImpl();
		Network network = mgr.createNetwork(args[0]);
		Util.log("VariableElimination: Create Network");
		mgr.instantiateEvidence(network, args[1]);
		Util.log("VariableElimination: Inst Evidence");
		double z = mgr.computePOE(network);
		Util.log("VariableElimination: Compute POE: "+z);
		Util.log("VariableElimination: Total execution time->"+((double)(System.currentTimeMillis()-startTime)/1000)+" secs.");
	}

}
