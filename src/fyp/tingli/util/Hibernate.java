package fyp.tingli.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

public class Hibernate {
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static SessionFactory mysql; // SessionFactory����
	private static SessionFactory oracle;
	// ��̬��
	static {
		try {
	            	mysql=new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
					oracle=new Configuration().configure("/oracle.cfg.xml").buildSessionFactory();
		} catch (Exception e) {
			System.err.println("�����Ự����ʧ��");
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡSession
	 * 
	 * @return Session
	 * @throws HibernateException
	 */
	public static Session getSessionByDB(String DBName) throws HibernateException {
		Session session = (Session) threadLocal.get();
		
		if(DBName=="mysql"){
			if(!mysql.isClosed()){
				session=mysql.openSession();
			}
		}else if(DBName=="oracle"){
			if(!oracle.isClosed()){
				session=oracle.openSession();
			}
		}
		
			threadLocal.set(session);
			return session;
		
	}


	/**
	 * �ر�Session
	 * 
	 * @throws HibernateException
	 */
	public static void closeSession(String DBName) throws HibernateException {
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);
		if(DBName=="mysql"){
			if(!mysql.isClosed()){
				mysql.close();
			}
		}else if(DBName=="oracle"){
			if(!oracle.isClosed()){
				oracle.close();
			}
		}
	}
}
