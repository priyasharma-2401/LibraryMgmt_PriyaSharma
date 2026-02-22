package service;

import model.*;
import observer.ReservationManager;
import strategy.SearchStrategy;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LibraryService {

    private static final Logger logger =
            Logger.getLogger(LibraryService.class.getName());

    private final Map<String, LibraryBranch> branches = new HashMap<>();
    private final Map<String, Patron> patrons = new HashMap<>();
    private final Map<String, Loan> activeLoans = new HashMap<>();
    private final ReservationManager reservationManager = new ReservationManager();

    // ---------- Branch ----------
    public void addBranch(LibraryBranch branch) {
        branches.put(branch.getBranchId(), branch);
    }

    public LibraryBranch getBranch(String id) {
        return branches.get(id);
    }

    // ---------- Book ----------
    public void addBook(String branchId, Book book) {
        branches.get(branchId).addBook(book);
        logger.info("Book added to branch " + branchId);
    }

    public List<Book> search(String branchId,
                             SearchStrategy strategy,
                             String query) {
        return strategy.search(
                new ArrayList<>(branches.get(branchId).getBooks()),
                query);
    }

    // ---------- Patron ----------
    public void addPatron(Patron patron) {
        patrons.put(patron.getId(), patron);
    }

    public Patron getPatron(String id) {
        return patrons.get(id);
    }

    // ---------- Lending ----------
    public void checkout(String branchId, String isbn, String patronId) {
        Book book = branches.get(branchId).getBook(isbn);
        Patron patron = patrons.get(patronId);

        if (!book.isAvailable()) {
            reservationManager.reserve(isbn,
                    new observer.PatronNotifier(patron));
            logger.warning("Book not available — reserved instead.");
            return;
        }

        Loan loan = new Loan(book, patron);
        activeLoans.put(isbn, loan);
        book.setAvailable(false);
        patron.addLoan(loan);

        logger.info("Book checked out: " + book.getTitle());
    }

    public void returnBook(String branchId, String isbn) {
        Loan loan = activeLoans.remove(isbn);
        if (loan != null) {
            loan.markReturned();
            Book book = branches.get(branchId).getBook(isbn);
            book.setAvailable(true);
            reservationManager.notifyNext(isbn);
            logger.info("Book returned: " + book.getTitle());
        }
    }

    // ---------- Transfer ----------
    public void transferBook(String fromBranch,
                             String toBranch,
                             String isbn) {

        LibraryBranch source = branches.get(fromBranch);
        LibraryBranch target = branches.get(toBranch);

        Book book = source.getBook(isbn);
        if (book != null) {
            source.removeBook(isbn);
            target.addBook(book);
            logger.info("Book transferred: " + isbn);
        }
    }
}