package magna.vlt.ui.perspectives.project.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import magna.vlt.db.retriever.DBWrapper;
import magna.vlt.ui.common.TreeNodeRetriever;

public class FeaturEventRetriever extends DBWrapper implements TreeNodeRetriever{

	private String viewId;
	public FeaturEventRetriever(String viewId){
		this.viewId = viewId;
	}
	
	public void getRoot(){
		
		String query = "select id, name, description from project;";
		ResultSet rs = super.getResultSet(query);
		
		try{
			while(rs.next())
			{
				String id = rs.getString("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				FeatureEvent featureEvent = new FeatureEvent(viewId, FeatureEvent.project, id, name, description);
				FeatureEvent.addRoots(viewId, featureEvent);
				this.getChilds(featureEvent);
			}

		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void getChilds(Object parent){
		FeatureEvent parentFeatureEvent = (FeatureEvent)parent;
		String query = "select a.id, a.name, a.description from feature_list a, project_feature_map b ";
		query += " where a.id = b.featureid and b.projectid = "+parentFeatureEvent.getId()+" group by a.id, a.name, a.description;";
		
		ResultSet rs = super.getResultSet(query);
		try {	
			while(rs.next())
			{
				String id = rs.getString("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				FeatureEvent featureEvent = new FeatureEvent(viewId, FeatureEvent.feature, id, name, description);
				featureEvent.setParent(parentFeatureEvent);
				parentFeatureEvent.addChild(featureEvent);
				this.getEvent(parentFeatureEvent.getId(), featureEvent);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void getEvent(String projectid, Object parent){
		FeatureEvent parentFeatureEvent = (FeatureEvent)parent;
		String query = "select b.id, b.name, c.description, c.eventcategoryid, d.name as categoryName ";
		query += " from project_event_map a, event_list b, event_description c, event_category_list d ";
		query += " where a.eventid = b.id and a.eventdescriptionid = c.id and c.eventcategoryid = d.id ";
		query += " and a.projectid = "+projectid+" and b.featureid = " + parentFeatureEvent.getId()+ ";";

		ResultSet rs = super.getResultSet(query);
		
		try {	
			while(rs.next())
			{
				String id = rs.getString("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String categoryId = rs.getString("eventcategoryid");
				String categoryName = rs.getString("categoryName");
				
				FeatureEvent childFeatureEvent = new FeatureEvent(viewId, FeatureEvent.event, id, name, description, categoryId, categoryName);
				childFeatureEvent.setParent(parentFeatureEvent);
				parentFeatureEvent.addChild(childFeatureEvent);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
