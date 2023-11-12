package trabalholivro.DAO;

import java.util.ArrayList;
import trabalholivro.Book;

public interface lDao{
    
    long saveOrUpdate(Book book);
    
    Book findById(long id); 
    ArrayList<Book> findAll();
    
    void delete(long id);
}