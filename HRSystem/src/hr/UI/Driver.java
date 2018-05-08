package hr.UI;
import java.util.*;
import java.text.*;
import hr.staff.*;
import hr.data.*;
import hr.school.*;

public class Driver {
    private static final int MAX_ID = 100000;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
    private static Date date = new Date();  

    // Arraylists that contains entire data for each class
    private static ArrayList<Staff> staffData = new ArrayList<Staff>();
    private static ArrayList<Course> courseData = new ArrayList<Course>();
    private static ArrayList<Position> positionData = new ArrayList<Position>();
    private static ArrayList<PositionApplication> applicationData = new ArrayList<PositionApplication>();
    private static ArrayList<PositionClass> classData = new ArrayList<PositionClass>();
    private static ArrayList<PositionRequest> positionRequests = new ArrayList<PositionRequest>();
    private static ArrayList<ApplicationApprovalRequest> applicationApprovalRequests = new ArrayList<ApplicationApprovalRequest>();

    // These variables store objects that are currently being edited/chosen/viewed
    private static Staff currentUser;
    private static CourseCoordinator currentCO;
    private static Course currentCourse;
    private static ArrayList<Position> availablePositions;
    private static Position currentPosition;
    private static PositionApplication currentApplication;
    private static PositionClass currentClass;
    private static PositionRequest currentPositionRequest;
    private static ApplicationApprovalRequest currentApplicationApprovalRequest;

    public static void main(String args[]) {
        
        System.out.println("Current users (All passwords = letmein):");
        System.out.println("\tStaff: s123456");
        System.out.println("\tCourse Coordinator: e000001");
        System.out.println("\tAdmin: a000000");
        System.out.println("WARNING! Data is only written to files when logging out.");

        while(true) {
            // Clear arraylists and fill them with the data from files
            staffData.clear();
            courseData.clear();
            positionData.clear();
            applicationData.clear();
            classData.clear();
            positionRequests.clear();
            applicationApprovalRequests.clear();
            FileHandler.readStaffData(staffData);
            FileHandler.readCourseData(courseData, staffData);
            FileHandler.readPositionData(positionData, courseData);
            FileHandler.readApplicationData(applicationData, positionData, staffData);
            FileHandler.readClassData(classData, positionData);
            FileHandler.readPositionRequestData(positionRequests, staffData);
            FileHandler.readApplicationApprovalRequestData(applicationApprovalRequests, staffData);

            login();

            // Run main menu until user chooses to quit
            while(mainMenu()) {
                System.out.println();
            }
            System.out.println("Successfully logged out.\n");

            // Write out new data to files
            FileHandler.writeStaffData(staffData);
            FileHandler.writeCourseData(courseData);
            FileHandler.writePositionData(positionData);
            FileHandler.writeApplicationData(applicationData);
            FileHandler.writeClassData(classData);
            FileHandler.writePositionRequestData(positionRequests);
            FileHandler.writeApplicationApprovalRequestData(applicationApprovalRequests);
        }
    }
    
    private static boolean mainMenu() {
        int command;
        String prompt;

        while(true) {
            // Show the main menu
            System.out.println("HR System");
            System.out.println("-----------------------------------\n");

            System.out.println("1. View/Edit personal info.");
            System.out.println("2. View current applications.");
            System.out.println("3. View/Apply for available positions.");
            System.out.println("4. View current classes.");
            System.out.println("5. View/Edit time availability.");
            
            if(currentUser instanceof CourseCoordinator) {
                System.out.println("\n6. Manage position requests.");
                System.out.println("7. Manage staff applications to positions.");
                System.out.println("8. Manage classes for courses");
            } else if(currentUser instanceof Admin) {
                System.out.println("\n6. Manage requests for positions from Course Coordinators.");
                System.out.println("7. Manage requests for staff application approvals from Course Coordinators.");
                System.out.println("\n8. Manage positions for a course.");
                System.out.println("9. Manage staff applications to positions.");
                System.out.println("10. Manage classes for courses.");
            }

            System.out.println("\n0. Logout.\n");
            
            if(currentUser instanceof CourseCoordinator) {
                prompt = "Please enter an option (0-8): ";
                command = getCommand(prompt, 0, 8);
            } else if (currentUser instanceof Admin) {
                prompt = "Please enter an option (0-10): ";
                command = getCommand(prompt, 0, 10);
            } else {
                prompt = "Please enter an option (0-5): ";
                command = getCommand(prompt, 0, 5);
            }
            
            // Run the chosen sub menu
            if(command == 1) {
                viewPersonalInfoMenu();
            } else if (command == 2) {
                viewCurrentApplicationsMenu();
            } else if (command == 3) {
                viewAvailablePositionsMenu();
            } else if (command == 4) {
                viewCurrentClassesMenu();
            } else if(command == 5) {
                viewAvailabilityMenu();
            } else if (command == 0) {
                return false;
            } else if(currentUser instanceof CourseCoordinator) {
            	if(command == 6) {
                    managePositionRequestsCO();
                } else if(command == 7) {
                    manageStaffApplicationsCO();
                } else if(command == 8) {
                    manageClasses();
                }
            } else if(currentUser instanceof Admin) {
                if(command == 6) {
                    managePositionRequests();
                } else if(command == 7) {
                    manageApplicationApprovalRequests();
                } else if(command == 8) {
                    managePositions();
                } else if(command == 9) {
                    manageStaffApplications();
                } else if(command == 10) {
                    manageClasses();
                }
            }
        }
    }

    private static void login() {
        boolean loginStatus = false;
        String username, password;
        Scanner kb = new Scanner(System.in);

        System.out.println("Login");
        System.out.println("-----------------------------------\n");

        // Keep repeating login prompt
        while(!loginStatus) {
            System.out.println("Username: ");
            username = kb.nextLine();

            System.out.println("Password: ");
            password = kb.nextLine();
        
            // Check if user credentials exists and is correct
            if(getCurrentUser(username, password)) {
                System.out.println("Successfully logged in.");    
                loginStatus = true;
            } else {
                System.out.println("Invalid credentials, please try again.");    
            }
        }
    } 

// Methods from this point are sub-menus that lead on from the main menu-----------------------------------------------

    // Staff submenus--------------------------------------------------------------------------------------------------

    private static void viewPersonalInfoMenu()  {
        int command;
        String prompt;
        while(true) {
            System.out.println("Personal Information");
            System.out.println("-----------------------------------\n");

            System.out.println("1. ID Number:\t" + currentUser.getID());
            System.out.println("2. Password:\t<Classified>");
            System.out.println("3. First Name:\t" + currentUser.getFirstName());
            System.out.println("4. Last Name:\t" + currentUser.getLastName());
            System.out.println("5. Phone #:\t" + currentUser.getPhone());
            System.out.println("6. Email:\t" + currentUser.getEmail());
            System.out.println("\n0. Return to main menu");
            System.out.println();
            
            prompt = "Enter a value to edit (0-6): ";    
            command = getCommand(prompt, 0, 6);
            
            if(command == 0) {
                return;
            } else {
                editPersonalInfo(command);
            } 
        }    
    }

    private static void viewCurrentApplicationsMenu()  {
        int command;
        String prompt;
        while(selectCurrentPositionApplication()) {
            while(true) {
                System.out.println("\nApplication Details");
                System.out.println("-----------------------------------\n");
                System.out.println("Application ID:\t" + currentApplication.getApplicationID());
                System.out.println("Position ID:\t" + currentApplication.getPosition().getID());
                System.out.println("Course Code:\t" + currentApplication.getPosition().getCourse().getCourseCode());
                System.out.println("Type:\t" + currentApplication.getPosition().getType());
                System.out.println("Date Submitted:\t" + currentApplication.getDateSubmitted());
                System.out.println("Approval Status:\t" + currentApplication.getApprovalStatus());
                System.out.println("Offer Status:\t" + currentApplication.getOfferStatus());

                System.out.println("\n1. Remove this application");
                System.out.println("2. Accept an offer (if one has been given)");
                System.out.println("3. Decline an offer (if one has been given)");
                System.out.println("\n0. Return to main menu");
                System.out.println();
                
                prompt = "Select an application to remove it (0-" + currentUser.getApplications().size() + ")";
                command = getCommand(prompt, 0, currentUser.getApplications().size());
                
                if(command == 0) {
                    return;
                } else if (command == 1) {
                    prompt = "Are you sure you want to remove this application (0 for yes, 1 for no)? ";
                    if(getCommand(prompt, 0, 1) == 0) {
                        removeApplication();
                        break;
                    }
                } else if(command == 2) {
                    editOffer(true);
                } else if(command == 3) {
                    editOffer(false);
                }
            }    
        }
    }

    private static void viewAvailablePositionsMenu()  {
        int command;
        String prompt;
        while(true) {
            System.out.println("Available Positions");
            System.out.println("-----------------------------------\n");

            // Find all available positions
            availablePositions = new ArrayList<Position>();
            for(int i = 0; i < positionData.size(); i++) {
                if(!positionData.get(i).isFilled()) {
                    availablePositions.add(positionData.get(i));
                }
            }

            // Display all available positions
            for(int i = 0; i < availablePositions.size(); i++) {
                currentPosition = availablePositions.get(i);
                System.out.println((i+1) + ".");
                System.out.println("Position ID:\t" + currentPosition.getID());
                System.out.println("Course:\t" + currentPosition.getCourse().getCourseCode());
                System.out.println("Type:\t" + currentPosition.getType());
                System.out.println("Wage:\t$" + currentPosition.getWage() + "/hour.");
                System.out.println();
            }

            System.out.println("\n0. Return to main menu");
            System.out.println();
            
            prompt = "Select a position to apply for (0-" + availablePositions.size() +"): ";    
            command = getCommand(prompt, 0, availablePositions.size());
            
            if(command == 0) {
                return;
            } else {
                submitApplication();
            }
        }    
    }

    private static void viewCurrentClassesMenu()  {
        int command;
        String prompt;
        while(true) {
            System.out.println("Current Classes");
            System.out.println("-----------------------------------\n");

            // Find all applications that have an accepted offer and corresponding 
            // positions for current user
            for(int i = 0; i < currentUser.getApplications().size(); i++) {
                currentApplication = currentUser.getApplications().get(i);
                if(currentApplication.getOfferStatus().equals("Accepted")) {
                    currentPosition = currentApplication.getPosition();

                    // Print out details of every class of every position
                    System.out.println("Position: " + currentPosition.getID());
                    for(int j = 0; j < currentPosition.getClasses().size(); j++) {
                        currentClass = currentPosition.getClasses().get(j);
                        System.out.println("\tLocation:\t" + currentClass.getLocation());
                        System.out.println("\tStart Time:\t" + formatTime(currentClass.getStartTime()));
                        System.out.println("\tEnd Time:\t" + formatTime(currentClass.getEndTime()));
                        System.out.print("\tRepeats:\t");
                        for(int k = 0; k < FileHandler.DAYS_IN_WEEK; k++) {
                            if(currentClass.getRepeats()[k] == 1) {
                                System.out.print(dayString(k+1) + " ");
                            }
                        }
                        System.out.println();
                        System.out.println();
                    }
                    System.out.println();
                }
            }

            System.out.println("\n0. Return to main menu");
            System.out.println();
            
            prompt = "Enter an option (0-0): ";    
            command = getCommand(prompt, 0, 0);
            
            if(command == 0) {
                return;
            }
        }    
    }

    private static void viewAvailabilityMenu()  {
        int command;
        String prompt;
        while(true) {
            System.out.println("Availability");
            System.out.println("-----------------------------------\n");
            System.out.println("1. View current availability");
            System.out.println("2. Edit availability");
            System.out.println("\n0. Return to main menu");
            System.out.println();
            
            prompt = "Enter an option (0-2): ";    
            command = getCommand(prompt, 0, 2);
            
            if(command == 0) {
                return;
            } else if(command == 2){
                editAvailability(command, currentUser.getAvailability());
            } else {
                printAvailability(currentUser.getAvailability());
            } 
        }    
    }

    //-----------------------------------------------------------------------------------------------------------------

    // Course coordinator sub-menus------------------------------------------------------------------------------------

    private static void managePositionRequestsCO()  {
        int command;
        String prompt;

        currentCO = (CourseCoordinator) currentUser;

        while(true) {
            System.out.println("\nCurrent position requests");
            System.out.println("-----------------------------------\n");

            // Prints out all requests for new positions from the current course coordinator
            for(int i = 0; i < ((CourseCoordinator) currentUser).getPositionRequests().size(); i++) {
                currentPositionRequest = ((CourseCoordinator) currentUser).getPositionRequests().get(i); 
                System.out.println("Course Code:\t" + currentPositionRequest.getCourseCode()); 
                System.out.println("Type:\t" + currentPositionRequest.getType()); 
                System.out.println("Wage:\t$" + currentPositionRequest.getWage() + "/hour"); 
                System.out.println("Approval Status:\t" + currentPositionRequest.getApprovalStatus()); 
                System.out.println();
            }

            System.out.println("1. Request a new position for a course.");
            System.out.println("0. Return to main menu");
            System.out.println();
            
            prompt = "Select an option (0-1): ";    
            command = getCommand(prompt, 0, 1);
            
            if(command == 0) {
                return;
            } else if(command == 1) {
                // Make user select a course and prompt for details of new position
                if(COSelectCourse()) {
                    addPositionRequest();
                }
            }
        }    
    }

    private static void manageStaffApplicationsCO()  {
        boolean requestSent;

        currentCO = (CourseCoordinator)currentUser;
        while(COSelectCourse()) {
            while(selectPosition()) {
                while(selectPositionApplication()) {
                    requestSent = false;

                    // Check if currentCO has already sent a request for the current position
                    for(int i = 0; i < currentCO.getApplicationApprovalRequests().size(); i++) {
                        if(currentCO.getApplicationApprovalRequests().get(i).getApplicationID().equals(currentApplication.getApplicationID())) {
                            requestSent = true;
                        }
                    }

                    // Only add a new request if the application has not been processed and the current CO
                    // doesn't have an existing request
                    if(currentApplication.getApprovalStatus().equals("Pending") && requestSent == false) {
                        addApplicationApprovalRequest();
                    } else {
                        if(requestSent) {
                            System.out.println("Unable to make changes, a request has already been sent.");
                        } else {
                            System.out.println("Unable to make changes, application has already been " + 
                                               currentApplication.getApprovalStatus() + ".");
                        }
                    }
                }
            }
        }    
    }

    //-----------------------------------------------------------------------------------------------------------------

    // Admin sub-menus-------------------------------------------------------------------------------------------------

    private static void managePositions()  {
        int command;
        String prompt;

        if(AdminSelectCourse()) {
            while(true) {
                System.out.println("\nCurrrent Positions");
                System.out.println("-----------------------------------\n");

                // Prints out all positions for the chosen course
                for(int i = 0; i < currentCourse.getPositions().size(); i++) {
                    currentPosition = currentCourse.getPositions().get(i); 
                    System.out.print((i+1) + ". " + currentPosition.getID() + " ");
                    System.out.print(currentPosition.getType() + " $");
                    System.out.println(currentPosition.getWage() + "/hour.");
                }

                System.out.println();
                System.out.println((currentCourse.getPositions().size() + 1) + ". Add a new position for a course.");
                System.out.println("0. Return to main menu");
                System.out.println();
                
                prompt = "Select an option (0-" + (currentCourse.getPositions().size() + 1) + "): ";    
                command = getCommand(prompt, 0, (currentCourse.getPositions().size() + 1));
                
                if(command == 0) {
                    return;
                } else if(command == currentCourse.getPositions().size() + 1) {
                    // Prompt for details of new position
                    addPosition();
                } else {
                    // Run menu to edit a position
                    currentPosition = currentCourse.getPositions().get(command - 1);
                    editPosition();
                }
            }
        }
    }

    private static void managePositionRequests()  {
        int command;
        String prompt;
        Position newPosition;

        while(selectCO("Position")) {
            while(selectPositionRequest()) {
                System.out.println("1. Approve request");
                System.out.println("2. Deny request");

                System.out.println("\n0. Return");
                System.out.println();
                
                prompt = "Select an option (0-2): ";    
                command = getCommand(prompt, 0, 2);
                
                if(command == 1) {
                    currentPositionRequest.setApprovalStatus("Approved");

                    // Create a new position based on request's details
                    newPosition = new Position(currentPositionRequest.getID(),
                                               currentPositionRequest.getType(), currentCourse,
                                               false, currentPositionRequest.getWage());

                    // Add new position to the course and the current position data
                    currentCourse.getPositions().add(newPosition);
                    positionData.add(newPosition);
                } else if(command == 2) {
                    currentPositionRequest.setApprovalStatus("Denied");
                }
            }
        }
    }

    private static void manageApplicationApprovalRequests()  {
        int command;
        String prompt;
        Position position;

        while(selectCO("ApplicationApproval")) {
            while(selectApplicationApprovalRequest()) {
                // Prompt to approve or deny request for approval of currentApplication
                System.out.println("1. Approve request");
                System.out.println("2. Deny request");

                System.out.println("\n0. Return");
                System.out.println();
                
                prompt = "Select an option (0-2): ";    
                command = getCommand(prompt, 0, 2);
                
                if(command == 1) {
                    // Set application approval request to approved
                    currentApplicationApprovalRequest.setApprovalStatus("Approved");

                    // Find application associated with approval request
                    findApplication();

                    // Set application to the position as offer sent
                    currentApplication.setOfferStatus("Offer Received");

                    // Find corresponsing position and set as filled
                    setfill:for(int i = 0; i < currentCO.getCourses().size(); i++) {
                        for(int j = 0; j < currentCO.getCourses().get(i).getPositions().size(); j++) {
                            position = currentCO.getCourses().get(i).getPositions().get(j);
                            if(position.getID().equals(currentApplicationApprovalRequest.getPositionID())) {
                                position.setFilled(true);
                                break setfill;
                            }
                        }
                    }
                } else if(command == 2) {
                    currentApplicationApprovalRequest.setApprovalStatus("Denied");
                }    
            }
        } 
    }

    private static void manageStaffApplications()  {
        int command;
        String prompt;

        // Select a course 
        while(AdminSelectCourse()) {
            while(selectPosition()) {
                while(selectPositionApplication()) {
                    // Deny changes if application to position has already been processed
                    if(!currentApplication.getApprovalStatus().equals("Pending")) {
                        System.out.println("Cannot make changes, application has already been " +
                                           currentApplication.getApprovalStatus() + "\n");
                    } else {
                        // Prompt to approve or deny position application from staff
                        System.out.println("1. Approve application");
                        System.out.println("2. Deny application");

                        System.out.println("\n0. Return");
                        System.out.println();
                        
                        prompt = "Select an option (0-2): ";    
                        command = getCommand(prompt, 0, 2);
                         
                        if(command == 1) {
                            // Set position application to approved
                            currentApplication.setApprovalStatus("Approved");

                            // Send offer to the staff member
                            currentApplication.setOfferStatus("Offer received");

                            // Sets position as filled
                            currentApplication.getPosition().setFilled(true);
                        } else if(command == 2) {
                            // Set application to denied
                            currentApplication.setApprovalStatus("Denied");
                        }
                    }
                }
            }
        }    
    }

    //-----------------------------------------------------------------------------------------------------------------

    // Course coordinator and Admin sub-menus--------------------------------------------------------------------------

    private static void manageClasses()  {
        int command;
        String prompt;

        if((currentUser instanceof CourseCoordinator && COSelectCourse()) ||
           (currentUser instanceof Admin && AdminSelectCourse())) {
            while(selectPosition()) {
                while(true) {
                    System.out.println("\nSelect a class");
                    System.out.println("-----------------------------------\n");

                    // List all classes for currentPosition
                    for(int i = 0; i < currentPosition.getClasses().size(); i++) {
                        currentClass = currentPosition.getClasses().get(i);
                        System.out.println((i+1) + ".");
                        System.out.println("Location:\t" + currentClass.getLocation());
                        System.out.println("Start Time:\t" + formatTime(currentClass.getStartTime()));
                        System.out.println("End Time:\t" + formatTime(currentClass.getEndTime()));
                        System.out.print("Repeats:\t");
                        for(int k = 0; k < FileHandler.DAYS_IN_WEEK; k++) {
                            if(currentClass.getRepeats()[k] == 1) {
                                System.out.print(dayString(k+1) + " ");
                            }
                        }
                        System.out.println();
                        System.out.println();
                    }

                    System.out.println((currentPosition.getClasses().size() + 1) + ". Add a new class");
                    System.out.println("0. Return to main menu\n");

                    prompt = "Select a class (0-" + (currentPosition.getClasses().size() + 1) +"): ";    
                    command = getCommand(prompt, 0, currentPosition.getClasses().size() + 1);
                    
                    if(command == 0) {
                        break;
                    } else if(command == currentPosition.getClasses().size() + 1) {
                        addClass();
                    } else {
                        currentClass = currentPosition.getClasses().get(command - 1);
                        editClass();
                    }
                }
            }
        }
    }

    //-----------------------------------------------------------------------------------------------------------------

//---------------------------------------------------------------------------------------------------------------------

// Methods from this point prompts user to select specific objects-----------------------------------------------------

    // Staff related---------------------------------------------------------------------------------------------------

    // Promtps the current user to select an application they've submitted to
    // various positions
    private static boolean selectCurrentPositionApplication() {
        int command;
        String prompt;

        System.out.println("Current Applications");
        System.out.println("-----------------------------------\n");

        // List all the applications that the currentUser has sent to various positions
        for(int i = 0; i < currentUser.getApplications().size(); i++) {
            currentApplication = currentUser.getApplications().get(i);
            System.out.println((i+1) + ".");
            System.out.println("Application ID:\t" + currentApplication.getApplicationID());
            System.out.println("Position ID:\t" + currentApplication.getPosition().getID());
            System.out.println("Course Code:\t" + currentApplication.getPosition().getCourse().getCourseCode());
            System.out.println("Type:\t" + currentApplication.getPosition().getType());
            System.out.println("Date Submitted:\t" + currentApplication.getDateSubmitted());
            System.out.println("Approval Status:\t" + currentApplication.getApprovalStatus());
            System.out.println("Offer Status:\t" + currentApplication.getOfferStatus());
            System.out.println();
        }

        System.out.println("\n0. Return to main menu");
        System.out.println();
        
        prompt = "Select an application (0-" + currentUser.getApplications().size() + ")";
        command = getCommand(prompt, 0, currentUser.getApplications().size());
        
        if(command == 0) {
            return false;
        } else {
            currentApplication = currentUser.getApplications().get(command - 1);
            return true;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------

    // Course Coordinator related--------------------------------------------------------------------------------------

    // Prompts current course coordinator to select a course that they manage
    private static boolean COSelectCourse() {
        int command;
        String prompt;

        System.out.println("\nSelect a course");
        System.out.println("-----------------------------------\n");

        //Lists all courses for current Course Coordinator
        for(int i = 0; i < ((CourseCoordinator) currentUser).getCourses().size(); i++) {
            currentCourse = ((CourseCoordinator) currentUser).getCourses().get(i);
            System.out.print((i+1) + ". " + currentCourse.getCourseCode() + " ");
            System.out.print(currentCourse.getYearRun());
            System.out.println(" S" + currentCourse.getSemesterRun());
        }
        System.out.println("\n0. Return to main menu\n");

        prompt = "Select a course (0-" + ((CourseCoordinator) currentUser).getCourses().size() +"): ";    
        command = getCommand(prompt, 0, ((CourseCoordinator) currentUser).getCourses().size());
        
        if(command == 0) {
            return false;
        } else {
            currentCourse = ((CourseCoordinator) currentUser).getCourses().get(command - 1);
            return true;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------

    // Admin related---------------------------------------------------------------------------------------------------

    // Prompts current admin to select a course from all existing courses
    private static boolean AdminSelectCourse() {
        int command;
        String prompt;

        System.out.println("\nSelect a course");
        System.out.println("-----------------------------------\n");

        //Lists all courses
        for(int i = 0; i < courseData.size(); i++) {
            currentCourse = courseData.get(i);
            System.out.print((i+1) + ". " + currentCourse.getCourseCode() + " ");
            System.out.print(currentCourse.getYearRun());
            System.out.println(" S" + currentCourse.getSemesterRun());
        }
        System.out.println("\n0. Return to main menu\n");

        prompt = "Select a course (0-" + courseData.size() +"): ";    
        command = getCommand(prompt, 0, courseData.size());
        
        if(command == 0) {
            return false;
        } else {
            currentCourse = courseData.get(command - 1);
            return true;
        }
    }

    // Prompts the current admin to select a course coordinator
    private static boolean selectCO(String requestType) {
        int command;
        int numCO = 0;
        String prompt;

        System.out.println("\nSelect a course coordinator");
        System.out.println("-----------------------------------\n");

        // List all course coordinators
        for(int i = 0; i < staffData.size(); i++) {
            if(staffData.get(i) instanceof CourseCoordinator) {
                numCO++;
                currentCO = (CourseCoordinator)staffData.get(i);
                System.out.print(numCO + ". ");
                System.out.print(currentCO.getID() + ", ");
                System.out.print(currentCO.getFirstName() + " ");
                System.out.print(currentCO.getLastName() + " (");

                // Print out the number of unprocessed requests
                if(requestType.equals("Position")) {
                    System.out.println(getNumPendingPositionRequests() + " pending requests)");
                } else {
                    System.out.println(getNumPendingApplicationApprovalRequests() + " pending requests)");
                }
            }
        }

        System.out.println("\n0. Return to main menu\n");

        prompt = "Select a course coordinator (0-" + numCO +"): ";    
        command = getCommand(prompt, 0, numCO);
        
        if(command == 0) {
            return false;
        } else {
            // Find the chosen Course Coordinator and store them in currentCO
            for(int i = 0; i < staffData.size(); i++) {
                if(staffData.get(i) instanceof CourseCoordinator) {
                    if(command == 1) {
                        currentCO = (CourseCoordinator)staffData.get(i);
                        break;
                    } else {
                        command--;
                    }
                }
            }
            return true;
        }
    }

    // Prompts the current admin to select a request for a new position
    // from the currentCO
    private static boolean selectPositionRequest() {
        int command;
        String prompt;

        System.out.println("\nSelect a position request");
        System.out.println("-----------------------------------\n");

        // List all requests for a new position from the currentCO
        for(int i = 0; i < currentCO.getPositionRequests().size(); i++) {
            currentPositionRequest = currentCO.getPositionRequests().get(i);
            System.out.println((i + 1) + ". ");
            System.out.println("Course Code:\t" + currentPositionRequest.getCourseCode()); 
            System.out.println("Type:\t" + currentPositionRequest.getType()); 
            System.out.println("Wage:\t$" + currentPositionRequest.getWage() + "/hour"); 
            System.out.println("Approval Status:\t" + currentPositionRequest.getApprovalStatus()); 
            System.out.println();
        }

        System.out.println("\n0. Return\n");

        prompt = "Select a position request (0-" + currentCO.getPositionRequests().size() +"): ";    
        command = getCommand(prompt, 0, currentCO.getPositionRequests().size());
        
        if(command == 0) {
            return false;
        } else {
            currentPositionRequest = currentCO.getPositionRequests().get(command - 1);

            // Find the corresponding course
            for(int i = 0; i < currentCO.getCourses().size(); i++) {
                if(currentCO.getCourses().get(i).getCourseCode().equals(currentPositionRequest.getCourseCode())) {
                    currentCourse = currentCO.getCourses().get(i);
                    break;
                }        
            }

            return true;
        }
    }

    // Prompts the current admin to select a request to approve an application
    // from the currentCO
    private static boolean selectApplicationApprovalRequest() {
        int command;
        String prompt;

        System.out.println("\nSelect an application approval request");
        System.out.println("-----------------------------------\n");

        // List all requests to approve an application from the currentCO
        for(int i = 0; i < currentCO.getApplicationApprovalRequests().size(); i++) {
            currentApplicationApprovalRequest = currentCO.getApplicationApprovalRequests().get(i);
            System.out.println((i + 1) + ". ");
            System.out.println("Position ID:\t" + currentApplicationApprovalRequest.getPositionID()); 
            System.out.println("Application ID:\t" + currentApplicationApprovalRequest.getApplicationID()); 
            System.out.println("Approval Status:\t" + currentApplicationApprovalRequest.getApprovalStatus()); 
            System.out.println();
        }

        System.out.println("\n0. Return to main menu\n");

        prompt = "Select an application approval request (0-" + currentCO.getApplicationApprovalRequests().size() +"): ";    
        command = getCommand(prompt, 0, currentCO.getApplicationApprovalRequests().size());
        
        if(command == 0) {
            return false;
        } else {
            currentApplicationApprovalRequest = currentCO.getApplicationApprovalRequests().get(command - 1);
            return true;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    
    // Course Coordinator and Admin related----------------------------------------------------------------------------

    // Prompts current user to select a position from the currentCourse
    private static boolean selectPosition() {
        int command;
        String prompt;

        System.out.println("\nSelect a position");
        System.out.println("-----------------------------------\n");

        //Lists all positions for currentCousrse
        for(int i = 0; i < currentCourse.getPositions().size(); i++) {
            currentPosition = currentCourse.getPositions().get(i);
            System.out.println((i+1) + ".");
            System.out.println("Position ID:\t" + currentPosition.getID());
            System.out.println("Type:\t" + currentPosition.getType());
            System.out.println("Wage:\t$" + currentPosition.getWage());
            System.out.println("Filled Status:\t" + currentPosition.isFilled());
            System.out.println();
        }
        System.out.println("\n0. Return to main menu\n");

        prompt = "Select a position (0-" + currentCourse.getPositions().size() +"): ";    
        command = getCommand(prompt, 0, currentCourse.getPositions().size());
        
        if(command == 0) {
            return false;
        } else {
            currentPosition = currentCourse.getPositions().get(command - 1);
            return true;
        }
    }

    // Prompts course coordinator/admin to select an application a staff member
    // has made to th currentPosition
    private static boolean selectPositionApplication() {
        int command;
        String prompt;

        System.out.println("\nSelect a staff application");
        System.out.println("-----------------------------------\n");

        //Lists all applications to the currentPosition
        for(int i = 0; i < currentPosition.getApplications().size(); i++) {
                currentApplication = currentPosition.getApplications().get(i);
                System.out.println((i+1) + ". ");
                System.out.println("Application ID:\t" + currentApplication.getApplicationID());
                System.out.println("Staff ID:\t" + currentApplication.getStaff().getID());
                System.out.println("Staff Name:\t" + currentApplication.getStaff().getFirstName() + 
                                   " " + currentApplication.getStaff().getLastName());
                System.out.println("Date Submitted:\t" + currentApplication.getDateSubmitted());
                System.out.println("Approval Status:\t" + currentApplication.getApprovalStatus());
                System.out.println("Offer Status:\t" + currentApplication.getOfferStatus());
                System.out.println();
        }
        System.out.println("\n0. Return to main menu\n");

        prompt = "Select a staff application (0-" + currentPosition.getApplications().size() +"): ";    
        command = getCommand(prompt, 0, currentPosition.getApplications().size());
        
        if(command == 0) {
            return false;
        } else {
            currentApplication = currentPosition.getApplications().get(command - 1);
            return true;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------

//---------------------------------------------------------------------------------------------------------------------

// Methods from this point prompts user to create/edit classes---------------------------------------------------------

    private static void editPersonalInfo(int command) {
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter new value: ");
        
        if(command == 1) {
            if(currentUser instanceof Admin) {
                currentUser.setID(kb.next());
            } else {
                System.out.println("Permission Denied. Please contact an admin.\n\n");
                return;
            }
        } else if (command == 2) {
            currentUser.setPassword(kb.nextLine());
        } else if (command == 3) {
            currentUser.setFirstName(kb.nextLine());
        } else if (command == 4) {
            currentUser.setLastName(kb.nextLine());
        } else if(command == 5) {
            currentUser.setPhone(kb.nextLine());
        } else if (command == 6) {
            currentUser.setEmail(kb.nextLine());
        }
        
        System.out.println("Value successfully changed.\n\n");
    }

    private static void editAvailability(int command, boolean[][] availability) {
        String prompt;
        int day, startTime, endTime;
        int startTimePosition, endTimePosition;
        boolean setAvailable;
        
        while(true) {
            System.out.println("Availability Editor");
            System.out.println("-----------------------------------\n");

            System.out.println("1. Set available");
            System.out.println("2. Set unavailable");
            System.out.println("\n0. Return");
            System.out.println();
            
            prompt = "Enter an option (0-2): ";    
            command = getCommand(prompt, 0, 2);
            
            if(command == 0) {
                return;
            } else {
                if(command == 1) {
                    setAvailable = true;
                } else {
                    setAvailable = false;
                }

                // Prompt for details of availability/unavailability
                prompt = "What day of the week (1-7)? ";
                day = getCommand(prompt, 1, 7);
                prompt = "What starting time(e.g. 1830 = 6:30pm)? ";
                startTime = getTime(prompt);
                prompt = "What ending time(e.g. 1830 = 6:30pm)? ";
                endTime = getTime(prompt);

                if(startTime >= endTime) {
                    System.out.println("Invalid period of time.");
                } else {
                    // Get position in availability matrix
                    startTimePosition = (startTime / 100) * 4 + (startTime % 100) / 15;
                    endTimePosition = (endTime / 100) * 4 + (endTime % 100) / 15;

                    // Set the chosen time as available/unavailable
                    for(int i = startTimePosition; i <= endTimePosition; i++) {
                        availability[day - 1][i] = setAvailable;
                    }

                    // Print out updates availability table
                    printAvailability(availability);
                }
            }
        }
    }

    private static void addPosition() {
        Random rand = new Random();
        Scanner kb = new Scanner(System.in);
        String type;
        String positionID = currentCourse.getCourseCode() + "-" +rand.nextInt(MAX_ID);
        double wage;
        Position position;

        System.out.println("Creating a Position for Chosen Course");
        System.out.println("-----------------------------------\n");

        System.out.print("Enter the position's type (e.g. tutor): ");
        type = kb.nextLine();
        System.out.print("Enter the position's wage per hour (e.g. 25.00): ");
        wage = kb.nextDouble();

        position = new Position(positionID, type, currentCourse, false, wage);

        // Add the new position to objects that are associated with it
        positionData.add(position);
        currentCourse.getPositions().add(position);

        System.out.println("\nSuccessfully created new position.\n");
    }

    private static void editPosition() {
        Scanner kb = new Scanner(System.in);
        int command;
        String prompt;

        while(true) {
            System.out.println("Editing Position");
            System.out.println("-----------------------------------\n");

            System.out.println("1. Position ID:\t" + currentPosition.getID());
            System.out.println("2. Type:\t" + currentPosition.getType());
            System.out.println("3. Wage:\t$" + currentPosition.getWage() + "/hour");
            System.out.println("4. Filled Status:\t" + currentPosition.isFilled());

            System.out.println("\n5. Remove this position");
            System.out.println("0. Return");
            System.out.println();
            
            prompt = "Enter a value to edit (0-5): ";    
            command = getCommand(prompt, 0, 5);
            
            if(command == 0) {
                return;
            } else if(command == 1) {
                System.out.println("\nEnter new ID (e.g. COSC1234-123456):\n");
                currentPosition.setID(kb.nextLine());
            } else if(command == 2) {
                System.out.println("\nEnter new type (e.g. lecturer):\n");
                currentPosition.setType(kb.nextLine());
            } else if(command == 3) {
                System.out.println("\nEnter new wage per hour (e.g. 25.00):\n");
                currentPosition.setWage(kb.nextDouble());
            } else if(command == 4) {
                System.out.println("\nEnter new value (lower-case! e.g. true):\n");
                currentPosition.setFilled(kb.nextBoolean());
            } else {
                if(removePosition()) {
                    return;
                }
            }
        }  
    }

    private static boolean removePosition() {
        String prompt;
        PositionApplication removeApplication;
        PositionRequest removePositionRequest;
        ApplicationApprovalRequest removeApplicationApprovalRequest;

        prompt = "Are you sure you want to remove this position (0 for yes, 1 for no)? ";
        if(getCommand(prompt, 0, 1) == 1) {
            return false;
        }

        // Remove position from objects that are associated with it
        currentCourse.getPositions().remove(currentPosition);
        positionData.remove(currentPosition);

        // Remove any associated classes for the position
        for(int i = 0; i < currentPosition.getClasses().size(); i++) {
            classData.remove(currentPosition.getClasses().get(i));
        }

        // Remove any associated applications for this position
        for(int i = 0; i < currentPosition.getApplications().size(); i++) {
            removeApplication = currentPosition.getApplications().get(i);
            removeApplication.getStaff().getApplications().remove(removeApplication);
            applicationData.remove(removeApplication);
        }

        // Remove any application approval requests associated with this position
        for(int i = 0; i < currentPosition.getCourse().getCoordinator().getApplicationApprovalRequests().size(); i++) {
            removeApplicationApprovalRequest = currentPosition.getCourse().getCoordinator().getApplicationApprovalRequests().get(i);
            if(removeApplicationApprovalRequest.getPositionID().equals(currentPosition.getID())) {
                currentPosition.getCourse().getCoordinator().getApplicationApprovalRequests().remove(i);
                applicationApprovalRequests.remove(removeApplicationApprovalRequest);
                i--;
            }
        }

        // Remove any requests for this position to be made by course coordinators
        for(int i = 0; i < currentPosition.getCourse().getCoordinator().getPositionRequests().size(); i++) {
            removePositionRequest = currentPosition.getCourse().getCoordinator().getPositionRequests().get(i);
            if(removePositionRequest.getID().equals(currentPosition.getID())) {
                currentPosition.getCourse().getCoordinator().getPositionRequests().remove(i);
                positionRequests.remove(removePositionRequest);
                i--;
            }
        }

        System.out.println("Position removed.\n");
        return true;
    }

    private static void addClass() {
        Scanner kb = new Scanner(System.in);
        StringTokenizer tokenizer;
        String location;
        int startTime, endTime;
        int[] repeats;
        PositionClass newClass;

        System.out.println("Adding New Class");
        System.out.println("-----------------------------------\n");

        System.out.println("\nEnter new location:\n");
        location = kb.nextLine();
        System.out.println("\nEnter start time in 24h format (e.g. 0830 = 8:30am):\n");
        startTime = kb.nextInt();
        System.out.println("\nEnter end time in 24h format (e.g. 0830 = 8:30am):\n");
        endTime = kb.nextInt();
        kb.nextLine();
        System.out.println("\nEnter days to repeat seperated by spaces (e.g. 1 3 5 = Monday Wednesday Friday):\n");
        tokenizer = new StringTokenizer(kb.nextLine());

        repeats = new int[FileHandler.DAYS_IN_WEEK];

        // Reset days to repeat class
        for(int i = 0; i < FileHandler.DAYS_IN_WEEK; i++) {
            repeats[i] = 0;
        }

        // Set all chosen days to repeat the class
        while (tokenizer.hasMoreTokens()) {
            repeats[Integer.parseInt(tokenizer.nextToken()) - 1] = 1;
        }

        newClass = new PositionClass(currentPosition, location, startTime, endTime, repeats);

        currentPosition.getClasses().add(newClass);
        classData.add(newClass);

        System.out.println("Added the new class.\n");
    }

    private static void editClass() {
        Scanner kb = new Scanner(System.in);
        StringTokenizer tokenizer;
        int command;
        String prompt;

        while(true) {
            System.out.println("Editing Class");
            System.out.println("-----------------------------------\n");

            System.out.println("1. Location:\t" + currentClass.getLocation());
            System.out.println("2. Start Time:\t" + formatTime(currentClass.getStartTime()));
            System.out.println("3. End Time:\t" + formatTime(currentClass.getEndTime()));
            System.out.println("4. Repeats:\t");
            for(int i = 0; i < FileHandler.DAYS_IN_WEEK; i++) {
                if(currentClass.getRepeats()[i] == 1) {
                    System.out.print(dayString(i+1) + " ");
                }
            }
            System.out.println();
            System.out.println();

            System.out.println("5. Remove this class");
            System.out.println("0. Return");
            System.out.println();
            
            prompt = "Enter a value to edit (0-5): ";    
            command = getCommand(prompt, 0, 5);
            
            if(command == 0) {
                return;
            } else if(command == 1) {
                System.out.println("\nEnter new location:\n");
                currentClass.setLocation(kb.nextLine());
            } else if(command == 2) {
                System.out.println("\nEnter start time in 24h format (e.g. 0830 = 8:30am):\n");
                currentClass.setStartTime(kb.nextInt());
            } else if(command == 3) {
                System.out.println("\nEnter end time in 24h format (e.g. 0830 = 8:30am):\n");
                currentClass.setEndTime(kb.nextInt());
            } else if(command == 4) {
                System.out.println("\nEnter days to repeat seperated by spaces (e.g. 1 3 5 = Monday Wednesday Friday):\n");
                tokenizer = new StringTokenizer(kb.nextLine());

                // Reset days to repeat class
                for(int i = 0; i < FileHandler.DAYS_IN_WEEK; i++) {
                    currentClass.getRepeats()[i] = 0;
                }

                // Set all chosen days to repeat the class
                while (tokenizer.hasMoreTokens()) {
                    currentClass.getRepeats()[Integer.parseInt(tokenizer.nextToken()) - 1] = 1;
                }
            } else {
                // Remove class from any associated objects
                currentPosition.getClasses().remove(currentClass);
                classData.remove(currentClass);

                System.out.println("Class removed.\n");

                return;
            }
        } 
    }

    private static void addPositionRequest() {
        Random rand = new Random();
        Scanner kb = new Scanner(System.in);
        String type;
        double wage;
        PositionRequest positionRequest;

        System.out.println("Requesting a New Position for Chosen Course");
        System.out.println("-----------------------------------\n");

        System.out.print("Enter the position's type (e.g. tutor): ");
        type = kb.nextLine();
        System.out.print("Enter the position's wage per hour (e.g. 25.00): ");
        wage = kb.nextDouble();

        positionRequest = new PositionRequest(currentCourse.getCourseCode() + "-" + rand.nextInt(MAX_ID),
                                              currentCourse.getCourseCode(), type, wage, currentCO, "Pending");

        // Add request for a new position to associated objects
        positionRequests.add(positionRequest);
        currentCO.getPositionRequests().add(positionRequest);

        System.out.println("\nSuccessfully created a request for a new position.\n");
    }

    private static boolean addApplicationApprovalRequest() {
        String prompt;
        ApplicationApprovalRequest newRequest = new ApplicationApprovalRequest(currentApplication.getPosition().getID(),
                                                currentApplication.getApplicationID(), "Pending", currentCO);

        prompt = "Are you sure you want to make a request to approve this application (0 for yes, 1 for no)? ";
        if(getCommand(prompt, 0, 1) == 1) {
            return false;
        }

        // Add request to approve an application to associated objects
        currentCO.getApplicationApprovalRequests().add(newRequest);
        applicationApprovalRequests.add(newRequest);

        System.out.println("Request for approving the application has been sent.");
        return true;
    }

    private static void submitApplication() {
        String prompt;
        Random rand = new Random();
        String applicationID = currentUser.getID() + rand.nextInt(MAX_ID);
        PositionApplication positionApplication = new PositionApplication(applicationID, dateFormat.format(date),
                                                  "Pending", "No Offers", currentUser, currentPosition);

        System.out.println("\nPosition Details");
        System.out.println("-----------------------------------\n");
        System.out.println("Position ID:\t" + currentPosition.getID());
        System.out.println("Course:\t" + currentPosition.getCourse().getCourseCode());
        System.out.println("Type:\t" + currentPosition.getType());
        System.out.println("Wage:\t$" + currentPosition.getWage() + "/hour.");
        System.out.println();

        prompt = "Are you sure you want to apply for this position (0 for yes, 1 for no)? ";
        if(getCommand(prompt, 0, 1) == 0) {
            // Add new application to associated objects
            currentPosition.getApplications().add(positionApplication);
            applicationData.add(positionApplication);
            currentUser.getApplications().add(positionApplication);

            System.out.println("Application sent.");
        }
        
    }

    private static void removeApplication() {
        ApplicationApprovalRequest applicationApprovalRequest;

        // Remove application from associated objects
        currentApplication.getPosition().getApplications().remove(currentApplication);
        applicationData.remove(currentApplication);
        currentUser.getApplications().remove(currentApplication);

        // Set position to unfilled
        currentApplication.getPosition().setFilled(false);

        // Remove any associated application approval requests
        for(int i = 0; i < currentApplication.getPosition().getCourse().getCoordinator().getApplicationApprovalRequests().size(); i++) {
            applicationApprovalRequest = currentApplication.getPosition().getCourse().getCoordinator().getApplicationApprovalRequests().get(i);
            if(applicationApprovalRequest.getApplicationID().equals(currentApplication.getApplicationID())) {
                currentApplication.getPosition().getCourse().getCoordinator().getApplicationApprovalRequests().remove(i);
                applicationApprovalRequests.remove(applicationApprovalRequest);
                i--;
            }
        }
    }

    private static void editOffer(boolean status) {
        // Do not allow to accept/decline offer if there is no offer
        if(!currentApplication.getOfferStatus().equals("Offer Received")) {
            System.out.println("No offers have been received yet.");
            return;
        } else if(status == true) {
            // set offer as accepted
            currentApplication.setOfferStatus("Accepted");
        } else {
            // set offer as declined
            currentApplication.setOfferStatus("Declined");

            // set position as not Filled
            currentApplication.getPosition().setFilled(false);
        }
    }
//---------------------------------------------------------------------------------------------------------------------

// Methods from this point are auxiliary methods used in the ones above-----------------------------------------------

    // Returns the number of requests for new positions from the currentCO
    private static int getNumPendingPositionRequests() {
        int result = 0;
        for(int i = 0; i < currentCO.getPositionRequests().size(); i++) {
            if(currentCO.getPositionRequests().get(i).getApprovalStatus().equals("Pending")) {
                result++;
            }
        }
        return result;
    }

    // Returns the number of requests to approve applications from the currentCO
    private static int getNumPendingApplicationApprovalRequests() {
        int result = 0;
        for(int i = 0; i < currentCO.getApplicationApprovalRequests().size(); i++) {
            if(currentCO.getApplicationApprovalRequests().get(i).getApprovalStatus().equals("Pending")) {
                result++;
            }
        }
        return result;
    }

    // Returns 24 hour time as a string with leading 0s (e.g. 8:30am = 0830)
    private static String formatTime(int time) {
        String result = Integer.toString(time);
        int timeLength = result.length();
        
        for(int i = 0; i < 4 - timeLength; i++) {
            result = "0" + result;
        }

        return result;
    }
    
    // Returns th 24 hours time??
    private static int getTime(String prompt) {
        int time = -1, hour = -1, minute = -1;

        if(hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            time = getCommand(prompt, 0, 2359);
            hour = (int)(time / 100);
            minute = time % 100;
        }
        return time;
    }

    // Returns the chosen day of the week as a string.
    private static String dayString(int dayNumber) {
        if(dayNumber == 1) {
            return "Monday";
        } else if(dayNumber == 2) {
            return "Tuesday";
        } else if(dayNumber == 3) {
            return "Wednesday";
        } else if(dayNumber == 4) {
            return "Thursday";
        } else if(dayNumber == 5) {
            return "Friday";
        } else if(dayNumber == 6) {
            return "Saturday";
        } else if(dayNumber == 7) {
            return "Sunday";
        } else {
            return null;
        }
    }

    // Goes through staffData to find the input username and makes currentUser
    // refer to that user if they exist and input the correct password.
    // Returns true if successful, otherwise returns false.
    private static boolean getCurrentUser(String username, String password) {
        for(int i = 0; i < staffData.size(); i++) {
            if(staffData.get(i).getUsername().equals(username)) {
                if(staffData.get(i).getPassword().equals(password)) {
                    currentUser = staffData.get(i);
                    return true;
                }
                break;
            }
        }
        return false;
    }

    // Checks if the input can be converted to an integer and returns it, or
    // -1 if it is not a valid input
    public static int validInput(String input) {
        int command = 0;
        
        try {
            command = Integer.parseInt(input);
        }
        catch(NumberFormatException e) {
            return -1;
        }
        
        return command;
    }
    
    // Keeps prompting user for input until a valid input is received
    private static int getCommand(String prompt, int minInputValue, int maxInputValue) {
        Scanner kb = new Scanner(System.in);
        int command;
        
        while(true) {
            System.out.println(prompt);
            command = validInput(kb.nextLine());
             
            // Cheack if they chose a valid option
            if(command >= minInputValue && command <= maxInputValue) {
                return command;
            }
        }
    }

    // Given an availability array, this prints out the times that the user
    // is available to the screen
    private static void printAvailability(boolean[][] availability) {
        int startTime = -1, endTime = -1;
        
        System.out.println("Availability");
        System.out.println("-----------------------------------\n");
        System.out.println("(Days 1-7 represent Monday - Sunday. Times are in HHMM format.)\n");

        for(int i = 0; i < FileHandler.DAYS_IN_WEEK; i++) {
            System.out.print("Day " + (i + 1) + ":");
            for(int j = 0; j < FileHandler.HOURS_IN_DAY * 4; j++) {
                if(availability[i][j] == true) {
                    if(startTime == -1) {
                        startTime = (j/4)*100 + (j%4)*15;
                    } else if(j == FileHandler.HOURS_IN_DAY * 4 && startTime != -1) {
                        endTime = (j/4)*100 + (j%4)*15;
                        System.out.print(" | " + formatTime(startTime) + " - " + formatTime(endTime));
                        startTime = -1;
                    }
                } else if(startTime != -1){
                    endTime = (j/4)*100 + (j%4)*15;
                    System.out.print(" | " + formatTime(startTime) + " - " + formatTime(endTime));
                    startTime = -1;
                }
            }
            startTime = -1;
            System.out.println();
        }
        System.out.println();
    }

    // Uses the current application approval request and finds the corresponding application
    // Returns true if application if found, otherwise false
    private static boolean findApplication() {
        CourseCoordinator coordinator = currentApplicationApprovalRequest.getCoordinator();
        Position position;
        PositionApplication application;

        // Go through all the coordinator's courses...
        for(int i = 0; i < coordinator.getCourses().size(); i++) {
            // ... go through all of the positions in the courses ...
            for(int j = 0; j < coordinator.getCourses().get(i).getPositions().size(); j++) {
                // ... go through all of the applications for the positions ...
                position = coordinator.getCourses().get(i).getPositions().get(j);
                for(int k = 0; k < position.getApplications().size(); k++) {
                    // ... check if there is an application that matches the request's application
                    application = position.getApplications().get(k);
                    if(application.getApplicationID().equals(currentApplicationApprovalRequest.getApplicationID()))    {
                        currentApplication = application;
                        return true;
                    }
                }
            }
        }
        return false;
    }

//---------------------------------------------------------------------------------------------------------------------
}
