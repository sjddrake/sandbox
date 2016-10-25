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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 * Illustrates the use of Hibernate native APIs.  The code here is unchanged from the {@code basic} example, the
 * only difference being the use of annotations to supply the metadata instead of Hibernate mapping files.
 *
 * @author Steve Ebersole
 */
public class BreakdownEntityHBN8Test extends PersistenceBaseTestCase {
	private SessionFactory sessionFactory;

	@Test
	@SuppressWarnings({ "unchecked" })
	public void testBasicUsage() {

		// set up the test data
		Breakdown p = new Breakdown();
		p.setAmount(new Integer(1000));

		
		
		System.out.println( " saving now" );
		
		// save it
		Session session = getSession();
		session.beginTransaction();
		session.save(p);
		session.getTransaction().commit();
		session.close();

		System.out.println( " saved! " );
		
		// now lets pull events from the database and list them
		session = getSession();
        session.beginTransaction();
        List result = session.createQuery( "from Breakdown" ).list();
		for ( Breakdown breakdown : (List<Breakdown>) result ) {
			//System.out.println( "Payment (" + payment.getDate() + ") : " + payment.getDescription() + ") : " + payment.getAmount() + ") : " + payment.getMethod());
			System.out.println(breakdown.toString());
		}
        session.getTransaction().commit();
        session.close();
	}
}
