package in.Framework.dao;

import in.Framework.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GradebookDAO {

    public void printReportCard(int studentId) {
        String query = "SELECT s.first_name, s.last_name, c.course_code, c.course_name, c.credits, e.grade " +
                "FROM students s " +
                "JOIN enrollments e ON s.student_id = e.student_id " +
                "JOIN courses c ON e.course_id = c.course_id " +
                "WHERE s.student_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            boolean headerPrinted = false;
            while (rs.next()) {
                if (!headerPrinted) {
                    System.out.println("\n==============================================");
                    System.out.println(" REPORT CARD FOR: " + rs.getString("first_name") + " " + rs.getString("last_name"));
                    System.out.println("==============================================");
                    headerPrinted = true;
                }
                System.out.printf("Course: %-8s | %-20s | Credits: %d | Grade: %.2f%n",
                        rs.getString("course_code"),
                        rs.getString("course_name"),
                        rs.getInt("credits"),
                        rs.getDouble("grade"));
            }

            if (headerPrinted) {
                StudentDAO studentDAO = new StudentDAO();
                double gpa = studentDAO.getStudentGPA(studentId);
                System.out.println("----------------------------------------------");
                System.out.printf("Cumulative GPA: %.2f%n", gpa);
                System.out.println("==============================================\n");
            } else {
                System.out.println("⚠️ No enrollment records found for Student ID: " + studentId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}