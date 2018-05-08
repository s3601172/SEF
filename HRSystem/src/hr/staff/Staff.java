package hr.staff;

import java.util.*;

public class Staff {

	String sID;
	String sPhone;
	String sFirstName;
	String sLastName;
	String sEmail;
	String sUsername;
	String sPassword;
	boolean[][] sAvailability;
	ArrayList<PositionApplication> applications = new ArrayList<PositionApplication>();

	public Staff(String sID, String username, String password, String phone, String firstName, String lastName, String email,  boolean[][] availability) {
		this.sID = sID;
		sPhone = phone;
		sEmail = email;
		sUsername = username;
		sPassword = password;
		sFirstName = firstName;
		sLastName = lastName;
		sAvailability = availability;
	}

	public String getID() {
		return this.sID;
	}

	public void setID(String ID) {
		sID = ID;
	}

	public String getPhone() {
		return sPhone;
	}

	public void setPhone(String phone) {
		sPhone = phone;
	}

	public String getFirstName() {
		return sFirstName;
	}
	
	public void setFirstName(String firstName) {
		sFirstName = firstName;
	}
	
	public String getLastName() {
		return sLastName;
	}
	
	public void setLastName(String lastName) {
		sLastName = lastName;
	}
	
	public String getEmail() {
		return sEmail;
	}

	public void setEmail(String email) {
		sEmail = email;
	}

	public String getUsername() {
		return sUsername;
	}

	public void setUsername(String username) {
		sUsername = username;
	}

	public String getPassword() {
		return sPassword;
	}

	public void setPassword(String password) {
		sPassword = password;
	}
	
	public boolean[][] getAvailability() {
		return sAvailability;
	}
	
	public void setAvailability(boolean[][] availability) {
		sAvailability = availability;
	}

	public ArrayList<PositionApplication> getApplications() {
		return applications;
	}
	
	public void setApplications(ArrayList<PositionApplication> applications) {
		this.applications = applications;
	}	
}
