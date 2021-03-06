import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Calendar;  
import java.util.Date;
public class MDateClass{
    private String[] weekDays = {"Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat"};
    public String getDate(){
        Calendar cal = Calendar.getInstance();
        Date dt=new Date();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;   
        if (w < 0) w = 0;    
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
        String dateNowStr = sdf.format(dt);
        System.out.println("格式化后的日期：" + dateNowStr);
        return weekDays[w]+"@"+dateNowStr;
    }
}
