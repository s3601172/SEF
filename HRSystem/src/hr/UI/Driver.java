package hr.UI;
import java.io.*;
import java.util.*;
import hr.staff.*;

public class Driver {
    private static final String USER_DATA_FILE = "UserData.txt";

    private static Staff currentUser;
	
	public static void main(String args[]) {
        
        System.out.println("Diagnostic message: The only user so far is s123456, password is letmein\n");

        while(true) {
        	login();

            while(mainMenu()) {
            	System.out.println();
            }
            System.out.println("Successfully logged out.\n");
            
            writeStaffData();
        }
	}
	
	private static boolean mainMenu() {
		int command;
		boolean taskStatus;
		String prompt;

		while(true) {
			taskStatus = true;
			
			// Show the main menu
			System.out.println("HR System");
			System.out.println("-----------------------------------\n");

			System.out.println("1. View/Edit personal info.\n");
			System.out.println("2. View current applications.\n");
			System.out.println("3. View/Apply for available positions.\n");
			System.out.println("4. View timetable.\n");
			System.out.println("5. Edit time availabiliy.\n");
			System.out.println("6. Logout.\n");
			
			prompt = "Please select an action: ";
			command = getCommand(prompt, 1, 6);
			
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
					System.out.println();
					
					prompt = "Select a value to edit or select 7 to return to the main menu: ";	
					command = getCommand(prompt, 1, 7);
	    			
					if(command == 7) {
						taskStatus = false;
					} else {
						editPersonalInfo(command);
					}
				}
			} else if (command == 2) {
				System.out.println("Under construction... (Requires Position Application)\n");
			} else if (command == 3) {
				System.out.println("Under construction... (Requires Position Application)\n");
			} else if (command == 4) {
				System.out.println("Under construction... (Requires Roster + Classes)\n");
			} else if(command == 5) {
				
			} else if (command == 6) {
				return false;
			}
		}
	}

	private static void login() {
		boolean loginStatus = false;
        String username, password;
        Scanner kb = new Scanner(System.in);

        System.out.println("Login");
		System.out.println("-----------------------------------\n");

        // Keep getting user's credentials until a match is found in the data
        while(!loginStatus) {
        	System.out.println("Username: ");
        	username = kb.nextLine();

        	System.out.println("Password: ");
        	password = kb.nextLine();
        
        	// Check if user credentials exists and is correct
        	if(getStaffData(username, password)) {
        		System.out.println("Successfully logged in.");	
        		loginStatus = true;
        	} else {
        		System.out.println("Invalid credentials, please try again.");	
        	}
        }
	}
	
    private static void writeStaffData() {
        String ID, phone, email, username, password;
        String firstName, lastName;
        String staffType;
    	Scanner inputFile = null;
        PrintWriter outputFile = null;
        
        // Open user data file and an temporary output file
        try {
            inputFile = new Scanner(new FileInputStream(USER_DATA_FILE));
            outputFile = new PrintWriter(new FileWriter(USER_DATA_FILE + "_temp"));
        }
        catch (Exception e) {
            System.exit(0);
        }
        
        // Read in and write all previous data except for the current user's 
        // data to a temporary file
        while(inputFile.hasNextLine()) {
        	staffType = inputFile.nextLine();
        	ID = inputFile.nextLine();
        	username = inputFile.nextLine();
            password = inputFile.nextLine();
        	email = inputFile.nextLine();
        	phone = inputFile.nextLine();
        	firstName = inputFile.nextLine();
        	lastName = inputFile.nextLine();
        	inputFile.nextLine();

        	if(!ID.equals(currentUser.getID())) {
        		// Write out data to temporary file
        		outputFile.println(staffType);
        		outputFile.println(ID);
        		outputFile.println(username);
        		outputFile.println(password);
        		outputFile.println(email);
        		outputFile.println(phone);
        		outputFile.println(firstName);
        		outputFile.println(lastName);
        		outputFile.println();
        	}
        }
        
        inputFile.close();

        // Writes out new current user's data
        if(currentUser instanceof Admin) {
    		outputFile.println("admin");	
    	} else if(currentUser instanceof CourseCoordinator) {
    		outputFile.println("courseco");	
    	} else {
    		outputFile.println("staff");	
    	}
        outputFile.println(currentUser.getID());
        outputFile.println(currentUser.getUsername());
        outputFile.println(currentUser.getPassword());
        outputFile.println(currentUser.getEmail());
        outputFile.println(currentUser.getPhone());
        outputFile.println(currentUser.getFirstName());
        outputFile.println(currentUser.getLastName());
        outputFile.println();
        
        outputFile.close();
        
        // Remove old data file and rename the temporary file
        File original = new File(USER_DATA_FILE);
        File updated = new File(USER_DATA_FILE + "_temp");
        if(original.exists()) {
        	original.delete();
        }
        updated.renameTo(original);
    }

    private static boolean getStaffData(String inputUser, String inputPassword) {
        String ID, phone, email, username, password;
        String firstName, lastName;
        String staffType;
        boolean[] temp = new boolean[0];
        Scanner userData = null;

        // Open the user data file
        try {
            userData = new Scanner(new FileInputStream(USER_DATA_FILE));
        }
        catch (Exception e) {
            System.out.println("Could not open " + USER_DATA_FILE);
            System.exit(0);
        }

        // Keep reading in data until the current user's data is found
        while(userData.hasNextLine()) {
            staffType = userData.nextLine();
            ID = userData.nextLine();
            username = userData.nextLine();
            password = userData.nextLine();
            email = userData.nextLine();
            phone = userData.nextLine();
            firstName = userData.nextLine();
            lastName = userData.nextLine();
            userData.nextLine();
          
            // Check if this is the user's data.
            if(username.equals(inputUser) && password.equals(inputPassword)) {
            	if(staffType.equals("staff")) {
            		currentUser = new Staff(ID, username, password, phone, firstName, lastName, email, temp);	
            	} else if(staffType.equals("admin")) {
            		currentUser = new Admin(ID, username, password, phone, firstName, lastName, email, temp);	
            	} else if(staffType.equals("courseco")) {
            		currentUser = new CourseCoordinator(ID, username, password, phone, firstName, lastName, email, temp);
            	}
            	currentUser = new Staff(ID, username, password, phone, firstName, lastName, email, temp);
            	userData.close();
            	return true;
            }
        }
        
        // User's data was not found
        userData.close();
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
}
