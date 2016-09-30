package timesheet.integration;

import static org.junit.Assert.assertNotNull;

import org.hibernate.SessionFactory;
import org.hsqldb.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = "/persistence-beans.xml")
public class HibernateConfigurationTest extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	SessionFactory sf;
	
	@Test
	public void testHibernationConfiguration() {
		// Spring IOC container instantiated and prepared sessionFactory
		assertNotNull(sf);
	}
	
}
