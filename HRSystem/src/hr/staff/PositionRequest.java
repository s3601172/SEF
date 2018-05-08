package hr.staff;

public class PositionRequest {
	private String approvalStatus;
    String id;
	String courseCode;
	double wage;
	String type;
    CourseCoordinator coordinator;
	
	public PositionRequest(String id, String courseCode, String type, double wage, CourseCoordinator coordinator, String approvalStatus) {
        this.id = id;
        this.courseCode = courseCode;
        this.type = type;
        this.wage = wage;
        this.coordinator = coordinator;
        this.approvalStatus = approvalStatus;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseCode() {
        return courseCode;
    }
	
    public void setWage(double wage) {
        this.wage = wage;
    }

    public double getWage() {
        return wage;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setCoordinator(CourseCoordinator coordinator) {
        this.coordinator = coordinator;
    }

    public CourseCoordinator getCoordinator() {
        return coordinator;
    }
}
