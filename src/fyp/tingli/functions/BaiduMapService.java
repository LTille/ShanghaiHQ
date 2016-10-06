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

import fyp.tingli.message.resp.Article;


public class BaiduMapService {
	
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
	public static ArrayList<Article> searchLocation(String source, String x, String y){
	
		ArrayList<Article> al = new ArrayList<Article>();
		String requestUrl = "http://api.map.baidu.com/place/v2/search?ak=DtMDdk0Ag7YsVnrWlMw0FhLB&query={keyword}&page_size=5&page_num=0&location={x},{y}&radius=2000&output=xml&scope=2&filter=sort_name:distance";
		requestUrl = requestUrl.replace("{keyword}",urlEncodeUTF8(source));
	    requestUrl = requestUrl.replace("{x}",x);
	    requestUrl = requestUrl.replace("{y}",y);
	    
		try
		{
		    String json = httpRequest(requestUrl);
		    System.out.println(json);
	       
		 
		  Document doc = DocumentHelper.parseText(json);
		  Element root = doc.getRootElement();
		  String status = root.element("status").getText();
		
		  if(status!=null&&status.equals("0")) 
		  {
			  
			  List<Element> resEle = root.element("results").elements();
			  
			 if(resEle.size()>9) {
			  for(int i=0;i<9;i++)
			  {
				  
				 String name = resEle.get(i).element("name").getText();
				 String url = resEle.get(i).element("detail_info").element("detail_url").getText();
				 String distance = resEle.get(i).element("detail_info").element("distance").getText();
				 String address = resEle.get(i).element("address").getText();
				 
                 al.add(new Article("¡¾ "+name+" ¡¿"+"<"+distance+"M>" +address,"","",url));
 												          
			   }
			 } else{
				  for(int i=0;i<resEle.size();i++)
				  {
					  
					 String name = resEle.get(i).element("name").getText();
					 String url = resEle.get(i).element("detail_info").element("detail_url").getText();
					 String distance = resEle.get(i).element("detail_info").element("distance").getText();
					 String address = resEle.get(i).element("address").getText();
					 
	                 al.add(new Article("¡¾ "+name+" ¡¿"+"<"+distance+"M>" +address,"","",url));
	 												          
				   }
				 }
			  
		  } 
		}
		  
		   catch(Exception e){
			  e.printStackTrace();
		  }

		
			return al;
		
	}
	
	public static void main(String[] args){
		
		  System.out.println(searchLocation("²ÞËù","31.2788","120.7417"));
	}
	
	 
}

