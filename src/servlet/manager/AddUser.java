package servlet.manager;

import database.PreStatement;
import functions.ResMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "api/addUser", value = "/api/addUser")
public class AddUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String balance = request.getParameter("balance");
        String identity = request.getParameter("identity");
        String sql = "insert into users(u_id, password, balance, identity) values(?, ?, ?, ?)";
        try {
            PreStatement.execute(sql, new String[]{username, password, balance, identity});
            ResMessage.resp(response, "\"code\":1, \"message\":\"success\"}");
        } catch (SQLException | ClassNotFoundException e) {
            ResMessage.resp(response, "\"code\":0, \"message\":\"error\"}");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
