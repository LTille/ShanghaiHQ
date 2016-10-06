package fyp.tingli.functions;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class FlightSearch {
	
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
	public static String searchFlight(String source){
	
		StringBuffer outcome = new StringBuffer();

		
		String requestUrl = "http://apis.juhe.cn/plan/s?name={keyword}&date=2015-3-1&key=b562d75691aecce77b8d49205106b144";
		requestUrl = requestUrl.replace("{keyword}",urlEncodeUTF8(source));

		try
		{
		    String json = httpRequest(requestUrl);
		    System.out.println(json);
		    
		    JSONObject dataJson = new JSONObject(json);
		    String resultcode = dataJson.getString("resultcode");
			
			    
			if(resultcode.equals("200")){
				
				JSONArray data = dataJson.getJSONArray("result");	
			    JSONObject info = data.getJSONObject(0);
				String start = info.getString("start");
				String end = info.getString("end");
				String DepTerminal = info.getString("DepTerminal");
				String ArrTerminal = info.getString("ArrTerminal");
				String DepTime = info.getString("DepTime");
				String ArrTime = info.getString("ArrTime");
				String DepDelay = info.getString("DepDelay");
				String ArrDelay = info.getString("ArrDelay");
				
				
				outcome.append("起飞机场："+ start).append("\n");
				outcome.append("降落机场："+end).append("\n");
				outcome.append("准点起飞时间："+DepTime).append("\n");
				outcome.append("准点降落时间："+ArrTime).append("\n");
				outcome.append("登机口:"+DepTerminal).append("\n");
				outcome.append("延迟起飞时间："+DepDelay).append("\n");	  	
				
				}else{
					
					outcome.append("搜索出现异常，无该航班信息");
				}
			
		}
		  
		 
		   catch(Exception e){
			 
			   e.printStackTrace();
		  }

  
			return outcome.toString();
		
	}
	
	/*public static void main(String[] args){
		
        	 System.out.println(searchFlight("CZ3868"));
	}
	*/
	 
}


