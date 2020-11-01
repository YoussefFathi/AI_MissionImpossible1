import java.util.ArrayList;

public class MI_Qing_Func  {

	public static Qing_Func get_Qing_Func(String strategy) {
		if(strategy.equals("BF")) {
			return new BFS();
		}
		if(strategy.equals("DF")) {
			return new DFS();
		}
		if(strategy.equals("UC")) {
			return new UCS();
		}
//		if(strategy.equals("BF")) {
//			return new BFS();
//		}
		return null;
	}

}
class BFS implements Qing_Func {

	@Override
	public ArrayList<ST_Node> addNodes(ArrayList<ST_Node> queue, ArrayList<ST_Node> newNodes) {
		// TODO Auto-generated method stub
		return null;
	}

}
class DFS implements Qing_Func {

	@Override
	public ArrayList<ST_Node> addNodes(ArrayList<ST_Node> queue, ArrayList<ST_Node> newNodes) {
		// TODO Auto-generated method stub
		return null;
	}

}
class UCS implements Qing_Func {

	@Override
	public ArrayList<ST_Node> addNodes(ArrayList<ST_Node> queue, ArrayList<ST_Node> newNodes) {
		// TODO Auto-generated method stub
		return null;
	}

}
