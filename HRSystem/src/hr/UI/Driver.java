package hr.UI;
import java.util.*;

import hr.staff.*;
import hr.data.*;
import hr.school.*;

public class Driver {
    private static ArrayList<Staff> staffData = new ArrayList<Staff>();
    private static ArrayList<Course> courseData = new ArrayList<Course>();
    private static Staff currentUser;
    private static ArrayList<Course> coordinatorCourses;
    private static Course currentCourse;
    private static ArrayList<Position> positionData = new ArrayList<Position>();
    private static ArrayList<Position> availablePositions;
    private static Position currentPosition;
    private static ArrayList<PositionApplication> applicationData = new ArrayList<PositionApplication>();
    private static PositionApplication currentApplication;
    private static ArrayList<PositionClass> classData = new ArrayList<PositionClass>();
    private static ArrayList<PositionClass> currentClasses;
    private static PositionClass currentClass;
    private static ArrayList<PositionRequest> positionRequests = new ArrayList<PositionRequest>();
    private static PositionRequest currentPositionRequest;
    
    public static void main(String args[]) {
        
        System.out.println("Current users (All passwords = letmein):");
        System.out.println("\tStaff: s123456\n");
        System.out.println("\tCourse Coordinator: e000001\n");
        System.out.println("\tStaff: a000000\n");

        while(true) {
            staffData.clear();
            courseData.clear();
            positionData.clear();
            applicationData.clear();
            classData.clear();
            positionRequests.clear();
            FileHandler.readStaffData(staffData);
            FileHandler.readCourseData(courseData, staffData);
            FileHandler.readPositionData(positionData, courseData);
            FileHandler.readApplicationData(applicationData, positionData, staffData);
            FileHandler.readClassData(classData, positionData);
            FileHandler.readPositionRequestData(positionRequests, staffData);

            login();

            while(mainMenu()) {
                System.out.println();
            }
            System.out.println("Successfully logged out.\n");
            
            FileHandler.writeStaffData(staffData);
            FileHandler.writeCourseData(courseData);
            FileHandler.writePositionData(positionData);
            FileHandler.writeApplicationData(applicationData);
            FileHandler.writeClassData(classData);
            FileHandler.writePositionRequestData(positionRequests);
        }
    }
    
    private static boolean mainMenu() {
        int command, subCommand;
        boolean taskStatus;
        String prompt;

        // Should be done once till user logs out
        currentUser.getApplications().addAll(applicationData);
        
        while(true) {
            taskStatus = true;
            
            // Show the main menu
            System.out.println("HR System");
            System.out.println("-----------------------------------\n");

            System.out.println("1. View/Edit personal info.");
            System.out.println("2. View current applications.");
            System.out.println("3. View/Apply for available positions.");
            System.out.println("4. View current classes.");
            System.out.println("5. View/Edit time availability.");
            
            if(currentUser instanceof CourseCoordinator) {
                System.out.println("6. View current requests.");
                System.out.println("7. Request a new position.");
                System.out.println("8. Add a new class.");
                System.out.println("9. Edit an existing class.");
            } else if(currentUser instanceof Admin) {
                System.out.println("6. Add a new position.");
                System.out.println("7. Remove a position.");
                System.out.println("8. View and edit requests.");
            }

            System.out.println("\n0. Logout.\n");
            
            if(currentUser instanceof CourseCoordinator) {
                prompt = "Please enter an option (0-9): ";
                command = getCommand(prompt, 0, 9);
            } else if (currentUser instanceof Admin) {
                prompt = "Please enter an option (0-8): ";
                command = getCommand(prompt, 0, 8);
            } else {
                prompt = "Please enter an option (0-5): ";
                command = getCommand(prompt, 0, 5);
            }
            
            if(command == 1) {
                // Run the edit personal info menu
                while(taskStatus) {
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
                        taskStatus = false;
                    } else {
                        editPersonalInfo(command);
                    }
                }
            } else if (command == 2) {
            	
                // Display all the applications of the current staff member
                while(taskStatus) {
                    System.out.println("Current Applications");
                    System.out.println("-----------------------------------\n");

                    for(int i = 0; i < currentUser.getApplications().size(); i++) {
                        currentApplication = currentUser.getApplications().get(i);
                        System.out.print((i+1) + ". " + currentApplication.getPosition().getID() + " ");
                        System.out.print(currentApplication.getDateSubmitted() + " ");
                        System.out.println(currentApplication.getApprovalStatus());
                    }

                    System.out.println("\n0. Return to main menu");
                    System.out.println();
                    
                    prompt = "Enter an option (0-0): ";    
                    command = getCommand(prompt, 0, 0);
                    
                    if(command == 0) {
                        taskStatus = false;
                    }
                }
            } else if (command == 3) {
                // Display all available positions
            	
            	// Fetch all available positions only once
                availablePositions = new ArrayList<Position>();
                for(int i = 0; i < positionData.size(); i++) {
                    if(!positionData.get(i).isFilled()) {
                        availablePositions.add(positionData.get(i));
                    }
                }
            	
                while(taskStatus) {
                    System.out.println("Available Positions");
                    System.out.println("-----------------------------------\n");                    

                    // Display all available positions
                    for(int i = 0; i < availablePositions.size(); i++) {
                        currentPosition = availablePositions.get(i);
                        System.out.print((i+1) + ". " + currentPosition.getID() + " ");
                        System.out.print(currentPosition.getType() + " $");
                        System.out.println(currentPosition.getWage() + "/hour.");
                    }

                    System.out.println("\n0. Return to main menu");
                    System.out.println();
                    
                    prompt = "Enter an option (0-" + availablePositions.size() +"): ";    
                    subCommand = getCommand(prompt, 0, availablePositions.size());
                    
                    if(subCommand == 0) {
                        taskStatus = false;
                    } else if(subCommand <= availablePositions.size()){
                        applyForAvailablePosition(availablePositions.get(subCommand - 1));
                    }
                }
            } else if (command == 4) {
                while(taskStatus) {
                    System.out.println("Current Classes");
                    System.out.println("-----------------------------------\n");

                    // Find all approved applications and corresponding positions for current user
                    for(int i = 0; i < currentUser.getApplications().size(); i++) {
                        currentApplication = currentUser.getApplications().get(i);
                        if(currentApplication.getApprovalStatus().equals("Approved")) {
                            currentPosition = currentApplication.getPosition();
                            currentClasses = currentPosition.getClasses();

                            // Print out details of every class of every position
                            System.out.println("Position: " + currentPosition.getID());
                            for(int j = 0; j < currentClasses.size(); j++) {
                                currentClass = currentClasses.get(j);
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
                    subCommand = getCommand(prompt, 0, 0);
                    
                    if(subCommand == 0) {
                        taskStatus = false;
                    }
                }
            } else if(command == 5) {
                // Run the availability program
                while(taskStatus) {
                    System.out.println("Availability");
                    System.out.println("-----------------------------------\n");
                    System.out.println("1. View current availability");
                    System.out.println("2. Edit availability");
                    System.out.println("\n0. Return to main menu");
                    System.out.println();
                    
                    prompt = "Enter an option (0-2): ";    
                    command = getCommand(prompt, 0, 2);
                    
                    if(command == 0) {
                        taskStatus = false;
                    } else if(command == 2){
                        editAvailability(command, currentUser.getAvailability());
                    } else {
                        printAvailability(currentUser.getAvailability());
                    }
                }
            } else if (command == 0) {
                return false;
            } else if(currentUser instanceof CourseCoordinator) {
            	if(command == 6) {
            	    // Display all current requests	
                    while(taskStatus) {
                        for(int i = 0; i < ((CourseCoordinator) currentUser).getPositionRequests().size(); i++) {
                            currentPositionRequest = ((CourseCoordinator) currentUser).getPositionRequests().get(i); 
                            System.out.println("Course Code:\t" + currentPositionRequest.getCourseCode()); 
                            System.out.println("Type:\t" + currentPositionRequest.getType()); 
                            System.out.println("Wage:\t$" + currentPositionRequest.getWage() + "/hour"); 
                            System.out.println("Approval Status:\t" + currentPositionRequest.getApprovalStatus()); 
                            System.out.println();
                        }

                        System.out.println("\n0. Return to main menu");
                        System.out.println();
                        
                        prompt = "Enter an option (0-0): ";    
                        subCommand = getCommand(prompt, 0, 0);
                        
                        if(subCommand == 0) {
                            taskStatus = false;
                        }
                    }
            	} else if(command == 7 || command == 8 || command == 9) {
                    // Display available courses
                    while(taskStatus) {
                        System.out.println("Please select a course:\n");
                        coordinatorCourses = ((CourseCoordinator) currentUser).getCourses();
                        for(int i = 1; i <= coordinatorCourses.size(); i++) {
                            currentCourse = coordinatorCourses.get(i-1);
                            System.out.print(i + ". " + currentCourse.getCourseCode() + " ");
                            System.out.print(currentCourse.getYearRun());
                            System.out.println(" S" + currentCourse.getSemesterRun());
                        }
                        System.out.println("\n0. Return to main menu\n");

                        prompt = "Enter an option (0-" + coordinatorCourses.size() +"): ";    
                        subCommand = getCommand(prompt, 0, coordinatorCourses.size());
                        
                        if(subCommand == 0) {
                            taskStatus = false;
                        } else if(subCommand <= coordinatorCourses.size()){
                            if(command == 6) {
                                requestPosition();
                            } else if(command == 7) {
                                addClass();
                            } else if(command == 8) {
                                removeClass();
                            }
                        }
                    }
                }
            } else if(currentUser instanceof Admin) {
                if(command == 6 || command == 7) {
                    // Display available courses
                    while(taskStatus) {
                        System.out.println("Please select a course:\n");
                        for(int i = 1; i <= courseData.size(); i++) {
                            currentCourse = courseData.get(i-1);
                            System.out.print(i + ". " + currentCourse.getCourseCode() + " ");
                            System.out.print(currentCourse.getYearRun());
                            System.out.println(" S" + currentCourse.getSemesterRun());
                        }
                        System.out.println("\n0. Return to main menu\n");

                        prompt = "Enter an option (0-" + courseData.size() +"): ";    
                        subCommand = getCommand(prompt, 0, courseData.size());
                        
                        if(subCommand == 0) {
                            taskStatus = false;
                        } else if(subCommand <= coordinatorCourses.size()){
                            if(command == 6) {
                                addPosition();
                            } else if(command == 7) {
                                removePosition();
                            }
                        }
                    }
                } else if(command == 8) {
                    // View and edit requests
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
            
            if(command >= minInputValue && command <= maxInputValue) {
                return command;
            }
        }
    }

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
            currentUser.setPassword(kb.next());
        } else if (command == 3) {
            currentUser.setFirstName(kb.next());
        } else if (command == 4) {
            currentUser.setLastName(kb.next());
        } else if(command == 5) {
            currentUser.setPhone(kb.next());
        } else if (command == 6) {
            currentUser.setEmail(kb.next());
        }
        
        System.out.println("Value successfully changed.\n\n");
    }
    
    private static void editAvailability(int command, boolean[][] availability) {
        Scanner kb = new Scanner(System.in);
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
                    for(int i = startTimePosition; i <= endTimePosition; i++) {
                        availability[day - 1][i] = setAvailable;
                    }
                }
            }
        }
    }
    
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
    
    // Returns 24 hour time as a string with leading 0s
    private static String formatTime(int time) {
        String result = Integer.toString(time);
        int timeLength = result.length();
        
        for(int i = 0; i < 4 - timeLength; i++) {
            result = "0" + result;
        }

        return result;
    }
    
    private static int getTime(String prompt) {
        Scanner kb = new Scanner(System.in);
        int time = -1, hour = -1, minute = -1;

        if(hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            time = getCommand(prompt, 0, 2359);
            hour = (int)(time / 100);
            minute = time % 100;
        }
        return time;
    }

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

    private static void requestPosition() {
        System.out.println("To be implemented...");
    }

    private static void addPosition() {
        System.out.println("To be implemented...");
    }

    private static void removePosition() {
        System.out.println("To be implemented...");
    }

    private static void addClass() {
        System.out.println("To be implemented...");
    }

    private static void removeClass() {
        System.out.println("To be implemented...");
    }

    private static void applyForAvailablePosition(Position position) {
        
        CourseCoordinator courseCoordinator = null;
        for (Staff s : staffData) {
        	if (s instanceof CourseCoordinator) {
        		courseCoordinator = (CourseCoordinator) s;
        		break;
        	}
        }
        
        //PositionRequest positionRequest = new PositionRequest(position.getCourse().getCourseCode(), 
        //		position.getType(), position.getWage(), courseCoordinator, "Pending");
        
        PositionApplication positionApplication = new PositionApplication("05/05/2018", "Pending", currentUser, position);
        
        //courseCoordinator.addPositionRequest(positionRequest);
        
        courseCoordinator.getApplications().add(positionApplication);
        applicationData.add(positionApplication);
        
        availablePositions.remove(position);
        
        System.out.println("Applied, awaiting approval from Course coordinator.\n");
    }
}
