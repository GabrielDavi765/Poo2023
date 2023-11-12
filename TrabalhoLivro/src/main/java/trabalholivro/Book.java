package trabalholivro;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import trabalholivro.DAO.BookDao;

public final class Book implements BookDao {

    private static long nextId = 1;   // Variável estática que mantém o próximo ID a ser atribuído aos livros
    
    // Atributos da classe representando as propriedades de um livro
    private long id;
    private String title, authors;
    private LocalDate acquisition;
    private Short pages, year;
    private Byte edition;
    private BigDecimal price;

    public Book() {    // Construtor padrão que incrementa automaticamente o ID do livro

        this.id = nextId++;
    }
    
    // Construtor que aceita parâmetros para inicializar as propriedades do livro
    public Book(String title, String authors, LocalDate acquisition, short pages, short year, byte edition, BigDecimal price) {
        this();// Chama o construtor padrão para gerar um ID único
        setTitle(title);
        setAuthors(authors);
        setAcquisition(acquisition);
        setPages(pages);
        setYear(year);
        setEdition(edition);
        this.price = price;
    }
    
    // Construtor que aceita um ID, utilizado para instanciar livros a partir do banco de dados 
    public Book(long id, String title, String authors, LocalDate acquisition, short pages, short year, byte edition, BigDecimal price) {
        setId(id);
        setTitle(title);
        setAuthors(authors);
        setAcquisition(acquisition);
        setPages(pages);
        setYear(year);
        setEdition(edition);
        setPrice(price);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("( Alert ) Title cannot be null.");
        }

        if (title.length() > 150) {
            throw new IllegalArgumentException(" ( Alert ) Title cannot exceed 150 characters.");
        }

        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        if (authors == null) {
            throw new IllegalArgumentException("( Alert ) Authors cannot be null.");
        }

        if (authors.length() > 250) {
            throw new IllegalArgumentException("( Alert ) Authors cannot exceed 250 characters.");
        }

        this.authors = authors;
    }

    public LocalDate getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(LocalDate acquisition) {
        LocalDate now = LocalDate.now();
        if (acquisition.isAfter(now) || acquisition.until(now, ChronoUnit.DAYS) != 0) {
            throw new IllegalArgumentException("( Alert ) Acquisition date cannot be after or ahead of the current date.");
        }
        this.acquisition = acquisition;
    }

    public Short getPages() {
        return pages;
    }

    public void setPages(Short pages) {
        if (pages == null || pages < 1) {
            throw new IllegalArgumentException("( Alert ) Pages must be a positive number.");
        }
        this.pages = pages;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        if (year == null) {
            throw new IllegalArgumentException("( Alert ) Year cannot be null.");
        }
        this.year = year;
    }

    public Byte getEdition() {
        return edition;
    }

    public void setEdition(Byte edition) {
        if (edition == null || edition < 1) {
            throw new IllegalArgumentException("( Alert ) Edition must be a positive number.");
        }
        this.edition = edition;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("( Alert ) Price must be a non-negative value.");
        }
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getSaveStatement() {
        String stringSQL = "INSERT INTO book VALUES (";
        stringSQL += getId() + ", ";
        stringSQL += "'" + getTitle() + "', ";
        stringSQL += "'" + getAuthors() + "', ";
        stringSQL += "'" + getAcquisition().toString() + "', ";
        stringSQL += getPages() + ", ";
        stringSQL += getYear() + ", ";
        stringSQL += getEdition() + ", ";
        stringSQL += getPrice();
        stringSQL += ");";
        return stringSQL;
    }

    @Override
    public String getUpdateStatement() {
        String stringSQL = "UPDATE book SET ";
        stringSQL += "title = ?, ";
        stringSQL += "authors = ?, ";
        stringSQL += "acquisition = ?, ";
        stringSQL += "pages = ?, ";
        stringSQL += "year = ?, ";
        stringSQL += "edition = ?, ";
        stringSQL += "price = ? ";  
        stringSQL += "WHERE id = " + getId() + ";";
        return stringSQL;
    }

    @Override
    public String getFindByIdStatement() {
        return "SELECT * FROM book WHERE id = " + getId() + ";";
    }

    @Override
    public String getFindAllStatement() {
        return "SELECT * FROM books;";
    }

    @Override
    public String getDeleteStatement() {
        return "DELETE FROM book WHERE id = " + getId() + ";";
    }

    @Override
    public void composeSaveOrUpdateStatement(PreparedStatement pstmt, Book book) {
        try {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthors());
            pstmt.setDate(3, java.sql.Date.valueOf(book.getAcquisition()));
            pstmt.setShort(4, book.getPages());
            pstmt.setShort(5, book.getYear());
            pstmt.setByte(6, book.getEdition());
            pstmt.setBigDecimal(7, book.getPrice());
            pstmt.setLong(8, book.getId());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "javacrude/Book/composeSaveOrUpdateStatement: " + e.getMessage());
        }
    }

    @Override
    public Book extractObject(ResultSet rs) {
        Book book = new Book();
        try {
            book.setId(rs.getLong("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthors(rs.getString("authors"));
            book.setAcquisition(rs.getDate("acquisition").toLocalDate());
            book.setPages(rs.getShort("pages"));
            book.setYear(rs.getShort("year"));
            book.setEdition(rs.getByte("edition"));
            book.setPrice(rs.getBigDecimal("price"));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "javacrude/Book/extractObject: " + e.getMessage());
        }
        return book;
    }

    @Override
    public List<Book> extractObjects(ResultSet rs) {
        List<Book> books = new ArrayList<>();
        try {
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthors(rs.getString("authors"));
                book.setAcquisition(rs.getDate("acquisition").toLocalDate());
                book.setPages(rs.getShort("pages"));
                book.setYear(rs.getShort("year"));
                book.setEdition(rs.getByte("edition"));
                book.setPrice(rs.getBigDecimal("price"));
                books.add(book);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "javacrude/Book/extractObject: " + e.getMessage());
        }
        return books;
    }

    public void printBook() {
        if (this.id != -1) {
            System.out.println("Book ID: " + getId());
            System.out.println("Title: " + getTitle());
            System.out.println("Authors: " + getAuthors());
            System.out.println("Acquisition Date: " + getAcquisition());
            System.out.println("Pages: " + getPages());
            System.out.println("Year: " + getYear());
            System.out.println("Edition: " + getEdition());
            System.out.println("Price: " + getPrice());
        } else {
            System.out.println("Livro sem informações");
        }
    }

}

