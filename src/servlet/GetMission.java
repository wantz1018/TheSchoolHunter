package servlet;

import beans.Mission;
import beans.MissionsList;

import database.NonPreStatement;
import database.PreStatement;
import com.alibaba.fastjson.JSONObject;
import functions.ResMessage;
import functions.TransTimeRange;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/api/getMissionList")
public class GetMission extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求参数
        String req_page = request.getParameter("page");
        String req_limit = request.getParameter("limit");
        String req_place = request.getParameter("place");
        String req_timeRange = request.getParameter("timeRange");
        String req_orderName = request.getParameter("orderName");
        String req_order = request.getParameter("order");

        MissionsList missionsList = new MissionsList();
        try {
            String sql = sqlStringGenerate(req_page, req_limit, req_place, req_timeRange, req_orderName, req_order);
            ResultSet resultSet = NonPreStatement.execute(sql);
            List<Mission> missions = new ArrayList<Mission>();
            while (resultSet.next()){
                Mission mission = new Mission();
                mission.setId(resultSet.getString("id"));
                mission.setIcon(resultSet.getString("icon"));
                mission.setTitle(resultSet.getString("title"));
                mission.setContent(resultSet.getString("content"));
                mission.setMdate(resultSet.getString("mdate"));
                mission.setMplace(resultSet.getString("mplace"));
                mission.setRewards(resultSet.getString("rewards"));
                missions.add(mission);
            }
            missionsList.setMission(missions);
            String str = JSONObject.toJSONString(missionsList);

            ResMessage.resp(response, "{\"code\":1,\"message\":\"success\",\"data\":"+ str+"}");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private String sqlStringGenerate(String page, String limit, String place, String timeRange, String orderName, String order){
        try {
            String sql = "select id, icon, title, content, mdate, mplace, rewards from mission where status = 'free' ";
            if (place != null) if (place.length() != 0) sql = sql + " and mplace like '%" + place + "%'";
            if (timeRange != null)
                if (!"all".equals(timeRange)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date before = new Date();
                    Date after = TransTimeRange.future(before, timeRange);
                    sql = sql + " and mdate between '" + sdf.format(before) + "' and '" + sdf.format(after) + "'";
            }
            if (orderName != null){
                if (order == null) order = "asc";
                sql = sql + " order by " + orderName + " " + order;
            }
            if (page == null) page = "1";
            if (limit == null) limit = "5";
            sql = sql + " limit " + limit + " offset " + (Integer.parseInt(page) - 1) * Integer.parseInt(limit);
            System.out.println(sql);
            return sql;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
