import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "missionlist", value = "/missionlist")
public class MissionList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        String jsonStr = "{\"missionlist\":[{" +
                "\"id\":\"https://lptexas.top/img/cover/default_cover.jpg\"," +
                "\"title\":\"我是标题\"," +
                "\"content\":\"我是一段任务描述\"," +
                "\"mdate\":\"2022-11-10\"," +
                "\"mplace\":\"三江楼1217\"," +
                "\"rewards\":\"10\"}]}";
        PrintWriter out;
        out = response.getWriter();
        out.write(jsonStr);
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
