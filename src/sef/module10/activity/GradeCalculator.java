package sef.module10.activity;

import java.util.List;

public class GradeCalculator {

    public double average(List<Student> students) {
        if (students == null || students.isEmpty()) return 0.0;

        int total = 0;
        int count = 0;

        for (int i = 0; i < students.size(); i++) { // fixes skip index 0 bug
            Student s = students.get(i);
            total += s.getGrade();
            count++;
        }

        return  (double) total / count; // fixes int division
    }
}
