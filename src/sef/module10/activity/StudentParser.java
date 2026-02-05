package sef.module10.activity;

public class StudentParser {

    public Student parse(String record) {
        if (record == null) return null;

        String[] parts = record.split(",", -1);
        if (parts.length != 3) return null;

        String name = parts[0].trim();
        String courseRaw = parts[1].trim();
        String gradeStr = parts[2].trim();

        if (name.isEmpty() || courseRaw.isEmpty() || gradeStr.isEmpty()) {
            return null; // ignore missing values (fixes "Henrijs,Database,")
        }

        Integer grade;
        try {
            grade = Integer.parseInt(gradeStr);
        } catch (NumberFormatException e) {
            return null; // bonus: add try catch for avoid crash
        }

        if (grade < 1 || grade > 10) {  // && replace as ||
            return null; // fixes accepting 11
        }

        String course = normalizeCourse(courseRaw); // fixes Java vs java
        return new Student(name, course, grade);
    }

    private String normalizeCourse(String courseRaw) {
        String lower = courseRaw.toLowerCase();
        return switch (lower) {
            case "java" -> "Java";
            case "python" -> "Python";
            case "database" -> "Database";
            default -> courseRaw; // keep unknown as-is
        };
    }
}
