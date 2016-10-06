package fyp.tingli.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import fyp.tingli.util.CommonTool;
import fyp.tingli.util.Hibernate;
import fyp.tingli.util.xmlTransfer;


public class FlightDao {
	
	public String searchFlight(String flightNo, String date){
		
		StringBuffer buffer = new StringBuffer();
		Session session = null;
		Date fdate;
		String Terminal = null;
		String Airline = null;
		String dep = null;
		String arr = null;
		String status = null;
		
		try{
			
			fdate = CommonTool.strToDate(date);
			session = Hibernate.getSessionByDB("oracle");
			session.beginTransaction();
			
			String sql="select description from  F_3_TERMINAL where TERMINAL_CODE=(select aircraft_terminal_code from f_1_dynflight where flight_no = ? AND operation_date=?)";
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			sqlQuery.setParameter(0, flightNo);
			sqlQuery.setParameter(1, fdate);
			Terminal = (String)sqlQuery.uniqueResult();
						
			String sql1="select name_xml from  F_3_AIRLINE where AIRLINE_IATA=(select airline_iata from f_1_dynflight where flight_no = ? AND operation_date=?)";
			SQLQuery sqlQuery1 = session.createSQLQuery(sql1);
			sqlQuery1.setParameter(0, flightNo);
			sqlQuery1.setParameter(1, fdate);
		    Airline = (String)sqlQuery1.uniqueResult();
		    
		    String sql2="select name_xml from  F_3_AIRPORT where AIRPORT_IATA=(select origin_airport_iata from f_1_dynflight where flight_no = ? AND operation_date=?)";
			SQLQuery sqlQuery2 = session.createSQLQuery(sql2);
			sqlQuery2.setParameter(0, flightNo);
			sqlQuery2.setParameter(1, fdate);
			dep = (String)sqlQuery2.uniqueResult();
			
			String sql3="select name_xml from  F_3_AIRPORT where AIRPORT_IATA=(select dest_airport_iata from f_1_dynflight where flight_no = ? AND operation_date=?)";
			SQLQuery sqlQuery3 = session.createSQLQuery(sql3);
			sqlQuery3.setParameter(0, flightNo);
			sqlQuery3.setParameter(1, fdate);
			arr = (String)sqlQuery3.uniqueResult();
			
			String sql4="select description from  F_3_REMARK where REMARK_CODE=(select remark from f_1_dynflight where flight_no = ? AND operation_date=?)";
			SQLQuery sqlQuery4 = session.createSQLQuery(sql4);
			sqlQuery4.setParameter(0, flightNo);
			sqlQuery4.setParameter(1, fdate);
			status = (String)sqlQuery4.uniqueResult();
			
			buffer.append("航站楼:"+Terminal).append("\n");
			buffer.append("航空公司："+xmlTransfer.contentExtract(Airline)).append("\n");
			buffer.append("起飞站:"+xmlTransfer.contentExtract(dep)).append("\n");
			buffer.append("终点站:"+xmlTransfer.contentExtract(arr)).append("\n");
			buffer.append("航班状态:"+status).append("\n");
					
			
			String sql5="select * from  F_1_DYNFLIGHT where flight_no = ? AND operation_date=?";
			SQLQuery sqlQuery5 = session.createSQLQuery(sql5).addScalar("STD").addScalar("ETD")
					.addScalar("STA").addScalar("ETA");
			sqlQuery5.setParameter(0, flightNo);
			sqlQuery5.setParameter(1, fdate);
			List list = sqlQuery5.list();
			
			for(int i=0;i<list.size();i++){
			
				Object[] object =(Object[])list.get(i);
					
					//object[i]= "not available";
					buffer.append("计划起飞时间").append(object[0].toString()).append("\n");
					buffer.append("预计起飞时间").append(object[1].toString()).append("\n");
					buffer.append("计划落地时间").append(object[2].toString()).append("\n");
					buffer.append("预计落地时间").append(object[3].toString()).append("\n");				  
				 
				
			}
				
			session.getTransaction().commit();
	      
			
		}catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally{
			Hibernate.closeSession("oracle");;
		}
		return buffer.toString();
		
	}
		
	
}
