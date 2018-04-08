package hr.staff;
import java.util.*;


public class Admin extends Staff {
	
	private List<Staff> staff;
	private List<Staff> approvedStaff; // subset of all staff members
	private List<Position> positions;
	
	public Admin(String ID, String username, String password, String phone, String firstName, String lastName, String email,  boolean[][] availability) {
		super(ID, username, password, phone, firstName, lastName, email, availability);
		/*
		staff = new ArrayList<Staff>();
		approvedStaff = new ArrayList<Staff>();
		positions = new ArrayList<Position>();
		*/
	}
	
	/*
	 * Approve staff's request based on their name and loginDetails.
	 * 
	 * @param name
	 * @param loginDetails
	 * @return true if approval was successful, otherwise false
	 */
	public boolean approveStaffRequest(String firstName, String lastName, String username, String password) {
		// While approving staff, make sure the name and loginDetails match.
		for (Staff s : staff) {
			if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName) && s.getUsername().equals(username) && s.getPassword().equals(password)) {
				approvedStaff.add(s);
				return true;
			}
		}
		return false;
	}
	
	public void addPosition(Position p) {
		positions.add(p);
	}
	
	public void addStaff(Staff s) {
		staff.add(s);
	}
	
	/*
	 * Remove position entry based on a unique position id.
	 * If removal is successful, the removed Position object
	 * is returned.
	 * 
	 * @param positionId
	 * @return
	 */
	public Position removePosition(int positionId) {
		for (Position p : positions) {
			if (p.getID() == positionId) {
				Position tmp = p;
				positions.remove(p);
				return tmp;
			}
		}
		return null;
	}

}
