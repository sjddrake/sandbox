
package uk.co.neo9.apps.accounts.db.hibernate;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public abstract class PersistenceBaseTestCase extends TestCase {
	private SessionFactory sessionFactory;

	@Override
	protected void setUp() throws Exception {
		// A SessionFactory is set up once for an application
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
	}

	@Override
	protected void tearDown() throws Exception {
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}

	// @SuppressWarnings({ "unchecked" })
	protected Session getSession() {
		// create a couple of events...
		Session session = sessionFactory.openSession();
		return session;
	}
}
