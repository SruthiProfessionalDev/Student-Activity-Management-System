// Class representing a Module with marks and grading
public class Module {
    private int module1Marks; // Marks for Module 1
    private int module2Marks; // Marks for Module 2
    private int module3Marks; // Marks for Module 3

    // Default constructor
    public Module() {
        // Default constructor intentionally left blank
    }

    // Getter for Module 1 marks
    public int getModule1Marks() {
        return module1Marks;
    }

    // Getter for Module 2 marks
    public int getModule2Marks() {
        return module2Marks;
    }

    // Getter for Module 3 marks
    public int getModule3Marks() {
        return module3Marks;
    }

    // Setter for all module marks
    public void setModuleMarks(int module1Marks, int module2Marks, int module3Marks) {
        this.module1Marks = module1Marks;
        this.module2Marks = module2Marks;
        this.module3Marks = module3Marks;
    }

    // Method to display module marks
    public void displayModuleMarks() {
        System.out.println("Module 1 Marks: " + module1Marks);
        System.out.println("Module 2 Marks: " + module2Marks);
        System.out.println("Module 3 Marks: " + module3Marks);
    }

    // Method to calculate module grade based on average marks
    public String calculateModuleGrade() {
        int averageMarks = (module1Marks + module2Marks + module3Marks) / 3;

        if (averageMarks >= 80) {
            return "Distinction";
        } else if (averageMarks >= 70) {
            return "Merit";
        } else if (averageMarks >= 40) {
            return "Pass";
        } else {
            return "Fail";
        }
    }
}