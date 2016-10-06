package fyp.tingli.util;

public class xmlTransfer {

	public String Xml;
	
	
	public static String contentExtract(String Xml){
		
		String content = Xml;
		String result = null;
		String temp= content.substring(content.indexOf("_"),content.indexOf("/"));
		result = temp.substring(temp.indexOf(">")+1, temp.indexOf("<"));
		return result;
		
		
	}
}
