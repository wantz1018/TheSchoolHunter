package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NonPreStatement {
    public static ResultSet execute(String sql) throws SQLException, ClassNotFoundException {
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        connection = DatabaseConnection.getConnection();
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = statement.executeQuery(sql);
        return resultSet;
    }
}
