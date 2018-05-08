package hr.staff;

public class PositionApplication {
	private String applicationID;
	private String dateSubmitted;
	private String approvalStatus;
	private String offerStatus;

	private Staff staff;
	private Position position;

	public PositionApplication(String applicationID, String dateSubmitted, String approvalStatus, String offerStatus, Staff staff, Position position) {
		this.applicationID = applicationID;
		this.dateSubmitted = dateSubmitted;
		this.approvalStatus = approvalStatus;
		this.offerStatus = offerStatus;
		this.staff = staff;
		this.position = position;
	}
	
	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}

	public String getApplicationID() {
		return applicationID;
	}
	
	public void setApprovalStatus(String status) {
		approvalStatus = status;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setOfferStatus(String status) {
		offerStatus = status;
	}

	public String getOfferStatus() {
		return offerStatus;
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
