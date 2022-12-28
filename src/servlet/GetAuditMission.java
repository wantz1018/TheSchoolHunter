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

@WebServlet(name = "getAuditMission", value = "/api/getAuditMission")
public class GetAuditMission extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String u_id = request.getParameter("username");
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");

        if (page != null)
            if ("".equals(page))
                page = "1";
        else page = "1";

        if (limit != null)
            if ("".equals(limit))
                limit = "10";
        else limit = "10";

        assert page != null;
        assert limit != null;
        MissionsList missionsList = new MissionsList();
        String sql = "select * from ttasks where check_status = '待审核' and m_id not in (select m_id from record where send_id = ?) limit " + limit + " offset " + (Integer.parseInt(page) - 1) * Integer.parseInt(limit);
        try {
            ResultSet resultSet = PreStatement.execute(sql, new String[]{u_id});
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
            ResMessage.resp(response, "{\"code\":0,\"message\":\"error\",\"data\":{}");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
