package sef.module10.activity;
import javax.swing.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * Application fixed using debugging
 *
 * Expected output:
 * Java: average mark = 8.75
 * Python: average mark = 7.33
 * Database: average mark = 10.0
 */
public class StudentGradeProcessor {

    private static final Logger logger = Logger.getLogger(StudentGradeProcessor.class.getName());

    public static void main(String[] args) {
        StudentGradeProcessor processor = new StudentGradeProcessor();
        processor.processGrades();
    }

    public void processGrades() {

        logger.info("\u001B[32mStarting grade processing \u001B[0m "); // flow start
        List<String> rawRecords = Arrays.asList(
                "Alise,Java,9",
                "Rihards,Python,9",
                "Elza,Java,8",
                "Daina,Database,10",
                "Ieva,Python,7",
                "Mantas,Python,11",     // invalid
                "Franks,java,9",        // lowercase course
                "Gita,Python,6",
                "Henrijs,Database,",    // missing grade
                "Ivars,Java,9"
        );

        Map<String, List<Student>> studentsByCourse = new HashMap<>();

        int invalidCount = 0;

        for (String record : rawRecords) {
            Student student = parseStudent(record);
            if (student != null) {
                String course = student.getCourse();
                if (!studentsByCourse.containsKey(course)) {
                    studentsByCourse.put(course, new ArrayList<>());
                }
                studentsByCourse.get(course).add(student);
            }else{
                invalidCount ++;
            }
        }

        logger.info("\u001B[32mFinished parsing records. Invalid Record:"+ invalidCount +"\u001B[0m "); // summary useful for debugging

        for (String course : studentsByCourse.keySet()) {
            double average = calculateAverage(studentsByCourse.get(course));
            System.out.println(course + ": average mark = "+String.format("%.2f" , average)); // FIX: removed *10
        }
    }

    private Student parseStudent(String record) {
        String[] parts = record.split(",", -1);

        if (parts.length != 3) {
            logger.warning("Skipping invalid record (Wrong format)" + record);
            return null;
        }

        String name = parts[0].trim();
        String courseRaw = parts[1].trim();
        String gradeStr = parts[2].trim();

        // FIX: ignore missing values
        if (name.isEmpty() || courseRaw.isEmpty() || gradeStr.isEmpty()) {
            logger.warning("Skipping invalid record (missing value): "+ record);
            return null;
        }

        Integer grade;
        try {
            grade = Integer.parseInt(gradeStr);
        } catch (NumberFormatException exception) {
            logger.warning("Skipping invalid record (non numeric grade): "+ record);
            return null;
        }

        // FIX: correct validation condition
        if (grade < 1 || grade > 10) {
            logger.warning("Skipping invalid record (grade out of range): " + record);
            return null;
        }

        // FIX: normalize course name (Java vs java)
        String course =
                courseRaw.substring(0, 1).toUpperCase() +
                        courseRaw.substring(1).toLowerCase();

        logger.fine("Parsed student: " + name +", " + course+ "," + grade); //detailed info only useful during debugging
        return new Student(name, course, grade);
    }

    private double calculateAverage(List<Student> students) {
        if (students == null || students.isEmpty()) {
            logger.warning("No students found for course. Returning 0.0");
            return 0.0;
        }

        int total = 0;
        int count = 0;

        // FIX: start loop from index 0
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            total += student.getGrade();
            count++;
        }
        double average = (double) total / students.size();
//logger.fine("Calculated average: total=" + total +
//        ", count=" + students.size() +
//        ", average=" + average);
        // FIX: avoid integer division
        return average;
    }

    static class Student {
        private String name;
        private String course;
        private Integer grade;

        public Student(String name, String course, Integer grade) {
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

        public Integer getGrade() {
            return grade;
        }

        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", course='" + course + '\'' +
                    ", grade=" + grade +
                    '}';
        }
    }
}
