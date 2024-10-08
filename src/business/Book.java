package business;

import exceptions.BookNotFoundException;

import java.io.Serializable;
import java.util.*;

/**
 *
 */
final public class Book implements Serializable {

    private static final long serialVersionUID = 6110690276685962829L;
    private BookCopy[] copies;
    private List<Author> authors;
    private String isbn;
    private String title;
    private int maxCheckoutLength;

    public Book(String isbn, String title, int maxCheckoutLength, List<Author> authors) {
        this.isbn = isbn;
        this.title = title;
        this.maxCheckoutLength = maxCheckoutLength;
        this.authors = Collections.unmodifiableList(authors);
        copies = new BookCopy[]{new BookCopy(this, 1, true)};
    }

    public Book(String isbn, String title, int maxCheckoutLength, List<Author> authors, int numberOfCopies) {
        this.isbn = isbn;
        this.title = title;
        this.maxCheckoutLength = maxCheckoutLength;
        this.authors = Collections.unmodifiableList(authors);
        copies = new BookCopy[]{};
        this.addCopy(numberOfCopies);
    }

    public void updateCopies(BookCopy copy) {
        for (int i = 0; i < copies.length; ++i) {
            BookCopy c = copies[i];
            if (c.equals(copy)) {
                copies[i] = copy;
            }
        }
    }

    public List<Integer> getCopyNums() {
        List<Integer> retVal = new ArrayList<>();
        for (BookCopy c : copies) {
            retVal.add(c.getCopyNum());
        }
        return retVal;

    }

    public void addCopy() {
        BookCopy[] newArr = new BookCopy[copies.length + 1];
        System.arraycopy(copies, 0, newArr, 0, copies.length);
        newArr[copies.length] = new BookCopy(this, copies.length + 1, true);
        copies = newArr;
    }

    public void addCopy(int numberOfCopies) {
        for (int i = 0; i < numberOfCopies; i++) {
            this.addCopy();
        }
    }

    @Override
    public boolean equals(Object ob) {
        if (ob == null) return false;
        if (ob.getClass() != getClass()) return false;
        Book b = (Book) ob;
        return b.isbn.equals(isbn);
    }

    @Override
    public String toString() {
        return "isbn: " + isbn + ", maxLength: " + maxCheckoutLength + ", available: " + getNextAvailableCopy().isPresent() + ", copies: " + Arrays.toString(this.copies) + "\n";
    }

    public int getNumCopies() {
        return copies.length;
    }

    public String getTitle() {
        return title;
    }

    public BookCopy[] getCopies() {
        return copies;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public Optional<BookCopy> getNextAvailableCopy() {
        return Arrays.stream(copies)
                .filter(BookCopy::isAvailable).findFirst();
    }

    public BookCopy getCopy(int copyNum) {
        for (BookCopy c : copies) {
            if (copyNum == c.getCopyNum()) {
                return c;
            }
        }
        return null;
    }

    public int getMaxCheckoutLength() {
        return maxCheckoutLength;
    }
}
