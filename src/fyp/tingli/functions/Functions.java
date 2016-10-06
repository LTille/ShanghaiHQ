package fyp.tingli.functions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import fyp.tingli.message.resp.Article;


public class Functions {
	
/**
 * Initiate http request and then receive result
 * @param requestUrl
 * @return 
 */

	public static String httpRequest(String requestUrl){
		
		StringBuffer buffer = new StringBuffer();
		try{
			URL url = new URL (requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection)url.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setDoOutput(false);
			httpUrlConn.setUseCaches(false);
			
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();
			
			//transfer the inputStream returned into string
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			String str = null;
			while((str=bufferedReader.readLine())!=null){
				
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream=null;
			httpUrlConn.disconnect();
			
		}catch(Exception e){
				
		}
		
		return buffer.toString();
		
	}
	
	/**
	 * Utf编码
	 * 
	 * @param source
	 * @return
	 */
	
	public static String urlEncodeUTF8(String source){
		
		String result = source;
		try{
			result=java.net.URLEncoder.encode(source,"utf-8");
		} catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		return result;
	}
	

	
	/**
	 * API　places
	 * 
	 * @param source
	 * @return
	 */
	public static String searchLocation(String source, String x, String y){
	
		String location = null;
		String requestUrl1 = "http://api.map.baidu.com/place/v2/search?ak=DtMDdk0Ag7YsVnrWlMw0FhLB&query={keyword}&page_size=5&page_num=0&location={x},{y}&radius=2000&output=xml&scope=2&filter=sort_name:distance";
		requestUrl1 = requestUrl1.replace("{keyword}",urlEncodeUTF8(source));
	    requestUrl1 = requestUrl1.replace("{x}",x);
	    requestUrl1 = requestUrl1.replace("{y}",y);
	    
		try
		{
		    location = httpRequest(requestUrl1);
	     		
		}catch(Exception e){
		
			e.printStackTrace();
		}
		return location;
	}
	
	
	public static String searchTrain(String source){
		
		String  train = null;	
		String requestUrl = "http://apis.juhe.cn/train/s?name={keyword}&key=44a866e7e4df7f48753eda8a9f897a3f";
		requestUrl = requestUrl.replace("{keyword}",urlEncodeUTF8(source));

		try
		{
		    train = httpRequest(requestUrl);
		    System.out.println(train);
		
		}
		  
		   catch(Exception e){
			 
			   e.printStackTrace();
		  }

		
			return train;
		
	}
	
	public static ArrayList<Article> searchTrainTicket(String init, String dest, String date){
		
		ArrayList<Article> articleList = new ArrayList<Article>();
		String requestUrl = "http://apis.juhe.cn/train/yp?key=44a866e7e4df7f48753eda8a9f897a3f&dtype=json&from={init}&to={dest}&date={date}&tt=";
	    requestUrl = requestUrl.replace("{init}",urlEncodeUTF8(init));
	    requestUrl = requestUrl.replace("{dest}", urlEncodeUTF8(dest));
	    requestUrl = requestUrl.replace("{date}",date);

		try
		{
		    String json = httpRequest(requestUrl);
		    System.out.println(json);
		    JSONObject dataJson = new JSONObject(json);
		    String reason = dataJson.getString("reason");    
		 
		    JSONArray result = dataJson.getJSONArray("result");	
		    
		    for(int i=0; i<10;i++){
		    	
		    	JSONObject info = result.getJSONObject(i);
		    	
		    	String train_no = info.getString("train_no");
		    	String start_station_name = info.getString("start_station_name");
		    	String end_station_name = info.getString("end_station_name");
		    	String from_station_name = info.getString("from_station_name");
		    	String to_station_name = info.getString("to_station_name");
		    	String start_time = info.getString("start_time");
		    	String arrive_time = info.getString("arrive_time");
		    	String lishi = info.getString("lishi");
		    	
		    	String ze_num = info.getString("ze_num");
		    	String zy_num = info.getString("zy_num");
		    	String swz_num = info.getString("swz_num");
		    	
		    	String gr_num = info.getString("gr_num");
	    		String rw_num = info.getString("rw_num");
	    		String rz_num = info.getString("rz_num");
	    		String tz_num = info.getString("tz_num");
	    		String wz_num = info.getString("wz_num");
	    		String yw_num = info.getString("yw_num");
	    		String yz_num = info.getString("yz_num");
	    		
		    	/*buffer.append("车次："+train_no).append("\n");
		    	//buffer.append("车次始发站："+start_station_name).append("\n");
		    	//buffer.append("车次终点站："+end_station_name).append("\n");
		    	buffer.append("出发站："+from_station_name).append("\n");
		    	buffer.append("到达站："+to_station_name).append("\n");
		    	buffer.append("出发时间："+start_time).append("\n");
		    	buffer.append("到达时间："+arrive_time).append("\n");
		    	

	    		buffer.append("二等座"+ze_num).append("\n");
		    	buffer.append("一等座："+zy_num).append("\n");
		    	buffer.append("商务座："+swz_num).append("\n");
		    
		    	 buffer.append("高级软卧"+gr_num).append("\n");
			     buffer.append("软卧："+rw_num).append("\n");
			     buffer.append("软座"+rz_num).append("\n");
			     buffer.append("特等座："+tz_num).append("\n");
			     buffer.append("无座："+wz_num).append("\n");
			     buffer.append("硬卧"+yw_num).append("\n");
			     buffer.append("硬座："+yz_num).append("\n");*/
			    
	    		//articleList.add(new Article("车次："+train_no+" 出发站："+from_station_name+" 到达站："+to_station_name
	    				//+" 出发时间："+start_time+" 到达时间："+arrive_time+" 二等座"+ze_num+" 一等座："+zy_num+" 商务座："+swz_num,"","",""));
	    		articleList.add(new Article(train_no+" "+from_station_name+"到"+to_station_name
	    	    				+" "+start_time+"出发"+arrive_time+"到达"+" 二等座"+ze_num+" 一等座："+zy_num+" 商务座："+swz_num,"","",""));
		    }	    
		  }
		  
		   catch(Exception e){
			 
			   e.printStackTrace();
		  }
		
			return articleList;
		
	}
	
	public static String searchTravel(){
		
		StringBuffer  buffer = new StringBuffer();	
		String requestUrl3 ="http://api.map.baidu.com/telematics/v3/travel_city?location=上海&day=&output=json&ak=81685b5135efd484a84727bfb9931525";

		try
		{
		    String travel = httpRequest(requestUrl3);
		    System.out.println(travel);
		    JSONObject dataJson = new JSONObject(travel);
		    JSONObject result = dataJson.getJSONObject("result");
		    String url = result.getString("url");
		  
		    //JSONArray itineraries = result.getJSONArray("itineraries");
		    buffer.append(url);
		
		}
		  
		   catch(Exception e){
			 
			   e.printStackTrace();
		  }

		
			return buffer.toString();
		
	}
	
  public static String RoadCondition(){
		
		StringBuffer  buffer = new StringBuffer();       
		String requestUrl =" http://api.map.baidu.com/telematics/v3/trafficEvent?location=上海&output=json&ak=81685b5135efd484a84727bfb9931525";

		try
		{
		    String roadInfo = httpRequest(requestUrl);
		    System.out.println(roadInfo);
		    JSONObject dataJson = new JSONObject(roadInfo);
		    String status = dataJson.getString("status");
		    
		    if(status.equals("success")){
		    	
		       JSONArray results = dataJson.getJSONArray("results");
		       
		       for(int i=0;i<results.length();i++){
		    	   
		    	   JSONObject info = results.getJSONObject(i);
		    	   String description = info.getString("description");
		    	   String startTime = info.getString("startTime");
		    	   String endTime = info.getString("endTime");
		    	   
		    	   buffer.append(startTime.substring(0, 10)+"-"+endTime.substring(0,9)+":"+description).append("\n\n");
		    	   
		       }
				    
		    	
		    }
		  
		    
		
		}
		  
		   catch(Exception e){
			 
			   e.printStackTrace();
		  }

		
			return buffer.toString();
		
	}
	
   
	
	
	public static void main(String[] args){
		
		 //System.out.println(searchLocation("ATM","31.2788","120.7417"));
		System.out.println(RoadCondition());
	}
	
	 
}

