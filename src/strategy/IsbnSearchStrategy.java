package strategy;

import model.Book;
import java.util.List;
import java.util.stream.Collectors;

public class IsbnSearchStrategy implements SearchStrategy {

    @Override
    public List<Book> search(List<Book> books, String query) {
        return books.stream()
                .filter(b -> b.getIsbn().equalsIgnoreCase(query))
                .collect(Collectors.toList());
    }
}