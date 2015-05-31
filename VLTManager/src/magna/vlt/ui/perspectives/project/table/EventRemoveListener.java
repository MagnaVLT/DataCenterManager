package magna.vlt.ui.perspectives.project.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import magna.vlt.db.retriever.DBWrapper;
import magna.vlt.ui.perspectives.project.ProjectManager;
import magna.vlt.ui.perspectives.project.data.Event;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class EventRemoveListener extends DBWrapper implements SelectionListener {

	private Composite source;
	private Composite parent;
	
	public EventRemoveListener(Composite parent, Composite source){
		this.parent = parent;
		this.source = source;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		if(source instanceof Table){
			doEvent(); 
		}
	}

	public void doEvent() {
		Table t = (Table)source;
		String eventId = String.valueOf(((Event)t.getItem(t.getSelectionIndex()).getData()).getId());
		String description = String.valueOf(((Event)t.getItem(t.getSelectionIndex()).getData()).getDescription());
		
		try {
			String message = "";
			
			ArrayList<String> project = checkProjectEventMap(eventId);
			if(project.size()>0) message += "The selected event (" + eventId + ") is used in the project(s): " ;
			for(int i = 0 ; i < project.size(); i++) message+=project.get(i) + "/";
			
			if(checkEventDescription(eventId))
			{
				message += "\n and there are description(s) on the selected event (" + eventId + ").";
			}
			
			message += "\n Are you sure to remove the event?";
			
			MessageDialog dialog = new MessageDialog(parent.getShell(), "Warning", null, message, MessageDialog.ERROR, new String[] { "Delete All", "Delete Description", "Cancel"}, 0);
			int result = dialog.open(); 
			if(result==0){
				removeAllEvent(eventId);
			}else if(result==1){
				String id = getDescriptionId(eventId, description);
				removeDescriptionOnly(id);
			}else if(result==2){
				
			}
			 IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ProjectManager.ID);
			 ((ProjectManager)view).refresh();
		} catch (SQLException | PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getDescriptionId(String eventId, String description) throws SQLException{
		String id = "";
		String query= "select id from event_description where eventid = "+eventId+" and description = '" + description + "';";
		ResultSet rs = super.getResultSet(query);
		if(rs.next()) id = rs.getString("id");
		return id;
	}
	
	private void removeDescriptionOnly(String descriptionId){
		
		super.execute("SET foreign_key_checks = 0;");
		this.removeEventDescriptionByDescriptionId(descriptionId);
		this.removeEventFromProjectByDescriptionId(descriptionId);
		super.execute("SET foreign_key_checks = 1;");
	}
	
	private void removeEventFromProjectByDescriptionId(String descriptionId) {
		String query = "delete from project_event_map where eventdescriptionid = " + descriptionId;
		super.execute(query);
		
	}

	private void removeEventDescriptionByDescriptionId(String descriptionId) {
		String query = "delete from event_description where id = " + descriptionId;
		super.execute(query);
	}

	private void removeAllEvent(String eventId){

		super.execute("SET foreign_key_checks = 0;");
		this.removeEventDescriptionByEventId(eventId);
		this.removeProjectEventMap(eventId);
		this.removeEvent(eventId);
		super.execute("SET foreign_key_checks = 1;");
	}
	
	private void removeEventDescriptionByEventId(String eventId){
		String query = "delete  from event_description where eventid = " + eventId;
		super.execute(query);
	}
	
	
	private void removeProjectEventMap(String eventId){
		String query = "delete from project_event_map where eventid = " + eventId;
		super.execute(query);
	}
	
	private void removeEvent(String eventId) {
		String query = "delete from event_list where id = " + eventId;
		super.execute(query);
	}

	private ArrayList<String> getEventUsing(String descriptionId) throws SQLException{
		ArrayList<String> project = new ArrayList<String>();
//		String query = "select b.name as name from project_event_map a, project b where a.projectid = b.id and a.eventid = " + descriptionId;
//		ResultSet rs = super.getResultSet(query);
		
//		while(rs.next())
//		{
//			project.add(rs.getString("name"));
//		}
		
		return project;
	}
	
	private ArrayList<String> checkProjectEventMap(String eventId) throws SQLException{
		String query = "select b.name as name from project_event_map a, project b where a.projectid = b.id and a.eventid = " + eventId;
		ResultSet rs = super.getResultSet(query);
		ArrayList<String> project = new ArrayList<String>();
		
		while(rs.next())
		{
			project.add(rs.getString("name"));
		}
		
		return project;
	}
	
	private boolean checkEventDescription(String eventId) throws SQLException{
		String query = "select id from event_description where eventid = " + eventId;
		ResultSet rs = super.getResultSet(query);
		return rs.next();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
