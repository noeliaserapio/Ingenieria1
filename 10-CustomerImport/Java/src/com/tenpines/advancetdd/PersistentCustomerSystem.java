package com.tenpines.advancetdd;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class PersistentCustomerSystem {
	public Session session;

	public PersistentCustomerSystem() {
	}

	public void beginTransaction() {
		session.beginTransaction();
	}

	void configureSession() {
		Configuration configuration = new Configuration();
	    configuration.configure();
	
	    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
	    SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		session = sessionFactory.openSession();
	}

	List<Customer> customersIdentifiedAs(String idType, String idNumber) {
		List<Customer> customers;
		customers = session.createCriteria(Customer.class).
				add(Restrictions.eq("identificationType", idType)).
				add(Restrictions.eq("identificationNumber",idNumber)).list();
		return customers;
	}

	void close() {
		session.close();
	}

	void commit() {
		session.getTransaction().commit();
	}

	void persistSystem(CustomerImporter customerImporter, Customer newCustomer) {
		session.persist(newCustomer);
	}

	List<Customer> customers(CustomerImporterTest customerImporterTest) {
		List<Customer> customers = session.createCriteria(Customer.class).list();
		return customers;
	}
}