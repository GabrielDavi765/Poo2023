package trabalholivro.DAO;

import java.util.List;
import trabalholivro.Book;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface BookDao {

    String getSaveStatement();
    String getUpdateStatement();
    String getFindByIdStatement();
    String getFindAllStatement();
    String getDeleteStatement();

    void composeSaveOrUpdateStatement(PreparedStatement pstmt, Book book);

    Book extractObject(ResultSet rs);
    List<Book> extractObjects(ResultSet rs);
}