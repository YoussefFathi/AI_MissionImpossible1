import java.util.ArrayList;

public class Generic_Search {

	public static String general_search(Generic_Problem problem, Qing_Func func, int maxDepth) {
		ArrayList<ST_Node> queue = new ArrayList<ST_Node>();
		ST_Node initialNode = new ST_Node(problem.initialState, null, null, 0, new int[] { 0, 0 });
		queue.add(initialNode);
		int depth = 0;
		while (!queue.isEmpty()) {
			if (depth < maxDepth && maxDepth!=-1) {
				ST_Node currentNode = queue.remove(0);
				if (problem.goalTest(currentNode.getState())) {
					return new String("Solution is Here");
				}
				ArrayList<String> newStates = problem.operators.apply(currentNode);
				ArrayList<ST_Node> newNodes = problem.getNewNodes(newStates, currentNode);
				queue = func.addNodes(queue, newNodes);
				depth++;
			}else {
				break;
			}
		}
		return new String("Fail");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
