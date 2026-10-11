package in.Framework.dao;

import in.Framework.config.DatabaseConfig;
import in.Framework.model.AttendanceRecord;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AttendanceDAO {

    public void recordBulkAttendance(List<AttendanceRecord> records) {
        String sql = "INSERT INTO attendance (student_id, course_id, attendance_date, status) " +
                "VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE status = VALUES(status)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (AttendanceRecord record : records) {
                stmt.setInt(1, record.getStudentId());           // 1 -> student_id
                stmt.setInt(2, record.getCourseId());            // 2 -> course_id
                stmt.setDate(3, Date.valueOf(record.getDate())); // 3 -> attendance_date
                stmt.setString(4, record.getStatus());           // 4 -> status

                stmt.addBatch();
            }

            int[] results = stmt.executeBatch();
            conn.commit();
            System.out.println("Recorded attendance batch for " + results.length + " records!");

        } catch (SQLException e) {
            System.err.println("Batch Update Error: " + e.getMessage());
        }
    }
}