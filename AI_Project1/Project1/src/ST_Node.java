
public class ST_Node implements Comparable<ST_Node> {
	private String state;
	private ST_Node parentNode;
	private String operator;
	private int depth;
	private int[] cost;
	
	public ST_Node(String state,ST_Node parentNode,String operator,int depth, int[] cost ) {
		this.state=state;
		this.parentNode = parentNode;
		this.operator = operator;
		this.depth = depth;
		this.cost=  cost; // Cost is [num of deaths, total health of rescued]
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public ST_Node getParentNode() {
		return parentNode;
	}
	public void setParentNode(ST_Node parentNode) {
		this.parentNode = parentNode;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int[] getCost() {
		return cost;
	}
	public void setCost(int[] cost) {
		this.cost = cost;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	@Override
	public int compareTo(ST_Node node1) {
		ST_Node node0 = this;
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
