package org.howard.edu.lsp.midterm.crccards;

/**
 * Represents a single book in the Library Lending System.
 * 
 * A Book stores its own descriptive information (ISBN, title, author) and
 * is solely responsible for maintaining and validating its own availability.
 * Availability can only be changed through {@link #borrowBook()} and
 * {@link #returnBook()}, so other classes cannot put a Book into an
 * inconsistent state.
 * 
 *
 * @author Robert Hardy
 */
public class Book {

    /** The book's unique identifier. */
    private final String isbn;

    /** The book's title. */
    private final String title;

    /** The book's author. */
    private final String author;

    /** {@code true} when the book can be borrowed; {@code false} when it is borrowed. */
    private boolean available;

    /**
     * Creates a new Book. New books always start out available.
     *
     * @param isbn   the book's ISBN
     * @param title  the book's title
     * @param author the book's author
     */
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    /**
     * Returns the book's ISBN.
     *
     * @return the ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Returns the book's title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the book's author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Indicates whether the book is currently available to be borrowed.
     *
     * @return {@code true} if the book is available, {@code false} if it is borrowed
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Borrows this book, making it unavailable.
     *
     * @throws IllegalStateException if the book is already borrowed
     */
    public void borrowBook() {
        if (!available) {
            throw new IllegalStateException("Book " + isbn + " is already borrowed.");
        }
        available = false;
    }

    /**
     * Returns this book, making it available again.
     *
     * @throws IllegalStateException if the book is not currently borrowed
     */
    public void returnBook() {
        if (available) {
            throw new IllegalStateException("Book " + isbn + " is not currently borrowed.");
        }
        available = true;
    }

    /**
     * Returns a description of the book in the form
     * {@code "B101 Effective Java by Joshua Bloch [AVAILABLE]"}, using
     * {@code [BORROWED]} when the book is unavailable.
     *
     * @return the formatted book description
     */
    @Override
    public String toString() {
        return isbn + " " + title + " by " + author
                + " [" + (available ? "AVAILABLE" : "BORROWED") + "]";
    }
}
