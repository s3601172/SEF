package hr.staff;

import java.util.ArrayList;
import java.util.List;


public class Admin {
	
	private List<Staff> staff;
	private List<Staff> approvedStaff; // subset of all staff members
	private List<Position> positions;
	
	public Admin() {
		staff = new ArrayList<Staff>();
		approvedStaff = new ArrayList<Staff>();
		positions = new ArrayList<Position>();
	}
	
	/*
	 * Approve staff's request based on their name and loginDetails.
	 * 
	 * @param name
	 * @param loginDetails
	 * @return true if approval was successful, otherwise false
	 */
	public boolean approveStaffRequest(String name, String loginDetails) {
		// While approving staff, make sure the name and loginDetails match.
		for (Staff s : staff) {
			if (s.getName().equals(name) && s.getLoginDetails().equals(loginDetails)) {
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
			if (p.getPositionId() == positionId) {
				Position tmp = p;
				positions.remove(p);
				return tmp;
			}
		}
		return null;
	}

}
