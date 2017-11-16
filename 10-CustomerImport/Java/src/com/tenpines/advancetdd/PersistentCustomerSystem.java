package com.tenpines.advancetdd;

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
	 * @see com.tenpines.advancetdd.CustomerSystem#customersIdentifiedAs(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Customer> customersIdentifiedAs(String idType, String idNumber) {
		List<Customer> customers;
		customers = session.createCriteria(Customer.class).
				add(Restrictions.eq("identificationType", idType)).
				add(Restrictions.eq("identificationNumber",idNumber)).list();
		return customers;
	}

	/* (non-Javadoc)
	 * @see com.tenpines.advancetdd.CustomerSystem#close()
	 */
	@Override
	public void close() {
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
	 * @see com.tenpines.advancetdd.CustomerSystem#customers()
	 */
	@Override
	public List<Customer> customers() {
		List<Customer> customers = session.createCriteria(Customer.class).list();
		return customers;
	}
	
	/* (non-Javadoc)
	 * @see com.tenpines.advancetdd.CustomerSystem#persistSystem(com.tenpines.advancetdd.Customer)
	 */
	@Override
	public void persistSystem(Customer newCustomer) {
		session.persist(newCustomer);
	}

}