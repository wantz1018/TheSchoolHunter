package servlet.manager;

import database.PreStatement;
import functions.ResMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "api/changeUser", value = "/api/changeUser")
public class ChangeUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String u_id = request.getParameter("u_id");
        String password = request.getParameter("password");
        String balance = request.getParameter("balance");
        String identity = request.getParameter("identity");

        String sql = "update users set password = ?, balance = ?, identity = ? where u_id = ?";
        try {
            PreStatement.execute(sql, new String[]{password, balance, identity, u_id});
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
