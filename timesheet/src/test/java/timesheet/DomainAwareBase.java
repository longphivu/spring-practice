package timesheet;

import org.hsqldb.Server;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.jdbc.JdbcTestUtils;

@ContextConfiguration(locations = "{/persitence-beans.xml}")
public class DomainAwareBase extends AbstractJUnit4SpringContextTests {
	private final String deleteScript = "classpath:/resources/sql/cleanup.sql";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static Server server;
	
	@BeforeClass
	protected static void startDB() {
		server = new Server();
        server.setDatabaseName(0, "mydb");
        server.setDatabasePath(0, "mem:mydb");
        server.setPort(9001);
        server.setLogWriter(null);
        server.setErrWriter(null);
        server.start();
	}

	@Before
	protected void deleteAllDomainEntites() {
		JdbcTestUtils.executeSqlScript(jdbcTemplate, new FileSystemResource(deleteScript), false);
		//ScriptUtils.executeSqlScript(connection, new FileSystemResource(deleteScript));
	}
	
	@AfterClass
	protected static void stopDB() {
		if (server != null)
			server.stop();
	}
}
