package com.pao.laboratory11.exercise1;

public class Transaction {
    private int id;
    private double amount;
    private String date;
    private String country;
    private String channel;
    private Verdict verdict;
    private int score;

    public Transaction(int id, double amount, String date, String country, String channel) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.country = country;
        this.channel = channel;
        this.verdict = Verdict.ALLOW;
        this.score = 0;
    }

    public int getId() {return id;}
    public double getAmount() {return amount;}
    public String getDate() {return date;}
    public String getCountry() {return country;}
    public String getChannel() {return channel;}
    public Verdict getVerdict() {return verdict;}
    public int getScore() {return score;}

    public void setFlag() {this.verdict = Verdict.FLAG;}
    public void setScore(int score) {this.score = score;}
}
