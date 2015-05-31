package magna.vlt.ui.perspectives.project.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

import magna.vlt.db.retriever.DBWrapper;

public class ProjectCreator extends DBWrapper {
	
	private Project newProject;
	private ArrayList<String> featureIDMap = new ArrayList<String>();
	private Hashtable<String, ArrayList<String>> eventIDMap = new Hashtable<String, ArrayList<String>>();
	
	public ProjectCreator(){}
	public ProjectCreator(Project newProject){
		try {
			this.newProject = newProject;
			this.newProject.setId(this.getID("project"));
			this.insertProject();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	private void insertProject() throws SQLException{
		this.insertToProjectTable();
		this.insertFeautre();
		this.insertEvent();
	}

	private void insertToProjectTable() {
		String query = "";

		query += "insert into project (id, name, companyid, ownerid, generation, start, end, isopen) values (";
		query += "" + this.newProject.getId() + ", ";
		query += "'" + this.newProject.getName() + "', ";
		query += "" + this.newProject.getOem() + ", ";
		query += "'" + this.newProject.getOwnerid() + "', ";
		query += "" + this.newProject.getStrGeneration() + ", ";
		query += "'" + this.newProject.getStrStart() + "', ";
		query += "'" + this.newProject.getStrEnd() + "', ";
		query += "" + "0" + "";
		query += ");";
		
		super.execute(query);
	}
	
	
	private void insertFeautre() throws SQLException{
		String[] featureList = this.newProject.getFeatureList();
		String[] featureNameList = this.newProject.getFeatureNameList();
		String[] featureDescriptionList = this.newProject.getFeatureDescription();
		for(int i = 0 ; i < featureList.length ; i++){
			if(this.checkFeature(featureList[i], featureNameList[i], featureDescriptionList[i])){
				this.featureIDMap.add(featureList[i]);
			}else{
				int newId = this.insertToFeatureList(featureNameList[i], featureDescriptionList[i]);
				this.featureIDMap.add(String.valueOf(newId));
			}
		}
		insertTotMapTable(this.featureIDMap, "project_feature_map");
	}
	
	private int insertToFeatureList(String featureName, String featureDescription) throws SQLException{
		int curFeatureId = this.getID("feature_list");
		String query = "insert into featureList (id, name, description) values (";
		query += "" + curFeatureId + ", ";
		query += "'" + featureName + "', ";
		query += "'" + featureDescription + "' ";
		query += ");";
		super.execute(query);
		return curFeatureId;
	}
	
	
	private void insertEvent() throws SQLException{
		String[] eventList = this.newProject.getEventList();
		String[] featureList = this.newProject.getFeatureList();
		String[] eventNameList = this.newProject.getEventNameList();
		String[] eventDescriptionList = this.newProject.getEventDescription();
		String[] eventCategoryList = this.newProject.getEventCategoryList();
		
		for(int i = 0 ; i < eventList.length ; i++){
			
			ArrayList<String> values = new ArrayList<String>();
			String eventID = getEventId(eventList[i], featureList[i], eventNameList[i], eventDescriptionList[i]);
			String descriptionID = this.getDecriptionID(eventList[i], eventDescriptionList[i], eventCategoryList[i]);
			values.add(eventID);
			values.add(descriptionID);
			this.eventIDMap.put(eventList[i], values);
		}
		
		insertTotMapTable(this.eventIDMap, "project_event_map");
	}

	private String getEventId(String id, String featureid, String name, String desc)
			throws SQLException {
		
		String newId = id;
		if(!this.checkEvent(id, name, desc)){
			newId = String.valueOf(this.insertToEventList(featureid, name, "", ""));
		}
		
		return newId;
	}
	
	private String getDecriptionID(String id, String desc, String category) throws SQLException{
		
		if(this.checkEventDescription(id, desc, category)){
			return id;
		}else{
			String newId = String.valueOf(this.insertToEventDescription(id, desc, category));
			return newId;
		}
	}
	
	private void insertTotMapTable(ArrayList<String> map, String table) {
		int projectId = this.newProject.getId();
		HashSet<String> tempSet = new HashSet<String>(); 
		for(String id: map){
			String key = projectId + "-" + id;
			if(!tempSet.contains(key)){
				tempSet.add(key);
				String query = "insert into "+table+" (projectid, featureid) values (";
				query += "" + projectId + ", ";
				query += "" + id + "); ";
				super.execute(query);
			}
		}
	}
	
	private void insertTotMapTable(Hashtable<String, ArrayList<String>> map, String table) {
		int projectId = this.newProject.getId();
		ArrayList<String> queries = new ArrayList<String>();
		
		for(String id: map.keySet()){
			String query = "insert into "+table+" (projectid, eventid, eventdescriptionid) values (";
			query += "" + projectId + ", ";
			query += "" + map.get(id).get(0) + ", ";
			query += "" + map.get(id).get(1) + "); ";
			queries.add(query);
		}
		
		super.insert(queries);
	}

	private int insertToEventDescription(String id, String description, String category) throws SQLException {
		int curFeatureId = this.getID("event_description");
		String query = "insert into event_description (id, eventid, description, eventCategoryID) values (";
		query += "" + curFeatureId + ", ";
		query += "" + id + ", ";
		query += "'" + description + "', ";
		query += "" + category + " ";		
		query += ");";
		super.execute(query);
		
		return curFeatureId;
	}
	
	private int insertToEventList(String featureid, String name, String feature_sub_category_id, String requestor) throws SQLException {
		int curFeatureId = this.getID("event_list");
		String query = "insert into event_list (id, featureid, name, feature_sub_category_id, requestor) values (";
		query += "" + curFeatureId + ", ";
		query += "" + featureid + ", ";
		query += "'" + name + "', ";
		query += "" + feature_sub_category_id + ", ";
		query += "'" + requestor + "' ";
		query += ");";
		super.execute(query);
		return curFeatureId;
	}

	private boolean checkFeature(String featureId, String featureName, String featureDescription) throws SQLException{
		String query = "select name, description from feature_list where id = " + featureId;
		ResultSet rs = super.getResultSet(query);
		String curDescription = "";
		String curName = "";
		
		if(rs.next()){
			curDescription = rs.getString("description");
			curName = rs.getString("name");
		}
		
		return curDescription.equals(featureDescription) && curName.equals(featureName);
	}
	
	private boolean checkEventDescription(String eventId, String eventDescription, String eventCategoryID) throws SQLException{
		String query = "select description from event_description where id = " + eventId + " and eventCategoryID = " + eventCategoryID + ";";
		
		ResultSet rs = super.getResultSet(query);
		String curDescription = "";
		
		if(rs.next()){
			curDescription = rs.getString("description");
		}
		
		return curDescription.equals(eventDescription);
	}
	
	
	private boolean checkEvent(String eventId, String eventName, String eventDescription) throws SQLException{
		String query = "select name from event_list where id = " + eventId;
		ResultSet rs = super.getResultSet(query);
		String curName = "";
		if(rs.next()){
			curName = rs.getString("name");
		}
		return curName.equals(eventName);
	}
	
	public void remove(String projectId){
		String query = "delete from project where id = " + projectId + ";";
		super.execute(query);
		query = "delete from project_feature_map where projectid = " + projectId + ";";
		super.execute(query);
		query = "delete from project_event_map where projectid = " + projectId + ";";
		super.execute(query);
		query = "delete from users_feature_project_map where projectid = " + projectId + ";";
		super.execute(query);
		query = "delete from users_project_map where projectid = " + projectId + ";";
		super.execute(query);
	}
	
	public void close(String projectId) {
		String query = "update project set isOpen = 1 where id = " + projectId + ";";
		super.execute(query);
	}
	
	public void open(String projectId) {
		String query = "update project set isOpen = 0 where id = " + projectId + ";";
		super.execute(query);
	}
}

