package functions;

import database.PreStatement;

import java.sql.SQLException;

public class SaveLog {
    public static void save(String operatorID, String operationText) throws SQLException, ClassNotFoundException {
        String sql = "insert into log(uid, event) values(?, ?)";
        PreStatement.execute(sql, new String[]{operatorID, operationText});
    }
}
