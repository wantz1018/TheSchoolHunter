package servlet.manager;

import beans.User;
import beans.UserList;
import com.alibaba.fastjson.JSONObject;
import database.NonPreStatement;
import functions.ResMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "api/getUserList", value = "/api/getUserList")
public class GetUserList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        String sql;
        sql = "select u_id, password, balance, identity from users limit 10 offset " + (Integer.parseInt(page) - 1) * 10;
        try {
            ResultSet resultSet = NonPreStatement.execute(sql);
            UserList userList = new UserList();
            List<User> users = new ArrayList<User>();
            while (resultSet.next()){
                User user = new User();
                user.setU_id(resultSet.getString("u_id"));
                user.setPassword(resultSet.getString("password"));
                user.setBalance(resultSet.getString("balance"));
                user.setIdentity(resultSet.getString("identity"));
                users.add(user);
            }
            userList.setUserList(users);
            String str = JSONObject.toJSONString(userList);
            ResMessage.resp(response, "{\"code\":1, \"message\":\"success\",\"data\":"+ str+"}");
        } catch (SQLException | ClassNotFoundException e) {
            ResMessage.resp(response, "{\"code\":0, \"message\":\"error\",\"data\":{}");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
