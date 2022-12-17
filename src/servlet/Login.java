package servlet;

import com.alibaba.fastjson.JSONObject;
import database.PreStatement;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "/api/login", value = "/api/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ResultSet resultSet;
        PrintWriter res;
        res = response.getWriter();
        String sql = "select id, username, password from yonghu where username = ? and password = ?";
        String str;
        try {
            resultSet = PreStatement.execute(sql, new String[]{username, password});
            if (!resultSet.next()){
                //没有这个用户
                str = "{\"code\":0, \"message\":\"invalid user\"}";
            }
            else{
                //登录成功
                str = "{\"code\":1, \"message\":\"success\"}";
                request.getSession().setAttribute("username", request.getRemoteAddr()+username);
//                添加Cookie
                Cookie cookie = new Cookie("JSESSIONID", request.getSession().getId());
                cookie.setMaxAge(60*100);
                cookie.setPath("/");
                response.addCookie(cookie);
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
