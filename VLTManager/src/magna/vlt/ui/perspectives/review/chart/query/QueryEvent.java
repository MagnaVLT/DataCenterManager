package magna.vlt.ui.perspectives.review.chart.query;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfree.data.general.DefaultPieDataset;

public class QueryEvent extends Query {

	@Override
	protected void retrieveRecord() throws SQLException {
		String queryString = "select c.name as name, count(*) as cnt from event_report b, event_list c ";
		queryString += " where b.eventid = c.id group by name order by cnt desc;";
		ResultSet rs = super.getResultSet(queryString);
		
		while(rs.next()){
			this.name.add(rs.getString("name"));
			this.value.add(rs.getInt("cnt"));
		}
	}
	
	public void updateRecordByFeature(DefaultPieDataset dataset, String projectid, String featureid) {

		String queryString = "select c.name as name, count(*) as cnt from project_event_map a, event_report b, event_list c ";
		queryString += " where a.projectid = "+projectid+" and c.featureid = "+featureid+" "; 
		queryString += " and b.eventid = a.eventid and a.eventid = c.id group by name order by cnt desc;";
		ResultSet rs = super.getResultSet(queryString);
		
		try {
			while(rs.next()){
				int cnt = rs.getInt("cnt");
				if(cnt>0){
					dataset.setValue(rs.getString("name"), rs.getInt("cnt"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getFeatureName(String featureid){
		String queryString = "select name from feature_list where id = " + featureid + ";";
		ResultSet rs = super.getResultSet(queryString);
		String name = "";
		try {
			while(rs.next()){
					name = rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return name;
	}
	
	public String getEventName(String eventid){
		String queryString = "select name from event_list where id = " + eventid + ";";
		ResultSet rs = super.getResultSet(queryString);
		String name = "";
		try {
			while(rs.next()){
					name = rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return name;
	}
	
}
