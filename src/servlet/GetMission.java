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
        String req_status = request.getParameter("status");
        String req_check_status = request.getParameter("checkStatus");

        MissionsList missionsList = new MissionsList();
        try {
            String sql = sqlStringGenerate(req_page, req_limit, req_place, req_timeRange, req_orderName, req_order, req_status, req_check_status);
            ResultSet resultSet = NonPreStatement.execute(sql);
            response.setHeader("x-total-count", String.valueOf(resultSet.getRow()));
            List<Mission> missions = new ArrayList<Mission>();
            while (resultSet .next()){
                Mission mission = new Mission();
                mission.setId(resultSet.getString("m_id"));
                mission.setIcon(resultSet.getString("picurl"));
                mission.setTitle(resultSet.getString("title"));
                mission.setContent(resultSet.getString("text"));
                mission.setMdate(resultSet.getString("deadline"));
                mission.setMplace(resultSet.getString("location"));
                mission.setRewards(resultSet.getString("points"));
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

    private String sqlStringGenerate(String page, String limit, String place, String timeRange, String orderName, String order, String status, String checkStatus){
        try {
            String sql = "select m_id, picurl, title, text, deadline, location, points from ttasks ";
            if (place != null) if (place.length() != 0) sql = sql + " and location like '%" + place + "%'";
            if (timeRange != null)
                if (!"all".equals(timeRange)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date before = new Date();
                    Date after = TransTimeRange.future(before, timeRange);
                    sql = sql + " and deadline between '" + sdf.format(before) + "' and '" + sdf.format(after) + "'";
            }
            if (status != null)
                if (!"".equals(status))
                    sql = sql + " and status = " + status;
            if (checkStatus != null)
                if (!"".equals(checkStatus))
                    sql = sql + " and check_status = " + checkStatus;
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
