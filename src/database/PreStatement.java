package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PreStatement {

    public ResultSet execute(String id) throws SQLException, ClassNotFoundException {
        Connection connection;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        connection = DatabaseConnection.getConnection();
        String sql = "select * from mission where id = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        preparedStatement.execute();
        resultSet = preparedStatement.getResultSet();
        return resultSet;
    }
    public static ResultSet execute(String sql, String[] params) throws SQLException, ClassNotFoundException {
        Connection connection;
        PreparedStatement preparedStatement;
        connection = DatabaseConnection.getConnection();
        preparedStatement = connection.prepareStatement(sql);
        for (int i=1; i<=params.length; i++){
            preparedStatement.setString(i, params[i-1]);
        }
        preparedStatement.execute();
        return preparedStatement.getResultSet();
    }

    /**
     * 根据条件查询任务
     * @param place: 任务地点
     * @param timeRange: 时间范围
     * @param order: 排序方式
     * @param limit: 每页条数
     * @param page: 当前第几页
     * @return 查询结果
     */
    public ResultSet getMission(String place, String timeRange, String order, int limit, int page) throws SQLException, ClassNotFoundException {
        Connection connection;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement;
        connection = DatabaseConnection.getConnection();
        String sql = "select id, icon, title, content, mdate, mplace, rewards from mission where";
        return resultSet;
    }
    

}
