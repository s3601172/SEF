package hr.school;
import hr.staff.*;
import java.util.*;

public class Course {
	private String courseCode;
	private int yearRun;
	private int semesterRun;
	private CourseCoordinator coordinator;
	private ArrayList<Position> positions = new ArrayList<Position>();

	
	public Course (String courseCode, int yearRun, int semesterRun, CourseCoordinator coordinator) {
		this.courseCode = courseCode;
		this.yearRun = yearRun;
		this.semesterRun = semesterRun;
		this.coordinator = coordinator;
	}
	
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	public String getCourseCode() {
		return courseCode;
	}
	
	public void setYearRun(int yearRun) {
		this.yearRun = yearRun;
	}
	
	public int getYearRun() {
		return yearRun;
	}
	
	public void setSemesterRun(int semesterRun) {
		this.semesterRun = semesterRun;
	}
	
	public int getSemesterRun() {
		return semesterRun;
	}
	
	public void setPositions(ArrayList<Position> positions) {
		this.positions = positions;
	}
	
	public ArrayList<Position> getPositions() {
		return positions;
	}
	
	public void setCoordinator(CourseCoordinator coordinator) {
		this.coordinator = coordinator;
	}
	
	public CourseCoordinator getCoordinator() {
		return coordinator;
	}
}
