package magna.vlt.ui.perspectives.project.data.provider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import magna.vlt.db.retriever.DBWrapper;
import magna.vlt.ui.common.GUIInfoRetriever;
import magna.vlt.ui.perspectives.project.data.Event;
import magna.vlt.ui.perspectives.project.data.Feature;

public class FeatureEventProvider extends DBWrapper{

	  private List<Feature> features;
	  private List<Event> events;


	  public String[] getEventCategory(){
		  String query = "select name from event_category_list;";
		  String[] result = new GUIInfoRetriever().getNames(query);
		  
		  return result;
	  }
	  
	  public void setFeature(){
		  features = new ArrayList<Feature>();
		  String query = "select id, name, description from feature_list order by id;";
		  ResultSet rs = super.getResultSet(query);
		  try {
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				features.add(new Feature(id, name, description));
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	  }
	  
	  public void setFeature(int projectid){
		  features = new ArrayList<Feature>();
		  String query = "select a.id, a.name, a.description from feature_list a, project_feature_map b ";
		  query+= " where a.id = b.featureid and b.projectid ="+projectid+" order by id;";
		  ResultSet rs = super.getResultSet(query);
		  try {
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				features.add(new Feature(id, name, description));
			  }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	  }
	  
	  
	  public void setEvent(int featureId){
		  events = new ArrayList<Event>();
		  String query = "select a.id as id, b.eventcategoryid as categoryid, c.name as categoryName, a.featureid as featureid, a.name as name, b.description as description ";
		  query+= " from event_list a, event_description b, event_category_list c where b.eventcategoryid = c.id and a.id = b.eventid and a.featureid = " + featureId + " ";
		  query+= " order by id;";

		  ResultSet rs = super.getResultSet(query);
		  try {
			while(rs.next()){
				int feature = rs.getInt("featureid");
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				int categoryid = rs.getInt("categoryid");
				String categoryName = rs.getString("categoryName");
				events.add(new Event(feature, id, name, description, categoryid, categoryName));
			  }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	  }
	  
	  public List<Feature> getFeature() {
	    return features;
	  }
	  
	  public List<Event> getEvent() {
		    return events;
	  }
	  
	} 

