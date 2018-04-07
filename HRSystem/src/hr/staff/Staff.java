package hr.staff;

public class Staff {

	int sID;
	int sPhone;
	String sFirstName;
	String sLastName;
	String sEmail;
	String sUsername;
	String sPassword;
	boolean[] sAvailability;

	public Staff(int ID, int phone, String sFirstName, String sLastName, String email, String username, String password, boolean[] availability) {
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

	public String getFirstName() {
		return this.sFirstName;
	}
	
	public void setFirstName(String firstName) {
		firstName = sFirstName;
	}
	
	public String getLastName() {
		return this.sLastName;
	}
	
	public void setLastName(String lastName) {
		lastName = sLastName;
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
	
	public boolean[] getAvailability() {
		return this.sAvailability;
	}
	
	public void setAvailability(boolean[] availability) {
		availability = sAvailability;
	}
	
}
