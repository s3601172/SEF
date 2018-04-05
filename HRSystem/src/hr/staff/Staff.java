package hr.staff;

public class Staff {

	int sID;
	int sPhone;
	String sEmail;
	String sUsername;
	String sPassword;
	boolean[] sAvailability;

	public Staff(int ID, int phone, String email, String username, String password, boolean[] availability) {
		sID = ID;
		sPhone = phone;
		sEmail = email;
		sUsername = username;
		sPassword = password;
		//array
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

	public String getUsername() {
		return this.sUsername;
	}

	public void setUsername(String username) {
		username = this.sUsername;
	}

	public String getPassword() {
		return this.sPassword;
	}

	public void setPassword(String password) {
		password = this.sPassword;
	}
	//not sure how to do getters and setters for the availability
}
