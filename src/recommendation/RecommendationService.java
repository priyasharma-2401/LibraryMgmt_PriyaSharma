package recommendation;

import model.*;

import java.util.*;
import java.util.stream.Collectors;

public class RecommendationService {

    public List<Book> recommend(Patron patron, Collection<Book> allBooks) {

        Set<String> favoriteAuthors = patron.getBorrowingHistory()
                .stream()
                .map(loan -> loan.getBook().getAuthor())
                .collect(Collectors.toSet());

        return allBooks.stream()
                .filter(Book::isAvailable)
                .filter(book -> favoriteAuthors.contains(book.getAuthor()))
                .limit(5)
                .collect(Collectors.toList());
    }
}