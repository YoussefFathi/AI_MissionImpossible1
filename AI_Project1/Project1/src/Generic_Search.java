import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet; 
public class Generic_Search {
	

	public static String general_search(Generic_Problem problem, Qing_Func func, int maxDepth) {
		ArrayList<ST_Node> queue = new ArrayList<ST_Node>();
		HashSet<String> dict  = new HashSet<String>();
		ST_Node initialNode = new ST_Node(problem.initialState, null, null, 0, getComputedCost(problem.initialState));
		queue.add(initialNode);
		int depth = 0;
		while (!queue.isEmpty()) {
				if (depth > maxDepth && maxDepth!=-1) {
					break;
				}
				ST_Node currentNode = queue.remove(0);
				if(dict.contains(currentNode.getState())) {
					continue;
				}
				dict.add(currentNode.getState());
				System.out.println("Current State");
				System.out.println(currentNode.getState());
				System.out.println(Arrays.toString(currentNode.getCost()));

				if (problem.goalTest(currentNode.getState())) {
					return new String("Solution is Here");
				}
				ArrayList<String> newStates = problem.operators.apply(currentNode);
				System.out.println("New States" + " Depth: "+ currentNode.getDepth());
				System.out.println(newStates);
				
				ArrayList<ST_Node> newNodes = problem.getNewNodes(newStates, currentNode,dict);
				queue = func.addNodes(queue, newNodes);
				System.out.println("Total Nodes in Queue");
				System.out.println(queue.size());
				depth=currentNode.getDepth()+1;
		}
		return new String("Fail");
	}
	public static int[] getComputedCost(String currentState) {
		String[] imfAll = currentState.split(";")[3].split("-");
		int[] cost = new int[] { 0, 0 };
		int totalDeaths = 0;
		int totalSurvived = 0;
		for (int j = 0; j < imfAll.length; j++) {
			if (imfAll[j].split(",")[2].equals("100")) {
				totalDeaths++;
			}
			//if (!imfAll[j].split(",")[3].equals("F")) {
			totalSurvived = totalSurvived + Integer.parseInt(imfAll[j].split(",")[2]);
			//}
		}
		cost[0] = totalDeaths;
		cost[1] = totalSurvived;
		return cost;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
