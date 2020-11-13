import java.util.ArrayList;
import java.util.HashSet;

public abstract class Generic_Problem {
	
	public String initialState;
	public Operators operators;
	public String stateSpace;
	public String pathCost;
	public abstract boolean goalTest(String currentState);
//	public abstract ArrayList<ST_Node> getNewNodes(ArrayList<String> newStates, ST_Node parent, HashSet<String> dict);
	
	

}
