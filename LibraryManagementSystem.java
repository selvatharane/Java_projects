import java.util.ArrayList;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true; // Book is available when created
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Available: " + (isAvailable ? "Yes" : "No");
    }
}

class Library {
    private ArrayList<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(String title, String author) {
        books.add(new Book(title, author));
        System.out.println("Book added: " + title);
    }

    public void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        System.out.println("Available Books:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void borrowBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isAvailable()) {
                book.setAvailable(false);
                System.out.println("You borrowed: " + book.getTitle());
                return;
            }
        }
        System.out.println("Book not available for borrowing.");
    }

    public void returnBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && !book.isAvailable()) {
                book.setAvailable(true);
                System.out.println("You returned: " + book.getTitle());
                return;
            }
        }
        System.out.println("This book is not borrowed or does not belong to this library.");
    }

    public void removeBook(String title) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getTitle().equalsIgnoreCase(title)) {
                books.remove(i);
                System.out.println("Book removed: " + title);
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void searchBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Book found: " + book);
                return;
            }
        }
        System.out.println("Book not found.");
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to the Library Management System!");

        do {
            System.out.println("\nEnter command (add/view/borrow/return/remove/search/exit): ");
            command = scanner.nextLine();

            switch (command) {
                case "add":
                    System.out.println("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.println("Enter book author: ");
                    String author = scanner.nextLine();
                    library.addBook(title, author);
                    break;

                case "view":
                    library.viewBooks();
                    break;

                case "borrow":
                    System.out.println("Enter book title to borrow: ");
                    title = scanner.nextLine();
                    library.borrowBook(title);
                    break;

                case "return":
                    System.out.println("Enter book title to return: ");
                    title = scanner.nextLine();
                    library.returnBook(title);
                    break;

                case "remove":
                    System.out.println("Enter book title to remove: ");
                    title = scanner.nextLine();
                    library.removeBook(title);
                    break;

                case "search":
                    System.out.println("Enter book title to search: ");
                    title = scanner.nextLine();
                    library.searchBook(title);
                    break;

                case "exit":
                    System.out.println("Exiting. Thank you for using the Library Management System.");
                    break;

                default:
                    System.out.println("Unknown command. Please try again.");
            }
        } while (!command.equals("exit"));

        scanner.close();
    }
}
