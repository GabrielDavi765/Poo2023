package trabalholivro.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import trabalholivro.Book;

public class Dao implements lDao {

    @Override
    public long saveOrUpdate(Book book) {
        String sql = "SELECT id FROM book WHERE id = ?";
        try (Connection conn = new ConnectionDao().conectaBD();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            
            pstm.setLong(1, book.getId());
          
            try (ResultSet rs = pstm.executeQuery()) {

                if (rs.next()) {
                    try {
                        sql = book.getUpdateStatement();
                        try (PreparedStatement updatePstm = conn.prepareStatement(sql)) {
                            updatePstm.setString(1, book.getTitle());
                            updatePstm.setString(2, book.getAuthors());
                            updatePstm.setDate(3, java.sql.Date.valueOf(book.getAcquisition()));
                            updatePstm.setShort(4, book.getPages());
                            updatePstm.setShort(5, book.getYear());
                            updatePstm.setByte(6, book.getEdition());
                            updatePstm.setBigDecimal(7, book.getPrice());
                            updatePstm.executeUpdate();
                            System.out.println("\nAtualizado\n");
                        }
                    } catch (SQLException erro) {
                        Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, erro);
                    }
                } else {
                    sql = book.getSaveStatement();
                    try (PreparedStatement insertPstm = conn.prepareStatement(sql)) {
                        insertPstm.executeUpdate();
                        System.out.println("\nInserido\n");
                    } catch (SQLException erro) {
                        Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, erro);
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, e);
        }

        return book.getId();
    }

    @Override
    public Book findById(long id) {
        try (Connection conn = new ConnectionDao().conectaBD();
             PreparedStatement pstm = conn.prepareStatement("SELECT * FROM book WHERE id = ?")) {

            pstm.setLong(1, id);
            try (ResultSet rs = pstm.executeQuery()) {

                if (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getLong("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthors(rs.getString("authors"));
                    book.setAcquisition(rs.getDate("acquisition").toLocalDate());
                    book.setPages(rs.getShort("pages"));
                    book.setYear(rs.getShort("year"));
                    book.setEdition(rs.getByte("edition"));
                    book.setPrice(rs.getBigDecimal("price"));
                    return book;
                } else {
                    Book voidBook = new Book();
                    voidBook.setId(-1);
                    return voidBook;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }

    @Override
    public ArrayList<Book> findAll() {
        try (Connection conn = new ConnectionDao().conectaBD();
             PreparedStatement pstm = conn.prepareStatement("SELECT * FROM book");
             ResultSet rs = pstm.executeQuery()) {

            ArrayList<Book> books = new ArrayList<>();

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
            return books;
        } catch (SQLException e) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, e);
        }

        return new ArrayList<>(); // Retorna uma lista vazia em caso de erro
    }

    @Override
    public void delete(long id) {
        try (Connection conn = new ConnectionDao().conectaBD();
             PreparedStatement pstm = conn.prepareStatement("DELETE FROM book WHERE id = ?")) {

            pstm.setLong(1, id);
            int rowsAffected = pstm.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Excluído");
            } else {
                System.out.println("ID inválido");
            }
        } catch (SQLException e) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}