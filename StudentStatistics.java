import java.io.*;
import java.util.*;

/**
 * a simple program to compute statistics of std' marks in an assignment.
 * @author (Milan Gautam)
 * @version (24/09/2023)
 */
class Student {
    //Variable Declaration
    String firstName;
    String lastName;
    String studentID;
    double ass1Mark;
    double ass2Mark;
    double ass3Mark;
    double totalMark;

    public Student(String firstName, String lastName, String studentID, double ass1Mark, double ass2Mark, double ass3Mark) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.ass1Mark = ass1Mark;
        this.ass2Mark = ass2Mark;
        this.ass3Mark = ass3Mark;
        totalMarkCalculation();
       
    }
    
    private void totalMarkCalculation() {
        totalMark = ass1Mark + ass2Mark + ass3Mark;
    }
}

public class StudentStatistics
{
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" \n **** \t Student Statistics \t ****");
        System.out.print("Enter the File Name with Extension: ");
        String fileName = scanner.nextLine();

        ArrayList<Student> students = readStudentDataFromFile(fileName);

        if (students != null) {
            while (true) {
                System.out.println("\n****** \t Menu \t ******");
                System.out.println("1: Calculate the Total Marks and Print Student Mark Details");
                System.out.println("2: Print student name with total marks less than a threshold");
                System.out.println("3: Print the top 5 student with the highest total marks");
                System.out.println("4: Print the top 5 student with the lowest total marks");
                System.out.println("5: Exit the Program");
                System.out.print("Please Choose an option (1 | 2 | 3 | 4 | 5): ");

                int select = scanner.nextInt();

                switch (select) {
                    case 1:
                        studentTotalMarkCalculation(students);
                        break;
                    case 2:
                        System.out.print("Please enter the threshold Marks: ");
                        double threshold = scanner.nextDouble();
                        studentsBelowThreshold(students, threshold);
                        break;
                    case 3:
                        topNumStudentPrint(students, 5, false);
                        break;
                    case 4:
                        topNumStudentPrint(students, 5, true);
                        break;
                    case 5:
                        System.out.println("Program Exit");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("File Error: Select a valid option (1 | 2 | 3 | 4 | 5).");
                }
            }
        } else {
            System.out.println("File Error: Please check correct the file name and format.");
        }
    }

    private static ArrayList<Student> readStudentDataFromFile(String fileName) {
        ArrayList<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1) {
                    //first line skip which is unit name
                    continue;
                }

                String[] segment = line.split(",");
                if (segment.length >= 5) { 
                    // Check for at least 5 columns for three assignment marks
                    String lastName = segment[0].trim();
                    String firstName = segment[1].trim();
                    String studentID = segment[2].trim();
                    double ass1Mark = parseDouble(segment[3]);
                    double ass2Mark = parseDouble(segment[4]);
                    double ass3Mark = 0.0; 
                    // Default value for ass 3 if missing

                    if (segment.length >= 6) { 
                        // Checking A3 if present
                        ass3Mark = parseDouble(segment[5]);
                    }

                    // Check if studentID is a valid integer and all marks are valid (non-negative)
                    if (integerCheck (studentID) && ass1Mark >= 0 && ass2Mark >= 0 && ass3Mark >= 0) {
                        students.add(new Student(lastName, firstName, studentID, ass1Mark, ass2Mark, ass3Mark));
                }
            }
        }
            return students;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File Error: Please check the File");
            return null;
        }
    }
    
    private static boolean integerCheck(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static double parseDouble(String value) {
        try {
            if (!value.isEmpty() && value.matches("[0-9.]+")) {
                return Double.parseDouble(value);
            }
        } catch (NumberFormatException e) {
            System.out.println("File Error: Please check the File Number Format.");
            // exception handling
        }
        return 0.0; // default value
    }
    
    private static void studentTotalMarkCalculation(ArrayList<Student> students) {
        System.out.println("Student list with their name, student ID, assessment marks and the total mark:");
        for (Student student : students) {
            if (student != null) { 
                // Checking if student is null
                double totalMark = student.ass1Mark + student.ass2Mark + student.ass3Mark;
                System.out.println("Student Name: " + student.firstName + " " + student.lastName +
                    ", Student ID: " + student.studentID +
                    ", Assignment 1 Mark: " + student.ass1Mark +
                    ", Assignment 2 Mark: " + student.ass2Mark +
                    ", Assignment 3 Mark: " + student.ass3Mark +
                    ", Total Mark: " + totalMark);
            }
        }
    }
    
    private static void studentsBelowThreshold(ArrayList<Student> students, double threshold) {
        System.out.println("Total marks below  " + threshold + ":");
        for (Student student : students) { 
            double totalMark = student.ass1Mark + student.ass2Mark + student.ass3Mark;
            if (totalMark < threshold) {
               System.out.println("Name: " + student.firstName + " " + student.lastName +
                ", Student ID: " + student.studentID +
                ", Total Mark: " + totalMark);
            }
        }
    }
    
    private static void sortStudentUsingBubbleSort(ArrayList<Student> students, boolean ascending) {
        int num = students.size();
        for (int j = 0; j < num - 1; j++) {
            for (int k = 0; k < num - j - 1; k++) {
                 
                double mark1 = students.get(k).totalMark;
                double mark2 = students.get(k + 1).totalMark;
                //sorting order checking    
                boolean shouldSwap = ascending ? mark1 > mark2 : mark1 < mark2;

                if ((ascending && mark1 > mark2) || (!ascending && mark1 < mark2)) {
                    Student temp = students.get(k);
                    students.set(k, students.get(k + 1));
                    students.set(k + 1, temp);
                }
            }
        }
    }
    
    private static void topNumStudentPrint(ArrayList<Student> students, int num, boolean highest) {
        // Create a copy of the students ArrayList to avoid modifying the original list
        ArrayList<Student> copyStudents = new ArrayList<>(students);

        sortStudentUsingBubbleSort(copyStudents, highest); // Sort in the specified order

        String order = highest ? "highest" : "lowest";
        System.out.println("Top " + num + " students with " + order + " total marks:");

        // Actual number of students to print (up to num)
        int count = 0;
        for (int i = 0; i < copyStudents.size() && count < num; i++) {
            Student student = copyStudents.get(i);
            double totalMark = student.ass1Mark + student.ass2Mark + student.ass3Mark;
            if (totalMark > 0.0) { // Skip students with total marks of 0.0
                System.out.println("Name: " + student.firstName + " " + student.lastName +
                    ", Student ID: " + student.studentID +
                    ", Total Mark: " + totalMark);
                count++;
            }
        }
    }
}
