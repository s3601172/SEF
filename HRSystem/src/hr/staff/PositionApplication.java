package hr.staff;

public class PositionApplication {
	private String dateSubmitted;
	private String approvalStatus;

	private Staff staff;
	private Position position;

	public PositionApplication(String dateSubmitted, String approvalStatus, Staff staff, Position position) {
		this.dateSubmitted = dateSubmitted;
		this.approvalStatus = approvalStatus;
		this.staff = staff;
		this.position = position;
	}
	
	public void setApprovalStatus(String status) {
		approvalStatus = status;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}
	
	public void setDateSubmitted(String date) {
		dateSubmitted = date;
	}

	public String getDateSubmitted() {
		return dateSubmitted;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
