package hr.staff;

public class Staff {

	int sID;
	int sPhone;
	String sEmail;
	// split into username and password?
	String sLoginDetails;
	boolean[] sAvailability;

	public Staff(int ID, int phone, String email, String loginDetails, boolean[] availability) {

	}

	public int getID() {
		return this.sID;
	}

	public void setID(int ID) {
		ID = this.sID;
	}

	public int getPhone() {
		return this.sPhone;
	}

	public void setPhone(int phone) {
		phone = this.sPhone;
	}

	public String getEmail() {
		return this.sEmail;
	}

	public void setEmail(String email) {
		email = this.sEmail;
	}

	public String getLoginDetails() {
		return this.sLoginDetails;
	}

	public void setLoginDetails(String loginDetails) {
		loginDetails = this.sLoginDetails;
	}

	//not sure how to do getters and setters for the availability
}
