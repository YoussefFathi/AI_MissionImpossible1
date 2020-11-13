import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class MI_Qing_Func {

	public static Qing_Func get_Qing_Func(String strategy) {
		if (strategy.equals("BF")) {
			return new BFS();
		}
		if (strategy.equals("DF")) {
			return new DFS();
		}
		if (strategy.equals("UC")) {
			return new UCS();
		}
		if (strategy.equals("ID")) {
			return new DFS();
		}
		if (strategy.equals("AS1")) {
			return new AStar("h1");
		}
		if (strategy.equals("AS2")) {
			return new AStar("h2");
		}
		if (strategy.equals("GR1")) {
			return new Greedy("h1");
		}
		if (strategy.equals("GR2")) {
			return new Greedy("h2");
		}
		return null;
	}

	public static ArrayList<ST_Node> getNewNodes(ArrayList<String> newStates, ST_Node parent, HashSet<String> dict,
			String h) {
		// TODO Auto-generated method stub
		String[] states = new String[newStates.size()];
		states = newStates.toArray(states);
		ArrayList<ST_Node> newNodes = new ArrayList<ST_Node>();

		for (int i = 0; i < states.length; i++) {
			String currentState = states[i];
			String[] imfAll = currentState.split(";")[3].split("-");
			//Habdet Zengo
			String[] imfAllParent = parent.getState().split(";")[3].split("-");
			int parentDamage = parent.getCost()[1];
			int newDamage = 0;
			// 
			int[] cost = new int[] { 0, 0 };
			int totalDeaths = 0;
			int totalSurvived = 0;

			for (int j = 0; j < imfAll.length; j++) {
				if (imfAll[j].split(",")[2].equals("100")) {
					totalDeaths++;
				}

				//Habdet Zengo
				newDamage += Math.abs(Integer.parseInt(imfAll[j].split(",")[2]) - Integer.parseInt(imfAllParent[j].split(",")[2]));
				//
//				if(imfAll[j].split(",")[3].equals("S"))
				totalSurvived = totalSurvived + Integer.parseInt(imfAll[j].split(",")[2]);
			}
			cost[0] = totalDeaths;
			//Habdet Zengo
//			cost[1] = totalSurvived;
			cost[1] = newDamage+parentDamage;
			//
			String op = currentState.split(">>")[1];
			currentState = currentState.split(">>")[0];

			if (!dict.contains(currentState)) {
				ST_Node newNode = new ST_Node(currentState, parent, op, parent.getDepth() + 1, cost);
				if (h.equals("h1")) {
					int hcost = MI_Qing_Func.get_h1_cost(currentState);
					newNode.setHcost(hcost);
				} else if (h.equals("h2")) {
					int hcost;
					try {
						hcost = MI_Qing_Func.get_h2_cost(currentState);
						if(hcost<0) {
							throw new Exception("NEGATIVE H COST");
						}
						newNode.setHcost(hcost);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				if(imfAll[0].split(",")[2].equals("62")&& imfAll[1].split(",")[2].equals("62")) {
					System.out.println("ALO2");
				}
				if(newNode.getCost()[0]==1 && newNode.getCost()[1]==114) {
					System.out.println("ALO");
				}
				newNodes.add(newNode);
			}

		}
		return newNodes;
	}

	public static int get_h1_cost(String state) {
		return 0;
	}

	public static int get_h2_cost(String stateString)  {
		String[] state = stateString.split(";");
		String[] bounds = state[0].split(",");
		String[] e_coor = state[1].split(",");
		int e_x_coor = Integer.parseInt(e_coor[0]);
		int e_y_coor = Integer.parseInt(e_coor[1]);
		String[] s_coor = state[2].split(",");
		int s_x_coor = Integer.parseInt(s_coor[0]);
		int s_y_coor = Integer.parseInt(s_coor[1]);
		String[] imf_members = state[3].split("-");
		int capacity = Integer.parseInt(state[4]);
		int maxCapacity = Integer.parseInt(bounds[2]);
		int num_imf = imf_members.length;
		
		int[] distances = new int[num_imf];
		int[] healthsOnArrive = new int[num_imf];
		int[] currentHealths = new int[num_imf];
		String[] survivedAll = new String[num_imf];
		int maxHealthOnArrive = -1;
		int distanceToMaxHealth = -1;
		int closestImfDistance = Integer.MAX_VALUE;
		int notSurvivedNum = 0;
		for (int i=0;i<num_imf;i++) {
			String[] currentImf = imf_members[i].split(",");
			int x_coor = Integer.parseInt(currentImf[0]);
			int y_coor = Integer.parseInt(currentImf[1]);
			int health = Integer.parseInt(currentImf[2]);
			String survived = currentImf[3];
			if(survived.equals("F")) {
				notSurvivedNum++;
			}
			int distance = Math.abs(e_x_coor-x_coor)+Math.abs(e_y_coor-y_coor) +1;
			int healthOnArrive = ((distance-1)*2) + health;
//			if(distance==0 && survived.equals("F") && healthOnArrive>=100) {
//				distance=0;
//			}
			
			if(healthOnArrive>maxHealthOnArrive && survived.equals("F") && healthOnArrive<100) {
				maxHealthOnArrive = healthOnArrive;
				distanceToMaxHealth = distance;
			}
			if(distance<closestImfDistance && survived.equals("F")) {
				closestImfDistance = distance;
			}
		}
		int distanceToSub = Math.abs(e_x_coor-s_x_coor)+Math.abs(e_y_coor-s_y_coor) + 1;
		int returnVal=-1;
		
		if(capacity==0 || distanceToSub==1 ) { // Cost is Equal to Damage Done till Submarine ( Go to Submarine)
//			return maxHealthOnArrive - (distanceToSub*2);
			returnVal= distanceToSub*2   ;
//		}else if (capacity==0 && distanceToSub==0) { 
//			returnVal=2;
		}
		else if(closestImfDistance==0 && distanceToMaxHealth!=0&& distanceToMaxHealth!=-1 ) { //Standing on an unsaveable IMF Member while there are others saveable
			returnVal = distanceToMaxHealth *2 * notSurvivedNum;
		}
		else if(distanceToMaxHealth!=-1) { // Otherwise go to a saveable IMF member that will have the highest damage on arrival
//			return maxHealthOnArrive;
			
			returnVal= distanceToMaxHealth *2 * notSurvivedNum ;
			
		}else if(notSurvivedNum==0) { // Capacity is not full and there is no one left to save -> Go to submarine to drop and end
			returnVal= distanceToSub*2;
		}
		else if(maxHealthOnArrive==-1 &&closestImfDistance!=Integer.MAX_VALUE){ // IF All the members will be dead from current place -> Go to the nearest one
//			return (closestImfDistance*2) + 100;
			returnVal= closestImfDistance*2 * notSurvivedNum;
		}
		if(returnVal>=0) {
			return returnVal ;// ;
		}else {
			return -1;
		}
	}

}

class BFS implements Qing_Func {

	@Override
	public Queue<ST_Node> addNodes(Queue<ST_Node> queue, ArrayList<String> newStates, ST_Node parent,
			HashSet<String> dict) {
		// TODO Auto-generated method stub
		ArrayList<ST_Node> newNodes = MI_Qing_Func.getNewNodes(newStates, parent, dict, "");
		((ArrayDeque<ST_Node>)queue).addAll(newNodes);
		return queue;
	}

}

class DFS implements Qing_Func {

	@Override
	public Queue<ST_Node> addNodes(Queue<ST_Node> queue, ArrayList<String> newStates, ST_Node parent,
			HashSet<String> dict) {
		// TODO Auto-generated method stub
		ArrayList<ST_Node> newNodes = MI_Qing_Func.getNewNodes(newStates, parent, dict, "");
		newNodes.forEach((node) -> ((ArrayDeque<ST_Node>)queue).addFirst(node));
		return queue;
	}

}

class UCS implements Qing_Func {

	@Override
	public Queue<ST_Node> addNodes(Queue<ST_Node> queue, ArrayList<String> newStates, ST_Node parent,
			HashSet<String> dict) {
		// TODO Auto-generated method stub
		ArrayList<ST_Node> newNodes = MI_Qing_Func.getNewNodes(newStates, parent, dict, "");
		queue.addAll(newNodes);
		UCS_Comparator comp = new UCS_Comparator();
		PriorityQueue<ST_Node> pq = new PriorityQueue<ST_Node>(comp);
		pq.addAll(queue);
		// int index = queue.indexOf(Collections.min(queue));
		//
		// ST_Node n = queue.remove(index);
		// queue.add(0,n);
		

//		queue.sort(comp);
		// System.out.println("After Sorting");
		// for(ST_Node s : queue) {
		// System.out.print(Arrays.toString(s.getCost()));
		// }
		System.out.println("Compare" + Arrays.toString(pq.peek().getCost()));
		return pq;
	}

}

class AStar implements Qing_Func {
	private String s;

	public AStar(String s) {
		this.s = s;
	}

	@Override
	public Queue<ST_Node> addNodes(Queue<ST_Node> queue, ArrayList<String> newStates, ST_Node parent,
			HashSet<String> dict) {
		// TODO Auto-generated method stub
		ArrayList<ST_Node> newNodes = MI_Qing_Func.getNewNodes(newStates, parent, dict, this.s);
		queue.addAll(newNodes);
		AStar_Comparator comp = new AStar_Comparator();
		PriorityQueue<ST_Node> pq = new PriorityQueue<ST_Node>(comp);
		pq.addAll(queue);
		// int index = queue.indexOf(Collections.min(queue));
		//
		// ST_Node n = queue.remove(index);
		// queue.add(0,n);
		

//		queue.sort(comp);
		// System.out.println("After Sorting");
		// for(ST_Node s : queue) {
		// System.out.print(Arrays.toString(s.getCost()));
		// }
		System.out.println("Compare G(n)" + Arrays.toString(pq.peek().getCost()));
		System.out.println("Compare H(n)" + (pq.peek().getHcost()));
		return pq;
	}

}

class Greedy implements Qing_Func {
	private String s;

	public Greedy(String s) {
		this.s = s;
	}

	@Override
	public Queue<ST_Node> addNodes(Queue<ST_Node> queue, ArrayList<String> newStates, ST_Node parent,
			HashSet<String> dict) {
		// TODO Auto-generated method stub
		ArrayList<ST_Node> newNodes = MI_Qing_Func.getNewNodes(newStates, parent, dict, this.s);
		queue.addAll(newNodes);
		Greedy_Comparator comp = new Greedy_Comparator();
		PriorityQueue<ST_Node> pq = new PriorityQueue<ST_Node>(comp);
		pq.addAll(queue);
		// int index = queue.indexOf(Collections.min(queue));
		//
		// ST_Node n = queue.remove(index);
		// queue.add(0,n);
		

//		queue.sort(comp);
		// System.out.println("After Sorting");
		// for(ST_Node s : queue) {
		// System.out.print(Arrays.toString(s.getCost()));
		// }
//		System.out.println("Compare" + Arrays.toString(queue.get(0).getCost()));
//		System.out.println("Compare H(n)" + (queue.get(0).getHcost()));
		System.out.println("Compare H(n)" + (pq.peek().getHcost()));
		return queue;
	}

}

class UCS_Comparator implements Comparator<ST_Node> {

	@Override
	public int compare(ST_Node node0, ST_Node node1) {
		// TODO Auto-generated method stub
		int deaths0 = node0.getCost()[0];
		int deaths1 = node1.getCost()[0];
		int health0 = node0.getCost()[1] ;
		int health1 = node1.getCost()[1];
		if (deaths0 == deaths1) {
			return health0 - health1;
		} else {
			return deaths0 - deaths1;
		}
	}

}

class AStar_Comparator implements Comparator<ST_Node> {

	@Override
	public int compare(ST_Node node0, ST_Node node1) {
		// TODO Auto-generated method stub
		int hCost0 = node0.getHcost();
		int hCost1 = node1.getHcost();
		int deaths0 = node0.getCost()[0] *100;
		int deaths1 = node1.getCost()[0] * 100;
		int health0 = node0.getCost()[1];
		int health1 = node1.getCost()[1];
		int gDiff = deaths0 - deaths1;
//		int gDiff = health0 - health1;
		if (deaths0 == deaths1) {
			gDiff = health0 - health1;
		}
		
		int hDiff = hCost0 - hCost1;

		return gDiff + hDiff;

	}

}

class Greedy_Comparator implements Comparator<ST_Node> {

	@Override
	public int compare(ST_Node node0, ST_Node node1) {
		// TODO Auto-generated method stub
		int hCost0 = node0.getHcost();
		int hCost1 = node1.getHcost();
		return hCost0 - hCost1;
	}

}
