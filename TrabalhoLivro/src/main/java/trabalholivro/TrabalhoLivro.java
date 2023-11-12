package trabalholivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import trabalholivro.DAO.Dao;

public class TrabalhoLivro {

    public static void main(String[] args) {

        Dao executeDao = new Dao();

        createAndSaveBook(executeDao, "Game of Thrones Volume 2", "George R. R. Martin", LocalDate.now().plusDays(3), 416 , 2010, 2, "19.58");
        createAndSaveBook(executeDao, "The Hunger Games", "Suzanne Collins", LocalDate.now(), 400, 2009, 1, "29.99");
        createAndSaveBook(executeDao, "A Arte da Guerra", "Sun Tzu", LocalDate.now(), 128, 401 , 1, "27.49");
        createAndSaveBook(executeDao, "O Príncipe", "Nicolau Maquiavel", LocalDate.now(), 112, 1513, 1, "19.90");

        System.out.println("======== O livro foi adicionado ao banco de dados ========");   
        executeDao.findAll().forEach(Book::printBook);
        
        updateBook(executeDao, 2, "Animal Farm", "George Orwell", LocalDate.now(), 112, 1945, 1, "10.99");

        System.out.println("======== Busca ========");
        System.out.println("======== Busca ========");
        Book foundBook = executeDao.findById(3);
            if (foundBook != null) {
                 foundBook.printBook();
            } else {
                 System.out.println("========O Livro não encontrado========.");
}

        System.out.println("========Todos os livros adicionados ao banco de dados ========");
        executeDao.findAll().forEach(Book::printBook);

        executeDao.delete(2);
    }

    private static void createAndSaveBook(Dao executeDao, String title, String authors, LocalDate acquisition, int pages, int year, int edition, String price) {
        try {
            Book book = new Book(title, authors, acquisition, (short) pages, (short) year, (byte) edition, new BigDecimal(price));
            executeDao.saveOrUpdate(book);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateBook(Dao executeDao, long id, String title, String authors, LocalDate acquisition, int pages, int year, int edition, String price) {
        try {
            Book book = new Book(id, title, authors, acquisition, (short) pages, (short) year, (byte) edition, new BigDecimal(price));
            executeDao.saveOrUpdate(book);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
