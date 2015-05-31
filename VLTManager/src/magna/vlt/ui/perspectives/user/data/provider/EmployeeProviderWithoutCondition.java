package magna.vlt.ui.perspectives.user.data.provider;

import java.sql.SQLException;
import java.util.ArrayList;

import magna.vlt.ui.perspectives.user.data.Employee;

import org.eclipse.core.runtime.IProgressMonitor;

public class EmployeeProviderWithoutCondition extends EmployeeProvider {

	public EmployeeProviderWithoutCondition(){
		employees = new ArrayList<Employee>();
		String query = "select a.id as id, concat(firstname, ' ', lastname) as name, a.privilege as privilege, online, location, password, b.name privilegeName ";
		query += " from users a, privilege b where a.privilege = b.id;";
		rs = super.getResultSet(query);
		
		try {
			
			rs.last();
			total = rs.getRow();
			rs.beforeFirst();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void doJob(IProgressMonitor monitor) {
		int cnt = 0;
		
		try {
			while(rs.next()){
				
				String id = rs.getString("id");
				String name = rs.getString("name");
				String privilege = rs.getString("privilege");
				String isOnline = rs.getString("online");
				String location = rs.getString("location");
				String password = rs.getString("password");
				String privilegeName = rs.getString("privilegeName");

				ArrayList<ArrayList<String>> managers = this.getManagerRelationship(id);
				Employee employee = new Employee(name, id, privilege, privilegeName, managers.get(0), managers.get(1), location, password, isOnline);
				employee.setProjectList(this.getInvolovedProject(employee));
				employee.setFeatureList(this.getInvolovedFeature(employee));
				employees.add(employee);
				monitor.worked(cnt++);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public String getTitle() {
		
		return "Searching All Employees...";
	}

	@Override
	public int getTotal() {
		
		return this.total;
	}

}
