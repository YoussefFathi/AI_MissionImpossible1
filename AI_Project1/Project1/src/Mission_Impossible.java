import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Mission_Impossible extends  Generic_Problem{
	
	public Mission_Impossible() {
		// TODO Auto-generated constructor stub
	}
	public static String gen_grid() {
		
		int minDim = 5;
		int maxDim = 16;
	    int width = (int)(Math.random() * (maxDim - minDim + 1) + minDim);
	    int height = (int)(Math.random() * (maxDim - minDim + 1) + minDim);
	    int total_cells = (width*height ) ;
	    
	    int cell_e = (int)(Math.random() * (total_cells+1 - 0 + 1) + 0);
	    
	    int cell_s = (int)(Math.random() * (total_cells+1 - 0 + 1) + 0);
	    while(cell_e==cell_s) {
		    cell_s = (int)(Math.random() * (total_cells+1 - 0 + 1) + 0);
	    }
	    Set<Integer> generated = new LinkedHashSet<Integer>();
	    generated.add(cell_e);
	    generated.add(cell_s);
	    int num_imf = (int)(Math.random() * (11 - 5 + 1) + 5);
	    
	    while (generated.size() < num_imf+2)
	    {
	        Integer next = (int)(Math.random() * (total_cells+1 - 0 + 1) + 0);
	        // As we're adding to a set, this will automatically do a containment check
	        generated.add(next);
	    }
	    
	    String grid = width+","+height+";";
	    Integer[] generated_array = new Integer[generated.size()];
	    generated.toArray(generated_array);
	    int[] health = new int[num_imf];
	    for (int i =0;i<generated_array.length;i++) {
	    	int x = generated_array[i] / width;
	    	int y = generated_array[i] % width;
	    	if (i<2) {
	    		grid = grid+x+","+y+";";
	    	}else {
	    		if(i!=generated_array.length-1) {
		    		grid = grid+x+","+y+",";

	    		}else {
	    			grid = grid+x+","+y;
	    		}
	    		int h = (int)(Math.random() * (100 - 0 + 1) + 0);
	    		health[i-2] = h;
	    	}
	    	
	    }
	    grid  = grid+ ";";
	    for (int i=0;i<health.length;i++) {
	    	if(i>0) {
	    		grid = grid + ",";
	    	}
	    	grid = grid+ health[i];
	    }
	    int c = (int)(Math.random() * (num_imf - 1 + 1) + 1);
	    grid = grid + ";" + c + ";";
//	    
//	    System.out.println("WIDTH"+width);
//	    System.out.println("Height"+height);
//	    System.out.println(num_imf);
//	    System.out.println(generated);
//	    System.out.println(grid);


	    

	    

		return grid;
	}
	public static void solve(String grid, String strategy, boolean visualize) {
		
	}


	@Override
	public boolean goalTest(String currentState) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<ST_Node> getNewNodes(ArrayList<String> newStates, ST_Node parent) {
		// TODO Auto-generated method stub
		return null;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mission_Impossible.gen_grid();
	}

}
