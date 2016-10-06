package fyp.tingli.model;

import java.util.Date;

public class F1Abnormal {
	
	private int id;
	private int dynflight_no;
	private Date record_timestamp;
	private String abnormal_status;
	private String delay_code;
	private String cause_of_delay;
	private int estimated_delay_time;
	private String cause_of_cancel;
	private String cause_of_divert;
	private String divert_location;
	private Date depart_time;
	private Date arrival_time;
	private String cause_of_return;
	private Date return_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDynflight_no() {
		return dynflight_no;
	}
	public void setDynflight_no(int dynflight_no) {
		this.dynflight_no = dynflight_no;
	}
	public Date getRecord_timestamp() {
		return record_timestamp;
	}
	public void setRecord_timestamp(Date record_timestamp) {
		this.record_timestamp = record_timestamp;
	}
	public String getAbnormal_status() {
		return abnormal_status;
	}
	public void setAbnormal_status(String abnormal_status) {
		this.abnormal_status = abnormal_status;
	}
	public String getDelay_code() {
		return delay_code;
	}
	public void setDelay_code(String delay_code) {
		this.delay_code = delay_code;
	}
	public String getCause_of_delay() {
		return cause_of_delay;
	}
	public void setCause_of_delay(String cause_of_delay) {
		this.cause_of_delay = cause_of_delay;
	}
	public int getEstimated_delay_time() {
		return estimated_delay_time;
	}
	public void setEstimated_delay_time(int estimated_delay_time) {
		this.estimated_delay_time = estimated_delay_time;
	}
	public String getCause_of_cancel() {
		return cause_of_cancel;
	}
	public void setCause_of_cancel(String cause_of_cancel) {
		this.cause_of_cancel = cause_of_cancel;
	}
	public String getCause_of_divert() {
		return cause_of_divert;
	}
	public void setCause_of_divert(String cause_of_divert) {
		this.cause_of_divert = cause_of_divert;
	}
	public String getDivert_location() {
		return divert_location;
	}
	public void setDivert_location(String divert_location) {
		this.divert_location = divert_location;
	}
	public Date getDepart_time() {
		return depart_time;
	}
	public void setDepart_time(Date depart_time) {
		this.depart_time = depart_time;
	}
	public Date getArrival_time() {
		return arrival_time;
	}
	public void setArrival_time(Date arrival_time) {
		this.arrival_time = arrival_time;
	}
	public String getCause_of_return() {
		return cause_of_return;
	}
	public void setCause_of_return(String cause_of_return) {
		this.cause_of_return = cause_of_return;
	}
	public Date getReturn_time() {
		return return_time;
	}
	public void setReturn_time(Date return_time) {
		this.return_time = return_time;
	}
	

}
