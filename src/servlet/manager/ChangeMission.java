package servlet.manager;

import database.PreStatement;
import functions.ResMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "api/changeMission", value = "/api/changeMission")
public class ChangeMission extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String missionId = request.getParameter("missionId");
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
        try {
            //修改任务表
            sql = "update ttasks set title = ?, deadline = ?, location = ?, text = ?, picurl = ?, points = ?, status = ?, check_status = ? where m_id = ?";
            PreStatement.execute(sql, new String[]{title, deadline, location, text, picurl, points, status, check_status, missionId});
            //修改领取表
            sql = "update record set receive_id = ?, send_id = ?, acceptdate = ?, postdate = ?, finishdate = ? where m_id = ?";
            PreStatement.execute(sql, new String[]{receive_id, send_id, acceptdate, postdate, finishdate, missionId});
            ResMessage.resp(response, "{\"code\":1, \"message\":\"success\"}");
        } catch (SQLException | ClassNotFoundException e) {
            ResMessage.resp(response, "{\"code\":0, \"message\":\"error\"}");
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
