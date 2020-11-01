import java.util.ArrayList;

public abstract class Generic_Problem {
	
	public String initialState;
	public Operators operators;
	public String stateSpace;
	public String pathCost;
	public abstract boolean goalTest(String currentState);
	public abstract ArrayList<ST_Node> getNewNodes(ArrayList<String> newStates, ST_Node parent);
	
	

}
