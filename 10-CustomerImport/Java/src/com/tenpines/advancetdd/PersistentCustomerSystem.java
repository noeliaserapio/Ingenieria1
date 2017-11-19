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
	public void addCustomer(Customer newCustomer) {
		session.persist(newCustomer);
	}

	public int numberOfCustomers(){
		List<Customer> customers = session.createCriteria(Customer.class).list();
		return customers.size();
	}

	public Customer customerIdentifiedAs(String idType, String idNumber) {
		List<Customer> customers;
		Customer customer;
		customers = session.createCriteria(Customer.class).
				add(Restrictions.eq("identificationType", idType)).
				add(Restrictions.eq("identificationNumber",idNumber)).list();
		assertEquals(1,customers.size());
		customer = customers.get(0);
		return customer;
	}

}