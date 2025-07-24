// Class representing a Student with student ID, name, and module information
public class Student {
    private final String studentId;     // Unique student ID
    private String studentName;         // Student's name
    private final Module studentModules; // Object to store module marks

    // Constructor to initialize a student with ID and name
    public Student(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentModules = new Module(); // Initialize student's module information
    }

    // Getter for student ID
    public String getStudentId() {
        return studentId;
    }

    // Getter for student name
    public String getStudentName() {
        return studentName;
    }

    // Getter for Module 1 marks
    public int getModule1Marks() {
        return studentModules.getModule1Marks();
    }

    // Getter for Module 2 marks
    public int getModule2Marks() {
        return studentModules.getModule2Marks();
    }

    // Getter for Module 3 marks
    public int getModule3Marks() {
        return studentModules.getModule3Marks();
    }

    // Setter for student name
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // Setter for module marks
    public void setModuleMarks(int module1Marks, int module2Marks, int module3Marks) {
        studentModules.setModuleMarks(module1Marks, module2Marks, module3Marks);
    }

    // Method to display module marks
    public void displayModuleMarks() {
        studentModules.displayModuleMarks();
    }

    // Method to calculate final grade based on module marks
    public String calculateFinalGrade() {
        return studentModules.calculateModuleGrade();
    }
}