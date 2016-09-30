package timesheet;

import org.hsqldb.Server;

public class TestDB {
	private static Server server;
	public static void main(String[] args) {
		startDB();

	}
	protected static void startDB() {
		server = new Server();
        server.setDatabaseName(0, "mydb");
        server.setDatabasePath(0, "mem:mydb");
        server.setPort(9001);
        server.setLogWriter(null);
        server.setErrWriter(null);
        server.start();
	}
	
	protected static void stopDB() {
		if (server != null)
			server.stop();
	}
}
