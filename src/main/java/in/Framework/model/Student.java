package in.Framework.model;

public class Student {
    private int studentId;
    private String firstName;
    private String lastName;
    private String email;

    // Constructor to instantiate student objects when fetching rows from database
    public Student(int studentId, String firstName, String lastName, String email) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Getters
    public int getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
}