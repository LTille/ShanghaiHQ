package fyp.tingli.model;

import java.sql.Date;

public class RStopTime {
	
	private int id;
	private int train_no;
	private String stop_sta;
    private int order_sta;
    private String arrive_train_no;
    private String depart_train_no;
    private Date plan_arriveTime;
    private Date plan_departTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTrain_no() {
		return train_no;
	}
	public void setTrain_no(int train_no) {
		this.train_no = train_no;
	}
	public String getStop_sta() {
		return stop_sta;
	}
	public void setStop_sta(String stop_sta) {
		this.stop_sta = stop_sta;
	}
	public int getOrder_sta() {
		return order_sta;
	}
	public void setOrder_sta(int order_sta) {
		this.order_sta = order_sta;
	}
	public String getArrive_train_no() {
		return arrive_train_no;
	}
	public void setArrive_train_no(String arrive_train_no) {
		this.arrive_train_no = arrive_train_no;
	}
	public String getDepart_train_no() {
		return depart_train_no;
	}
	public void setDepart_train_no(String depart_train_no) {
		this.depart_train_no = depart_train_no;
	}
	public Date getPlan_arriveTime() {
		return plan_arriveTime;
	}
	public void setPlan_arriveTime(Date plan_arriveTime) {
		this.plan_arriveTime = plan_arriveTime;
	}
	public Date getPlan_departTime() {
		return plan_departTime;
	}
	public void setPlan_departTime(Date plan_departTime) {
		this.plan_departTime = plan_departTime;
	}
    
    
	
	

}
