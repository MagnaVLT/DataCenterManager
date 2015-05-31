package magna.vlt.db.retriever;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import magna.vlt.db.connection.DBConnector;
import magna.vlt.db.connection.DBInfo;

public class DBWrapper {
	protected DBWrapper(){
		DBConnector.getConnection(new DBInfo("jdbc:mysql://10.64.231.50:3306/fcm_gen3_hil_playback" + "?zeroDateTimeBehavior=convertToNull", "root", "Magnatkfkd"));
//		DBConnector.getConnection(new DBInfo("jdbc:mysql://localhost:3306/fcm_gen3_hil_playback" + "?zeroDateTimeBehavior=convertToNull", "root", "hil"));
//		DBConnector.getConnection(new DBInfo("jdbc:mysql://192.168.0.9:3306/fcm_gen3_hil_playback" + "?zeroDateTimeBehavior=convertToNull", "root", "dkagh"));
	}
	
	public ResultSet getResultSet(String query){
		Connection conn = DBConnector.getConn();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return rs;
	}
	
	protected void execute(String query){
		
		try {
			Connection conn = DBConnector.getConn();
			Statement stmt = conn.createStatement();
			stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	protected void insert(ArrayList<String> queries){
		
		try {
			Connection conn = DBConnector.getConn();
			Statement stmt = conn.createStatement();
			conn.setAutoCommit(false);
			for(String query : queries){
				stmt.addBatch(query);
			}
			stmt.executeBatch();
			conn.commit();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected int getID(String table) throws SQLException {
		
		String query = "select id from " + table + ";";
		ResultSet data = this.getResultSet(query);
		ArrayList<Integer> idList = new ArrayList<Integer>();
		int id = 1;
		data.last();
		if(data.getRow()==0){
			id = 1;
		}else{
			data.beforeFirst();
			while(data.next()){
				idList.add(data.getInt("id"));
			}
			Collections.sort(idList, Collections.reverseOrder());
			id = idList.get(0) + 1;
		}
		
		return id;
	}


}
