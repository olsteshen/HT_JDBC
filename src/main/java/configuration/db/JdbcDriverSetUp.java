package configuration.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcDriverSetUp {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/HomeTask";
    private static final String USER = "root";
    private static final String PASSWORD = "temp12345";
    private Connection connection;

    public void dbDriverSetUp() {
        try{
            Class.forName(JDBC_DRIVER);
         } catch(ClassNotFoundException e) {
            System.out.println("Unable to load class.");
            e.printStackTrace();
            return;
        }

        try{
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        } catch(SQLException e)
        {
            e.printStackTrace();
            return;
        }

    }

    public Connection getConnection(){
        return this.connection;
    }

}
