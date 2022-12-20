package servlet;

import database.PreStatement;
import functions.ResMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "api/delMission", value = "/api/delMission")
public class DelMission extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String sql = "update mission set status = 'del' where id = ?";
        String str = "";
        try {
            PreStatement.execute(sql, new String[]{id});
            str = "{\"code\":1, \"message\":\"success\"}";
        } catch (SQLException | ClassNotFoundException e) {
            str = "{\"code\":0, \"message\":\"error\"}";
            throw new RuntimeException(e);
        } finally {
            ResMessage.resp(response, str);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
