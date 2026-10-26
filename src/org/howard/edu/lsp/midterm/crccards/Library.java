package org.howard.edu.lsp.midterm.crccards;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maintains a collection of {@link Book} objects and coordinates borrowing and
 * returning of those books.
 * 
 * Books are stored in a {@link HashMap} keyed by ISBN. A map was chosen because
 * ISBNs are unique, so duplicate detection and lookup by ISBN are constant-time
 * operations. The Library enforces a collection-wide limit of
 * {@value #MAX_BORROWED_BOOKS} borrowed books at a time, but it never changes a
 * book's availability itself: it locates the Book and delegates the state
 * change to {@link Book#borrowBook()} or {@link Book#returnBook()}.
 * 
 *
 * @author Robert Hardy
 */
public class Library {

    /** The maximum number of books that may be borrowed from the Library at one time. */
    public static final int MAX_BORROWED_BOOKS = 3;

    /** Books in the Library, keyed by ISBN. */
    private final Map<String, Book> books = new HashMap<>();

    /** Creates an empty Library. */
    public Library() {
        // starts with no books
    }

    /**
     * Adds a book to the Library.
     *
     * @param book the book to add
     * @throws IllegalArgumentException if {@code book} is null or a book with the
     *                                  same ISBN is already in the Library (the
     *                                  original book is preserved)
     */
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null.");
        }
        if (books.containsKey(book.getIsbn())) {
            throw new IllegalArgumentException("A book with ISBN " + book.getIsbn() + " already exists.");
        }
        books.put(book.getIsbn(), book);
    }

    /**
     * Finds a book by ISBN.
     *
     * @param isbn the ISBN to look up
     * @return the matching {@link Book} instance, or {@code null} if no such book exists
     */
    public Book findBook(String isbn) {
        return books.get(isbn);
    }

    /**
     * Borrows the book with the given ISBN, subject to the borrowing limit.
     * If the operation fails, nothing in the Library changes.
     *
     * @param isbn the ISBN of the book to borrow
     * @throws IllegalArgumentException if no book with this ISBN is in the Library
     * @throws IllegalStateException    if the book is already borrowed or
     *                                  {@value #MAX_BORROWED_BOOKS} books are already borrowed
     */
    public void borrowBook(String isbn) {
        Book book = findBook(isbn);
        if (book == null) {
            throw new IllegalArgumentException("No book with ISBN " + isbn + " in the Library.");
        }
        if (getBorrowedBookCount() >= MAX_BORROWED_BOOKS) {
            throw new IllegalStateException("Borrowing limit of " + MAX_BORROWED_BOOKS + " books reached.");
        }
        book.borrowBook(); // Book validates and changes its own state
    }

    /**
     * Returns the borrowed book with the given ISBN to the Library.
     * If the operation fails, nothing in the Library changes.
     *
     * @param isbn the ISBN of the book to return
     * @throws IllegalArgumentException if no book with this ISBN is in the Library
     * @throws IllegalStateException    if the book is not currently borrowed
     */
    public void returnBook(String isbn) {
        Book book = findBook(isbn);
        if (book == null) {
            throw new IllegalArgumentException("No book with ISBN " + isbn + " in the Library.");
        }
        book.returnBook(); // Book validates and changes its own state
    }

    /**
     * Returns all books that are currently available.
     *
     * @return a new list of available books; empty if there are none
     */
    public List<Book> getAvailableBooks() {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.isAvailable()) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Returns all books by the given author, whether available or borrowed.
     * The comparison is exact and case-sensitive.
     *
     * @param author the author name to match
     * @return a new list of matching books; empty if there are none
     */
    public List<Book> getBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        if (author == null) {
            return result;
        }
        for (Book book : books.values()) {
            if (author.equals(book.getAuthor())) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Returns all books sorted by ISBN in ascending lexicographical
     * ({@link String}) order, regardless of insertion order or borrowing status.
     * This method does not change the Library's contents or any book's state.
     *
     * @return a new sorted list of all books; empty if the Library is empty
     */
    public List<Book> getBooksSortedByIsbn() {
        List<Book> result = new ArrayList<>(books.values());
        result.sort(Comparator.comparing(Book::getIsbn));
        return result;
    }

    /**
     * Returns the number of books that are currently borrowed. The count is
     * derived from the books' own state, so it can never get out of sync.
     *
     * @return the current number of borrowed books
     */
    public int getBorrowedBookCount() {
        int count = 0;
        for (Book book : books.values()) {
            if (!book.isAvailable()) {
                count++;
            }
        }
        return count;
    }
}
