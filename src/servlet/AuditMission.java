package servlet;

import database.PreStatement;
import functions.ResMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "auditMission", value = "/api/auditMission")
public class AuditMission extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String UID = request.getParameter("u_id");
        String MId = request.getParameter("m_id");
        String status = request.getParameter("status");
        String sql = "select identity from users where u_id = ?";
        try{
            ResultSet resultSet = PreStatement.execute(sql, new String[]{UID});
            resultSet.next();
            if (resultSet.getString("identity").contains("审核员") || resultSet.getString("identity").contains("管理员")){
                sql = "update ttasks set check_status = ? where m_id = ?";
                PreStatement.execute(sql, new String[]{status, MId});
                ResMessage.resp(response, "{\"code\":1, \"message\":\"success\"}");
            }
            else{
                ResMessage.resp(response, "{\"code\":0, \"message\":\"error\"}");
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
