package servlet.manager;

import database.NonPreStatement;
import database.PreStatement;
import functions.ResMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "api/appendMission", value = "/api/appendMission")
public class AppendMission extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        String picurl = request.getParameter("picurl");
        String location = request.getParameter("location");
        String deadline = request.getParameter("deadline");
        String points = request.getParameter("points");
        String status = request.getParameter("status");
        String check_status = request.getParameter("check_status");
        String send_id = request.getParameter("send_id");
        String receive_id = request.getParameter("receive_id");
        String postdate = request.getParameter("postdate");
        String acceptdate = request.getParameter("acceptdate");
        String finishdate = request.getParameter("finishdate");

        String sql;
        try{
            sql = "insert into ttaske(title, deadline, location, text, picurl, points, status, check_status) values(?, ?, ?, ?, ?, ?, ?, ?)";
            PreStatement.execute(sql, new String[]{title, deadline, location, text, picurl, points, status, check_status});
            sql = "select max(m_id) as id from ttasks";
            ResultSet resultSet = NonPreStatement.execute(sql);
            resultSet.next();
            String m_id = resultSet.getString("id");
            sql = "insert into record(m_id, receive_id, send_id, acceptdate, postdate, finishdate) values(?, ?, ?, ?, ?, ?)";
            PreStatement.execute(sql, new String[]{m_id, receive_id, send_id, acceptdate, postdate, finishdate});
            ResMessage.resp(response, "{\"code\":1, \"message\":\"success\"}");
        } catch (SQLException | ClassNotFoundException e) {
            ResMessage.resp(response, "{\"code\":0, \"message\":\"error\"}");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
