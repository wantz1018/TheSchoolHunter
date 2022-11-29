package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection connection = null;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://sh-cynosdbmysql-grp-fdbj0l3y.sql.tencentcdb.com:23129/st?useUnicode=true&characterEncoding=utf-8";
        String username = "web";
        String password = "Web123456";
        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}
