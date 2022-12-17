package servlet;

import database.PreStatement;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "api/register", value = "/api/register")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ResultSet resultSet;
        PrintWriter res;
        res = response.getWriter();
        String sql = "select password from yonghu where username = ?";
        String str;
        try {
            resultSet = PreStatement.execute(sql, new String[]{username});
            if  (resultSet.next()){
                str = "\"message\":\"user already exists\"";
            }
            else{
                /*
                这里可能是添加用户的ID，可能是数据库自己生成，也可能是程序生成
                 */
                sql = "insert into yonghu(username, password) values(?, ?)";
                resultSet = PreStatement.execute(sql, new String[]{username, password});
                str = "\"message\":\"success\"";
            }
            res.write(str);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            res.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
