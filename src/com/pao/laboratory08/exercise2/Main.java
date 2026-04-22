package com.pao.laboratory08.exercise2;

import com.pao.laboratory08.exercise1.Student;
import com.pao.laboratory08.exercise1.Adresa;

import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";
    private static final String RESULT_PATH = "src/com/pao/laboratory08/tests/rezultate.txt";
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
        String linie = reader.readLine();
        List<Student> studenti = new ArrayList<>();
        while (linie != null)
        {
            String[] splitted = linie.split(",");
            String nume = splitted[0];
            int varsta = Integer.parseInt(splitted[1]);
            String oras = splitted[2];
            String strada = splitted[3];
            Adresa adresa = new Adresa(oras, strada);
            Student student = new Student(nume, varsta, adresa);
            studenti.add(student);
            linie = reader.readLine();
        }
        reader.close();
        Scanner scanner = new Scanner(System.in);
        int opt = scanner.nextInt();
        System.out.println("Filtru: varsta >= " + opt);
        int contor = 0;
        List<String> studentiFiltrati = new ArrayList<>();
        BufferedWriter writer = new BufferedWriter(new FileWriter(RESULT_PATH));
        for (Student student:studenti)
        {
            if (student.getVarsta() >= opt)
            {
                studentiFiltrati.add(student.toString());
                writer.write(student.toString() + "\n");
                ++contor;
            }
        }
        System.out.println("Rezultate: " + contor + " studenti");
        System.out.println();
        for (String student:studentiFiltrati)
        {
            System.out.println(student);
        }
        System.out.println();
        String[] splitted = RESULT_PATH.split("/");
        System.out.println("Scris in " + splitted[splitted.length - 1]);
        writer.close();
    }
}

