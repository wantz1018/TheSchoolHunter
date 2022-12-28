package servlet;

import beans.Mission;
import beans.MissionsList;
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

@WebServlet(name = "api/getMyMission", value = "/api/getMyMission")
public class GetMyMission extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("username");
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");

        if (page == null) page = "1";
        else if ("".equals(page))
            page = "1";

        if (limit == null) limit = "10";
        else if ("".equals(limit))
                limit = "10";
        System.out.println(page);
        System.out.println(limit);

        MissionsList sendMissionsList = new MissionsList();
        MissionsList receiveMissionsList = new MissionsList();
        String sql = "select * from users where u_id = ?";
        try {
            if (PreStatement.execute(sql, new String[]{uid}).next()){
                sql = "select * from ttasks where status != '已结束' and check_status = '已通过' and m_id in (select m_id from record where send_id = ?) limit " + limit + " offset " +  (Integer.parseInt(page) - 1) * Integer.parseInt(limit);
                System.out.println(sql);
                ResultSet sendSet = PreStatement.execute(sql, new String[]{uid});
                sql = "select * from ttasks where status != '已结束' and check_status = '已通过' and m_id in (select m_id from record where receive_id = ?) limit " + limit + " offset " +  (Integer.parseInt(page) - 1) * Integer.parseInt(limit);
                ResultSet receiveSet = PreStatement.execute(sql, new String[]{uid});
                receiveSet.last();
                response.setHeader("x-total-count", String.valueOf(receiveSet.getRow()));
                receiveSet = PreStatement.execute(sql, new String[]{uid});
                List<Mission> sendMissions = new ArrayList<Mission>();
                while (sendSet.next()){
                    Mission mission = new Mission();
                    mission.setId(sendSet.getString("m_id"));
                    mission.setIcon(sendSet.getString("picurl"));
                    mission.setTitle(sendSet.getString("title"));
                    mission.setContent(sendSet.getString("text"));
                    mission.setMdate(sendSet.getString("deadline"));
                    mission.setMplace(sendSet.getString("location"));
                    mission.setRewards(sendSet.getString("points"));
                    mission.setCheckStatus(sendSet.getString("check_status"));
                    sendMissions.add(mission);
                }
                sendMissionsList.setMission(sendMissions);
                String str_send = JSONObject.toJSONString(sendMissionsList);

                List<Mission> receiveMissions = new ArrayList<Mission>();
                while (receiveSet.next()){
                    Mission mission = new Mission();
                    mission.setId(receiveSet.getString("m_id"));
                    mission.setIcon(receiveSet.getString("picurl"));
                    mission.setTitle(receiveSet.getString("title"));
                    mission.setContent(receiveSet.getString("text"));
                    mission.setMdate(receiveSet.getString("deadline"));
                    mission.setMplace(receiveSet.getString("location"));
                    mission.setRewards(receiveSet.getString("points"));
                    mission.setCheckStatus(receiveSet.getString("check_status"));
                    receiveMissions.add(mission);
                }
                receiveMissionsList.setMission(receiveMissions);
                String str_receive = JSONObject.toJSONString(receiveMissionsList);

                ResMessage.resp(response, "{\"code\":1,\"message\":\"success\",\"data\":{\"postingMissions\":" + str_send + ", \"acceptedMissions\":" + str_receive + "}}");

            }
        } catch (SQLException | ClassNotFoundException e) {
            ResMessage.resp(response, "{\"code\":0, \"message\":\"error\"}");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
