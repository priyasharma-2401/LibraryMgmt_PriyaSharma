import factory.BookFactory;
import model.Book;
import model.LibraryBranch;
import model.Patron;
import recommendation.RecommendationService;
import service.LibraryService;
import strategy.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final LibraryService library = new LibraryService();
    private static final RecommendationService recommender = new RecommendationService();

    public static void main(String[] args) {

        System.out.println("===== Library Management System =====");

        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> addBranch();
                case 2 -> addBook();
                case 3 -> addPatron();
                case 4 -> searchBooks();
                case 5 -> checkoutBook();
                case 6 -> returnBook();
                case 7 -> transferBook();
                case 8 -> showRecommendations();
                case 9 -> viewInventory();
                case 0 -> {
                    running = false;
                    System.out.println("Exiting system...");
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // ================= MENU =================

    private static void printMenu() {
        System.out.println("""
                
                1. Add Branch
                2. Add Book
                3. Add Patron
                4. Search Books
                5. Checkout Book
                6. Return Book
                7. Transfer Book
                8. Recommend Books
                9. View Branch Inventory
                0. Exit
                """);
    }

    // ================= FEATURES =================

    private static void addBranch() {
        System.out.print("Branch ID: ");
        String id = scanner.nextLine();

        System.out.print("Branch Name: ");
        String name = scanner.nextLine();

        library.addBranch(new LibraryBranch(id, name));
        System.out.println(" Branch added.");
    }

    private static void addBook() {
        System.out.print("Branch ID: ");
        String branchId = scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Author: ");
        String author = scanner.nextLine();

        int year = readInt("Publication Year: ");

        Book book = BookFactory.createBook(isbn, title, author, year);
        library.addBook(branchId, book);

        System.out.println(" Book added.");
    }

    private static void addPatron() {
        System.out.print("Patron ID: ");
        String id = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        library.addPatron(new Patron(id, name));
        System.out.println(" Patron added.");
    }

    private static void searchBooks() {
        System.out.print("Branch ID: ");
        String branchId = scanner.nextLine();

        System.out.println("""
                Search by:
                1. Title
                2. Author
                3. ISBN
                """);

        int type = readInt("Choose: ");
        System.out.print("Query: ");
        String query = scanner.nextLine();

        SearchStrategy strategy = switch (type) {
            case 1 -> new TitleSearchStrategy();
            case 2 -> new AuthorSearchStrategy();
            case 3 -> new IsbnSearchStrategy();
            default -> null;
        };

        if (strategy == null) {
            System.out.println("Invalid search type.");
            return;
        }

        List<Book> results = library.search(branchId, strategy, query);

        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            results.forEach(System.out::println);
        }
    }

    private static void checkoutBook() {
        System.out.print("Branch ID: ");
        String branchId = scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Patron ID: ");
        String patronId = scanner.nextLine();

        library.checkout(branchId, isbn, patronId);
    }

    private static void returnBook() {
        System.out.print("Branch ID: ");
        String branchId = scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        library.returnBook(branchId, isbn);
    }

    private static void transferBook() {
        System.out.print("From Branch: ");
        String from = scanner.nextLine();

        System.out.print("To Branch: ");
        String to = scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        library.transferBook(from, to, isbn);
    }

    private static void showRecommendations() {
        System.out.print("Patron ID: ");
        String patronId = scanner.nextLine();

        System.out.print("Branch ID: ");
        String branchId = scanner.nextLine();

        Patron patron = library.getPatron(patronId);
        LibraryBranch branch = library.getBranch(branchId);

        if (patron == null || branch == null) {
            System.out.println("Invalid patron or branch.");
            return;
        }

        List<Book> recs = recommender.recommend(patron, branch.getBooks());

        if (recs.isEmpty()) {
            System.out.println("No recommendations available.");
        } else {
            recs.forEach(System.out::println);
        }
    }

    private static void viewInventory() {
        System.out.print("Branch ID: ");
        String branchId = scanner.nextLine();

        LibraryBranch branch = library.getBranch(branchId);

        if (branch == null) {
            System.out.println("Branch not found.");
            return;
        }

        branch.getBooks().forEach(book ->
                System.out.println(book + " | Available: " + book.isAvailable()));
    }

    // ================= HELPERS =================

    private static int readInt(String message) {
        System.out.print(message);
        int value = Integer.parseInt(scanner.nextLine());
        return value;
    }
}