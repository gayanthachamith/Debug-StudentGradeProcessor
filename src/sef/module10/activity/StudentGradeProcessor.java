package sef.module10.activity;

import java.util.*;

/**
 * Application does not work!
 *
 * Expected output:
 * Java: average mark = 8.75
 * Python: average mark = 7.33
 * Database: average mark = 10.0
 */

public class StudentGradeProcessor {

    public static void main(String[] args) {
        new StudentGradeProcessor().processGrades();
    }

    public void processGrades() {
        List<String> rawRecords = Arrays.asList(
                "Alise,Java,9",
                "Rihards,Python,9",
                "Elza,Java,8",
                "Daina,Database,10",
                "Ieva,Python,7",
                "Mantas,Python,11",     // should be ignored
                "Franks,java,9",        // should count as Java
                "Gita,Python,6",
                "Henrijs,Database,",    // should be ignored bug
                "Ivars,Java,9"
        );

        StudentParser parser = new StudentParser();
        GradeCalculator calculator = new GradeCalculator();

        Map<String, List<Student>> studentsByCourse = new HashMap<>();

        for (String record : rawRecords) {
            Student student = parser.parse(record);
            if (student == null) continue;

            String course = student.getCourse();
            studentsByCourse.computeIfAbsent(course, k -> new ArrayList<>())
                    .add(student);
        }

        // bonus: stable order (Java, Python, Database)
        List<String> courses = new ArrayList<>(studentsByCourse.keySet());
        Collections.sort(courses);

        for (String course : courses) {
            double avg = calculator.average(studentsByCourse.get(course));
            System.out.println(course + ": average mark = " + avg); // fixes *10 1st bug
        }
    }
}
