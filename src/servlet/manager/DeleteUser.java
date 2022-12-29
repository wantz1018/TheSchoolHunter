package servlet.manager;

import database.PreStatement;
import functions.ResMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "api/deleteUser", value = "/api/deleteUser")
public class DeleteUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String sql = "delete from users where u_id = ?";
        try {
            PreStatement.execute(sql, new String[]{username});
            sql = "delete from records where send_id = ?";
            PreStatement.execute(sql, new String[]{username});
            sql = "delete from records where receive_id = ?";
            PreStatement.execute(sql, new String[]{username});
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
