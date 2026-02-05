package sef.module10.activity;

public class Student {
    private final String name;
    private final String course;
    private final int grade;

    public Student(String name, String course, int grade) {
        this.name = name;
        this.course = course;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public int getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', course='" + course + "', grade=" + grade + "}";
    }
}
