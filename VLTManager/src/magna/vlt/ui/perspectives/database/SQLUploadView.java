package magna.vlt.ui.perspectives.database;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;

import magna.vlt.ui.common.GUIInfoRetriever;
import magna.vlt.ui.common.UIHelper;
import magna.vlt.ui.perspectives.database.conn.DBConnectorLegacy;
import magna.vlt.ui.perspectives.database.conn.DBConnectorMySQL;
import magna.vlt.ui.perspectives.database.conn.DBConnectorNewDB;
import magna.vlt.ui.perspectives.database.conn.DBInfo;
import magna.vlt.ui.perspectives.database.migrator.ForceToUpdate;
import magna.vlt.ui.perspectives.database.migrator.Migrator;
import magna.vlt.ui.perspectives.database.migrator.TemporalDBManager;
import magna.vlt.ui.perspectives.database.migrator.tables.Clip_Info;
import magna.vlt.ui.perspectives.database.migrator.tables.EyeQ_Event_Report;
import magna.vlt.ui.perspectives.database.migrator.tables.FCM_Event_Report;
import magna.vlt.ui.perspectives.database.migrator.tables.mappingTable.Clip_Info_Map;
import magna.vlt.ui.perspectives.database.migrator.tables.mappingTable.EyeQ_Event_Report_Map;
import magna.vlt.ui.perspectives.database.migrator.tables.mappingTable.FCM_Event_Report_Map;
import magna.vlt.util.MessageBox;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class SQLUploadView extends ViewPart {
	private Text text;
	private Table tbl_progress;
	private Table tbl_not_uploaded;
	private Button btnBrowse;
	private Button btnUpload;
	private TableViewer tblvr_progress;
	private CheckboxTableViewer tblvr_notUploaded;
	private Composite parent;
	private Button btn_force;
	private Combo cbo_Table;
	private UIHelper uiHelper = new UIHelper();
	private GUIInfoRetriever initializer;
	private MessageBox mb = new MessageBox();
	private static final String MAZDA = "1";
	private static final String CHRYSLER = "2";
	private Button btnSelection;
	private Label lblProject;
	private Combo cbo_project;
	private Label lblTable;
	
	public SQLUploadView() {
		initializer = new GUIInfoRetriever();
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		createGUI(parent);
		initGUI();
		addListener();
	}

	private void initGUI() {
		initProject(); 
	}

	private void initProject() {
		Hashtable<Integer, String> table = initializer.getIdNamePairList("id", "name", "project");
		uiHelper.initCombo(this.cbo_project, table);
	}

	private void initTable(String project) {
		Hashtable<Integer, String> table = new Hashtable<Integer, String>();
		
		if(project.equals(MAZDA)){
			table.put(1, "eyeq_event_report");
			table.put(2, "clip_info");
		}else if(project.equals(CHRYSLER)){
			table.put(1, "eyeq_event_report");
			table.put(2, "fcm_event_report");
			table.put(3, "clip_info");
		}
		
		uiHelper.initCombo(this.cbo_Table, table);
	}

	private void createGUI(Composite parent) {
		parent.setLayout(new GridLayout(4, true));
		
		lblProject = new Label(parent, SWT.NONE);
		lblProject.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblProject.setAlignment(SWT.CENTER);
		lblProject.setText("project");
		
		cbo_project = new Combo(parent, SWT.READ_ONLY);
		cbo_project.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		text = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		btnBrowse = new Button(parent, SWT.NONE);
		btnBrowse.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnBrowse.setText("Browse");
		
		lblTable = new Label(parent, SWT.NONE);
		lblTable.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblTable.setText("table");
		
		cbo_Table = new Combo(parent, SWT.READ_ONLY);
		cbo_Table.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnUpload = new Button(parent, SWT.NONE);
		btnUpload.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		btnUpload.setText("Upload");
		
		Label lblNewLabel_1 = new Label(parent, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		lblNewLabel_1.setText("Progress");
		
		Label lblNewLabel_2 = new Label(parent, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		lblNewLabel_2.setText("Not Uploaded");
		
		tblvr_progress = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tbl_progress = tblvr_progress.getTable();
		GridData gd_tbl_progress = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tbl_progress.widthHint = 265;
		tbl_progress.setLayoutData(gd_tbl_progress);
		tblvr_progress.setContentProvider(new ArrayContentProvider());
		createColumns(parent, tblvr_progress);
		
		tblvr_notUploaded = CheckboxTableViewer.newCheckList(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tbl_not_uploaded = tblvr_notUploaded.getTable();
		tbl_not_uploaded.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		tblvr_notUploaded.setContentProvider(new ArrayContentProvider());
		createColumns(parent, tblvr_notUploaded);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		btnSelection = new Button(parent, SWT.NONE);
		btnSelection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnSelection.setText("Select All");
		
		btn_force = new Button(parent, SWT.NONE);
		btn_force.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btn_force.setText("Force to Upload");
	}
	
	private void addListener(){
		
		
		btnBrowse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				
				FileDialog dialog = new FileDialog(parent.getShell(), SWT.OPEN);
				dialog.setFilterExtensions(new String [] {"*.sql"});
				dialog.setFilterPath("c:\\");
				String fileURL = dialog.open();
				text.setText(fileURL);
			}
		});
		
		btnUpload.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(cbo_Table.getText().equals("")){
					String[] buttons = {"Confirm"};
					mb.showErrorBox(parent.getShell(), "Table Selection Error", "Please select a table to be migrated.", MessageDialog.ERROR, buttons);
				}else if(text.getText().equals("")){
					String[] buttons = {"Confirm"};
					mb.showErrorBox(parent.getShell(), ".sql File Selection Error", "Please select a .sql file to be migrated.", MessageDialog.ERROR, buttons);
				}else{
					String dbName = "temp";
					DBConnectorMySQL.getConnection(new DBInfo("jdbc:mysql://localhost:3306/mysql",  "root", "hil"));
					Migrator migrator = null;
					String pid = getProjectId();
					if(pid!=null && !pid.equals("")){
						if(pid.equals(MAZDA)){
							migrator = mazdaSetup(dbName, pid);
						}else if(pid.equals(CHRYSLER)){
							migrator = chryslerSetup(dbName, pid);
						}
					}
					
					if(migrator!=null){
						try{
							TemporalDBManager.drop(DBConnectorMySQL.getConn(), dbName);
							TemporalDBManager.create(DBConnectorMySQL.getConn(), dbName);
							
							DBConnectorLegacy.getConnection(new DBInfo("jdbc:mysql://localhost:3306/" + dbName,  "root", "hil"));
							TemporalDBManager tempDbManager = new TemporalDBManager(DBConnectorLegacy.getConn(), dbName, text.getText());
							new ProgressMonitorDialog(parent.getShell()).run(true, true, tempDbManager);
							
							DBConnectorNewDB.getConnection(new DBInfo("jdbc:mysql://localhost:3306/fcm_gen3_hil_playback" ,  "root", "hil"));
							
							migrator.migrate();
							ArrayList<String> updated = migrator.getUpdator().getUpdatedRows();
							setColumnOfProgress(updated);
							ArrayList<String> notUpdated = migrator.getUpdator().getNotUpdatedRows();
							setColumnOfNotUploaded(notUpdated);
//							TemporalDBManager.drop(DBConnectorMySQL.getConn(), dbName);
							
						}catch(NullPointerException | InvocationTargetException | InterruptedException e1){
							String[] buttons = {"Confirm"};
							mb.showErrorBox(parent.getShell(), "Table Selection Error", "Please select a table to be migrated.", MessageDialog.ERROR, buttons);
						}
					}
				}
				
			}

			private Migrator mazdaSetup(String dbName, String pid) {
				Migrator migrator = null;
				if(uiHelper.getToken(cbo_Table.getText(), "-", 0).equals("1")){
					migrator = new EyeQ_Event_Report(parent, new EyeQ_Event_Report_Map(), "eyeq_event_report", "event_report", pid, "2");
				}else if(uiHelper.getToken(cbo_Table.getText(), "-", 0).equals("2")){
					migrator = new Clip_Info(parent, new Clip_Info_Map(), "clip_info", "clip_info", pid);
				}
				return migrator;
				
			}
			
			private Migrator chryslerSetup(String dbName, String pid) {
				Migrator migrator = null;
				if(uiHelper.getToken(cbo_Table.getText(), "-", 0).equals("1")){
					migrator = new EyeQ_Event_Report(parent, new EyeQ_Event_Report_Map(), "eyeq_event_report", "event_report", pid, "2");
				}else if(uiHelper.getToken(cbo_Table.getText(), "-", 0).equals("2")){
					migrator = new FCM_Event_Report(parent, new FCM_Event_Report_Map(), "fcm_event_report", "event_report", pid, "1");
				}else if(uiHelper.getToken(cbo_Table.getText(), "-", 0).equals("3")){
					migrator = new Clip_Info(parent, new Clip_Info_Map(), "clip_info", "clip_info", pid);
				}
				
				return migrator;
			}
				
		});
		
		
		btn_force.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Object[] elements = tblvr_notUploaded.getCheckedElements();
				ForceToUpdate updator = new ForceToUpdate(elements, parent, cbo_Table.getText(), getProjectId());
				
				try {
					new ProgressMonitorDialog(parent.getShell()).run(true, true, updator);
				} catch (InvocationTargetException | InterruptedException e1) {
					e1.printStackTrace();
				}
				
				tblvr_notUploaded.remove(elements);
			}
		});
		
		
		btnSelection.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				int noOfCheckedElement = tblvr_notUploaded.getCheckedElements().length;
				int noOfAllElement = tblvr_notUploaded.getTable().getItems().length;
				if(noOfCheckedElement<noOfAllElement){
					btnSelection.setText("Unselect All");
					tblvr_notUploaded.setAllChecked(true);
				}else{
					btnSelection.setText("Select All");
					tblvr_notUploaded.setAllChecked(false);
				}
			}
		});
		
		cbo_project.addModifyListener(new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent e) {
				String text = cbo_project.getText();
				if(text!=null && !text.equals("")){
					String pid = uiHelper.getToken(text, "-", 0);
					initTable(pid);	
				}
			}
		});
		
	}

	private String getProjectId() {
		String pid = uiHelper.getToken(cbo_project.getText(), "-", 0);
		return pid;
	}
	
	private void setColumnOfNotUploaded(ArrayList<String> rows) {
		tblvr_notUploaded.setInput(rows);
	}
	
	private void setColumnOfProgress(ArrayList<String> rows) {
		tblvr_progress.setInput(rows);
	}
	
	private void createColumns(Composite parent, TableViewer viewer){
		String[] titles = {"ROW"};
	    int[] bounds = {1000};
	    TableViewerColumn[] column = new TableViewerColumn[1];
	    
	    column[0] = uiHelper.createTableViewerColumn(viewer, titles[0], bounds[0], 0);
	    column[0].setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	  String e = (String) element;
	        return e;
	      }
	    });
	}

	@Override
	public void setFocus() {

	}

}
