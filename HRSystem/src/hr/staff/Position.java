package hr.staff;

import java.util.*;
import hr.school.*;
import hr.staff.*;

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
		return this.pWage;
	}

	public void setWage(double wage) {
		wage = this.pWage;
	}

	public boolean isFilled() {
		return this.pFilledStatus;
	}

	public void setFilled(boolean filledStatus) {
		filledStatus = this.pFilledStatus;
	}

	public String getType() {
		return this.pType;
	}

	public void setType(String type) {
		type = pType;
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
