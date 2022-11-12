package servlet;

import beans.Mission;


import beans.MissionsList;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "missionlist", value = "/missionlist")
public class MissionList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        MissionsList missionsList = new MissionsList();
        Mission mission = new Mission();
        mission.setId("1");
        mission.setIcon("https://lptexas.top/img/cover/default_cover.jpg");
        mission.setTitle("我是标题");
        mission.setContent("我是一段任务内容描述");
        mission.setMdate("2022-11-10");
        mission.setMplace("三江楼1217");
        mission.setRewards("10");

        List missions = new ArrayList();
        missions.add(mission);
        missionsList.setMission(missions);
        String str = JSONObject.toJSONString(missionsList);

        PrintWriter res;
        res = response.getWriter();
        res.write(str);
        res.close();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
