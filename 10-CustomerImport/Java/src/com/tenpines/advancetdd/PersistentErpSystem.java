package com.tenpines.advancetdd;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class PersistentErpSystem implements ErpSystem  {
	public Session session;

	public PersistentErpSystem() {
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
	//customer
	@Override
	public void addCustomer(Customer newCustom) {
		session.persist(newCustom);
	}
	@Override
	public int numberOfCustomers(){
		List<Customer> customers = session.createCriteria(Customer.class).list();
		return customers.size();
	}
	@Override
	public Customer customerIdentifiedAs(String idType, String idNumber) {
		
		List<Customer> customers = session.createCriteria(Customer.class,"c").
				add(Restrictions.eq("c.identificationNumber",  idNumber)).add(Restrictions.eq("c.identificationType",  idType)).list();
		
		
		assertEquals(1,customers.size());
		Customer customer = customers.get(0);
		return customer;
	}
	
	//Supplier
	@Override
	public void addSupplier(Supplier newSupplier) {
		session.persist(newSupplier);
	}
	@Override
	public int numberOfSuppliers(){
		List<Supplier> supliers = session.createCriteria(Supplier.class).list();
		return supliers.size();
	}
	@Override
	public Supplier supplierIdentifiedAs(String idType, String idNumber) {
		
		List<Supplier> supliers = session.createCriteria(Supplier.class,"c").
				add(Restrictions.eq("c.identification.identificationNumber",  idNumber)).add(Restrictions.eq("c.identification.identificationType",  idType)).list();
		
		
		assertEquals(1,supliers.size());
		Supplier suplier = supliers.get(0);
		return suplier;
	}

}