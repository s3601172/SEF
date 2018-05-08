package hr.staff;

import java.util.*;
import hr.school.*;

public class Position {

	double pWage;
	boolean pFilledStatus;
	String pType;
	String pID;

	Course course;
	ArrayList<PositionClass> classes = new ArrayList<PositionClass>();
	ArrayList<PositionApplication> applications = new ArrayList<PositionApplication>();

	public Position(String pID, String type, Course course, boolean filledStatus, double wage) {
		this.pID = pID;
		pWage = wage;
		pFilledStatus = filledStatus;
		pType = type;
		this.course = course;
	}

	public double getWage() {
		return pWage;
	}

	public void setWage(double wage) {
		pWage = wage;
	}

	public boolean isFilled() {
		return pFilledStatus;
	}

	public void setFilled(boolean filledStatus) {
		pFilledStatus = filledStatus;
	}

	public String getType() {
		return pType;
	}

	public void setType(String type) {
		pType = type;
	}
	
	public void setID(String ID) {
		pID = ID;
	}
	
	public String getID() {
		return pID;
	}

	public ArrayList<PositionClass> getClasses() {
		return classes;
	}

	public void setClasses(ArrayList<PositionClass> classes) {
		this.classes = classes;
	}

	public ArrayList<PositionApplication> getApplications() {
		return applications;
	}

	public void setApplications(ArrayList<PositionApplication> applications) {
		this.applications = applications;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Course getCourse() {
		return course;
	}
}
