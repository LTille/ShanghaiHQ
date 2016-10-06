package fyp.tingli.model;

import java.io.Serializable;

public class WechatUser implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private String FromUserName;
    private String Label;
	private String Location_X;
    private String Location_Y;

	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	public String getLocation_X() {
		return Location_X;
	}
	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}
	public String getLocation_Y() {
		return Location_Y;
	}
	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}
	
}
