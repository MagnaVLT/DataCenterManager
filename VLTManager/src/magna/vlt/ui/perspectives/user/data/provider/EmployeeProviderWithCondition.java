package magna.vlt.ui.perspectives.user.data.provider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import magna.vlt.ui.perspectives.user.data.Employee;

import org.eclipse.core.runtime.IProgressMonitor;

public class EmployeeProviderWithCondition extends EmployeeProvider {
	private int total = 0;
	private ResultSet rs;
	private List<Employee> employees;
	private Hashtable<String, String> conditions;
	
	public List<Employee> getEmployees() {
		return employees;
	}
	
	public EmployeeProviderWithCondition(Hashtable<String, String> conditions){
		this.conditions = conditions;
	}
	
	@Override
	public void doJob(IProgressMonitor monitor) {
		employees = new ArrayList<Employee>();
		String query = "select a.id as id, concat(firstname, ' ', lastname) as name, a.privilege as privilege, online, location, password, b.name privilegeName ";
		query += " from users a, privilege b where a.privilege = b.id ";
		
		if(conditions.containsKey("feature")){
			query = "select a.id as id, concat(firstname, ' ', lastname) as name, a.privilege as privilege, online, location, password, b.name privilegeName ";
			query += " from users a, privilege b, users_feature_project_map c";
			query += " where a.privilege = b.id and a.id = c.userid and c.featureid = " + conditions.get("feature");
		}
		if(conditions.containsKey("name")) query += " and (a.firstname like '%"+conditions.get("name")+"%' or a.lastname like '%"+conditions.get("name")+"%')";
		if(conditions.containsKey("id")) query += " and a.id like '%"+conditions.get("id")+"%'";
		query += " group by id, name, privilege, online, location, password, privilegeName;";
		
		rs = super.getResultSet(query);
		

		try {
			

			rs.last();
			total = rs.getRow();
			rs.beforeFirst();
			int cnt = 0;
			while(rs.next()){
				String id = rs.getString("id");
				String name = rs.getString("name");
				String privilege = rs.getString("privilege");
				String isOnline = rs.getString("online");
				String location = rs.getString("location");
				String password = rs.getString("password");
				String privilegeName = rs.getString("privilegeName");
				
				ArrayList<ArrayList<String>> managers = super.getManagerRelationship(id);
				Employee employee = new Employee(name, id, privilege, privilegeName, managers.get(0), managers.get(1), location, password, isOnline);
				employee.setProjectList(super.getInvolovedProject(employee));
				employee.setFeatureList(super.getInvolovedFeature(employee));
				employees.add(employee);
				monitor.worked(cnt++);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getTitle() {
		
		return "Searching Employees...";
	}

	@Override
	public int getTotal() {
		
		return this.total;
	}
}
