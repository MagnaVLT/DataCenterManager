package magna.vlt.ui.perspectives.database.migrator;

import java.lang.reflect.InvocationTargetException;

import magna.vlt.ui.common.UIHelper;
import magna.vlt.ui.perspectives.database.migrator.tables.Clip_Info;
import magna.vlt.ui.perspectives.database.migrator.tables.EyeQ_Event_Report;
import magna.vlt.ui.perspectives.database.migrator.tables.mappingTable.Clip_Info_Map;
import magna.vlt.ui.perspectives.database.migrator.tables.mappingTable.EyeQ_Event_Report_Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Composite;

public class ForceToUpdate implements IRunnableWithProgress {

	private Object[] elements;
	private UIHelper uiHelper = new UIHelper();
	private Composite parent;
	private String tableRawText;
	private String projectid;
	
	public ForceToUpdate(Object[] elements, Composite parent, String tableRawText, String projectid){
		this.elements = elements;
		this.parent = parent;
		this.tableRawText = tableRawText;
		this.projectid = projectid;
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		
		monitor.beginTask("Inserting the selected data", elements.length);
		int incremental = 1;
		for(Object element: elements){
			String selectedData = (String) element;
			System.out.println(selectedData);
			String[] tokens = uiHelper.getToken(selectedData, "&");
			Migrator migrator = null;
			monitor.setTaskName("Now, inserting " + tokens[0] + " item.");
			
			if(uiHelper.getToken(tableRawText, "-", 0).equals("1")){
				migrator = new EyeQ_Event_Report(parent, new EyeQ_Event_Report_Map(), "eyeq_event_report", "event_report", projectid, "2");
			}else if(uiHelper.getToken(tableRawText, "-", 0).equals("2")){
				migrator = new Clip_Info(parent, new Clip_Info_Map(), "clip_info", "clip_info", projectid);
			}
			
			monitor.worked(incremental);
			migrator.forceToInsert(tokens);
		}
		
		monitor.done();
		
	}

}
