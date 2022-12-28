package servlet.manager;

import database.NonPreStatement;
import functions.ResMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "api/completelyDelete", value = "/api/completelyDelete")
public class CompletelyDelete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String missionId = request.getParameter("MissionId");
        String sql = "delete from ttasks where m_id = " + missionId;
        try {
            NonPreStatement.execute(sql);
            ResMessage.resp(response, "{\"code\":1,\"message\":\"success\"}");
        } catch (SQLException | ClassNotFoundException e) {
            ResMessage.resp(response, "{\"code\":0,\"message\":\"error\"}");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
