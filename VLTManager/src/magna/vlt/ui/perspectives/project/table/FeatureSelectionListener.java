package magna.vlt.ui.perspectives.project.table;

import magna.vlt.ui.perspectives.project.ProjectManager;
import magna.vlt.ui.perspectives.project.data.Feature;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class FeatureSelectionListener implements SelectionListener{
	private Composite source;
	
	public FeatureSelectionListener(Composite source){
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
		String featureId = String.valueOf(((Feature)t.getItem(t.getSelectionIndex()).getData()).getId());
		ProjectManager.selectedFeatureId = featureId;
		try {
			IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ProjectManager.ID);
			((ProjectManager)view).refreashTable(((ProjectManager)view).tbl_eventViewer, ProjectManager.selectedFeatureId, ProjectManager.EVENT);
		} catch (PartInitException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
