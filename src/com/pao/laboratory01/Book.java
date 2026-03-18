package com.pao.laboratory01;

import java.util.Arrays;
import java.util.Comparator;

public class Book implements Comparable<Book> {
    private String title;
    private int noPages;

    public Book(String title, int noPages) {
        this.title = title;
        this.noPages = noPages;
    }

    @Override
    public int compareTo(Book o) {
        // Sortare dupa titlu
        return this.title.compareTo(o.title);
    }

    @Override
    public String toString() {
        return title + ": " + noPages;
    }

    public int getNo() {
        return noPages;
    }

    public static void main(String[] args) {
        Book[] books = {
                new Book("Morometii", 180),
                new Book("Baltagul", 120)};
        Arrays.sort(books);
        System.out.println("Carti sortate dupa titlu: ");
        System.out.println(Arrays.toString(books));
        Arrays.sort(books, (b1, b2) -> b2.noPages - b1.noPages);
        System.out.println("Carti sortate dupa nr Pagini descrescator: ");
        System.out.println(Arrays.toString(books));
        Arrays.sort(books, new BookLengthComparator());
        System.out.println("Carti sortate dupa nr Pagini crescator: ");
        System.out.println(Arrays.toString(books));
    }
}

class BookLengthComparator implements Comparator<Book> {
    @Override
    public int compare(Book o1, Book o2) {
        return o1.getNo() - o2.getNo();
    }
}
