package fyp.tingli.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import org.springframework.context.support.StaticApplicationContext;

public class CommonTool {
	
	private static SimpleDateFormat DefaultDateFormat=new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat DefaultTimeFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public static String dateToStr(Date date)
	{
		String result="";
		if(date!=null)
		{
			result = DefaultDateFormat.format(date);
		}
		return result;
	}
	public static String timeToStr(Date time)
	{
		String result="";
		if(time!=null)
		{
			result = DefaultTimeFormat.format(time);
		}
		return result;
	}
	public static Date strToDate(String date) throws ParseException
	{
		Date result;
		if(date==null || date.equals(""))
		{
			result = new Date();
		}
		else {
			result = DefaultDateFormat.parse(date);
		}
		return result;
	}
	
	public static Date strToTime(String time) throws ParseException
	{
		Date result;
		if(time==null || time.equals(""))
		{
			result = new Date();
		}
		else {
			result = DefaultTimeFormat.parse(time);
		}
		return result;
	}
	

}
