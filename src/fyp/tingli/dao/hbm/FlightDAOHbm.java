/*package fyp.tingli.dao.hbm;

import java.util.List;



//import org.springframework.stereotype.*;
//import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Query;
import org.hibernate.Session; 


import fyp.tingli.dao.FlightDAO;
import fyp.tingli.model.Flight;
import fyp.tingli.util.HibernateUtil;


public class FlightDAOHbm extends BaseDAOHbm implements FlightDAO{

	@Override
	
	public List<Flight> getAllData() {
		
		return null;
    
	}

	@Override
  public List<Flight> findFlight(String flightNo){
		
		Session session = null;
		List result = null;
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			String hql="from Flight f where f.flight_no=?";
			Query query = session.createQuery(hql).setParameter(0, flightNo);
			result=(List<Flight>)query.uniqueResult();
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally{
			HibernateUtil.closeSession();
		}
	
		return result;
		
	}
	

}
*/