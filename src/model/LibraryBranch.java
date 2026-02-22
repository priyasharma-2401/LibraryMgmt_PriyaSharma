package model;

import java.util.*;

public class LibraryBranch {
    private final String branchId;
    private final String name;
    private final Map<String, Book> inventory = new HashMap<>();

    public LibraryBranch(String branchId, String name) {
        this.branchId = branchId;
        this.name = name;
    }

    public String getBranchId() { return branchId; }
    public String getName() { return name; }

    public Collection<Book> getBooks() {
        return inventory.values();
    }

    public Book getBook(String isbn) {
        return inventory.get(isbn);
    }

    public void addBook(Book book) {
        inventory.put(book.getIsbn(), book);
    }

    public void removeBook(String isbn) {
        inventory.remove(isbn);
    }
}