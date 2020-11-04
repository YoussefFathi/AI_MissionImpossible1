import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Mission_Impossible extends Generic_Problem {

	public Mission_Impossible(String initial, Operators ops, String stateSpace, String pathCost) {

		this.initialState = initial;
		this.operators = ops;
		this.stateSpace = stateSpace;
		this.pathCost = pathCost;
	}

	public static String gen_grid() {

		int minDim = 5;
		int maxDim = 16;
		int width = (int) (Math.random() * (maxDim - minDim + 1) + minDim);
		int height = (int) (Math.random() * (maxDim - minDim + 1) + minDim);
		int total_cells = (width * height);

		int cell_e = (int) (Math.random() * (total_cells + 1 - 0 + 1) + 0);

		int cell_s = (int) (Math.random() * (total_cells + 1 - 0 + 1) + 0);
		while (cell_e == cell_s) {
			cell_s = (int) (Math.random() * (total_cells + 1 - 0 + 1) + 0);
		}
		Set<Integer> generated = new LinkedHashSet<Integer>();
		generated.add(cell_e);
		generated.add(cell_s);
		int num_imf = (int) (Math.random() * (11 - 5 + 1) + 5);

		while (generated.size() < num_imf + 2) {
			Integer next = (int) (Math.random() * (total_cells + 1 - 0 + 1) + 0);
			// As we're adding to a set, this will automatically do a containment check
			generated.add(next);
		}

		String grid = width + "," + height + ";";
		Integer[] generated_array = new Integer[generated.size()];
		generated.toArray(generated_array);
		int[] health = new int[num_imf];
		for (int i = 0; i < generated_array.length; i++) {
			int x = generated_array[i] / width;
			int y = generated_array[i] % width;
			if (i < 2) {
				grid = grid + x + "," + y + ";";
			} else {
				if (i != generated_array.length - 1) {
					grid = grid + x + "," + y + ",";

				} else {
					grid = grid + x + "," + y;
				}
				int h = (int) (Math.random() * (100 - 0 + 1) + 0);
				health[i - 2] = h;
			}

		}
		grid = grid + ";";
		for (int i = 0; i < health.length; i++) {
			if (i > 0) {
				grid = grid + ",";
			}
			grid = grid + health[i];
		}
		int c = (int) (Math.random() * (num_imf - 1 + 1) + 1);
		grid = grid + ";" + c + ";";
		//
		System.out.println("WIDTH" + width);
		System.out.println("Height" + height);
		System.out.println(num_imf);
		System.out.println(generated);
		System.out.println(grid);

		return grid;
	}

	public static void solve(String grid, String strategy, boolean visualize) {
		/*
		 * State Fromat:
		 * <ex,ey;sx,sy;i1x,i1y,i1h,i1S|i2x,i2y,i2h,i2S|..inx,iny,inh,inS;carried so
		 * far>
		 */
		String[] splitted = grid.split(";");
		String initialState = splitted[1] + ";" + splitted[2] + ";";
		String[] imfMembers = splitted[3].split(",");
		String[] imfHealth = splitted[4].split(",");
		for (int i = 0; i < imfMembers.length; i = i + 2) {
			String imfString = "";
			if (i != 0) {
				imfString = imfString + "|";
			}
			imfString = imfString + imfMembers[i] + "," + imfMembers[i + 1] + "," + imfHealth[i / 2] + "," + "F";
			initialState = initialState.concat(imfString);
		}
		initialState = initialState.concat(";" + splitted[5]);
		MI_Operators ops = new MI_Operators();
		Mission_Impossible problem = new Mission_Impossible(initialState, ops, "", "");
		Qing_Func qing_func = MI_Qing_Func.get_Qing_Func(strategy);
		String solution = "";
		if (strategy.equals("ID")) {
			boolean solved = false;
			int depth = 1;
			while (!solved) {
				solution = Generic_Search.general_search(problem, qing_func, depth);
				if (!solution.equals("Fail")) {
					solved = true;
				}
				depth++;
			}
		}
		else {
			solution = Generic_Search.general_search(problem, qing_func, -1);
		}

		System.out.println(initialState);
	}

	@Override
	public boolean goalTest(String currentState) {
		// TODO Auto-generated method stub
		String[] imfAll = currentState.split(";")[2].split("|");
		int numImf = imfAll.length;
		for( int i =0;i<numImf;i++) {
			String[] splitted = imfAll[i].split(","); 
			if(Integer.parseInt(splitted[2])<100 && splitted[3]=="F") {
				return false;
			}
		}
		return true;
	}

	@Override
	public ArrayList<ST_Node> getNewNodes(ArrayList<String> newStates, ST_Node parent) {
		// TODO Auto-generated method stub
		String[] states  = new String[newStates.size()];
		states = newStates.toArray(states);
		ArrayList<ST_Node> newNodes = new ArrayList<ST_Node>();
		
		for (int i =0;i<states.length;i++) {
			String currentState = states[i];
			String[] imfAll = currentState.split(";")[2].split("|");
			int[] cost = new int[] {0,0};
			int totalDeaths = 0;
			int totalSurvived = 0;
			for ( int j=0;j<imfAll.length;j++) {
				if(imfAll[j].split(",")[2]=="100") {
					totalDeaths++;
				}
				if(imfAll[j].split(",")[3]=="T") {
					totalSurvived = totalSurvived + Integer.parseInt(imfAll[j].split(",")[2]);
				}
			}
			cost[0] = totalDeaths;
			cost[1] = totalSurvived;
			String op = currentState.split(";")[4];
			ST_Node newNode = new ST_Node(currentState,parent,op,parent.getDepth()+1,cost);
			newNodes.add(newNode);
		}
		return newNodes;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String grid = Mission_Impossible.gen_grid();
		Mission_Impossible.solve(grid, "DF", false);
	}

}
