import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class GradeCalculator {
    private static final ArrayList<Double> grades = new ArrayList<>();
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public static void main(String[] args) {
        showWelcomeMessage();

        int choice;
        do {
            choice = showMenu();
            processChoice(choice);
        } while (choice != 4);

        showGoodbyeMessage();
    }

    /** Shows welcome message to user */
    private static void showWelcomeMessage() {
        JOptionPane.showMessageDialog(null,
            "Welcome to the Grade Calculator System!\n\n" +
            "This program will help you track your grades\n" +
            "and calculate your current average.",
            "Grade Calculator",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /** Displays main menu and gets user choice */
    private static int showMenu() {
        String[] options = {
            "1. Add a Grade",
            "2. View Current Average",
            "3. View Letter Grade",
            "4. Exit"
        };

        String menu = "Grade Calculator Menu\n" +
                      "=====================\n\n";
        for (String option : options) {
            menu += option + "\n";
        }
        menu += "\nPlease enter your choice (1-4):";

        int choice = 0;
        boolean validChoice = false;

        while (!validChoice) {
            try {
                String input = JOptionPane.showInputDialog(null, menu,
                        "Grade Calculator Menu", JOptionPane.QUESTION_MESSAGE);

                if (input == null) {
                    choice = 4; // Treat cancel as exit
                    validChoice = true;
                } else {
                    choice = Integer.parseInt(input);
                    if (choice >= 1 && choice <= 4) {
                        validChoice = true;
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Please enter a number between 1 and 4.",
                                "Invalid Choice", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid number.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
        return choice;
    }

    /** Processes user's menu choice */
    private static void processChoice(int choice) {
        switch (choice) {
            case 1 -> addGrade();
            case 2 -> viewAverage();
            case 3 -> viewLetterGrade();
            case 4 -> { /* exit handled in loop */ }
            default -> JOptionPane.showMessageDialog(null,
                        "Invalid choice. Please try again.",
                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Adds a new grade to the collection */
    private static void addGrade() {
        boolean valid = false;
        while (!valid) {
            String input = JOptionPane.showInputDialog(null,
                    "Enter a grade (0–100):",
                    "Add Grade",
                    JOptionPane.QUESTION_MESSAGE);

            if (input == null) {
                // user cancelled
                return;
            }

            try {
                double grade = Double.parseDouble(input);
                if (grade >= 0 && grade <= 100) {
                    grades.add(grade);
                    JOptionPane.showMessageDialog(null,
                            "Grade " + df.format(grade) + " added successfully.",
                            "Grade Added",
                            JOptionPane.INFORMATION_MESSAGE);
                    valid = true;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Grade must be between 0 and 100.",
                            "Invalid Grade",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid number.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /** Calculates and displays current average */
    private static void viewAverage() {
        if (grades.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No grades entered yet.",
                    "Current Average",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            double avg = calculateAverage();
            JOptionPane.showMessageDialog(null,
                    "Current Average: " + df.format(avg) + "%",
                    "Current Average",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /** Determines and displays letter grade based on average */
    private static void viewLetterGrade() {
        if (grades.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No grades entered yet.",
                    "Letter Grade",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            double avg = calculateAverage();
            String letter = getLetterGrade(avg);
            JOptionPane.showMessageDialog(null,
                    "Average: " + df.format(avg) + "%\n" +
                    "Letter Grade: " + letter,
                    "Letter Grade",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /** Shows goodbye message with final stats if any */
    private static void showGoodbyeMessage() {
        String message = "Thank you for using Grade Calculator!\n\n";
        if (!grades.isEmpty()) {
            double average = calculateAverage();
            message += "Final Statistics:\n" +
                       "Total Grades: " + grades.size() + "\n" +
                       "Final Average: " + df.format(average) + "%\n" +
                       "Letter Grade: " + getLetterGrade(average);
        }
        JOptionPane.showMessageDialog(null, message,
                "Goodbye", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Helper: calculate numeric average */
    private static double calculateAverage() {
        double sum = 0.0;
        for (double g : grades) {
            sum += g;
        }
        return sum / grades.size();
    }

    /** Helper: convert numeric average to letter grade */
    private static String getLetterGrade(double average) {
        if (average >= 90) return "A";
        else if (average >= 80) return "B";
        else if (average >= 70) return "C";
        else if (average >= 60) return "D";
        else return "F";
    }
}
