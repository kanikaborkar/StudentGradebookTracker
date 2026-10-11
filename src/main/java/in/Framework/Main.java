package in.Framework;

// Add these DAO imports so Main can access GradebookDAO, StudentDAO, and AttendanceDAO
import in.Framework.dao.AttendanceDAO;
import in.Framework.dao.GradebookDAO;
import in.Framework.dao.StudentDAO;
import in.Framework.model.AttendanceRecord;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        StudentDAO studentDAO = new StudentDAO();
        GradebookDAO gradebookDAO = new GradebookDAO();

        while (true) {
            System.out.println("\n=================================");
            System.out.println(" STUDENT GRADEBOOK & ATTENDANCE ");
            System.out.println("=================================");
            System.out.println("1. Record Bulk Class Attendance (Batching)");
            System.out.println("2. Calculate Student GPA (Stored Procedure)");
            System.out.println("3. Generate Student Report Card (Relational Join)");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Course ID: ");
                    int courseId = scanner.nextInt();
                    System.out.print("How many students to log attendance for? ");
                    int count = scanner.nextInt();

                    List<AttendanceRecord> records = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        System.out.print("Enter Student ID #" + (i + 1) + ": ");
                        int studentId = scanner.nextInt();
                        System.out.print("Enter Status (PRESENT / ABSENT / LATE): ");
                        String status = scanner.next().toUpperCase();

                        records.add(new AttendanceRecord(studentId, courseId, LocalDate.now(), status));
                    }

                    attendanceDAO.recordBulkAttendance(records);
                    break;

                case 2:
                    System.out.print("Enter Student ID: ");
                    int gpaStudentId = scanner.nextInt();
                    double gpa = studentDAO.getStudentGPA(gpaStudentId);
                    System.out.printf("Calculated Cumulative GPA: %.2f%n", gpa);
                    break;

                case 3:
                    System.out.print("Enter Student ID: ");
                    int reportStudentId = scanner.nextInt();
                    gradebookDAO.printReportCard(reportStudentId);
                    break;

                case 4:
                    System.out.println("Exiting application. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please select 1, 2, 3, or 4.");
            }
        }
    }
}