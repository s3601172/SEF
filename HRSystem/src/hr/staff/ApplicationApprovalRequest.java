package hr.staff;

public class ApplicationApprovalRequest {
    private String positionID;
    private String applicationID;
    private String approvalStatus;

    CourseCoordinator coordinator;

    public ApplicationApprovalRequest(String positionID, String applicationID, String approvalStatus, CourseCoordinator coordinator) {
        this.positionID = positionID;
        this.applicationID = applicationID;
        this.approvalStatus = approvalStatus;
        this.coordinator = coordinator;
    }

    public void setPositionID(String positionID) {
        this.positionID = positionID;
    }

    public String getPositionID() {
        return positionID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public String getApplicationID() {
        return applicationID;
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
