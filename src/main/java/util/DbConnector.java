package util;

import java.sql.SQLException;
import org.postgresql.ds.PGSimpleDataSource;
import org.skife.jdbi.v2.DBI;

/**
 * @author Mike van Es
 */
public class DbConnector {
	private static final String url = "127.0.0.1";
	private static final int port = 5432;
	private static final String dbName = "pancy";
	private static final String username = "pancy";
	private static final String password = "f3823903b2dd6e35243b1bbe5a14f651";
	private static DbConnector singleInstance = null;
	private static PGSimpleDataSource source = null;

	/**
	 * @author Mike van Es
	 * @return DbConnector singleInstance
	 */
	public static DbConnector getInstance()
	{
		if (singleInstance == null) {
			singleInstance = new DbConnector();
			source = new PGSimpleDataSource();
			source.setServerName(url);
			source.setPortNumber(port);
			source.setDatabaseName(dbName);
			source.setUser(username);
			source.setPassword(password);
		}

		return singleInstance;
	}

	/**
	 * @author Mike van Es
	 * @return DBI
	 * @throws SQLException
	 */
	public static DBI getDBI() throws SQLException {
		return new DBI(source);
	}
}