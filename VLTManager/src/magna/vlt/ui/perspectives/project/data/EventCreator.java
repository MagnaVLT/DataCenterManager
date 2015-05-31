package magna.vlt.ui.perspectives.project.data;

import java.sql.SQLException;

import magna.vlt.db.retriever.DBWrapper;

public class EventCreator extends DBWrapper {

	private Event newEvent;
	public EventCreator(Event newEvent){
		this.newEvent = newEvent;
		try {
			this.newEvent.setId(super.getID("event_list"));
			this.insertEvent();
			this.insertEventDescription();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void insertEvent() throws SQLException{
		String query = "insert into event_list (id, featureid, name, feature_sub_category_id, requestor) values(";
		query += "" + newEvent.getId() + ",";
		query += "" + newEvent.getFeatureId() + ",";
		query += "'" + newEvent.getName() + "',";
		query += "" + newEvent.getSubFeatureCategoryId() + ",";
		query += "'" + newEvent.getRequestor() + "'";
		query +=");";
		
		super.execute(query);
	}
	
	private void insertEventDescription() throws SQLException{
		String query = "insert into event_description (id, eventid, description, eventCategoryID) values(";
		query += "" + super.getID("event_description") + ",";
		query += "" + newEvent.getId() + ",";
		query += "'" + newEvent.getDescription() + "',";
		query += "" + newEvent.getEventCategory() + "";
		query +=");";
		
		super.execute(query);
	}
}
