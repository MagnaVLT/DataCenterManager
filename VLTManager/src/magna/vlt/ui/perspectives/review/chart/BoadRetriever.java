package magna.vlt.ui.perspectives.review.chart;

import java.sql.ResultSet;
import java.sql.SQLException;

import magna.vlt.db.retriever.DBWrapper;

public class BoadRetriever extends DBWrapper{

	public String getEventID(String name){
		String value = "";
		String query = "select id from event_list where name = '" + name + "';";
		ResultSet rs = super.getResultSet(query);
		try {
			if(rs.next()) value = rs.getString("id");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
	}
}
