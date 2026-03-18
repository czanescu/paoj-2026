package com.pao.laboratory03.exercise.service;

import com.pao.laboratory03.exercise.exception.InvalidStudentException;
import com.pao.laboratory03.exercise.exception.StudentNotFoundException;
import com.pao.laboratory03.exercise.model.Student;
import com.pao.laboratory03.exercise.model.Subject;

import java.util.*;

public final class StudentService {

    private static final StudentService instance = new StudentService();

    private StudentService() {
        students = new ArrayList<>();
    }

    public static StudentService getInstance() {
        return instance;
    }

    public void addStudent(String name, int age) {
        Student student = new Student(name, age);
        for (Student s : students) {
            if (Objects.equals(s.getName(), student.getName())) throw new RuntimeException("Exista deja studentul cu numele " + student.getName());
        }
        students.add(student);
    }

    public Student findByName(String name) {
        for (Student s : students) {
            if (Objects.equals(s.getName(), name)) return s;
        }
        throw new StudentNotFoundException("Nu s-a gasit student cu numele " + name);
    }

    public void addGrade(String studentName, Subject subject, double grade) {
        findByName(studentName).addGrade(subject,grade);
    }

    public void printAllStudents(){
        for (Student s : students) {
            System.out.println("Studentul " + s.getName() + " are notele " + s.getGrades() );
        }
    }
    public void printTopStudents(){
        List<Student> ListaStud = new ArrayList<>(students);
        ListaStud.sort(Comparator.comparingDouble(Student::getAverage).reversed());
        for (Student s : ListaStud) {
            System.out.println("Studentul " + s.getName() + " are media " + s.getAverage());
        }
    }

    public Map<Subject, Double> getAveragePerSubject(){
        Map<Subject, Double> Harta = new HashMap<>();
        for (Subject materie : Subject.values()) {
            double suma = 0, nr = 0;
            for (Student s : students) {
                if (s.getGrades().containsKey(materie)){
                    suma += s.getGrades().get(materie);
                    ++nr;
                }
            }
            Harta.put(materie, suma/nr);
        }
        return Harta;
    }



    private List<Student> students;
}
