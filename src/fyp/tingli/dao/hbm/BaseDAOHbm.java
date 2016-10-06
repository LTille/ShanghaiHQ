package fyp.tingli.dao.hbm;

import org.hibernate.SessionFactory; 
public class BaseDAOHbm {
	
    protected SessionFactory sessionFactory; 
    
    public SessionFactory getSessionFactory() { 
    return sessionFactory; 
    } 
 
    public void setSessionFactory(SessionFactory sessionFactory) { 
    this.sessionFactory = sessionFactory; 
    } 

}
