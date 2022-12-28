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

        String receiverId = request.getParameter("username");
        String missionId = request.getParameter("missionId");

        try {
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            String sql = "update record set acceptdate = ? and receive_id = ? where m_id = ?";
            try {
                PreStatement.execute(sql, new String[]{String.valueOf(timestamp), receiverId, missionId});
                sql = "update ttasks set status = ? where m_id = ?";
                PreStatement.execute(sql, new String[]{"进行中", missionId});
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            ResMessage.resp(response, "{\"code\":1, \"message\":\"success\", \"data\":{\"id\":\"" + missionId + "\",start_time\":\"" + timestamp + "\"}}");
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
