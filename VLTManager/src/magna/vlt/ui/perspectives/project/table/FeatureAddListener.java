package magna.vlt.ui.perspectives.project.table;

import magna.vlt.ui.perspectives.project.ProjectManager;
import magna.vlt.ui.perspectives.project.data.Feature;
import magna.vlt.ui.perspectives.project.data.FeatureCreator;
import magna.vlt.ui.perspectives.project.dialog.FeatureCreateDialog;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class FeatureAddListener implements SelectionListener {

	private Shell shell;
	public FeatureAddListener(Shell shell){
		this.shell = shell;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		FeatureCreateDialog dialog = new FeatureCreateDialog(shell);
		dialog.create();
		try{
			if (dialog.open() == Window.OK) {
				  String id = String.valueOf(dialog.getId());
				  String name = dialog.getName();
				  String description = dialog.getDescription();
				  new FeatureCreator(new Feature(Integer.valueOf(id), name, description));
				  
				  IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ProjectManager.ID);
				  ((ProjectManager)view).refreashTable(((ProjectManager)view).tbl_featureViewer, ProjectManager.selectedFeatureId, ProjectManager.FEATURE);
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
