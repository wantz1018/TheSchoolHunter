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
        String uid = request.getParameter("username");
        String id = request.getParameter("missionId");
        String sql = "update ttasks set status = '已结束' where m_id = ? and check_status = '已通过'";
        String str = "";
        try {
            PreStatement.execute(sql, new String[]{id});
            str = "{\"code\":1, \"message\":\"success\"}";
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
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
