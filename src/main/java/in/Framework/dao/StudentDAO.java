package in.Framework.dao;

import in.Framework.config.DatabaseConfig;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class StudentDAO {

    public double getStudentGPA(int studentId) {
        String sql = "{CALL CalculateStudentGPA(?, ?)}";
        double gpa = 0.0;

        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            // Bind IN parameter (Input studentId)
            stmt.setInt(1, studentId);

            // Register OUT parameter (Output GPA type from MySQL)
            stmt.registerOutParameter(2, Types.DECIMAL);

            // Execute procedure inside MySQL engine
            stmt.execute();

            // Retrieve output parameter value returned from MySQL
            gpa = stmt.getDouble(2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return gpa;
    }
}