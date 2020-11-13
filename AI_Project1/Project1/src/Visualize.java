import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

@SuppressWarnings("serial")
public class Visualize extends JPanel {

    private final Cell [][] maze;
    private final int x_dim;
    private final int y_dim;
    private String intitalState;
    private String solution;
    private static final int SIZE = 35;

    public Visualize(int dim,String grid,String initialState,String solution) {
    	x_dim = Integer.parseInt(initialState.split(";")[0].split(",")[0]);
    	y_dim = Integer.parseInt(initialState.split(";")[0].split(",")[1]);
        setPreferredSize(new Dimension( x_dim*SIZE,y_dim*SIZE));
        
        maze = new Cell[x_dim][y_dim];
    }

    public void generator()
    {
        for(int i = 0; i < maze.length; i++)
        {
            for (int j = 0;j < maze[0].length; j++)
            {
                maze[i][j] = new Cell(i,j,"","","");
                //set some arbitrary initial date
                if(i%2 ==0 && j%2 ==0) {
                    maze[i][j].setVisited(true);
                }
            }
        }
    }

    public void DFS()
    {
        new DFSTask().execute();
    }

    @Override
    public void paintComponent(Graphics g) //override paintComponent not paint
    {
        super.paintComponent(g);
        for (int row = 0; row < maze.length; row++)
        {
            for (int col = 0; col < maze[0].length; col++)
            {
                g.drawRect(SIZE*row, SIZE * col , SIZE, SIZE);
                if(maze[row][col].visited)
                {
//                    g.fillRect(SIZE*row, SIZE * col , SIZE, SIZE);
                    g.drawString("ALO",SIZE*row , SIZE * col);
                }
            }
        }
    }

    public static void main(String[] args)
    {
        Visualize p = new Visualize(10,"5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3","","");
        p.generator();
        JFrame f = new JFrame();
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(p);
        f.pack();
        f.setVisible(true);
        p.DFS();
    }

    //use swing worker perform long task
    class DFSTask extends SwingWorker<Void,Void> {

        private static final long DELAY = 1000;
        private final Random rand = new Random();

        @Override
        public Void doInBackground() {
            dfs();
            return null;
        }

        @Override
        public void done() { }

        void dfs() { //simulates long process that repeatedly updates gui

            while (true){ //endless loop, just for demonstration
                //update info
                int row = rand.nextInt(x_dim);
                int col = rand.nextInt(y_dim);
                maze[row][col].setVisited(! maze[row][col].isVisited());
                repaint(); //update jpanel
                try {
                    Thread.sleep(DELAY); //simulate long process
                } catch (InterruptedException ex) { ex.printStackTrace();}
            }
        }
    }
}

class Cell{

    private final int row, column;
    boolean visited;
    private String objectInCell;
    private String health;
    private String saved;

    public Cell(int i, int j,String objectInCell,String health,String saved)
    {
        row = i;
        column = j;
        visited = false;
        this.objectInCell = objectInCell;
        this.health = health;
        this.saved = saved;
        
    }

    int getRow() {  return row; }

    int getColumn() {return column; }

    boolean isVisited() { return visited; }
    
    void setPlayer(String player) {this.objectInCell=player;}
    
    String getPlayer() {return this.objectInCell;}

    void setVisited(boolean visited) {  this.visited = visited;}

    @Override
    public String toString()
    {
        return row + " " + column;
    }

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getSaved() {
		return saved;
	}

	public void setSaved(String saved) {
		this.saved = saved;
	}
}