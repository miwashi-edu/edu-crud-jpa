package net.miwashi.crud;

import net.miwashi.crud.model.Book;
import net.miwashi.crud.model.BookRepository;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.MySQLContainer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class AppTest {
    protected static final Logger LOGGER = LoggerFactory.getLogger( AppTest.class );
    @Container
    private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:11")
            .withDatabaseName("prop")
            .withUsername("postgres")
            .withPassword("pass")
            .withExposedPorts(5432);

    static class EnvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    String.format("spring.datasource.url=jdbc:postgresql://localhost:%d/prop", postgres.getFirstMappedPort()),
                    "spring.datasource.username=postgres",
                    "spring.datasource.password=pass"
            ).applyTo(applicationContext);
        }
    }
    protected static final String INIT_DB_FILE_NAME = "initdb.sql";

    @BeforeAll
    public static void setUp() throws Exception{
        URL resource = AppTest.class.getClassLoader().getResource(INIT_DB_FILE_NAME);
        File sqlFile = new File(resource.toURI());

        Connection con = DriverManager.getConnection(mySQLContainer.getJdbcUrl(), mySQLContainer.getUsername(), mySQLContainer.getPassword());
        ScriptRunner sr = new ScriptRunner(con);
        Reader reader = new BufferedReader(new FileReader(sqlFile.getAbsolutePath()));
        sr.runScript(reader);

        Statement stmt = con.createStatement();
        ResultSet rs = con.createStatement().executeQuery("SHOW TABLES");
        while(rs.next()) {
            var tableName = rs.getString(1);
            LOGGER.info("Created table " + tableName + " for testing.");
        }
        con.close();

        System.setProperty("net.miwashi.crud.connectionstring", mySQLContainer.getJdbcUrl());
        System.setProperty("net.miwashi.crud.user", mySQLContainer.getUsername());
        System.setProperty("net.miwashi.crud.password", mySQLContainer.getPassword());
    }

    @Test
    public void shouldStoreBooks() throws Exception{
        var books = new BookRepository();
        var book = books.create(new Book("Java for Beginners"));
        assertNotEquals( 0, book.getId(), "Indx must not be zero!");
    }

    @Test
    public void shouldStoreAuthorOnBooks() throws Exception{
        fail("not implemented yet!");
    }

    @Test
    public void shouldStoreMultipleAuthorOnBooks() throws Exception{
        fail("not implemented yet!");
    }

    @Test
    public void shouldStoreLendings() throws Exception{
        fail("not implemented yet!");
    }

    @Test
    public void shouldStoreReturns() throws Exception{
        fail("not implemented yet!");
    }

    @Test
    public void shouldStoreAttributeIdOnBbook() throws Exception{
        fail("not implemented yet!");
    }

    @Test
    public void shouldStoreAttributeIsbnOnBbook() throws Exception{
        fail("not implemented yet!");
    }

    @Test
    public void shouldStoreAttributePublishDateOnBbook() throws Exception{
        fail("not implemented yet!");
    }

    @Test
    public void shouldStoreGradeOnBbook() throws Exception{
        fail("not implemented yet!");
    }

    @Test
    public void shouldStorelendingDateOnBbook() throws Exception{
        fail("not implemented yet!");
    }

    @Test
    public void shouldStoreLibraryCardOnUser() throws Exception{
        fail("not implemented yet!");
    }

    @Test
    public void shouldHaveCorrectFormatOnLibraryCardNumber() throws Exception{
        fail("not implemented yet!");
    }

    @Test
    public void shouldStoreFirstNameOnUser() throws Exception{
        fail("not implemented yet!");
    }

    @Test
    public void shouldStoreSurNameOnUser() throws Exception{
        fail("not implemented yet!");
    }

    @Test
    public void shouldStoreWhichBookOnLendingInformation() throws Exception{
        fail("not implemented yet!");
    }

    @Test
    public void shouldStoreOutDateOnLendingInformation() throws Exception{
        fail("not implemented yet!");
    }

    @Test
    public void shouldStoreReturnDateOnLendingInformation() throws Exception{
        fail("not implemented yet!");
    }
}
