package servlet.manager;

import beans.User;
import beans.UserList;
import com.alibaba.fastjson.JSONObject;
import database.PreStatement;
import functions.ResMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "api/getUserById", value = "/api/getUserById")
public class GetUserById extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String sql = "select u_id, password, balance, identity from users where u_id = ?";
        try {
            ResultSet resultSet = PreStatement.execute(sql, new String[]{username});
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
            ResMessage.resp(response, "{\"code\":00, \"message\":\"error\",\"data\":{}}");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
