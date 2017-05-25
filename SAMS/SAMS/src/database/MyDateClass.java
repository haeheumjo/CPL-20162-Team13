package database;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDateClass {
	private static final String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    public static String getDayOfWeek(){
        Calendar cal = Calendar.getInstance();
        Date dt=new Date();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;   
        if (w < 0) w = 0;    
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
        String dateNowStr = sdf.format(dt);
        System.out.println("̫����������Ѣ��" + dateNowStr);
        return weekDays[w];
    }
	public static String getYYYYMMDD(){
		Date dt=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        return sdf.format(dt);
	}
	public static String getHHMM(){
		Date dt=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(dt);
	}
}
