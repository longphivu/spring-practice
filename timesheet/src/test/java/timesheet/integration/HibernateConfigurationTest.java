package timesheet.integration;

import static org.junit.Assert.assertNotNull;

import org.hibernate.SessionFactory;
import org.hsqldb.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = "/persistence-beans.xml")
public class HibernateConfigurationTest extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	SessionFactory sf;
	
	Server server;
	
	@Before
	public void setup(){
		server = new Server();
        server.setDatabaseName(0, "mydb");
        server.setDatabasePath(0, "mem:mydb");
        server.setPort(9001);
        server.setLogWriter(null);
        server.setErrWriter(null);
        server.start();
	}
	
	@Test
	public void testHibernationConfiguration() {
		// Spring IOC container instantiated and prepared sessionFactory
		assertNotNull(sf);
	}
	
	@After
	public void stop(){
		if (server != null) 
			server.stop();
	}

}
