package model;

import java.util.*;

public class Patron {
    private final String id;
    private String name;
    private final List<Loan> borrowingHistory = new ArrayList<>();

    public Patron(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Loan> getBorrowingHistory() { return borrowingHistory; }

    public void setName(String name) { this.name = name; }

    public void addLoan(Loan loan) {
        borrowingHistory.add(loan);
    }
}