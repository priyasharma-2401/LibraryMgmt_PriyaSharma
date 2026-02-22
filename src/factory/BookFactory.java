package factory;

import model.Book;

public class BookFactory {

    private BookFactory() {}

    public static Book createBook(String isbn, String title,
                                  String author, int year) {
        return new Book(isbn, title, author, year);
    }
}