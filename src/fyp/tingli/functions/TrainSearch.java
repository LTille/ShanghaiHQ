package fyp.tingli.functions;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class TrainSearch {
	
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
	public static String searchTrain(String source){
	
		StringBuffer buffer = new StringBuffer();

		
		String requestUrl = "http://apis.juhe.cn/train/s?name={keyword}&key=44a866e7e4df7f48753eda8a9f897a3f";
		requestUrl = requestUrl.replace("{keyword}",urlEncodeUTF8(source));

		try
		{
		    String json = httpRequest(requestUrl);
		    System.out.println(json);
		    JSONObject dataJson = new JSONObject(json);
		    String resultcode = dataJson.getString("resultcode");
		    JSONObject resultJson = dataJson.getJSONObject("result");
		    	
		    if(resultcode.equals("200")){
		    	
		    	  JSONArray data = resultJson.getJSONArray("station_list");
		    	  
		    	  
					for (int i=0; i<data.length();i++){
						
						JSONObject info = data.getJSONObject(i);
						//JSONObject detail_info = info.getJSONObject("detail_info");
						String id = info.getString("train_id");
						String station_name = info.getString("station_name");
						String leave_time = info.getString("leave_time");
						String arrived_time = info.getString("arrived_time");
						
						buffer.append(station_name+"车站").append("\n");
						buffer.append("到达时间:"+arrived_time).append("\n");
						buffer.append("出发时间:"+leave_time).append("\n\n");
						
					  	
						
					} 
		    	
		    } else{
		    
		    	buffer.append("搜索出现异常");
		    }
		     
		
		}
		  
		   catch(Exception e){
			 
			   e.printStackTrace();
		  }

		
			return buffer.toString();
		
	}
	
	 
}

