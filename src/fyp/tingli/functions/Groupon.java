package fyp.tingli.functions;
/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-1-23
 * Project            : dianping-java-samples
 * File Name        : DemoApiTool.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import fyp.tingli.message.resp.Article;

/**
 * Java版本示例代码，使用见{@link DemoApiToolTest.java}
 * <p>
 * 
 * @author : xiaopeng.li
 *         <p>
 * @version 1.0 2013-1-23
 * 
 * @since dianping-java-samples 1.0
 */
public class Groupon{
	
	public static String httpRequest(String queryString, String apiUrl){
		   
		
		    StringBuffer response = new StringBuffer();
		    
	        HttpClientParams httpConnectionParams = new HttpClientParams();
	        httpConnectionParams.setConnectionManagerTimeout(1000);
	        HttpClient client = new HttpClient(httpConnectionParams);
	        HttpMethod method = new GetMethod(apiUrl);

	        BufferedReader reader = null;
	       
	       try{
	        String encodeQuery = URIUtil.encodeQuery(queryString, "UTF-8"); // UTF-8 请求
	        method.setQueryString(encodeQuery);
	        client.executeMethod(method);
	        reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8"));
	        String line = null;
	           while ((line = reader.readLine()) != null)
	         {
	              response.append(line).append(System.getProperty("line.separator"));
	         }
	          reader.close();
	          method.releaseConnection();
	       }catch(Exception e){
	    	   
	    	   e.printStackTrace();
	       }
	       
	        return response.toString();
	}
   
	 public static ArrayList<Article> SearchGroupon(String latitude, String longitude, String category)
    {
        //String apiUrl = "http://api.dianping.com/v1/business/find_businesses";
        String apiUrl = "http://api.dianping.com/v1/deal/find_deals";
    	String appKey = "749503920";    //请替换为自己的 App Key 和 App secret 
        String secret = "b1514414924649b1bb89832d867755ba";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("city", "上海");
        paramMap.put("latitude", latitude);
        paramMap.put("longitude", longitude);
        paramMap.put("radius", "2000");
        paramMap.put("category", category);
     //   paramMap.put("keyword", "烤鸭");
        paramMap.put("sort", "7"); 
        paramMap.put("format", "json");

        StringBuilder stringBuilder = new StringBuilder();
        
        // 对参数名进行字典排序
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        // 拼接有序的参数名-值串
        stringBuilder.append(appKey);
        for (String key : keyArray)
        {
            stringBuilder.append(key).append(paramMap.get(key));
        }
        String codes = stringBuilder.append(secret).toString();
        // SHA-1编码， 这里使用的是Apache-codec，即可获得签名(shaHex()会首先将中文转换为UTF8编码然后进行sha1计算，使用其他的工具包请注意UTF8编码转换)
		String sign = org.apache.commons.codec.digest.DigestUtils.shaHex(codes).toUpperCase();
       
        // 添加签名
        stringBuilder = new StringBuilder(); 
        stringBuilder.append("appkey=").append(appKey).append("&sign=").append(sign);
        for (Entry<String, String> entry : paramMap.entrySet())
        {
            stringBuilder.append('&').append(entry.getKey()).append('=').append(entry.getValue());
        }
        String queryString = stringBuilder.toString();
        ArrayList<Article> al = new ArrayList<Article>();
      
       try{
        	
       
        JSONObject dataJson = new JSONObject(httpRequest(queryString,apiUrl));
        String status = dataJson.getString("status");
        System.out.println(dataJson);
     
        if(status.equals("OK")){
        	
        	JSONArray deals = dataJson.getJSONArray("deals");
        
        	String s_image_url;
    		String deal_h5_url;
    		String title;
    
        	if(deals.length()>9)
        	{
   		
        	 for(int i=0;i<9;i++){	
        		
        		JSONObject info  = deals.getJSONObject(i);
        		 s_image_url = info.getString("s_image_url");
        		deal_h5_url = info.getString("deal_h5_url");
        		title = info.getString("title");
        		al.add(new Article(title,"",s_image_url,deal_h5_url));
        		
        	}
        	}else{
        		 for(int i=0;i<deals.length();i++){	
            		
            		JSONObject info  = deals.getJSONObject(i);
            		title = info.getString("title");
            		s_image_url = info.getString("s_image_url");
            	    deal_h5_url = info.getString("deal_h5_url");
            		
            		al.add(new Article(title,"",s_image_url,deal_h5_url));
            		
            	}
        	}
         }
        }catch(Exception e){
        	e.printStackTrace();
        }
       
        return al;
      }
        
    
    public static void main(String[] args){
    	
    	System.out.println(SearchGroupon("31.317823","120.714882","美食"));
    	//System.out.println(SearchGroupon("31.21016","121.56175","美食"));
    }
      
}
