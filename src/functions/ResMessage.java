package functions;

import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResMessage {
    public static void resp(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");


        PrintWriter res;
        res = response.getWriter();
        res.write(message);
        res.close();
    }
}
