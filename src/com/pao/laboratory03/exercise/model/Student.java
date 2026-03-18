package com.pao.laboratory03.exercise.model;

import com.pao.laboratory03.exercise.exception.InvalidGradeException;
import com.pao.laboratory03.exercise.exception.InvalidStudentException;

import java.util.HashMap;
import java.util.Map;

public class Student {

    public Student(String name, int age){
        this.grades = new HashMap<Subject, Double>();
        this.name = name;
        if (age < 18 || age > 60){
            throw new InvalidStudentException("InvalidStudentException: Varsta " + age + " a studentului este in afara intervalului permis (18 - 60)");
        }
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public Map<Subject, Double> getGrades() { return grades; }
    public void addGrade(Subject subject, double grade){
        if (grade < 0 || grade > 10){throw new InvalidGradeException("InvalidGradeException: Nota " + grade + " este in afara intervalului permis (0 - 10");}
        grades.put(subject, grade);
    }
    public double getAverage(){
        if (grades.isEmpty()){return 0;}
        double sum = 0;
        for (double grade : grades.values()){
            sum += grade;
        }
        return sum / grades.size();
    }
    public String toString(){
        return "Student{" + name + ", " + age + ", " + grades + "}";
    }



    private String name;
    private int age;
    private Map<Subject, Double> grades;
}
