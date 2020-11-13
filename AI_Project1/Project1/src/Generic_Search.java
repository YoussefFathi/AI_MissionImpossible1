import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue; 
public class Generic_Search {
	

	public static String general_search(Generic_Problem problem, Qing_Func func, int maxDepth) {
		Queue<ST_Node> queue = new ArrayDeque<ST_Node>();
		HashSet<String> dict  = new HashSet<String>();
		ST_Node initialNode = new ST_Node(problem.initialState, null, "", 0, getComputedCost(problem.initialState));
		queue.add(initialNode);
		int depth = 0;
		int total_nodes=0;
		while (!queue.isEmpty()) {
				if (depth > maxDepth && maxDepth!=-1) {
					break;
				}
				ST_Node currentNode = queue.remove();
				total_nodes++;
				if(dict.contains(currentNode.getState())) {
					continue;
				}
				dict.add(currentNode.getState());
				System.out.println("Current State");
				System.out.println(currentNode.getState());
				System.out.println(Arrays.toString(currentNode.getCost()));

				if (problem.goalTest(currentNode.getState())) {
					System.out.println("TOTAL NODES: "+total_nodes);
					return get_solution_string(currentNode,total_nodes);
					
				}
				ArrayList<String> newStates = problem.operators.apply(currentNode);
				System.out.println("New States" + " Depth: "+ currentNode.getDepth());
				System.out.println(newStates);
				
//				ArrayList<ST_Node> newNodes = problem.getNewNodes(newStates, currentNode,dict);
				queue = func.addNodes(queue, newStates, currentNode,dict);
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
		cost[1] = 0;
		return cost;
	}
	public static String get_solution_string(ST_Node finalNode,int total_nodes) {
		ST_Node parentNode = finalNode.getParentNode();
		ArrayList<String> ops = new ArrayList<>();
		ops.add(finalNode.getOperator());
		while (!Objects.isNull(parentNode)){
			ops.add(parentNode.getOperator());
			parentNode = parentNode.getParentNode();
		}
		Collections.reverse(ops);
		String finalString = "";
		while(!ops.isEmpty()) {
			finalString += ops.remove(0)+",";
		}
		finalString = finalString.substring(0,finalString.length()-1); //Operations
		finalString += ";"+finalNode.getCost()[0]; // Total Deaths
		finalString += ";"+get_healths(finalNode.getState()); // All healths
		finalString+= ";" + total_nodes;
		return finalString;
	}
	public static String get_healths(String stateString) {
		String[] state = stateString.split(";");
		String[] imf_members = state[3].split("-");
		String healths = "";
		for(int  i =0;i<imf_members.length;i++) {
			if(i!=0) {
			healths += "," + imf_members[i].split(",")[2];
			}else {
				healths += imf_members[i].split(",")[2];
			}
		}
		return healths;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Queue<Integer> ar = new ArrayDeque<Integer>();
		ar.add(1);
		ar.add(0);
//		ar = new PriorityQueue<Integer>(ar);
		System.out.println(ar.toString());
	}

}
