import java.util.*;

public class LibrarySystem {
    private static Map<String, Book> books = new HashMap<>();
    private static Map<String, Member> members = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Register Member");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. View Books");
            System.out.println("6. View Member Info");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1": addBook(); break;
                case "2": registerMember(); break;
                case "3": borrowBook(); break;
                case "4": returnBook(); break;
                case "5": viewBooks(); break;
                case "6": viewMemberInfo(); break;
                case "7": return;
                default: System.out.println("Invalid option!");
            }
        }
    }

    private static void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        Book book = new Book(title, author);
        books.put(title, book);
        System.out.println("Book added.");
    }

    private static void registerMember() {
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        Member member = new Member(name);
        members.put(name, member);
        System.out.println("Member registered.");
    }

    private static void borrowBook() {
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        Member member = members.get(name);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        Book book = books.get(title);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        if (!book.isAvailable()) {
            System.out.println("Book is already borrowed.");
            return;
        }
        member.borrowBook(book);
    }

    private static void returnBook() {
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        Member member = members.get(name);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        Book book = books.get(title);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        member.returnBook(book);
    }

    private static void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the system.");
            return;
        }
        for (Book book : books.values()) {
            System.out.println(book);
        }
    }

    private static void viewMemberInfo() {
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        Member member = members.get(name);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }
        member.displayInfo();
    }
}

// ---------- Book Class ----------
class Book {
    private String title;
    private String author;
    private boolean available = true;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void borrow() {
        available = false;
    }

    public void returnBook() {
        available = true;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        return title + " by " + author + (available ? " (Available)" : " (Borrowed)");
    }
}

// ---------- User Class ----------
abstract class User {
    protected String name;

    public User(String name) {
        this.name = name;
    }

    public abstract void borrowBook(Book book);
    public abstract void returnBook(Book book);
}

// ---------- Member Class ----------
class Member extends User {
    private List<Book> borrowedBooks = new ArrayList<>();

    public Member(String name) {
        super(name);
    }

    @Override
    public void borrowBook(Book book) {
        if (borrowedBooks.size() >= 3) {
            System.out.println("Cannot borrow more than 3 books.");
            return;
        }
        borrowedBooks.add(book);
        book.borrow();
        System.out.println(name + " borrowed \"" + book.getTitle() + "\".");
    }

    @Override
    public void returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.returnBook();
            System.out.println(name + " returned \"" + book.getTitle() + "\".");
        } else {
            System.out.println(name + " did not borrow this book.");
        }
    }

    public void displayInfo() {
        System.out.println("Member: " + name);
        System.out.println("Borrowed Books:");
        for (Book b : borrowedBooks) {
            System.out.println("- " + b.getTitle());
        }
    }
}