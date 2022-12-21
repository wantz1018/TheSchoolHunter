package servlet;

import database.NonPreStatement;
import database.PreStatement;
import functions.ResMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "api/addMission", value = "/api/addMission")
public class AddMission extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String title = request.getParameter("title");
        String mdate = request.getParameter("mdate");
        String content = request.getParameter("content");
        String icon = request.getParameter("icon");
        String mplace = request.getParameter("mplace");
        String rewards = request.getParameter("rewards");

        //设置默认值
        if (!(icon == null)){
            if ("".equals(icon)) icon = "https://wantz-pic.oss-cn-shenzhen.aliyuncs.com/tsh/TSH.png";
        }

        try{
            String sql = "insert into mission(icon, content, title, mdate, mplace, rewards) values(?, ?, ?, ?, ?, ?)";
            String[] params = new String[]{
                    icon, content, title, mdate, String.valueOf(mplace), rewards
            };
            PreStatement.execute(sql, params);
            ResultSet resultSet = NonPreStatement.execute("select max(id) as id from mission");
            resultSet.next();
            String missionID = resultSet.getString("id");
            ResMessage.resp(response, "{\"code\":1, \"message\":\"success\", \"data\":{\"missionID\":\"" + missionID + "\"}}");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            ResMessage.resp(response, "{\"code\":41,\"message\":\"error\", \"data\":{}}");
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
