package servlet;

import database.NonPreStatement;
import database.PreStatement;
import functions.ResMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

@WebServlet(name = "api/finishMission", value = "/api/finishMission")
public class FinMission extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String u_id = request.getParameter("username");
        String m_id = request.getParameter("missionId");
        String rank = request.getParameter("rank");
        assert m_id != null;
        String sql = "select * from record where m_id = ?";
        try {
            ResultSet resultSet_1 = PreStatement.execute(sql, new String[]{m_id});
            if (resultSet_1.next()){
                ResultSet resultSet;
                sql = "select points from ttasks where m_id = ?";
                resultSet = PreStatement.execute(sql, new String[]{m_id});
                String send_id = resultSet_1.getString("send_id");
                String receive_id = resultSet_1.getString("receive_id");
                resultSet.next();
                String points = resultSet.getString("points");
                //获取发送方积分
                sql = "select balance from users where u_id = ?";
                resultSet = PreStatement.execute(sql, new String[]{send_id});
                resultSet.next();
                String send_balance = resultSet.getString("balance");
                //修改发送方积分
                sql = "update users set balance = ? where u_id = ?";
                PreStatement.execute(sql, new String[]{String.valueOf(Integer.parseInt(send_balance) - Integer.parseInt(points)), send_id});
                //获取接收方积分
                sql = "select balance from users where u_id = ?";
                resultSet = PreStatement.execute(sql, new String[]{receive_id});
                resultSet.next();
                String receive_balance = resultSet.getString("balance");
                //修改接收方积分
                sql = "update users set balance = ? where u_id = ?";
                PreStatement.execute(sql, new String[]{String.valueOf(Integer.parseInt(receive_balance) + Integer.parseInt(points) * (Double.parseDouble(rank) / 5))});
                sql = "update ttasks set status = '已结束' where m_id = ? and check_status = '已通过';" ;
                PreStatement.execute(sql, new String[]{m_id});
                sql = "update record set finishdate = ? where m_id = ?;";
                String time = String.valueOf(new Timestamp(new Date().getTime()));
                PreStatement.execute(sql, new String[]{time, m_id});
                ResMessage.resp(response, "{\"code\":1, \"message\":\"success\", \"data\":{\"finishTime\":\"" + time + "\"}}");
            }
            else{
                ResMessage.resp(response, "{\"code\":0, \"message\":\"error\", \"data\":{}");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
