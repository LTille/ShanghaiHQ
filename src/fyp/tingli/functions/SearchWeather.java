package fyp.tingli.functions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import fyp.tingli.message.resp.Article;


public class SearchWeather {
	
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
	 * Utf±àÂë
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
	 * API¡¡places
	 * 
	 * @param source
	 * @return
	 */
	public static ArrayList<Article> searchWeather(String source){
	
		ArrayList<Article> al = new ArrayList<Article>();
		String requestUrl = "http://api.map.baidu.com/telematics/v3/weather?location={keyword}&output=json&ak=81685b5135efd484a84727bfb9931525";
		requestUrl = requestUrl.replace("{keyword}",urlEncodeUTF8(source));
	   
		try
		{
		    String json = httpRequest(requestUrl);
		    System.out.println(json);
	       
		   JSONObject dataJson= new JSONObject(json);
		   String status = dataJson.getString("status");
		
		  if(status.equals("success")) 
		  {
			 JSONArray results = dataJson.getJSONArray("results");
			 JSONObject re = results.getJSONObject(0);
			 JSONArray weather_data = re.getJSONArray("weather_data");
			 
			 for(int i=0;i<weather_data.length();i++){
				 
				 JSONObject day = weather_data.getJSONObject(i);
				 String date = day.getString("date");
				 String dayPictureUrl=day.getString("dayPictureUrl");
				 String weather = day.getString("weather");
				 String wind = day.getString("wind");
				 String temperature = day.getString("temperature");
				 al.add(new Article(date+"\n"+weather+" "+wind+" "+temperature,"",dayPictureUrl,""));
			 }
	               	   
			  
		  } 
		}
		  
		   catch(Exception e){
			  e.printStackTrace();
		  }

		
			return al;
		
	}
	

	
	 
}

