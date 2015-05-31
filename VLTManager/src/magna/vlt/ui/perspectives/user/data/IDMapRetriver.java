package magna.vlt.ui.perspectives.user.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import magna.vlt.db.connection.DBConnector;
import magna.vlt.db.retriever.DBWrapper;

public class IDMapRetriver extends DBWrapper{
	
	public static Hashtable<?, ?> getItem(String idField, String nameField, String table){
		Hashtable<String, String> items = new Hashtable<String, String>();
		Connection conn = DBConnector.getConn();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "select "+idField+", "+ nameField + " from " + table + ";";
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				items.put(rs.getString(idField), rs.getString(nameField));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return items;
	}
}
