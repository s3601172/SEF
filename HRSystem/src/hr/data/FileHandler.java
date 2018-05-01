package hr.data;

import java.io.*;
import java.util.*;

import hr.staff.*;
import hr.school.*;

public class FileHandler {
	private static final String USER_DATA_FILE = "UserData.txt";
	private static final String COURSE_DATA_FILE = "CourseData.txt";
	private static final String POSITION_DATA_FILE = "PositionData.txt";
	private static final String POSITION_APPLICATION_DATA_FILE = "PositionApplicationData.txt";
	private static final String CLASS_DATA_FILE = "ClassData.txt";
    private static final String POSITION_REQUEST_DATA_FILE = "PositionRequestData.txt";
	public static final int DAYS_IN_WEEK = 7;
    public static final int HOURS_IN_DAY = 24;
	
	public static void writeStaffData(ArrayList<Staff> staffData) {
        boolean[][] availability = new boolean[DAYS_IN_WEEK][HOURS_IN_DAY * 4];
        PrintWriter outputFile = null;
        
        // Open user data file and an temporary output file
        try {
            outputFile = new PrintWriter(new FileWriter(USER_DATA_FILE + "_temp"));
        }
        catch (Exception e) {
            System.exit(0);
        }
        
        // Writes out updated staff data
        for(int i = 0; i < staffData.size(); i++) {
        	availability = staffData.get(i).getAvailability();
        	if(staffData.get(i) instanceof Admin) {
        		outputFile.println("admin");	
        	} else if(staffData.get(i) instanceof CourseCoordinator) {
        		outputFile.println("courseco");	
        	} else {
        		outputFile.println("staff");	
        	}
        	outputFile.println(staffData.get(i).getID());
        	outputFile.println(staffData.get(i).getUsername());
        	outputFile.println(staffData.get(i).getPassword());
        	outputFile.println(staffData.get(i).getEmail());
        	outputFile.println(staffData.get(i).getPhone());
        	outputFile.println(staffData.get(i).getFirstName());
        	outputFile.println(staffData.get(i).getLastName());
        	for(int j = 0; j < DAYS_IN_WEEK; j++) {
        		for(int k = 0; k < HOURS_IN_DAY * 4; k++) {
        			if(availability[j][k]) {
        				outputFile.print(1 + " ");
        			} else {
        				outputFile.print(0 + " ");
        			}
        		}
        		outputFile.println();
        	}
        	outputFile.println();
        }
        
        outputFile.close();
        
        // Remove old data file and rename the temporary file
        File original = new File(USER_DATA_FILE);
        File updated = new File(USER_DATA_FILE + "_temp");
        if(original.exists()) {
        	original.delete();
        }
        updated.renameTo(original);
    }
	
	public static void readStaffData(ArrayList<Staff> staffData) {
        String ID, phone, email, username, password;
        String firstName, lastName;
        String staffType;
        boolean[][] availability = new boolean[DAYS_IN_WEEK][HOURS_IN_DAY * 4];
        Scanner userData = null;

        // Open the user data file
        try {
            userData = new Scanner(new FileInputStream(USER_DATA_FILE));
        }
        catch (Exception e) {
            System.out.println("Could not open " + USER_DATA_FILE);
            System.exit(0);
        }

        // Keep reading in staff data until end of file
        while(userData.hasNextLine()) {
            staffType = userData.nextLine();
            ID = userData.nextLine();
            username = userData.nextLine();
            password = userData.nextLine();
            email = userData.nextLine();
            phone = userData.nextLine();
            firstName = userData.nextLine();
            lastName = userData.nextLine();
            for(int i = 0; i < DAYS_IN_WEEK; i++) {
        		for(int j = 0; j < HOURS_IN_DAY * 4; j++) {
        			if(userData.nextInt() == 0) {
        				availability[i][j] = false;
        			} else {
        				availability[i][j] = true;
        			}
        		}
        	}
            userData.nextLine();
            userData.nextLine();
            
            // Use read in data to create appropriate staff member type
            if(staffType.equals("admin")) {
            	staffData.add(new Admin(ID, username, password, phone, firstName, lastName, email, availability));
            } else if(staffType.equals("courseco")) {
            	staffData.add(new CourseCoordinator(ID, username, password, phone, firstName, lastName, email, availability));
            } else {
            	staffData.add(new Staff(ID, username, password, phone, firstName, lastName, email, availability));
            }
        }
        
        userData.close();
    }
	
	public static void writeCourseData(ArrayList<Course> courseData) {
        PrintWriter outputFile = null;
        
        // Open couse data file and an temporary output file
        try {
            outputFile = new PrintWriter(new FileWriter(COURSE_DATA_FILE + "_temp"));
        }
        catch (Exception e) {
            System.exit(0);
        }
        
        // Writes out course data
        for(int i = 0; i < courseData.size(); i++) {
        	outputFile.println(courseData.get(i).getCourseCode());
        	outputFile.println(courseData.get(i).getCoordinator().getID());
        	outputFile.println(courseData.get(i).getYearRun());
        	outputFile.println(courseData.get(i).getSemesterRun());
        	
        	outputFile.println();
        }
        
        outputFile.close();
        
        // Remove old data file and rename the temporary file
        File original = new File(COURSE_DATA_FILE);
        File updated = new File(COURSE_DATA_FILE + "_temp");
        if(original.exists()) {
        	original.delete();
        }
        updated.renameTo(original);
	}
	
	public static void readCourseData(ArrayList<Course> courseData, ArrayList<Staff> staffData) {
        String courseCode;
        int yearRun, semesterRun;
        String coordinatorID;
        CourseCoordinator coordinator = null;
        Scanner courseInput = null;
        Course course;

        // Open the course data file
        try {
            courseInput = new Scanner(new FileInputStream(COURSE_DATA_FILE));
        }
        catch (Exception e) {
            System.out.println("Could not open " + COURSE_DATA_FILE);
            System.exit(0);
        }

        // Keep reading in course data until end of file
        while(courseInput.hasNextLine()) {
            courseCode = courseInput.nextLine();
            coordinatorID = courseInput.nextLine();
            yearRun = courseInput.nextInt();
            semesterRun = courseInput.nextInt();

            courseInput.nextLine();
            courseInput.nextLine();
            
            // Find course coordinator
            for(int i = 0; i < staffData.size(); i++) {
            	if(staffData.get(i).getID().equals(coordinatorID)) {
            		coordinator = (CourseCoordinator) staffData.get(i);
            		break;
            	}
            }
            course = new Course(courseCode, yearRun, semesterRun, coordinator);
            
            // Add read in course to the driver and coordinator ArrayLists
            courseData.add(course);
            coordinator.getCourses().add(course);
        }
        
        courseInput.close();
    }

    public static void writePositionData(ArrayList<Position> positionData) {
        PrintWriter outputFile = null;
        
        // Open position data file and an temporary output file
        try {
            outputFile = new PrintWriter(new FileWriter(POSITION_DATA_FILE + "_temp"));
        }
        catch (Exception e) {
            System.exit(0);
        }
        
        // Writes out position data
        for(int i = 0; i < positionData.size(); i++) {
            outputFile.println(positionData.get(i).getID());
            outputFile.println(positionData.get(i).getType());
            outputFile.println(positionData.get(i).getCourse().getCourseCode());
            outputFile.println(positionData.get(i).isFilled());
            outputFile.println(positionData.get(i).getWage());
            
            outputFile.println();
        }
        
        outputFile.close();
        
        // Remove old data file and rename the temporary file
        File original = new File(POSITION_DATA_FILE);
        File updated = new File(POSITION_DATA_FILE + "_temp");
        if(original.exists()) {
            original.delete();
        }
        updated.renameTo(original);
    }
    
    public static void readPositionData(ArrayList<Position> positionData, ArrayList<Course> courseData) {
        double wage;
        boolean filledStatus;
        String type, courseCode, pID;
        Course course = null;
        Scanner positionInput = null;
        Position position;

        // Open the position data file
        try {
            positionInput = new Scanner(new FileInputStream(POSITION_DATA_FILE));
        }
        catch (Exception e) {
            System.out.println("Could not open " + POSITION_DATA_FILE);
            System.exit(0);
        }

        // Keep reading in position data until end of file
        while(positionInput.hasNextLine()) {
            pID = positionInput.nextLine();
            type = positionInput.nextLine();
            courseCode = positionInput.nextLine();
            filledStatus = positionInput.nextBoolean();
            wage = positionInput.nextDouble();

            positionInput.nextLine();
            positionInput.nextLine();
            
            // Find course
            for(int i = 0; i < courseData.size(); i++) {
                if(courseData.get(i).getCourseCode().equals(courseCode)) {
                    course = courseData.get(i);
                    break;
                }
            }
            position = new Position(pID, type, course, filledStatus, wage);
            
            // Add read in position to the Arraylists in the driver and course classes
            positionData.add(position);
            course.getPositions().add(position);
        }
        
        positionInput.close();
    }

    public static void writeApplicationData(ArrayList<PositionApplication> applicationData) {
        PrintWriter outputFile = null;
        
        // Open position application data file and an temporary output file
        try {
            outputFile = new PrintWriter(new FileWriter(POSITION_APPLICATION_DATA_FILE + "_temp"));
        }
        catch (Exception e) {
            System.exit(0);
        }
        
        // Writes out position application data
        for(int i = 0; i < applicationData.size(); i++) {
            outputFile.println(applicationData.get(i).getPosition().getID());
            outputFile.println(applicationData.get(i).getStaff().getID());
            outputFile.println(applicationData.get(i).getDateSubmitted());
            outputFile.println(applicationData.get(i).getApprovalStatus());
            
            outputFile.println();
        }
        
        outputFile.close();
        
        // Remove old data file and rename the temporary file
        File original = new File(POSITION_APPLICATION_DATA_FILE);
        File updated = new File(POSITION_APPLICATION_DATA_FILE + "_temp");
        if(original.exists()) {
            original.delete();
        }
        updated.renameTo(original);
    }
    
    public static void readApplicationData(ArrayList<PositionApplication> applicationData, ArrayList<Position> positionData, ArrayList<Staff> staffData) {
        String dateSubmitted;
        String approvalStatus;
        Staff staff = null;
        String staffID;
        Position position = null;
        String positionID;
        Scanner applicationInput = null;
        PositionApplication application;

        // Open the position application data file
        try {
            applicationInput = new Scanner(new FileInputStream(POSITION_APPLICATION_DATA_FILE));
        }
        catch (Exception e) {
            System.out.println("Could not open " + POSITION_APPLICATION_DATA_FILE);
            System.exit(0);
        }

        // Keep reading in position application data until end of file
        while(applicationInput.hasNextLine()) {
            positionID = applicationInput.nextLine();
            staffID = applicationInput.nextLine();
            dateSubmitted = applicationInput.nextLine();
            approvalStatus = applicationInput.nextLine();

            applicationInput.nextLine();
            
            // Find position
            for(int i = 0; i < positionData.size(); i++) {
                if(positionData.get(i).getID().equals(positionID)) {
                    position = positionData.get(i);
                    break;
                }
            }

            // Find staff
            for(int i = 0; i < staffData.size(); i++) {
                if(staffData.get(i).getID().equals(staffID)) {
                    staff = staffData.get(i);
                    break;
                }
            }

            application = new PositionApplication(dateSubmitted, approvalStatus, staff, position);
            
            // Add read in position applications to the Arraylists in the driver, position, and staff classes
            position.getApplications().add(application);
            staff.getApplications().add(application);
            applicationData.add(application);
        }
        
        applicationInput.close();
    }

    public static void writeClassData(ArrayList<PositionClass> classData) {
        PrintWriter outputFile = null;
        
        // Open position class data file and an temporary output file
        try {
            outputFile = new PrintWriter(new FileWriter(CLASS_DATA_FILE + "_temp"));
        }
        catch (Exception e) {
            System.exit(0);
        }
        
        // Writes out position lcass data
        for(int i = 0; i < classData.size(); i++) {
            outputFile.println(classData.get(i).getPosition().getID());
            outputFile.println(classData.get(i).getLocation());
            outputFile.println(classData.get(i).getStartTime());
            outputFile.println(classData.get(i).getEndTime());
            for(int j = 0; j < DAYS_IN_WEEK; j++) {
                outputFile.print(classData.get(i).getRepeats()[j] + " ");
            }
            
            outputFile.println();
            outputFile.println();
        }
        
        outputFile.close();
        
        // Remove old data file and rename the temporary file
        File original = new File(CLASS_DATA_FILE);
        File updated = new File(CLASS_DATA_FILE + "_temp");
        if(original.exists()) {
            original.delete();
        }
        updated.renameTo(original);
    }
    
    public static void readClassData(ArrayList<PositionClass> classData, ArrayList<Position> positionData) {
        int startTime, endTime;
        String location, pID;
        int[] repeats = new int[DAYS_IN_WEEK];
        Position position = null;
        Scanner classInput = null;
        PositionClass positionClass;

        // Open the position class data file
        try {
            classInput = new Scanner(new FileInputStream(CLASS_DATA_FILE));
        }
        catch (Exception e) {
            System.out.println("Could not open " + CLASS_DATA_FILE);
            System.exit(0);
        }

        // Keep reading in position class data until end of file
        while(classInput.hasNextLine()) {
            pID = classInput.nextLine();
            location = classInput.nextLine();
            startTime = classInput.nextInt();
            endTime = classInput.nextInt();

            for(int i = 0; i < DAYS_IN_WEEK; i++) {
                repeats[i] = classInput.nextInt();
            }
            System.out.println();

            classInput.nextLine();
            classInput.nextLine();
            
            // Find position associated with class
            for(int i = 0; i < positionData.size(); i++) {
                if(positionData.get(i).getID().equals(pID)) {
                    position = positionData.get(i);
                    break;
                }
            }
            positionClass = new PositionClass(position, location, startTime, endTime, repeats);
            
            // Add read in class to the Arraylists in the driver and position classes
            classData.add(positionClass);
            position.getClasses().add(positionClass);
        }
        
        classInput.close();
    }

    public static void readPositionRequestData(ArrayList<PositionRequest> positionRequestData, ArrayList<Staff> staffData) {
        String courseCode, type, approvalStatus;
        double wage;
        String coordinatorID;
        CourseCoordinator coordinator = null;
        Scanner positionRequestInput = null;
        PositionRequest positionRequest;

        // Open the position requests data file
        try {
            positionRequestInput = new Scanner(new FileInputStream(POSITION_REQUEST_DATA_FILE));
        }
        catch (Exception e) {
            System.out.println("Could not open " + POSITION_REQUEST_DATA_FILE);
            System.exit(0);
        }

        // Keep reading in requests data until end of file
        while(positionRequestInput.hasNextLine()) {
            courseCode = positionRequestInput.nextLine();
            coordinatorID = positionRequestInput.nextLine();
            type = positionRequestInput.nextLine();
            approvalStatus = positionRequestInput.nextLine();
            wage = positionRequestInput.nextDouble();

            positionRequestInput.nextLine();
            positionRequestInput.nextLine();
            
            // Find course coordinator
            for(int i = 0; i < staffData.size(); i++) {
                if(staffData.get(i).getID().equals(coordinatorID)) {
                    coordinator = (CourseCoordinator) staffData.get(i);
                    break;
                }
            }
            positionRequest = new PositionRequest(courseCode, type, wage, coordinator, approvalStatus);
            
            // Add read in request to the driver and coordinator ArrayLists
            positionRequestData.add(positionRequest);
            coordinator.getPositionRequests().add(positionRequest);
        }
        
        positionRequestInput.close();
    }

    public static void writePositionRequestData(ArrayList<PositionRequest> positionRequestData) {
        PrintWriter outputFile = null;
        
        // Open position requests data file and an temporary output file
        try {
            outputFile = new PrintWriter(new FileWriter(POSITION_REQUEST_DATA_FILE + "_temp"));
        }
        catch (Exception e) {
            System.exit(0);
        }
        
        // Writes out position requests data
        for(int i = 0; i < positionRequestData.size(); i++) {
            outputFile.println(positionRequestData.get(i).getCourseCode());
            outputFile.println(positionRequestData.get(i).getCoordinator().getID());
            outputFile.println(positionRequestData.get(i).getType());
            outputFile.println(positionRequestData.get(i).getApprovalStatus());
            outputFile.println(positionRequestData.get(i).getWage());
            
            outputFile.println();
        }
        
        outputFile.close();
        
        // Remove old data file and rename the temporary file
        File original = new File(POSITION_REQUEST_DATA_FILE);
        File updated = new File(POSITION_REQUEST_DATA_FILE + "_temp");
        if(original.exists()) {
            original.delete();
        }
        updated.renameTo(original);
    }
}
