import java.util.ArrayList;

public class MI_Operators extends Operators {
	String[] operators;

	public MI_Operators() {
		operators = new String[] { "left", "right", "up", "down" };
	}

	public ArrayList<String> apply(ST_Node stateNode) {
		ArrayList<String> possible_states = new ArrayList<String>();
		String[] state = stateNode.getState().split(";");
		String[] bounds = state[0].split(",");
		String[] e_coor = state[1].split(",");
		String[] s_coor = state[2].split(",");
		String[] imf_members = state[3].split("-");
		System.out.println("Before");
		System.out.println(stateNode.getState());
		for (int i = 0; i < this.operators.length; i++) {
			int x = Integer.parseInt(e_coor[0]);
			int y = Integer.parseInt(e_coor[1]);
			int max_cap = Integer.parseInt(bounds[2]);
			int curr_cap = Integer.parseInt(state[4]);
			String penalized_members;
			switch (this.operators[i]) {
			case "left":
				if (x > 0) {
					x--;
				}
				;
				break;
			case "right":
				if (x < Integer.parseInt(bounds[0])) {
					x++;
				}
				;
				break;
			case "up":
				if (y > 0) {
					y--;
				}
				;
				break;
			case "down":
				if (y < Integer.parseInt(bounds[1])) {
					y++;
				}
				;
				break;
			}
			String members = checkMembers(x, y, imf_members, curr_cap, this.operators[i]);
			if (checkSub(e_coor, s_coor, max_cap, curr_cap)) {
				members = members.substring(0, members.length() - 1) + max_cap;
				members = penalize(members, -1, this.operators[i] + ",drop", max_cap);
			}
			penalized_members = penalize(members, -1, this.operators[i], curr_cap);
			System.out.println("After " + this.operators[i]);
			System.out.println(state[0] + ";" + x + "," + y + ";" + state[2] + ";" + penalized_members);
			possible_states.add(state[0] + ";" + x + "," + y + ";" + state[2] + ";" + penalized_members);
		}
		return possible_states;

	}

	private String penalize(String members, int exclude, String op, int cap) {
		// TODO Auto-generated method stub
		String new_members = "";
		String[] imf_members = members.split(";")[0].split("-");
		String[] check_action = members.split(">>");

		for (int i = 0; i < imf_members.length; i++) {
			String[] member = imf_members[i].split(",");
			int imf_health = Integer.parseInt(member[2]);
			String imf_isSaved = member[3];
			if (imf_isSaved.equals("F") && i != exclude) {
				imf_health -= 2;
			}
			new_members += member[0] + "," + member[1] + "," + imf_health + "," + imf_isSaved;
			if (i < imf_members.length - 1)
				new_members += "-";
		}
		if (check_action.length == 1) {
			new_members = new_members + ";" + cap + ">>" + op;
		} else {
			new_members = new_members + ";" + cap + ">>" + check_action[1];
		}
		return new_members;
	}

	private boolean checkSub(String[] e_coor, String[] s_coor, int max_cap, int curr_cap) {
		// TODO Auto-generated method stub
		if (e_coor[0].equals(s_coor[0]) && e_coor[1].equals(s_coor[1]) && curr_cap < max_cap) {
			return true;
		}
		return false;
	}

	private String checkMembers(int x, int y, String[] imf_members, int curr_cap, String op) {
		// TODO Auto-generated method stub
		String new_members = "";
		int carried_index = -1;
		for (int i = 0; i < imf_members.length; i++) {
			String[] member = imf_members[i].split(",");
			int imf_x = Integer.parseInt(member[0]);
			int imf_y = Integer.parseInt(member[1]);
			int imf_health = Integer.parseInt(member[2]);
			String imf_isSaved = member[3];
			if (imf_isSaved.equals("F")) {
				if (x == imf_x && y == imf_y && imf_health > 0) {
					if (curr_cap > 0) {
						imf_isSaved = "S";
						carried_index = i;
					}
				}
			}
			new_members += imf_x + "," + imf_y + "," + imf_health + "," + imf_isSaved;
			if (i < imf_members.length - 1)
				new_members += "-";
		}
		if (carried_index != -1) {
			int new_cap = curr_cap - 1;
			new_members = penalize(new_members, carried_index, op + ",carry", new_cap);
		}
		return new_members;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
