import java.util.ArrayList;
import java.util.Comparator;

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
		return null;
	}

}

class BFS implements Qing_Func {

	@Override
	public ArrayList<ST_Node> addNodes(ArrayList<ST_Node> queue, ArrayList<ST_Node> newNodes) {
		// TODO Auto-generated method stub
		queue.addAll(newNodes);
		return queue;
	}

}

class DFS implements Qing_Func {

	@Override
	public ArrayList<ST_Node> addNodes(ArrayList<ST_Node> queue, ArrayList<ST_Node> newNodes) {
		// TODO Auto-generated method stub
		queue.addAll(0, newNodes);
		return queue;
	}

}

class UCS implements Qing_Func {

	@Override
	public ArrayList<ST_Node> addNodes(ArrayList<ST_Node> queue, ArrayList<ST_Node> newNodes) {
		// TODO Auto-generated method stub
		queue.addAll(newNodes);
		UCS_Comparator comp = new UCS_Comparator();
		queue.sort(comp);
		return queue;
	}

}

class UCS_Comparator implements Comparator<ST_Node> {

	@Override
	public int compare(ST_Node node0, ST_Node node1) {
		// TODO Auto-generated method stub
		int deaths0 = node0.getCost()[0];
		int deaths1 = node1.getCost()[0];
		int health0 = node0.getCost()[1];
		int health1 = node1.getCost()[1];
		if (deaths0 == deaths1) {
			return health0 - health1;
		} else {
			return deaths0 - deaths1;
		}
	}

}
