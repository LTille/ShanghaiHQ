package fyp.tingli.functions;

public class ArticleList{
    String URLs;
    String title;

    public ArticleList(){}
    
    public ArticleList(String t,String u)
    {
        title=t;
        URLs=u;
         
    }

	public String getURLs() {
		return URLs;
	}

	public void setURLs(String uRLs) {
		URLs = uRLs;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
  
}