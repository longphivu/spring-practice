package timesheet;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.jdbc.JdbcTestUtils;

@ContextConfiguration(locations = {"/persistence-beans.xml","/controllers.xml"})
public class DomainAwareBase extends AbstractJUnit4SpringContextTests {
	String basePath = new File("").getAbsolutePath();
	String deleteScript = new File("src/main/resources/sql/cleanup.sql")
            .getAbsolutePath();
	String insertScript = new File("src/main/resources/sql/dummyData.sql")
            .getAbsolutePath();

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
//	private static Server server;
//	
//	@BeforeClass
//	public static void startDB() {
//		server = new Server();
//        server.setDatabaseName(0, "mydb");
//        server.setDatabasePath(0, "mem:mydb");
//        server.setPort(9001);
//        server.setLogWriter(null);
//        server.setErrWriter(null);
//        server.start();
//	}
//	
//	@AfterClass
//	public static void stopDB() {
//		if (server != null)
//			server.stop();
//	}
	
	@Before
	public void deleteAllDomainEntites() {
		JdbcTestUtils.executeSqlScript(jdbcTemplate, new FileSystemResource(deleteScript), false);
		//ScriptUtils.executeSqlScript(connection, new FileSystemResource(deleteScript));
	}
	
	@Before
	public void insertAllDomainEntities() {
		JdbcTestUtils.executeSqlScript(jdbcTemplate, new FileSystemResource(insertScript), false);
	}

	
	@Test
	public void testdummy() {
		
	}
}
