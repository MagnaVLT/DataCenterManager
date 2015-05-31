package magna.vlt.ui.perspectives.user.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import magna.vlt.db.retriever.DBWrapper;
import magna.vlt.ui.perspectives.user.table.UserRemoveListener;
import magna.vlt.util.Tokenizer;

public class EmployeeCreator extends DBWrapper{

	public void createEmployee(String id, String firstName, String lastName, String privilegeId, String privilegeName,  
			ArrayList<String> managerId, ArrayList<String> managerNames, String locationId, String password, String[] featureList, String[] projectList){
		String name = firstName + " " + lastName;
		Employee employee = new Employee(name, id, privilegeId, privilegeName, managerId, managerNames, locationId, password, "1");
		for(String feature: featureList) employee.addFeature(feature);
		for(String project: projectList) employee.addProject(project);
		
		this.insertEmployeeToDatabase(employee);
	}
	
	private void insertEmployeeToDatabase(Employee employee){
		if(isExistUser(employee.getId())){
			new UserRemoveListener().remove(employee.getId());
		}
		
		insertToUserTable(employee);
		insertToFeaureUserMapTable(employee);
		insertToUserRelationshipTable(employee);
		insertToPrectUserMapTable(employee);
	}

	private void insertToUserTable(Employee employee) {
		String query = "insert into users (id, firstname, lastname, privilege, location, password, online) values (";
		
		query += "'" + employee.getId();
		query += "', '" + Tokenizer.getToken(employee.getName(), " ", 0);
		query += "', '" + Tokenizer.getToken(employee.getName(), " ", 1);
		query += "', " + employee.getPrivilege();
		query += ", " + employee.getLocation();
		query += ", PASSWORD('" + employee.getPassword();
		query += "'), 0);";
		
		super.execute(query);
	}
	
	private void insertToUserRelationshipTable(Employee employee){
		if(employee.getPrivilege().equals("2")){
			String query = "insert into user_relationships (userid, managerid) values (";
			query += "'" + employee.getId() + "', ";
			query += "'0');";
			super.execute(query);
		}else{
			for(String managerid: employee.getManagerid()){
				String query = "insert into user_relationships (userid, managerid) values (";
				query += "'" + employee.getId() + "', ";
				query += "'" + managerid + "');";
				super.execute(query);
			}
		}
		
		
		
	}
	
	private void insertToFeaureUserMapTable(Employee employee) {
		String userID = employee.getId();
		
		for(int i = 0 ; i < employee.getFeatures().size() ; i++){
			String feature = employee.getFeatures().get(i);
			String query = "insert into users_feature_project_map (UserID, FeatureID, projectID) values (";
			query += "'" + userID + "', ";
			query += feature + ", ";
			query += employee.getProjects().get(i) + ");";
			super.execute(query);
		}
			
	}
	
	private void insertToPrectUserMapTable(Employee employee) {
		String userID = employee.getId();
		for(String project: employee.getProjects()){
			String query = "insert into users_project_map (UserID, projectID) values (";
			query += "'" + userID + "', ";
			query += project + ");";
			if(!isExistProject(userID, project))super.execute(query);
		}
	}
	
	private boolean isExistUser(String user)
	{
		String query = "select * from users where id = '" + user + "';";
		ResultSet rs = super.getResultSet(query);
		boolean exist = false;
		try {
			exist = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}
	
	private boolean isExistProject(String user, String project)
	{
		String query = "select * from users_project_map where userid = '" + user + "' and projectid = " + project +";";
		ResultSet rs = super.getResultSet(query);
		boolean exist = false;
		try {
			exist = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}

}
