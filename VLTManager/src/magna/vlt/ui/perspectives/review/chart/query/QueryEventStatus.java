package magna.vlt.ui.perspectives.review.chart.query;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfree.data.general.DefaultPieDataset;


public class QueryEventStatus extends Query {

	@Override
	protected void retrieveRecord() throws SQLException {
		String queryString = "select count(a.eventstatusid) as cnt, b.name as name ";
		queryString += " from event_report a, event_status_list b where a.eventstatusid = b.id group by eventstatusid, b.name;";
		

		ResultSet rs = super.getResultSet(queryString);
		while(rs.next()){
			this.name.add(rs.getString("name"));
			this.value.add(rs.getInt("cnt"));
		}
		
	}
	
	
	public void updateRecordByEvent(DefaultPieDataset dataset, String projectid, String eventid) {

		String queryString = "select count(a.eventstatusid) as cnt, b.name as name ";
		queryString += " from event_report a, event_status_list b, project_event_map c";
		queryString += " where a.eventstatusid = b.id and a.eventid = c.eventid and c.projectid = " + projectid + " and c.eventid = " + eventid + " ";
		queryString += " group by eventstatusid, b.name;";
		
		ResultSet rs = super.getResultSet(queryString);
		
		try {
			
			while(rs.next()){
				int cnt = rs.getInt("cnt");
				if(cnt > 0)
					dataset.setValue(rs.getString("name"), cnt);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateRecordByFeature(DefaultPieDataset dataset, String projectid, String featureid) {
		String queryString = "select count(a.eventstatusid) as cnt, b.name as name ";
		queryString += " from event_report a, event_status_list b, event_list c";
		queryString += " where a.eventstatusid = b.id and a.eventid = c.id and a.projectid = " + projectid + " and c.featureid = " + featureid + " ";
		queryString += " group by eventstatusid, b.name;";
		
		ResultSet rs = super.getResultSet(queryString);
		try {
			
			while(rs.next()){
				int cnt = rs.getInt("cnt");
				if(cnt > 0)
					dataset.setValue(rs.getString("name"), cnt);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
