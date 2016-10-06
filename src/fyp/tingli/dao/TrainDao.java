package fyp.tingli.dao;


import java.util.Date;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import fyp.tingli.util.CommonTool;
import fyp.tingli.util.Hibernate;
import fyp.tingli.util.xmlTransfer;

public class TrainDao {
	
	public String searchTrain(String trainNo, String date){
		
		StringBuffer buffer = new StringBuffer();
		Session session = null;
		Date fdate;
		String dep = null;
		String arr = null;
		
		try{
			
			fdate = CommonTool.strToDate(date);
			session = Hibernate.getSessionByDB("oracle");
			session.beginTransaction();
			
			String sql="select STA_Name from  R_STA where STA=(select StartStation from R_RDT where depart_train_no = ? AND run_date=?)";
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			sqlQuery.setParameter(0, trainNo);
			sqlQuery.setParameter(1, fdate);
			dep = (String)sqlQuery.uniqueResult();
						
			String sql3="select STA_Name from  R_STA where STA=(select TerminalStation from R_RDT where depart_train_no = ? AND run_date=?)";
			SQLQuery sqlQuery3 = session.createSQLQuery(sql3);
			sqlQuery3.setParameter(0, trainNo);
			sqlQuery3.setParameter(1, fdate);
			arr = (String)sqlQuery3.uniqueResult();
					
			String sql4="select * from R_RDT where depart_train_no = ? AND run_date=?";
			SQLQuery sqlQuery4 = session.createSQLQuery(sql4).addScalar("ABNORMALSTATUS").addScalar("CHECK_TIME")
					.addScalar("ESTIMATED_DEPART_TIME").addScalar("ESTIMATED_ARRIVE_TIME");
			sqlQuery4.setParameter(0, trainNo);
			sqlQuery4.setParameter(1, fdate);
			List list1 = sqlQuery4.list();

			for(int i=0;i<list1.size();i++){
			
				Object[] object =(Object[])list1.get(i);
				buffer.append("列车状态:").append(object[0].toString()).append("\n");
				buffer.append("开检时间").append(object[1].toString()).append("\n");				
				buffer.append("预计发点").append(object[2].toString()).append("\n");
				buffer.append("预计到点").append(object[3].toString()).append("\n");
			  
			}
			
			String sql5="select * from R_STOP_TIME where depart_train_no = ?";
			SQLQuery sqlQuery5 = session.createSQLQuery(sql5).addScalar("STOP_STA").addScalar("PLAN_ARRIVETIME")
					.addScalar("PLAN_DEPARTTIME");
			sqlQuery5.setParameter(0, trainNo);
			List list2 = sqlQuery5.list();
			buffer.append("始发站：").append(dep).append("\n");
			buffer.append("终点站：").append(arr).append("\n");
			for(int i=0;i<list2.size();i++){
			
				Object[] object =(Object[])list2.get(i);
				buffer.append("站点："+object[0].toString()).append("\n");
				buffer.append("预计到达时间："+object[1].toString()).append("\n");
				buffer.append("预计发车时间："+object[2].toString()).append("\n");
			  
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
