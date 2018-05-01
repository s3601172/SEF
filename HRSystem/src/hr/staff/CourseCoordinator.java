package hr.staff;

import java.util.*;
import hr.school.*;

public class CourseCoordinator extends Staff {
	private ArrayList<Course> courses = new ArrayList<Course>();
	private ArrayList<PositionRequest> positionRequests = new ArrayList<PositionRequest>();
	
	public CourseCoordinator(String ID, String username, String password, String phone, String firstName, String lastName, String email,  boolean[][] availability) {
		super(ID, username, password, phone, firstName, lastName, email, availability);
	}
	
	public ArrayList<Course> getCourses() {
		return courses;
	}
	
	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}

	public ArrayList<PositionRequest> getPositionRequests() {
		return positionRequests;
	}
	
	public void setPositionRequests(ArrayList<PositionRequest> positionRequests) {
		this.positionRequests = positionRequests;
	}
}
