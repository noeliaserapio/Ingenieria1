package com.tenpines.advancetdd;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class PersistentCustomerSystem implements CustomerSystem  {
	public Session session;

	public PersistentCustomerSystem() {
	}

	/* (non-Javadoc)
	 * @see com.tenpines.advancetdd.CustomerSystem#beginTransaction()
	 */
	@Override
	public void beginTransaction() {
		session.beginTransaction();
	}

	/* (non-Javadoc)
	 * @see com.tenpines.advancetdd.CustomerSystem#configureSession()
	 */
	@Override
	public void configureSession() {
		Configuration configuration = new Configuration();
	    configuration.configure();
	
	    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
	    SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		session = sessionFactory.openSession();
	}

	
	/* (non-Javadoc)
	 * @see com.tenpines.advancetdd.CustomerSystem#stop()
	 */
	@Override
	public void stop() {
		session.close();
	}

	/* (non-Javadoc)
	 * @see com.tenpines.advancetdd.CustomerSystem#commit()
	 */
	@Override
	public void commit() {
		session.getTransaction().commit();
	}
	
	/* (non-Javadoc)
	 * @see com.tenpines.advancetdd.CustomerSystem#persistSystem(com.tenpines.advancetdd.Customer)
	 */
	@Override
	public void addParty(Party newParti) {
		session.persist(newParti);
	}
	@Override
	public int numberOfCustomers(){
		List<Customer> customers = session.createCriteria(Party.class).list();
		return customers.size();
	}
	@Override
	public Party customerIdentifiedAs(String idType, String idNumber) {
		List<Party> parties;
		Party partie;
		parties = session.createCriteria(Party.class,"c").
				add(Restrictions.eq("c.identification.identificationNumber",  idNumber)).add(Restrictions.eq("c.identification.identificationType",  idType)).list();
		
		
		assertEquals(1,parties.size());
		partie = parties.get(0);
		System.out.println(partie);
		return partie;
	}

}