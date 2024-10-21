import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Book class to store book details
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

// User class to manage library users
class User {
    private String username;
    private ArrayList<Book> borrowedBooks;

    public User(String username) {
        this.username = username;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return "User: " + username + ", Borrowed Books: " + borrowedBooks;
    }
}

// Library class to manage books and users
class Library {
    private ArrayList<Book> books;
    private ArrayList<User> users;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    // Add a book to the library
    public void addBook(String title, String author) {
        books.add(new Book(title, author));
        System.out.println("Book added: " + title);
    }

    // View all available books
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

    // Borrow a book for a user
    public void borrowBook(String title, User user) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isAvailable()) {
                book.setAvailable(false);
                user.borrowBook(book);
                System.out.println("You borrowed: " + book.getTitle());
                return;
            }
        }
        System.out.println("Book not available for borrowing.");
    }

    // Return a book for a user
    public void returnBook(String title, User user) {
        for (Book book : user.getBorrowedBooks()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                book.setAvailable(true);
                user.returnBook(book);
                System.out.println("You returned: " + book.getTitle());
                return;
            }
        }
        System.out.println("This book was not borrowed by you or does not exist.");
    }

    // Search for a book by title
    public void searchBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Book found: " + book);
                return;
            }
        }
        System.out.println("Book not found.");
    }

    // Save books to file
    public void saveBooksToFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Book book : books) {
                writer.write(book.getTitle() + "," + book.getAuthor() + "," + book.isAvailable() + "\n");
            }
            System.out.println("Books saved to file.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving books to file.");
            e.printStackTrace();
        }
    }

    // Load books from file
    public void loadBooksFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String title = data[0];
                String author = data[1];
                boolean isAvailable = Boolean.parseBoolean(data[2]);
                Book book = new Book(title, author);
                book.setAvailable(isAvailable);
                books.add(book);
            }
            System.out.println("Books loaded from file.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading books from file.");
            e.printStackTrace();
        }
    }

    // Add a user to the library system
    public void addUser(String username) {
        users.add(new User(username));
        System.out.println("User added: " + username);
    }

    // Get a user by username
    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    // Save users to file
    public void saveUsersToFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (User user : users) {
                writer.write(user.getUsername() + "\n");
            }
            System.out.println("Users saved to file.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving users to file.");
            e.printStackTrace();
        }
    }

    // Load users from file
    public void loadUsersFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(new User(line));
            }
            System.out.println("Users loaded from file.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading users from file.");
            e.printStackTrace();
        }
    }
}

// Main class for Library Management System
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        String command;

        // Load books and users from file at the start
        library.loadBooksFromFile("books.txt");
        library.loadUsersFromFile("users.txt");

        System.out.println("Welcome to the Library Management System!");

        do {
            System.out.println("\nEnter command (add/view/borrow/return/search/addUser/exit): ");
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
                    System.out.println("Enter username: ");
                    String borrowUser = scanner.nextLine();
                    User user = library.getUser(borrowUser);
                    if (user == null) {
                        System.out.println("User not found.");
                    } else {
                        System.out.println("Enter book title to borrow: ");
                        title = scanner.nextLine();
                        library.borrowBook(title, user);
                    }
                    break;

                case "return":
                    System.out.println("Enter username: ");
                    String returnUser = scanner.nextLine();
                    user = library.getUser(returnUser);
                    if (user == null) {
                        System.out.println("User not found.");
                    } else {
                        System.out.println("Enter book title to return: ");
                        title = scanner.nextLine();
                        library.returnBook(title, user);
                    }
                    break;

                case "search":
                    System.out.println("Enter book title to search: ");
                    title = scanner.nextLine();
                    library.searchBook(title);
                    break;

                case "addUser":
                    System.out.println("Enter username: ");
                    String username = scanner.nextLine();
                    library.addUser(username);
                    break;

                case "exit":
                    // Save books and users to file before exiting
                    library.saveBooksToFile("books.txt");
                    library.saveUsersToFile("users.txt");
                    System.out.println("Exiting. Thank you for using the Library Management System.");
                    break;

                default:
                    System.out.println("Unknown command. Please try again.");
            }
        } while (!command.equals("exit"));

        scanner.close();
    }
}
