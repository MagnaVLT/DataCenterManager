package magna.vlt.ui.perspectives.user.data.provider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import magna.vlt.db.retriever.DBWrapper;
import magna.vlt.ui.common.VTJob;
import magna.vlt.ui.perspectives.user.data.Employee;

public abstract class EmployeeProvider extends DBWrapper implements VTJob {


	protected int total = 0;
	protected ResultSet rs;
	protected List<Employee> employees;

	public List<Employee> getEmployees() {
		return employees;
	}
	
	
	protected ArrayList<ArrayList<String>> getManagerRelationship(String id){
		ArrayList<ArrayList<String>> managers = new ArrayList<ArrayList<String>>(); 
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> ids = new ArrayList<String>();
		
		String query = "SELECT concat(b.firstname, ' ', b.lastname) as name, b.id as id FROM user_relationships a, users b "
				+ "where a.managerid = b.id and a.userid = '"+ id + "';";
		
		ResultSet rs = super.getResultSet(query);
		try {
			while(rs.next()){
				ids.add(rs.getString("id"));
				names.add(rs.getString("name"));
			}
			
			managers.add(ids);
			managers.add(names);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return managers;
	}
	
	protected ArrayList<String> getInvolovedProject(Employee employee){
		ArrayList<String> projectList = new ArrayList<String>();
		String query = "select b.id ";
		query += " from users_project_map a, project b, company c where a.projectid = b.id and b.companyid = c.id and a.userID = '" + employee.getId() + "';";
		ResultSet rs = super.getResultSet(query);
		try {
			while(rs.next()){
				String projectId = rs.getString("id");
				projectList.add(projectId);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return projectList;
	}

	protected ArrayList<String> getInvolovedFeature(Employee employee){
		ArrayList<String> featureList = new ArrayList<String>();
		String query = "select b.id from users_feature_project_map a, feature_list b where a.featureid = b.id and a.userID = '" + employee.getId() + "';";
		ResultSet rs = super.getResultSet(query);
		try {
			while(rs.next()){
				String featureId = rs.getString("id");
				featureList.add(featureId);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return featureList;
	}
}
