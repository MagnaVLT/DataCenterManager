package magna.vlt.ui.perspectives.project.data;

import java.sql.SQLException;

import magna.vlt.db.retriever.DBWrapper;

public class FeatureCreator extends DBWrapper {

	private Feature newEvent;
	public FeatureCreator(Feature newEvent){
		this.newEvent = newEvent;
		try {
			this.newEvent.setId(super.getID("feature_list"));
			this.insertFeature();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void insertFeature() throws SQLException{
		String query = "insert into feature_list (id, name, Description) values(";
		query += "" + newEvent.getId() + ",";
		query += "'" + newEvent.getName() + "',";
		query += "'" + newEvent.getDescription() + "'";
		query +=");";
		
		super.execute(query);
	}
}
