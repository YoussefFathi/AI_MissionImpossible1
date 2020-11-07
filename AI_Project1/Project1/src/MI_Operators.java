import java.util.ArrayList;

public class MI_Operators extends Operators {
	String[] operators;

	public MI_Operators() {
		operators = new String[] { "left", "right", "up", "down", "carry", "drop" };
	}

	public ArrayList<String> apply(ST_Node stateNode) {
		ArrayList<String> possible_states = new ArrayList<String>();
		String[] state = stateNode.getState().split(";");
		String[] bounds = state[0].split(",");
		String[] e_coor = state[1].split(",");
		String[] s_coor = state[2].split(",");
		String[] imf_members = state[3].split("-");
		// System.out.println("Before");
		// System.out.println(stateNode.getState());
		for (int i = 0; i < this.operators.length; i++) {
			int x = Integer.parseInt(e_coor[0]);
			int y = Integer.parseInt(e_coor[1]);
			int max_cap = Integer.parseInt(bounds[2]);
			int curr_cap = Integer.parseInt(state[4]);
			String penalized_members;
			boolean valid_op = true;
			String members =  state[3];
;
			switch (this.operators[i]) {
			case "left":
				if (x > 0) {
					x--;
				} else {
					valid_op = false;
				}
				;
				break;
			case "right":
				if (x < Integer.parseInt(bounds[0])) {
					x++;
				} else {
					valid_op = false;
				}
				;
				break;
			case "up":
				if (y > 0) {
					y--;

				} else {
					valid_op = false;
				}
				;
				break;
			case "down":
				if (y < Integer.parseInt(bounds[1])) {
					y++;
				} else {
					valid_op = false;
				}
				;
				break;
			case "carry":
				String[] members_a = checkMembers(x, y, imf_members, curr_cap, this.operators[i]);
				curr_cap--;
				if (members_a[2].equals("F")) {
					valid_op = false;
				} else {
					members = members_a[0];

				}
				break;
			case "drop":
				if (checkSub(e_coor, s_coor, max_cap, curr_cap)) {
					curr_cap = max_cap;
					System.out.println("DROPPPPPPPPPPPPP IS HEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
				} else {
					valid_op = false;
				}
				break;

			}
			// String[] new_coor = new String[] { x + "", y + "" };
			// curr_cap = Integer.parseInt(members_a[1]);
			penalized_members = penalize(members, this.operators[i], curr_cap);
			if (valid_op) {
				// System.out.println("After " + this.operators[i]);
				// System.out.println(state[0] + ";" + x + "," + y + ";" + state[2] + ";" +
				// penalized_members);
				possible_states.add(state[0] + ";" + x + "," + y + ";" + state[2] + ";" + penalized_members);
			}
		}
		return possible_states;

	}

	private String penalize(String members, String op, int cap) {
		// TODO Auto-generated method stub
		String new_members = "";
		String[] imf_members = members.split(";")[0].split("-");
		String[] check_action = members.split(">>");
		for (int i = 0; i < imf_members.length; i++) {
			String[] member = imf_members[i].split(",");
			int imf_health = Integer.parseInt(member[2]);
			String imf_isSaved = member[3];
			if (imf_isSaved.equals("F") && imf_health < 100) {
				imf_health = Integer.min(imf_health + 2, 100);
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

	private String[] checkMembers(int x, int y, String[] imf_members, int curr_cap, String op) {
		// TODO Auto-generated method stub
		String new_members = "";
		String change = "F";
		for (int i = 0; i < imf_members.length; i++) {
			String[] member = imf_members[i].split(",");
			int imf_x = Integer.parseInt(member[0]);
			int imf_y = Integer.parseInt(member[1]);
			int imf_health = Integer.parseInt(member[2]);
			String imf_isSaved = member[3];
			if (imf_isSaved.equals("F")) {
				if (x == imf_x && y == imf_y) {
					if (curr_cap > 0) {
						imf_isSaved = "S";
						change = "T";
					}
				}
			}
			new_members += imf_x + "," + imf_y + "," + imf_health + "," + imf_isSaved;
			if (i < imf_members.length - 1)
				new_members += "-";
		}
//		if (carried_index != -1) {
//			int new_cap = curr_cap - 1;
//			curr_cap = new_cap;
//			new_members = penalize(new_members, op + ",carry", new_cap);
//		}
		String[] returned = new String[3];
		returned[0] = new_members;
		returned[1] = curr_cap + "";
		returned[2] = change;
		return returned;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
