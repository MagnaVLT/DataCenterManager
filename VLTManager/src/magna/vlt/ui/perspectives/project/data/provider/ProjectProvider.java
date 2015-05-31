package magna.vlt.ui.perspectives.project.data.provider;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import magna.vlt.db.retriever.DBWrapper;
import magna.vlt.ui.common.VTJob;
import magna.vlt.ui.perspectives.project.data.Project;

public class ProjectProvider extends DBWrapper implements VTJob {

	private List<Project> projects;
	private ResultSet rs;
	private int total = 0;
	
	public ProjectProvider(){
		  projects = new ArrayList<Project>();
		  
		  String query = "select a.id as id, a.name as name, b.name as companyname, a.isopen, "
		  		+ "a.OwnerID, a.generation, a.start, a.end from project a, company b where a.companyid = b.id order by a.id;";
		  rs = super.getResultSet(query);
		  try {
			rs.afterLast();
			total = rs.getRow();
			rs.beforeFirst();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void doJob(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

		  int cnt=0;
		  try {
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String oem = rs.getString("companyName");
				String owner = rs.getString("ownerid");
				int generation = rs.getInt("generation");
				int isopen = rs.getInt("isopen");
				Date start = rs.getDate("start");
				Date end = rs.getDate("end");
				Project newProject = new Project(id, name, oem, generation, owner, start, end, isopen);
				this.setFeatureListOf(newProject);
				this.setEventListOf(newProject);
				projects.add(newProject);
				monitor.worked(cnt++);
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}		
	}
	  
	private void setFeatureListOf(Project project){
	  	String query = "select b.id, b.name, b.description from project_feature_map a, feature_list b "
	  			+ " where a.featureid = b.id and a.projectid = " +project.getId()+ ";";
	
		ResultSet rs = super.getResultSet(query);
		
		try {
			rs.last();
			String[] featureIdList = new String[rs.getRow()];
			String[] featureNameList = new String[rs.getRow()];
			String[] featureDescriptionList = new String[rs.getRow()];
			rs.beforeFirst();
			int cnt = 0;
			while(rs.next()){
				featureIdList[cnt] = rs.getString("id");
				featureNameList[cnt] = rs.getString("name");
				featureDescriptionList[cnt] = rs.getString("description");
				cnt++;
			}
			
			project.setFeatureList(featureIdList);
			project.setFeatureNameList(featureNameList);
			project.setFeatureDescription(featureDescriptionList);
			
		} catch (SQLException e) {
				e.printStackTrace();
		}
	}
	  
	  
	private void setEventListOf(Project project){
	  	String query = "select b.id, b.name, c.description from project_event_map a, event_list b, event_description c ";
	  	query += " where a.eventid = b.id and a.eventdescriptionid = c.id and a.projectid = " + project.getId() + ";";

		ResultSet rs = super.getResultSet(query);
		try {
			rs.last();
			String[] eventIdList = new String[rs.getRow()];
			String[] eventNameList = new String[rs.getRow()];
			String[] eventDescriptionList = new String[rs.getRow()];
			rs.beforeFirst();
			int cnt = 0;
			while(rs.next()){
				eventIdList[cnt] = rs.getString("id");
				eventNameList[cnt] = rs.getString("name");
				eventDescriptionList[cnt] = rs.getString("description");
				cnt++;
			}
			project.setEventList(eventIdList);
			project.setEventNameList(eventNameList);
			project.setEventDescription(eventDescriptionList);
			
		} catch (SQLException e) {
				e.printStackTrace();
		}
	}
	  
	  
	public List<Project> getProjects() {
		return projects;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Initilaizing Projects...";
	}

	@Override
	public int getTotal() {
		return this.total;
	}

}
