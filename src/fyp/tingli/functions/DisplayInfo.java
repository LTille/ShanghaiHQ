package fyp.tingli.functions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisplayInfo {

	/**
	 * 发起http get请求获取网页源代码
	 * 
	 * @param requestUrl
	 * @return
	 */
	private static String httpRequest(String requestUrl) {
		StringBuffer buffer = null;

		try {
			// 建立连接
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod("GET");

			// 获取输入流
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			// 读取返回结果
			buffer = new StringBuffer();
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			httpUrlConn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * 从html中抽取出历史上的今天信息
	 * 
	 * @param html
	 * @return
	 */
	private static ArrayList<ArticleList> extract(String html) {
	
		ArrayList <ArticleList>  al=new ArrayList<ArticleList>();
		
		Pattern p = Pattern.compile("(.*)(<div class=\"complex_info\">)(.*?)(<dd class=\"last\">)(.*)");
		Matcher m = p.matcher(html);
	
		System.out.println(m.matches());
		
		if (m.matches()) {
		     
			 String s = m.group(3);
             String s1="<a.*?</a>";
             String s2=">[^<].*?[^>]</a>";
             String s3="href=\'(.*?)\'";
            
			
             Pattern pt=Pattern.compile(s1);
             Matcher mt=pt.matcher(s);
             
             while(mt.find()){
            	
            	 Pattern pt2=Pattern.compile(s2);
                 Matcher mt2=pt2.matcher(mt.group());
                 Pattern pt3=Pattern.compile(s3);
                 Matcher mt3=pt3.matcher(mt.group());
                
                 while(mt2.find()&&mt3.find()){
                
                     String t=mt2.group().replaceAll("\\s|>|</a>","");
                     String u="http://www.cecsh.com/"+mt3.group().replaceAll("href='|'","");
                     al.add(new ArticleList(t,u));
                     
                 }
             }           
		}
		
		    return al;
	}

	/**
	 * 封装历史上的今天查询方法，供外部调用
	 * 
	 * @return
	 */
	public static ArrayList<ArticleList> searchInfo() {
		// 获取网页源代码
		String html = httpRequest("http://www.cecsh.com/complex_info.aspx?cateid=78");

		// 从网页中抽取信息
		ArrayList<ArticleList> articleList = extract(html);

		return articleList;
	}

	/**
	 * 通过main在本地测试
	 * 
	 * @param args
	 */
	/*public static void main(String[] args) {
		
		String info = searchInfo();
		System.out.println(info);
	}*/
	
	
}