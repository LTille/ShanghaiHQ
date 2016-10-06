package fyp.tingli.functions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusSearch {

	
	/**
	 * 浠巋tml涓娊鍙栧嚭鍘嗗彶涓婄殑浠婂ぉ淇℃伅
	 * 
	 * @param html
	 * @return
	 */
	private static String extract(String pattern, String matcher) {
	

		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

		Matcher m = p.matcher(matcher);
		
		if(m.find()){
			
			return m.group();
		} else{
			return "";
		}
		
		
	}

	
	public static String Bus(String dest){
		
	   String  requestUrl = "http://www.checi.org/city/shanghai/{keyword}/";
	   requestUrl = requestUrl.replace("{keyword}",dest);
	   StringBuffer buffer = new StringBuffer();
	 
	   
	try
	{   
		URL url = new URL(requestUrl);
		HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
		httpUrlConn.setDoInput(true);
		httpUrlConn.setRequestMethod("GET");

		// 鑾峰彇杈撳叆娴�
		InputStream inputStream = httpUrlConn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		
		   
		// 浠庣綉椤典腑鎶藉彇淇℃伅

		
		String regularStation = ">[^<>]*</a></td>";
        String regularTime = "(\\d{2}:\\d{2})";
		String regularPrice = ">(\\d{2}|(\\d{2}-\\d{2})[u4e00-u9fa5])</td>";

    
	    String str = null;
	    int i = 0;
	    String strGet = null;
	    
	     while ((str = bufferedReader.readLine()) != null) {
			
	    	
	    	strGet = extract(regularStation, str);
	        System.out.println(strGet);
	    	
	    	 while(strGet.contains("汽车西站")&&(str = bufferedReader.readLine()) != null){
	    		
	    		    String s =extract(regularTime, str);
	    		    if(""!=s){
	 	    		buffer.append("时间: "+ s).append("\t");
	 	    		}
	 	    		
	    		    String r =extract(regularPrice, str);
	 	    		if(""!=r){
	    		    r=r.substring(1, r.indexOf("</td>"));
	 	    		buffer.append("票价: "+r).append("\n");
	 	    		}
	 	    	}
	    	}
	        
			// 閲婃斁璧勬簮
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			httpUrlConn.disconnect();
		
	}catch(Exception e){
		
		e.printStackTrace();
	}
	
	  return buffer.toString();
	
	}

    public static void main(String[] arg){
    	
    	StringBuffer buffer = new StringBuffer();
    	System.out.println(Bus(ChineseToEnglish.getPingYin("无锡")));
    	
    	
    
    }
}