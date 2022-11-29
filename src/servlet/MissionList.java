package servlet;

import beans.Mission;
import beans.MissionsList;

import database.PreStatement;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//@WebServlet(name = "missionlist", value = "/missionlist")
@WebServlet("/categories/missionlist")
public class MissionList extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

//        获取请求参数
        String req_page = request.getParameter("_page");
        String req_limit = request.getParameter("_limit");
        String req_place = request.getParameter("_place");
        String req_timeRange = request.getParameter("_timeRange");
        String req_order = request.getParameter("_order");

        MissionsList missionsList = new MissionsList();
        PrintWriter res;
        res = response.getWriter();
        PreStatement preStatement = new PreStatement();
        try {
            ResultSet resultSet = preStatement.execute("1");
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
            res.write(str);
            res.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        res.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
