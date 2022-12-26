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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "api/addMission", value = "/api/addMission")
public class AddMission extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = String.valueOf(new Date().getTime());
        String user_id = request.getParameter("UserID");
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
            String sql = "insert into ttasks(m_id, picurl, text, title, deadline, location, points, status, check_status) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String[] params = new String[]{
                    id, icon, content, title, mdate, String.valueOf(mplace), rewards, "未领取", "待审核"
            };
            PreStatement.execute(sql, params);
            ResultSet resultSet = NonPreStatement.execute("select max(m_id) as id from ttasks");
            resultSet.next();
            String missionID = resultSet.getString("id");
            sql = "insert into record(m_id, send_id, postdate) values(?, ?, ?)";
            PreStatement.execute(sql, new String[]{missionID, user_id, String.valueOf(new Timestamp(new Date().getTime()))});
            ResMessage.resp(response, "{\"code\":1, \"message\":\"success\", \"data\":{\"missionID\":\"" + missionID + "\"}}");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            ResMessage.resp(response, "{\"code\":0,\"message\":\"error\", \"data\":{}}");
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
