/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package uk.co.neo9.apps.accounts.db.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Illustrates the use of Hibernate native APIs.  The code here is unchanged from the {@code basic} example, the
 * only difference being the use of annotations to supply the metadata instead of Hibernate mapping files.
 *
 * @author Steve Ebersole
 */
public class PaymentEntityJPATest extends TestCase { //extends PersistenceBaseTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Override
	protected void setUp() throws Exception {
		// like discussed with regards to SessionFactory, an EntityManagerFactory is set up once for an application
		// 		IMPORTANT: notice how the name here matches the name we gave the persistence-unit in persistence.xml!
		entityManagerFactory = Persistence.createEntityManagerFactory( "uk.co.neo9.apps.accounts" );
	}

	@Override
	protected void tearDown() throws Exception {
		entityManagerFactory.close();
	}	
	

	@Test
	@SuppressWarnings({ "unchecked" })
	public void testBasicUsage() {

		// set up the test data
		Payment p = new Payment();
		p.setAmount(new Integer(1000));

		
		Breakdown b = new Breakdown();
		b.setAmount(new Integer(1000));
		
		p.addBreakdown(b);
		
		
		System.out.println( " saving now" );
		
		// save it
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		
		entityManager.persist(b);
		
		entityManager.persist(p);
		
		
		
		entityManager.getTransaction().commit();
		entityManager.close();

		System.out.println( " saved! " );
		
		// now lets pull events from the database and list them
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
        List result = entityManager.createQuery( "from Payment", Payment.class ).getResultList();
		for ( Payment payment : (List<Payment>) result ) {
			System.out.println( "Payment (" + payment.getDate() + ") : " + payment.getDescription() + ") : " + payment.getAmount() + ") : " + payment.getMethod());
		}
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
