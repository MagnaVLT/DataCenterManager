package magna.vlt.ui.perspectives.review.chart.query;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfree.data.general.DefaultPieDataset;


public class QueryEventCategory extends Query{

	@Override
	protected void retrieveRecord() throws SQLException {
		String queryString = "select count(a.eventcategoryid) as cnt, trim(b.name) as name ";
		queryString += " from event_report a, event_category_list b where a.eventcategoryid = b.id group by eventcategoryid, b.name;";
		ResultSet rs = super.getResultSet(queryString);
		while(rs.next()){
			int cnt = rs.getInt("cnt");
			if(cnt>0){
				this.name.add(rs.getString("name"));
				this.value.add(cnt);
			}
		}
	}
	
	public void updateRecordByEvent(DefaultPieDataset dataset, String eventid, String projectid){
		String queryString = "select count(a.eventcategoryid) as cnt, trim(b.name) as name ";
		queryString += " from event_report a, event_category_list b where a.eventcategoryid = b.id and ";
		queryString += " a.projectid = " + projectid + " and a.eventid = " + eventid + " ";
		queryString += " group by eventcategoryid, b.name;";
		
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

	public void updateRecordByFeature(DefaultPieDataset dataset, String projectID, String featureID) {
		String queryString = "select count(a.eventcategoryid) as cnt, trim(b.name) as name ";
		queryString += " from event_report a, event_category_list b, event_list c";
		queryString += " where a.eventcategoryid = b.id and a.eventid = c.id and ";
		queryString += " a.projectid = " + projectID + " and c.featureid = " + featureID + " ";
		queryString += " group by eventcategoryid, b.name;";
		
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

}
