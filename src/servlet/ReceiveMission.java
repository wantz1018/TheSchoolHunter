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

@WebServlet(name = "receiveMission", value = "/api/receiveMission")
public class ReceiveMission extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String missionId = request.getParameter("missionId");

        try {
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            String sql = "insert into user_mission(userid, missionid, start_time) values(?, ?, ?)";
            try {
                PreStatement.execute(sql, new String[]{userId, missionId, String.valueOf(timestamp)});
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            sql = "select max(id) as id from user_mission";
            String id = "-1";
            try {
                ResultSet resultSet = NonPreStatement.execute(sql);
                if (resultSet.next()) {
                    id = resultSet.getString("id");
                }
                ResMessage.resp(response, "{\"code\":1, \"message\":\"success\", \"data\":{\"id\":" + id + "\"start_time\":\"" + timestamp + "\"");
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
