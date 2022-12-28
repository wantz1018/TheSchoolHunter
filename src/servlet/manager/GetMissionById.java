package servlet.manager;

import beans.AllMission;
import beans.AllMissionList;
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

@WebServlet(name = "api/getMissionById", value = "/api/getMissionById")
public class GetMissionById extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String missionId = request.getParameter("missionId");
        try{
            String sql = "select record.m_id, title, picurl, text, deadline, location, points, send_id, receive_id, postdate, acceptdate, finishdate, status, check_status from record, ttasks where record.m_id = ? and ttasks.m_id = ?";
            ResultSet resultSet = PreStatement.execute(sql, new String[]{missionId, missionId});
            AllMissionList allMissionsList = new AllMissionList();
            List<AllMission> allMissions = new ArrayList<AllMission>();
            while (resultSet .next()){
                AllMission allMission = new AllMission();
                allMission.setId(resultSet.getString("m_id"));
                allMission.setIcon(resultSet.getString("picurl"));
                allMission.setTitle(resultSet.getString("title"));
                allMission.setContent(resultSet.getString("text"));
                allMission.setMdate(resultSet.getString("deadline"));
                allMission.setMplace(resultSet.getString("location"));
                allMission.setRewards(resultSet.getString("points"));
                allMission.setSendId(resultSet.getString("receive_id"));
                allMission.setReceiveId(resultSet.getString("receive_id"));
                allMission.setAcceptDate(resultSet.getString("acceptdate"));
                allMission.setPostDate(resultSet.getString("postdate"));
                allMission.setFinishDate(resultSet.getString("finishdate"));
                allMission.setStatus(resultSet.getString("status"));
                allMission.setCheckStatus(resultSet.getString("check_status"));
                allMissions.add(allMission);
            }
            allMissionsList.setMission(allMissions);
            String str = JSONObject.toJSONString(allMissionsList);

            ResMessage.resp(response, "{\"code\":1,\"message\":\"success\",\"data\":"+ str+"}");
        } catch (SQLException | ClassNotFoundException e) {
            ResMessage.resp(response, "{\"code\":0,\"message\":\"error\"}");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
