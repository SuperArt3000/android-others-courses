package Model;

public class Student {
    private int id;
    private String department;
    private String firstName;
    private String lastName;
    private String averageMark;

    public Student() {
    }

    public Student(int id, String department, String firstName, String lastName, String averageMark) {
        this.id = id;
        this.department = department;
        this.firstName = firstName;
        this.lastName = lastName;
        this.averageMark = averageMark;
    }

    public Student(String department, String firstName, String lastName, String averageMark) {
        this.department = department;
        this.firstName = firstName;
        this.lastName = lastName;
        this.averageMark = averageMark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(String averageMark) {
        this.averageMark = averageMark;
    }
}
