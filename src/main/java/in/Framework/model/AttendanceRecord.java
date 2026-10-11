package in.Framework.model;

import java.time.LocalDate;

public class AttendanceRecord {
    private int studentId;
    private int courseId;
    private LocalDate date;
    private String status;

    public AttendanceRecord(int studentId, int courseId, LocalDate date, String status) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.date = date;
        this.status = status;
    }

    public int getStudentId() { return studentId; }
    public int getCourseId() { return courseId; }
    public LocalDate getDate() { return date; }
    public String getStatus() { return status; }
}