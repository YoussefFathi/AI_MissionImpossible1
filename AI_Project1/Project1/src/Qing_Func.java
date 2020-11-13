import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;

public interface Qing_Func {
	public Queue<ST_Node> addNodes(Queue<ST_Node> queue,ArrayList<String> newStates, ST_Node parent, HashSet<String> dict);
}
