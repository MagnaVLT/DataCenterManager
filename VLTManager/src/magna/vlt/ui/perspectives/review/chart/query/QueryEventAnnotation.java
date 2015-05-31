package magna.vlt.ui.perspectives.review.chart.query;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfree.data.general.DefaultPieDataset;

public class QueryEventAnnotation extends Query {
	@Override
	protected void retrieveRecord() throws SQLException {
		// TODO Auto-generated method stub
		String queryString = "select a.predefinedannotationid, b.name, count(a.reportid) as cnt from event_report a, predefined_annotation_list b "
				+ "where a.predefinedannotationid = b.id ";
		queryString += "group by b.name, a.reportid;";
		ResultSet rs = super.getResultSet(queryString);
		
		while(rs.next()){
			if(rs.getInt("predefinedannotationid")!=0){
				this.name.add(rs.getString("name"));
				this.value.add(rs.getInt("cnt"));
			}
		}
		
		int freeAnnotated = this.getFreeAnnotation();
		super.setPair("Not-Annotated", this.getTotal() - this.getPredefined() - freeAnnotated);
		if(freeAnnotated>0)super.setPair("Free-Annotated", freeAnnotated);
	}
	
	private int getTotal() throws SQLException{
		int result = 0;
		String queryString = "select count(*) as cnt from event_report;";
		ResultSet rs = super.getResultSet(queryString);
		if(rs.next()) result = rs.getInt("cnt");
				
		return result;
	}
	
	private int getFreeAnnotation() throws SQLException{
		int result = 0;
		String queryString = "select count(*) as cnt from event_report where userannotation != '';";
		ResultSet rs = super.getResultSet(queryString);
		if(rs.next()) result = rs.getInt("cnt");
		return result;
	}
	
	private int getPredefined() throws SQLException{
		int result = 0;
		String queryString = "select count(*) as cnt from event_report where predefinedannotationid !=0;";
		ResultSet rs = super.getResultSet(queryString);
		if(rs.next()) result = rs.getInt("cnt");

		return result;
	}
	
	public void updateRecordByEvent(DefaultPieDataset dataset, String projectid, String eventid){
		
		try {
			
			int total = getTotatlAnnotation(projectid, eventid, true);
			if(total>0){
				String query = "select b.name, count(b.id) as cnt from event_report a, predefined_annotation_list b, event_list c ";
				query += "  where a.predefinedannotationid = b.id and a.predefinedannotationid != 0 and ";
				query += " a.eventid = c.id and c.id = " + eventid + " and ";
				query += " a.projectid = " + projectid + ";";
				ResultSet rs = super.getResultSet(query);
			
			
				while(rs.next()){
					if(rs.getString("name")!=null)
						dataset.setValue(rs.getString("name"), rs.getInt("cnt"));
				}
				
				int freeAnnotated = getFreeAnnotation(projectid, eventid, true);
				int notAnnotated = getNotAnnotation(projectid, eventid, true);
				if(freeAnnotated > 0) {
					dataset.setValue("Free-Annotated", freeAnnotated);
				}
				if(notAnnotated > 0){
					dataset.setValue("Not-Annotated", notAnnotated);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void updateRecordByFeature(DefaultPieDataset dataset, String projectid, String featureid){
		
		try {
			
			int total = getTotatlAnnotation(projectid, featureid, false);
			if(total>0){
				String query = "select b.name, count(b.id) as cnt from event_report a, predefined_annotation_list b, event_list c ";
				query += "  where a.predefinedannotationid = b.id and a.predefinedannotationid != 0 and ";
				query += " a.eventid = c.id and c.featureid = " + featureid + " and ";
				query += " a.projectid = " + projectid + ";";
				ResultSet rs = super.getResultSet(query);
			
			
				while(rs.next()){
					if(rs.getString("name")!=null)
						dataset.setValue(rs.getString("name"), rs.getInt("cnt"));
				}
				
				int freeAnnotated = getFreeAnnotation(projectid, featureid, false);
				int notAnnotated = getNotAnnotation(projectid, featureid, false);
				if(freeAnnotated > 0) {
					dataset.setValue("Free-Annotated", freeAnnotated);
				}
				if(notAnnotated > 0){
					dataset.setValue("Not-Annotated", notAnnotated);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private int getTotatlAnnotation(String projectid, String eventid, boolean event) throws SQLException{
		int result = 0;
		
		String query = "select count(*) as cnt from event_report a, event_list c ";
		query += " where a.eventid = c.id and ";
		if(event)query += " c.id = " + eventid + " and ";
		else query += " c.featureid = " + eventid + " and ";
		query += " a.projectid = " + projectid + ";";
		
		ResultSet rs = super.getResultSet(query);
		if(rs.next()) {
			result = rs.getInt("cnt");
		}
		return result;
	}
	
	private int getFreeAnnotation(String projectid, String eventid, boolean event) throws SQLException{
		int result = 0;
		
		String query = "select count(*) as cnt from event_report a, event_list c ";
		query += "  where a.userannotation != '' and  a.eventid = c.id and ";
		if(event)query += " c.id = " + eventid + " and ";
		else query += " c.featureid = " + eventid + " and ";
		query += " a.projectid = " + projectid + ";";
		
		ResultSet rs = super.getResultSet(query);
		if(rs.next()) {
			result = rs.getInt("cnt");
		}
		return result;
	}

	
	private int getNotAnnotation(String projectid, String eventid, boolean event) throws SQLException{
		int result = 0;
		
		String query = "select count(b.id) as cnt from event_report a, predefined_annotation_list b, event_list c ";
		query += "  where a.predefinedannotationid = b.id and a.predefinedannotationid = 0 and a.userannotation = '' and ";
		query += " a.eventid = c.id and ";
		if(event)query += " c.id = " + eventid + " and ";
		else query += " c.featureid = " + eventid + " and ";
		
		query += " a.projectid = " + projectid + ";";
		
		
		ResultSet rs = super.getResultSet(query);
		if(rs.next()) result = rs.getInt("cnt");
		return result;
	}
	
}
