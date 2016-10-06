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
 * Java�汾ʾ�����룬ʹ�ü�{@link DemoApiToolTest.java}
 * <p>
 * 
 * @author : xiaopeng.li
 *         <p>
 * @version 1.0 2013-1-23
 * 
 * @since dianping-java-samples 1.0
 */
public class SearchAround{
	
	public static String httpRequest(String queryString, String apiUrl){
		   
		
		    StringBuffer response = new StringBuffer();
		    
	        HttpClientParams httpConnectionParams = new HttpClientParams();
	        httpConnectionParams.setConnectionManagerTimeout(1000);
	        HttpClient client = new HttpClient(httpConnectionParams);
	        HttpMethod method = new GetMethod(apiUrl);

	        BufferedReader reader = null;
	       
	       try{
	        String encodeQuery = URIUtil.encodeQuery(queryString, "UTF-8"); // UTF-8 ����
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
   
	 public static ArrayList<Article> SearchGroupon(String category,String latitude, String longitude)
    {
        //String apiUrl = "http://api.dianping.com/v1/business/find_businesses";
        String apiUrl = "http://api.dianping.com/v1/business/find_businesses";
    	String appKey = "749503920";    //���滻Ϊ�Լ��� App Key �� App secret 
        String secret = "b1514414924649b1bb89832d867755ba";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("latitude", latitude);
        paramMap.put("longitude", longitude);
        paramMap.put("radius", "1000");
        paramMap.put("category", category);
        paramMap.put("platform","2");
        paramMap.put("sort", "7"); 


        StringBuilder stringBuilder = new StringBuilder();
        
        // �Բ����������ֵ�����
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        // ƴ������Ĳ�����-ֵ��
        stringBuilder.append(appKey);
        for (String key : keyArray)
        {
            stringBuilder.append(key).append(paramMap.get(key));
        }
        String codes = stringBuilder.append(secret).toString();
        // SHA-1���룬 ����ʹ�õ���Apache-codec�����ɻ��ǩ��(shaHex()�����Ƚ�����ת��ΪUTF8����Ȼ�����sha1���㣬ʹ�������Ĺ��߰���ע��UTF8����ת��)
		String sign = org.apache.commons.codec.digest.DigestUtils.shaHex(codes).toUpperCase();
       
        // ���ǩ��
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
        	
        	JSONArray businesses = dataJson.getJSONArray("businesses");
        	String business_url;
    		String s_photo_url;
    		String name;
    		int distance;
    		String address;
    		
        	if(businesses.length()>9)
        	{
        			
        	for(int i=0;i<9;i++){	
        		
        		JSONObject info  = businesses.getJSONObject(i);
        		business_url = info.getString("business_url");
        		s_photo_url = info.getString("s_photo_url");
        		name = info.getString("name");
        		String s=name.substring(0, name.indexOf("("));
        		distance=info.getInt("distance");
        		address=info.getString("address");
        		al.add(new Article(s+"�� "+distance+"M��"+address,"",s_photo_url,business_url));
        		
        	 }
        	}else{
        		 for(int i=0;i<businesses.length();i++){	
            		
            		JSONObject info  = businesses.getJSONObject(i);
            		business_url = info.getString("business_url");
            		s_photo_url = info.getString("s_photo_url");
            		name = info.getString("name");
            		String s=name.substring(0, name.indexOf("("));
            		distance=info.getInt("distance");
            		address=info.getString("address");
            		al.add(new Article(s+"�� "+distance+"M ��"+address,"",s_photo_url,business_url));
        		 }	
            	}
        	}
         
        }catch(Exception e){
        	e.printStackTrace();
        }
       
        return al;
      }
        
    
    public static void main(String[] args){
    	
    	StringBuffer buffer = new StringBuffer();
    	ArrayList<Article> al = SearchGroupon("����","31.194672","121.319992");
    	for(int i=0;i<al.size();i++){
    		
    		buffer.append(al.get(i).getTitle());
    		buffer.append(al.get(i).getPicUrl());
    		buffer.append(al.get(i).getUrl());
    	}
    	System.out.println(buffer.toString());
        String s ="McCaf��(����һ�������̻����ݣ������ڲ��Կ�����������ɺ���������ʽ����...)";
        System.out.println();
    }
      
}
