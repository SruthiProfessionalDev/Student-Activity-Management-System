import java.io.*;
import java.util.*;

public class Main {
    // Constants for maximum students and arrays to store student data
    private static final int MAX_STUDENTS = 100;
    private static final Student[] students = new Student[MAX_STUDENTS];
    // Counter for total number of students
    private static int totalStudents = 0;
    // To track if the file has been loaded already
    private static boolean isDataLoaded = false;

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        String userInput;

        // Main program loop
        do {
            // Display menu options
            showMenu();
            // Reads the next line removing the leading whitespaces
            userInput = inputScanner.nextLine().trim();

            // Validate user input
            if (!isValidInput(userInput)) {
                System.out.println("Invalid input. Please enter a valid option.");
                continue; // Skip to next iteration of the loop
            }

            // Convert input to integer
            int choice = Integer.parseInt(userInput);

            // Perform actions based on user choice
            if (choice == 1) {
                displayAvailableSeats();
            } else if (choice == 2) {
                enrollStudent(inputScanner);
            } else if (choice == 3) {
                removeStudent(inputScanner);
            } else if (choice == 4) {
                searchStudent(inputScanner);
            } else if (choice == 5) {
                saveStudentData();
            } else if (choice == 6) {
                loadStudentData();
            } else if (choice == 7) {
                listStudentsByNameTable();
            } else if (choice == 8) {
                manageStudentsModulesOptions(inputScanner);
            } else if (choice == 0) {
                System.out.println("Exiting...");
            } else {
                System.out.println("Invalid option, please try again.");
            }
        } while (!userInput.equals("0")); // Continue loop until user chooses to exit

        // Close the input scanner
        inputScanner.close();
    }

    // Method to display menu options
    private static void showMenu() {
        System.out.println("\n1. Check available seats");
        System.out.println("2. Register student (with ID)");
        System.out.println("3. Delete student");
        System.out.println("4. Find student (with student ID)");
        System.out.println("5. Store student details into a file");
        System.out.println("6. Load student details from the file to the system");
        System.out.println("7. View the list of students based on their names (sorted)");
        System.out.println("8. Manage students' modules options");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    // Method to display available seats
    public static void displayAvailableSeats() {
        System.out.println("Available seats: " + (MAX_STUDENTS - totalStudents));
        if (totalStudents == 100) { // prevents a message that capacity is reached
            System.out.println("Maximum Student capacity reached.");
        }
    }

    // Method to enroll a new student
    public static void enrollStudent(Scanner inputScanner) {
        // prevents user from entering data  more than max amount
        if (totalStudents == 100) {
            System.out.println("Maximum Student capacity reached.");
            return;
        }

        // Check if there are available seats
        if (totalStudents < MAX_STUDENTS) {
            String studentId;
            // Prompt for a valid student ID
            do {
                System.out.print("Enter student ID (must start with 'w' and have 7 digits): ");
                studentId = inputScanner.nextLine().trim();
                // Validate student ID format
                if (!isValidStudentIdFormat(studentId)) {
                    System.out.println("Invalid ID format.");
                } else if (findStudentById(studentId) != null) {
                    System.out.println("This ID already exists.");
                }
            } while (!isValidStudentIdFormat(studentId) || findStudentById(studentId) != null);

            String studentName;
            // Prompt for a valid student name
            while (true) {
                System.out.print("Enter student name: ");
                studentName = inputScanner.nextLine().trim();
                // Validate student name format
                if (isAlphabeticString(studentName)) {
                    studentName = capitalizeName(studentName);
                    // Create a new Student object and add to the array
                    Student newStudent = new Student(studentId, studentName);
                    students[totalStudents] = newStudent;
                    totalStudents++;
                    System.out.println("Student registered successfully.");

                    // Provide warnings based on available seats
                    if (totalStudents == MAX_STUDENTS) {
                        System.out.println("Maximum capacity reached (100 students).");
                    } else if (totalStudents >= MAX_STUDENTS - 5) {
                        System.out.println("Warning: Only " + (MAX_STUDENTS - totalStudents) + " seats remaining.");
                    }
                    break;
                } else {
                    System.out.println("Invalid name format.");
                }
            }
        } else {
            System.out.println("Maximum capacity reached (100 students).");
        }
    }

    // Method to remove a student by ID
    public static void removeStudent(Scanner inputScanner) {
        // Check if there are students to delete
        if (totalStudents == 0) {
            System.out.println("No entries to delete.");
            return;
        }

        // Prompt for student ID to delete
        System.out.print("Enter student ID to delete: ");
        String studentId = inputScanner.nextLine().trim();

        // Find the student by ID
        Student studentToDelete = findStudentById(studentId);

        // Check if student exists
        if (studentToDelete == null) {
            System.out.println("Student ID not found.");
        } else {
            // Remove the student from the array
            for (int i = 0; i < totalStudents; i++) {
                if (students[i].getStudentId().equals(studentId)) {
                    students[i] = students[totalStudents - 1]; // Move the last student to the current position
                    totalStudents--; // Decrease the total count of students
                    System.out.println("Student deleted successfully.");
                    break;
                }
            }
        }
    }

    // Method to search for a student by ID
    public static void searchStudent(Scanner inputScanner) {
        // Check if there are students to search
        if (totalStudents == 0) {
            System.out.println("No entries to search.");
            return;
        }

        // Prompt for student ID to find
        System.out.print("Enter student ID to find: ");
        String studentId = inputScanner.nextLine().trim();

        // Find the student by ID
        Student foundStudent = findStudentById(studentId);

        // Display student details if found
        if (foundStudent == null) {
            System.out.println("Student ID not found.");
        } else {
            System.out.println("Student Name: " + foundStudent.getStudentName());
            System.out.println("Student ID: " + foundStudent.getStudentId());
        }
    }

    // Method to save student data to a file
    public static void saveStudentData() {
        // Check if there are students to save
        if (totalStudents == 0) {
            System.out.println("No entries to save.");
            return;
        } else if (totalStudents == 100) { // prevents user from entering data more than max amount
            System.out.println("Maximum Student capacity reached.");
            return;
        }

        // Save student details to a text file
        try (PrintWriter writer = new PrintWriter(new FileWriter("students.txt"))) {
            for (int i = 0; i < totalStudents; i++) {
                writer.println(students[i].getStudentId() + "," + students[i].getStudentName() + "," +
                        students[i].getModule1Marks() + "," + students[i].getModule2Marks() + "," + students[i].getModule3Marks());
            }
            System.out.println("Student details saved successfully to students.txt.");
        } catch (IOException e) {
            System.out.println("Error saving student details: " + e.getMessage());
        }
    }

    // Method to load student data from a file
    public static void loadStudentData() {
        // Check if data has already been loaded
        if (isDataLoaded) {
            System.out.println("Student data has already been loaded.");
            return;
        }

        // Prevent user from entering data more than max amount
        if (totalStudents == MAX_STUDENTS) {
            System.out.println("Maximum student capacity reached.");
            return;
        }

        // Load student details from a text file
        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            totalStudents = 0;
            int lineNumber = 0;

            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length == 5) {
                    // Extract student details from each line
                    String studentId = parts[0];
                    String studentName = parts[1].trim();
                    int module1Marks, module2Marks, module3Marks;
                    try {
                        module1Marks = Integer.parseInt(parts[2].trim());
                        module2Marks = Integer.parseInt(parts[3].trim());
                        module3Marks = Integer.parseInt(parts[4].trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing module marks on line " + lineNumber + ": " + line);
                        continue;
                    }

                    // Validate and create a new Student object
                    if (isValidStudentIdFormat(studentId) && isAlphabeticString(studentName)) {
                        Student loadedStudent = new Student(studentId, capitalizeName(studentName));
                        loadedStudent.setModuleMarks(module1Marks, module2Marks, module3Marks);
                        students[totalStudents] = loadedStudent;
                        totalStudents++;

                        // Stop loading if maximum capacity is reached
                        if (totalStudents == MAX_STUDENTS) {
                            System.out.println("Maximum student capacity reached. Stopping further loading.");
                            break;
                        }
                    } else {
                        System.out.println("Invalid data format on line " + lineNumber + ": " + line);
                    }
                } else {
                    System.out.println("Invalid data format on line " + lineNumber + ": " + line);
                }
            }
            System.out.println("Student details loaded successfully from students.txt.");

            // Provide warning if maximum capacity is reached
            if (totalStudents >= MAX_STUDENTS) {
                System.out.println("Maximum capacity reached (100 students).");
            } else if (totalStudents >= MAX_STUDENTS - 5) {
                System.out.println("Warning: Only " + (MAX_STUDENTS - totalStudents) + " seats remaining.");
            }

            // Set the tag to true after loading data
            isDataLoaded = true;
        } catch (IOException e) {
            System.out.println("Error loading student details: " + e.getMessage());
        }
    }

    // Method to list students sorted by name
    public static void listStudentsByNameTable() {
        // Check if there are students to list
        if (totalStudents == 0) {
            System.out.println("No students available.");
            return;
        }

        // Sort students by name and display
        Student[] sortedStudents = Arrays.copyOf(students, totalStudents);
        Arrays.sort(sortedStudents, Comparator.comparing(Student::getStudentName));

        // Display table header
        System.out.println("+-----------------------+-------------+");
        System.out.printf("| %-21s | %-11s |\n", "Student Name", "Student ID");
        System.out.println("+-----------------------+-------------+");

        // Display table rows
        for (Student student : sortedStudents) {
            System.out.printf("| %-21s | %-11s |\n", student.getStudentName(), student.getStudentId());
        }

        // Display table footer
        System.out.println("+-----------------------+-------------+");
    }

    // Method to manage students' module options (update name, add module marks)
    public static void manageStudentsModulesOptions(Scanner inputScanner) {
        // Check if there are students to manage
        if (totalStudents == 0) {
            System.out.println("No entries to manage.");
            return;
        }

        // Manage options for the selected student
        while (true) {
            System.out.println("\na. Update student name");
            System.out.println("b. Add student module marks");
            System.out.println("c. Generate a summary of the system");
            System.out.println("d. Generate complete report");
            System.out.println("0. Return to main menu");
            System.out.print("Enter your choice: ");
            String choice = inputScanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "a":
                    updateStudentName(inputScanner);
                    break;
                case "b":
                    addStudentModuleMarks(inputScanner);
                    break;
                case "c":
                    generateSummary();
                    break;
                case "d":
                    generateCompleteReport();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }
    }

    public static void updateStudentName(Scanner inputScanner) {
        // Prompt for student ID to find
        System.out.print("Enter student ID to find: ");
        String studentId = inputScanner.nextLine().trim();

        // Find the student by ID
        Student foundStudent = findStudentById(studentId);

        // Check if student found
        if (foundStudent == null) {
            System.out.println("Student ID not found.");
            return;
        }

        // Display current name
        System.out.println("Current name: " + foundStudent.getStudentName());

        // Prompt for new name
        System.out.print("Enter new name: ");
        String newName = inputScanner.nextLine().trim();

        // Validate and update the name
        if (isAlphabeticString(newName)) {
            foundStudent.setStudentName(capitalizeName(newName));
            System.out.println("Name updated successfully.");
        } else {
            System.out.println("Invalid name format.");
        }
    }

    // Method to add module marks for a student
    public static void addStudentModuleMarks(Scanner inputScanner) {
        // Prompt for student ID to find
        System.out.print("Enter student ID to find: ");
        String studentId = inputScanner.nextLine().trim();

        // Find the student by ID
        Student foundStudent = findStudentById(studentId);

        // Check if student exists
        if (foundStudent == null) {
            System.out.println("Student ID not found.");
            return;
        }

        // Display current module marks
        System.out.println("Current Module Marks:");
        foundStudent.displayModuleMarks();

        // Prompt for module 1 marks
        int module1Marks;
        do {
            System.out.print("Enter module 1 marks: ");
            String moduleMark = inputScanner.nextLine().trim();
            if (isNonNumeric(moduleMark)) {
                System.out.println("Invalid input. Please enter a valid integer.");
                continue;
            }
            module1Marks = Integer.parseInt(moduleMark);
            if (module1Marks < 0 || module1Marks > 100) {
                System.out.println("Module marks should be between 0 and 100.");
            } else {
                break;
            }
        } while (true);

        // Prompt for module 2 marks
        int module2Marks;
        do {
            System.out.print("Enter module 2 marks: ");
            String moduleMark = inputScanner.nextLine().trim();
            if (isNonNumeric(moduleMark)) {
                System.out.println("Invalid input. Please enter a valid integer.");
                continue;
            }
            module2Marks = Integer.parseInt(moduleMark);
            if (module2Marks < 0 || module2Marks > 100) {
                System.out.println("Module marks should be between 0 and 100.");
            } else {
                break;
            }
        } while (true);

        // Prompt for module 3 marks
        int module3Marks;
        do {
            System.out.print("Enter module 3 marks: ");
            String moduleMark = inputScanner.nextLine().trim();
            if (isNonNumeric(moduleMark)) {
                System.out.println("Invalid input. Please enter a valid integer.");
                continue;
            }
            module3Marks = Integer.parseInt(moduleMark);
            if (module3Marks < 0 || module3Marks > 100) {
                System.out.println("Module marks should be between 0 and 100.");
            } else {
                break;
            }
        } while (true);

        // Update student module marks
        foundStudent.setModuleMarks(module1Marks, module2Marks, module3Marks);
        System.out.println("Module marks added successfully.");
    }

    // Method to generate a summary of the student data
    private static void generateSummary() {
        System.out.println("\nSystem Summary:");
        System.out.println("Total student registrations: " + totalStudents);

        // Variables to track the number of students scoring above 40 in each module
        int studentsScoringAbove40InModule1 = 0;
        int studentsScoringAbove40InModule2 = 0;
        int studentsScoringAbove40InModule3 = 0;

        // Loop through all registered students and count those scoring above 40 in each module
        for (int i = 0; i < totalStudents; i++) {
            if (students[i].getModule1Marks() > 40) {
                studentsScoringAbove40InModule1++;
            }
            if (students[i].getModule2Marks() > 40) {
                studentsScoringAbove40InModule2++;
            }
            if (students[i].getModule3Marks() > 40) {
                studentsScoringAbove40InModule3++;
            }
        }

        // Print the number of students scoring above 40 in each module
        System.out.println("Total students scoring more than 40 in Module 1: " + studentsScoringAbove40InModule1);
        System.out.println("Total students scoring more than 40 in Module 2: " + studentsScoringAbove40InModule2);
        System.out.println("Total students scoring more than 40 in Module 3: " + studentsScoringAbove40InModule3);
    }

    // Method to generate a complete report of all student data
    private static void generateCompleteReport() {
        // Check if there are any students registered
        if (totalStudents == 0) {
            System.out.println("No students available.");
            return;
        }

        // Create a copy of the student array to sort without altering the original data
        Student[] sortedStudents = Arrays.copyOf(students, totalStudents);
        // Sort the students by their average marks in descending order
        bubbleSortStudentsByAverage(sortedStudents);

        // Print the report header
        System.out.println("\nComplete Report:");
        System.out.printf("%-13s %-20s %-10s %-10s %-10s %-10s %-10s %-10s\n", "Student ID", "Student Name", "Module 1", "Module 2", "Module 3", "Total", "Average", "Grade");

        // Loop through each student and print their details
        for (Student student : sortedStudents) {
            // Calculate the total and average marks for the student
            int totalMarks = student.getModule1Marks() + student.getModule2Marks() + student.getModule3Marks();
            double averageMarks = totalMarks / 3.0;
            // Get the student's grade based on their average marks
            String grade = student.calculateFinalGrade();

            // Print the student's details in a formatted manner
            System.out.printf("%-13s %-20s %-10d %-10d %-10d %-10d %-10.2f %-10s\n",
                    student.getStudentId(),
                    student.getStudentName(),
                    student.getModule1Marks(),
                    student.getModule2Marks(),
                    student.getModule3Marks(),
                    totalMarks,
                    averageMarks,
                    grade);
        }
    }

    // Helper method to check if input is valid
    private static boolean isValidInput(String input) {
        return input.matches("[0-8]");
    }

    // Method to check if student ID format is valid
    public static boolean isValidStudentIdFormat(String studentId) {
        return studentId.matches("(?i)w\\d{7}");
    }

    // Helper method to find a student by ID
    private static Student findStudentById(String studentId) {
        for (int i = 0; i < totalStudents; i++) {
            if (students[i].getStudentId().equals(studentId)) {
                return students[i];
            }
        }
        return null;
    }

    // Helper method to check if a string contains only alphabetic characters
    private static boolean isAlphabeticString(String str) {
        return str.matches("[a-zA-Z\\s]+");
    }

    // Helper method to capitalize the first letter of each word in a name
    private static String capitalizeName(String name) {
        String[] words = name.split("\\s+");
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalized.append(Character.toUpperCase(word.charAt(0)));
                capitalized.append(word.substring(1).toLowerCase());
                capitalized.append(" ");
            }
        }

        return capitalized.toString().trim();
    }

    // Helper method to check if a string represents a non-numeric value
    private static boolean isNonNumeric(String str) {
        return !str.matches("-?\\d+");  // matches a number with optional '-' sign
    }

    // Method to sort students by their average marks using bubble sort algorithm
    private static void bubbleSortStudentsByAverage(Student[] studentsArray) {
        int n = studentsArray.length;
        // Loop through the array multiple times to ensure sorting
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Calculate the average marks for the current student and the next student
                double avg1 = (studentsArray[j].getModule1Marks() + studentsArray[j].getModule2Marks() + studentsArray[j].getModule3Marks()) / 3.0;
                double avg2 = (studentsArray[j + 1].getModule1Marks() + studentsArray[j + 1].getModule2Marks() + studentsArray[j + 1].getModule3Marks()) / 3.0;
                // Swap students if the current student's average is less than the next student's average
                if (avg1 < avg2) {
                    Student temp = studentsArray[j];
                    studentsArray[j] = studentsArray[j + 1];
                    studentsArray[j + 1] = temp;
                }
            }
        }
    }
}