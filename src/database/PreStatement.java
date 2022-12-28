package database;

import java.sql.*;


public class PreStatement {

    public ResultSet execute(String sql) throws SQLException, ClassNotFoundException {
        Connection connection;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        connection = DatabaseConnection.getConnection();
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();
        resultSet = preparedStatement.getResultSet();
        return resultSet;
    }
    public static ResultSet execute(String sql, String[] params) throws SQLException, ClassNotFoundException {
        Connection connection;
        PreparedStatement preparedStatement;
        connection = DatabaseConnection.getConnection();
        preparedStatement = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        for (int i=1; i<=params.length; i++){
            preparedStatement.setString(i, params[i-1]);
        }
        preparedStatement.execute();
        return preparedStatement.getResultSet();
    }
    

}
