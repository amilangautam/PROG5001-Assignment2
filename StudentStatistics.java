import java.io.*;
import java.util.*;

/**
 * a simple program to compute statistics of std' marks in an assignment.
 * @author (Milan Gautam)
 * @version (24/09/2023)
 */
class Student {
    //Variable Declaration
    String lastName;
    String firstName;
    String studentID;
    double ass1Mark;
    double ass2Mark;
    double ass3Mark;
    double totalMark;

    public Student(String lastName, String firstName, String studentID, double ass1Mark, double ass2Mark, double ass3Mark) {
        this.lastName = lastName;
        this.firstName = firstName;
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
        
        //allow user to enter the File Name
        System.out.print("Please Enter the file name with Extension: ");
        String fileName = scanner.nextLine();
        

        try {
            BufferedReader buff = new BufferedReader(new FileReader(fileName));

            String textLine;
            
            while ((textLine = buff.readLine()) != null) {
                System.out.println(textLine);
            }

            buff.close();
        } catch (IOException e) {
            System.err.println("Error reading student data from the file.");
        }

        scanner.close();
    }

    private static Student[] readStudentDataFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;
            Student[] students = new Student[70]; 

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
                    double ass1Mark = Double.parseDouble(segment[3]);
                    double ass2Mark = Double.parseDouble(segment[4]);
                    double ass3Mark = 0.0; 
                    // Default value for ass 3 if missing

                    if (segment.length >= 6) { 
                        // Checking A3 if present
                        ass3Mark = Double.parseDouble(segment[5]);
                    }

                    students[lineNumber - 2] = new Student(lastName, firstName, studentID, ass1Mark, ass2Mark, ass3Mark);
                }
            }
            return students;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static void studentTotalMarkCalculation(Student[] students) {
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
}
