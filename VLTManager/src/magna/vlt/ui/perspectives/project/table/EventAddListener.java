package magna.vlt.ui.perspectives.project.table;

import magna.vlt.ui.perspectives.project.ProjectManager;
import magna.vlt.ui.perspectives.project.data.Event;
import magna.vlt.ui.perspectives.project.data.EventCreator;
import magna.vlt.ui.perspectives.project.dialog.EventCreateDialog;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class EventAddListener implements SelectionListener {

	private Shell shell;
	public EventAddListener(Shell shell){
		this.shell = shell;
	}
	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		EventCreateDialog dialog = new EventCreateDialog(shell);
		dialog.create();
		try{
			if (dialog.open() == Window.OK) {
				  String featureId = dialog.getFeatureId();
				  String name = dialog.getName();
				  String description = dialog.getDescription();
				  String subfeature = dialog.getSubFeatureId();
				  String eventCategory = dialog.getEventCategory();
				  String requestor = dialog.getRequestor();
				  if(subfeature.equals("")) subfeature = "1"; 
				  new EventCreator(new Event(Integer.valueOf(featureId), name, description, Integer.valueOf(eventCategory), Integer.valueOf(subfeature), requestor));
				  
				  IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ProjectManager.ID);
				  ((ProjectManager)view).refreashTable(((ProjectManager)view).tbl_eventViewer, ProjectManager.selectedFeatureId, ProjectManager.EVENT);
			}
			
		}catch(NullPointerException err){
			
		} catch (PartInitException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
