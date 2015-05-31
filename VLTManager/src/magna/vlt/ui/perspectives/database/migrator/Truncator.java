package magna.vlt.ui.perspectives.database.migrator;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Truncator {

	public void truncate(Connection connNewDB, String tableNameInNewDB)
	{
		try {
			Statement stmt2 = connNewDB.createStatement();
			
			stmt2.executeQuery("SET FOREIGN_KEY_CHECKS = 0;");
			stmt2.executeQuery("truncate table " + tableNameInNewDB);
			stmt2.executeQuery("SET FOREIGN_KEY_CHECKS = 1;");
			stmt2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
