package magna.vlt.ui.perspectives.user.data.provider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import magna.vlt.db.retriever.DBWrapper;
import magna.vlt.ui.perspectives.user.data.Log;

public class UserHistoryProvider extends DBWrapper{
	private List<Log> history;

	public List<Log> getHistory() {
		return history;
	}

	public void setHistory(String userId) {
		history = new ArrayList<Log>();
		String query = "select a.id as id, concat(firstname, ' ', lastname) as name, reviewStartTime, reviewEndTime, online from users a, user_log b where"
				+ " a.id = b.userid and a.id = '" + userId + "';";
		
		ResultSet rs = super.getResultSet(query);
		try {
			while(rs.next()){
				String id = rs.getString("id");
				String name = rs.getString("name");
				String reviewStartTime = rs.getString("reviewStartTime");
				String reviewEndTime = rs.getString("reviewEndTime");
				history.add(new Log(id, name, reviewStartTime, reviewEndTime));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
}
