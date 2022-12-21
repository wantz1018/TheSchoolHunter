package functions;

import org.apache.http.concurrent.Cancellable;

import java.util.Calendar;
import java.util.Date;

public class TransTimeRange {

    public static Date future(Date before, String timeRange){
        Date after;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(before);
        if ("today".equals(timeRange)) {
            calendar.add(Calendar.DATE, 0);
        }
        else if ("threeDays".equals(timeRange)){
            calendar.add(Calendar.DATE, 2);
        }
        else if ("fiveDays".equals(timeRange)){
            calendar.add(Calendar.DATE, 4);
        }
        else if ("oneWeek".equals(timeRange)) {
            calendar.add(Calendar.DATE, 6);
        }
        after = calendar.getTime();
        return after;
    }
}
