package net.miwashi.crud.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BookRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger( BookRepository.class );
    private final String CONNECTION_STRING;
    private final Properties connectionProps;

    public BookRepository(){
        CONNECTION_STRING = System.getProperty("net.miwashi.crud.connectionstring");
        connectionProps = new Properties();
        connectionProps.put("user", System.getProperty("net.miwashi.crud.user"));
        connectionProps.put("password", System.getProperty("net.miwashi.crud.password"));
    }

    public Book create(Book Book) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(CONNECTION_STRING, connectionProps);
            var stmt = con.prepareStatement("INSERT INTO BOOKS(TITLE) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, Book.getTitle());
            stmt.execute();
            var rs = stmt.getGeneratedKeys();
            if(rs.next()){
                int bookId = rs.getInt(1);
                Book.setId(bookId);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception thrown in Book.create", e);
        } finally {
            try {
                con.close();
            }catch(Exception ignore){}
        }
        return Book;
    }

    public Book findById(int id) {
        Connection connection = null;
        Book Book = null;
        try{
            connection = DriverManager.getConnection(CONNECTION_STRING, connectionProps);
            var stmt = connection.prepareStatement("SELECT ID, TITLE FROM BOOKS WHERE ID = ?");
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if(rs.next()){
                String title = rs.getString(2);
                int balance = rs.getInt(3);
                Book = new Book(title);
                Book.setId(id);
            }
        }catch(Exception e){
            LOGGER.error("Exception thrown in Book.findById", e);
        }finally{
            try {
                connection.close();
            }catch(Exception ignore){}
        }
        return Book;
    }

    public Book findByTitle(String searchedTitle) {
        Connection connection = null;
        Book Book = null;
        try{
            connection = DriverManager.getConnection(CONNECTION_STRING, connectionProps);
            var stmt = connection.prepareStatement("SELECT ID, TITLE FROM BOOKS WHERE TITLE = ?");
            stmt.setString(1, searchedTitle);
            var rs = stmt.executeQuery();
            if(rs.next()){
                int id = rs.getInt(1);
                String title = rs.getString(2);
                Book = new Book(title);
                Book.setId(id);
            }
        }catch(Exception e){
            LOGGER.error("Exception thrown in Book.findByTitle", e);
        }finally{
            try {
                connection.close();
            }catch(Exception ignore){}
        }
        return Book;
    }
}
