package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    // Calea către fișierul cu date — relativă la rădăcina proiectului
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

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
        String opt = scanner.nextLine();
        String[] splitted = opt.split(" ");
        if (opt.equals("PRINT"))
        {
            for (Student student:studenti)
            {
                System.out.println(student);
            }
        }
        else if (splitted[0].equals("SHALLOW"))
        {
            String nume = splitted[1];
            for (Student student:studenti)
            {
                if (student.getNume().equals(nume))
                {
                    try{
                        Student student2 = (Student) student.clone();
                        student2.getAdresa().setOras("MODIFICAT");
                        System.out.println("Original: " + student);
                        System.out.println("Clona: " + student2);
                    } catch (CloneNotSupportedException e)
                    {
                        System.out.println("CloneNotSupportedException: " + e);
                    }
                }
            }
        }
        else if (splitted[0].equals("DEEP"))
        {
            String nume = splitted[1];
            for (Student student:studenti)
            {
                if (student.getNume().equals(nume))
                {
                    try{
                        Student student2 = (Student) student.deepClone();
                        student2.getAdresa().setOras("MODIFICAT");
                        System.out.println("Original: " + student);
                        System.out.println("Clona: " + student2);
                    } catch (CloneNotSupportedException e)
                    {
                        System.out.println("CloneNotSupportedException: " + e);
                    }
                }
            }
        }
        scanner.close();
    }
}
