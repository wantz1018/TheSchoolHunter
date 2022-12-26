package servlet;

import database.PreStatement;
import functions.ResMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "register", value = "/api/register")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ResultSet resultSet;
        String sql = "select password from users where u_id = ?";
        String str;
        try {
            resultSet = PreStatement.execute(sql, new String[]{username});
            if  (resultSet.next()){
                str = "{\"code\":\"12\",\"message\":\"this user name already exists\"}";
            }
            else{
                sql = "insert into users(u_id, password) values(?, ?)";
                resultSet = PreStatement.execute(sql, new String[]{username, password});
                str = "{\"code\":\"1\",\"message\":\"success\"}";
            }
            ResMessage.resp(response, str);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
