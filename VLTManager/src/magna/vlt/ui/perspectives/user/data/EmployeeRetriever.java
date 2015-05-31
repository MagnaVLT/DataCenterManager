package magna.vlt.ui.perspectives.user.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import magna.vlt.db.retriever.DBWrapper;
import magna.vlt.ui.common.TreeNodeRetriever;
import magna.vlt.ui.perspectives.project.data.Feature;
import magna.vlt.ui.perspectives.project.data.Project;

public class EmployeeRetriever extends DBWrapper implements TreeNodeRetriever{

	private String viewId;
	public EmployeeRetriever(String viewId){
		this.viewId = viewId;
	}
	
	public EmployeeRetriever(){
	
	}
	
	public void getRoot(){
		
		String query = "select a.id as id, concat(a.firstname, ' ', a.lastname) as name, b.name as privilegeType, a.managerid as managerid, ";
		query += " concat(c.firstname, ' ', c.lastname) as managername, d.name as location, a.password as password, a.online as online ";
		query += " from Users a, Privilege b, Users c, Location d where  a.privilege = 2 and a.managerid = c.id and a.privilege = b.id and a.location = d.id;";
		ResultSet rs = super.getResultSet(query);
		
		try{
			while(rs.next())
			{
				String id = rs.getString("id");
				String name = rs.getString("name");
				String privilegeType = rs.getString("privilegeType");
				String managerid = rs.getString("managerid");
				String managername = rs.getString("managername");
				String location = rs.getString("location");
				String password = rs.getString("password");
				String online = rs.getString("online");
//				Employee root = new Employee(viewId, name, id, privilegeType, managerid, managername, location, password, online);
//				Employee.addRoots(viewId, root);
//				this.getChilds(root);
			}

		}catch(SQLException e)
		{
			e.printStackTrace();
		}
			
	}
	
	public void getChilds(Object parent){
		Employee parentEmployee = ((Employee)parent);
		String query = "select a.id as id, concat(a.firstname, ' ', a.lastname) as name, b.name as privilegeType, a.managerid, ";
		query += " concat(c.firstname, ' ', c.lastname) as managername, d.name as location, a.password as password, a.online as online  ";
		query += " from Users a, Privilege b, Users c, Location d where a.id!= '"+parentEmployee.getManagerid()+"' and a.managerid = '"+parentEmployee.getId()+"' ";
		query += " and a.managerid = c.id and a.privilege = b.id and a.location = d.id;";
		ResultSet rs = super.getResultSet(query);
		
		try {	
			while(rs.next())
			{
				String id = rs.getString("id");
				String name = rs.getString("name");
				String privilegeType = rs.getString("privilegeType");
				String managerid = rs.getString("managerid");
				String managername = rs.getString("managername");
				String location = rs.getString("location");
				String password = rs.getString("password");
				String online = rs.getString("online");
//				Employee child = new Employee(viewId, name, id, privilegeType, managerid, managername, location, password, online);
//				child.setParent(parentEmployee);
//				parentEmployee.addChild(child);
//				this.getChilds(child);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<Project> getInvolovedProject(Employee employee){
		ArrayList<Project> projectList = new ArrayList<Project>();
		String query = "select b.id, c.name, b.ownerid ";
		query += " from users_project_map a, project b, company c where a.projectid = b.id and b.companyid = c.id and a.userID = '" + employee.getId() + "';";
		ResultSet rs = super.getResultSet(query);
		try {
			while(rs.next()){
				int projectId = rs.getInt("id");
				String projectName = rs.getString("name");
				String ownerId = rs.getString("ownerid");
				projectList.add(new Project(projectId, projectName, ownerId));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return projectList;
	}

	public ArrayList<Feature> getInvolovedFeature(Employee employee){
		ArrayList<Feature> featureList = new ArrayList<Feature>();
		String query = "select b.id, b.name, b.description from users_feature_project_map a, feature_list where a.userID = '" + employee.getId() + "';";
		ResultSet rs = super.getResultSet(query);
		try {
			while(rs.next()){
				int featureId = rs.getInt("id");
				String featureName = rs.getString("name");
				String featureDescription = rs.getString("description");
				featureList.add(new Feature(featureId, featureName, featureDescription));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return featureList;
	}
	
	public String getField(String field, String condition, String conditionValue, String table, boolean isStr){
		String result = "";
		String query = "";
		if(isStr)
			query = "select "+field+" from " + table + " where "+condition+" = '" + conditionValue + "';";
		else
			query = "select "+field+" from " + table + " where "+condition+" = " + conditionValue + ";";
		
		ResultSet rs = super.getResultSet(query);
		try {
			if(rs.next()) result =  rs.getString(field);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	public String getConcatenatedString(String link, ArrayList<String> str){
		String concat = "";
		
		return concat;
	}
	
}
