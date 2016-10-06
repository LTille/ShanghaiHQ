package fyp.tingli.model;

import java.io.Serializable;
import java.util.Date;

public class RRdt implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private int id;
    private Date run_date;//运行日
    private int train_no;//车次编号
    private String arrive_train_no;
    private String depart_train_no;
    private Date estimated_arrive_time;
    private Date estimated_depart_time;
    private Date arrive_time;
    private Date depart_time;
    private Date check_time;
    private Date stop_time;
    private String train_status;//行车状态
    private String StartStation;//FK
    private String TerminalStation;//FK
    private String AbnormalStatus;
    private String AbnormalCause;
    private int AbnormalTime;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getRun_date() {
		return run_date;
	}
	public void setRun_date(Date run_date) {
		this.run_date = run_date;
	}
	public int getTrain_no() {
		return train_no;
	}
	public void setTrain_no(int train_no) {
		this.train_no = train_no;
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
	public Date getEstimated_arrive_time() {
		return estimated_arrive_time;
	}
	public void setEstimated_arrive_time(Date estimated_arrive_time) {
		this.estimated_arrive_time = estimated_arrive_time;
	}
	public Date getEstimated_depart_time() {
		return estimated_depart_time;
	}
	public void setEstimated_depart_time(Date estimated_depart_time) {
		this.estimated_depart_time = estimated_depart_time;
	}
	public Date getArrive_time() {
		return arrive_time;
	}
	public void setArrive_time(Date arrive_time) {
		this.arrive_time = arrive_time;
	}
	public Date getDepart_time() {
		return depart_time;
	}
	public void setDepart_time(Date depart_time) {
		this.depart_time = depart_time;
	}
	public Date getCheck_time() {
		return check_time;
	}
	public void setCheck_time(Date check_time) {
		this.check_time = check_time;
	}
	public Date getStop_time() {
		return stop_time;
	}
	public void setStop_time(Date stop_time) {
		this.stop_time = stop_time;
	}
	public String getTrain_status() {
		return train_status;
	}
	public void setTrain_status(String train_status) {
		this.train_status = train_status;
	}
	public String getStartStation() {
		return StartStation;
	}
	public void setStartStation(String startStation) {
		StartStation = startStation;
	}
	public String getTerminalStation() {
		return TerminalStation;
	}
	public void setTerminalStation(String terminalStation) {
		TerminalStation = terminalStation;
	}
	public String getAbnormalStatus() {
		return AbnormalStatus;
	}
	public void setAbnormalStatus(String abnormalStatus) {
		AbnormalStatus = abnormalStatus;
	}
	public String getAbnormalCause() {
		return AbnormalCause;
	}
	public void setAbnormalCause(String abnormalCause) {
		AbnormalCause = abnormalCause;
	}
	public int getAbnormalTime() {
		return AbnormalTime;
	}
	public void setAbnormalTime(int abnormalTime) {
		AbnormalTime = abnormalTime;
	}
	
    
   
	
	
}
