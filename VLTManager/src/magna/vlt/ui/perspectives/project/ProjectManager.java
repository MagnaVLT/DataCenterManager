package magna.vlt.ui.perspectives.project;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import magna.vlt.ui.common.GUIInfoRetriever;
import magna.vlt.ui.common.UIHelper;
import magna.vlt.ui.common.VTJob;
import magna.vlt.ui.common.VTProgressBar;
import magna.vlt.ui.perspectives.project.data.Event;
import magna.vlt.ui.perspectives.project.data.Feature;
import magna.vlt.ui.perspectives.project.data.Project;
import magna.vlt.ui.perspectives.project.data.ProjectCreator;
import magna.vlt.ui.perspectives.project.data.ProjectUpdator;
import magna.vlt.ui.perspectives.project.data.provider.FeatureEventProvider;
import magna.vlt.ui.perspectives.project.table.EventAddListener;
import magna.vlt.ui.perspectives.project.table.EventRemoveListener;
import magna.vlt.ui.perspectives.project.table.EventRemoveOfSelectedListener;
import magna.vlt.ui.perspectives.project.table.EventSelectionListener;
import magna.vlt.ui.perspectives.project.table.FeatureAddListener;
import magna.vlt.ui.perspectives.project.table.FeatureSelectionListener;
import magna.vlt.ui.perspectives.project.table.ItemEditingSupport;
import magna.vlt.ui.perspectives.project.table.OptionEditingSupport;
import magna.vlt.util.MessageBox;
import magna.vlt.util.Tokenizer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class ProjectManager extends ViewPart {
	public static final boolean EVENT = true;
	public static final boolean FEATURE = false;
	public static String ID = "magna.vlt.ui.project.ProjectManager";
	private Text txt_name;
	private Combo cbo_oem;
	private Combo cbo_gen;
	private Combo cbo_owner;
	private DateTime date_start;
	private DateTime date_end;
	private ListViewer lst_featureEventVeiewer;
	private List lst_feature_event;
	private Button btn_clear;
	private Button btn_submit;
	private UIHelper helper = new UIHelper();
	private GUIInfoRetriever initiator = new GUIInfoRetriever();
	private ArrayList<Integer> selectedFeature = new ArrayList<Integer>();
	private Table tbl_feature;
	public TableViewer tbl_featureViewer;
	private Table tbl_event;
	public TableViewer tbl_eventViewer;
	private FeatureEventProvider model = new FeatureEventProvider();
	private Composite parent;
	private MessageBox message;
	public static String selectedFeatureId;
	private Button btn_remove;
	private Label lblId;
	private Text txt_id;
	private Button btn_close;
	
	public ProjectManager() {
		message = new MessageBox();
	}

	@Override
	public void createPartControl(Composite parent) {
		draw(parent);
		
		btn_close = new Button(parent, SWT.NONE);
		btn_close.setEnabled(false);
		btn_close.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btn_close.setText("Close");
		initGUIControls();
		addListeners();
	}

	private void draw(Composite parent) {
		this.parent = parent;
		parent.setLayout(new GridLayout(6, false));
		new Label(parent, SWT.NONE);
		
		lblId = new Label(parent, SWT.NONE);
		lblId.setText("ID");
		new Label(parent, SWT.NONE);
		
		txt_id = new Text(parent, SWT.BORDER);
		txt_id.setEnabled(false);
		txt_id.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		new Label(parent, SWT.NONE);
		
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setText("Name");
		new Label(parent, SWT.NONE);
		
		txt_name = new Text(parent, SWT.BORDER);
		txt_name.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));
		new Label(parent, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(parent, SWT.NONE);
		lblNewLabel_1.setText("OEM");
		new Label(parent, SWT.NONE);
		
		cbo_oem = new Combo(parent, SWT.READ_ONLY);
		cbo_oem.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		new Label(parent, SWT.NONE);
		
		Label lblNewLabel_2 = new Label(parent, SWT.NONE);
		lblNewLabel_2.setText("Generation");
		new Label(parent, SWT.NONE);
		
		cbo_gen = new Combo(parent, SWT.READ_ONLY);
		cbo_gen.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		new Label(parent, SWT.NONE);
		
		Label lblNewLabel_3 = new Label(parent, SWT.NONE);
		lblNewLabel_3.setText("Owner");
		new Label(parent, SWT.NONE);
		
		cbo_owner = new Combo(parent, SWT.READ_ONLY);
		cbo_owner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		new Label(parent, SWT.NONE);
		
		Label lblNewLabel_4 = new Label(parent, SWT.NONE);
		lblNewLabel_4.setText("Duration");
		
		Label lblStart = new Label(parent, SWT.NONE);
		lblStart.setText("Start");
		
		date_start = new DateTime(parent, SWT.BORDER);
		date_start.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lblEnd = new Label(parent, SWT.NONE);
		lblEnd.setText("End");
		
		date_end = new DateTime(parent, SWT.BORDER);
		date_end.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		Label lblNewLabel_5 = new Label(parent, SWT.NONE);
		lblNewLabel_5.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_5.setAlignment(SWT.CENTER);
		lblNewLabel_5.setText("Feature");
		new Label(parent, SWT.NONE);
		
		Label lblNewLabel_6 = new Label(parent, SWT.NONE);
		lblNewLabel_6.setAlignment(SWT.CENTER);
		GridData gd_lblNewLabel_6 = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel_6.widthHint = 33;
		lblNewLabel_6.setLayoutData(gd_lblNewLabel_6);
		lblNewLabel_6.setText("Event");
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		tbl_featureViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		initFeatureTable(parent, tbl_featureViewer);
		
		tbl_feature = tbl_featureViewer.getTable();
		GridData gd_tbl_feature = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_tbl_feature.minimumHeight = 100;
		tbl_feature.setLayoutData(gd_tbl_feature);
		tbl_featureViewer.setContentProvider(new ArrayContentProvider());
		tbl_featureViewer.setInput(model.getFeature());
		
		new Label(parent, SWT.NONE);		
		tbl_eventViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION| SWT.MULTI);
		
		tbl_event = tbl_eventViewer.getTable();
		GridData gd_tbl_event = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_tbl_event.minimumHeight = 100;
		tbl_event.setLayoutData(gd_tbl_event);
		tbl_event.setRedraw(true);
		tbl_eventViewer.setContentProvider(new ArrayContentProvider());
		tbl_eventViewer.setInput(model.getEvent());
		
		
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		Label lblNewLabel_7 = new Label(parent, SWT.NONE);
		lblNewLabel_7.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1));
		lblNewLabel_7.setAlignment(SWT.CENTER);
		lblNewLabel_7.setText("Selected Feature-Event");
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		lst_featureEventVeiewer = new ListViewer(parent, SWT.BORDER | SWT.H_SCROLL| SWT.V_SCROLL| SWT.MULTI);
		lst_feature_event = lst_featureEventVeiewer.getList();
		GridData gd_lst_feature_event = new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1);
		gd_lst_feature_event.heightHint = 50;
		gd_lst_feature_event.minimumHeight = 50;
		gd_lst_feature_event.widthHint = 0;
		lst_feature_event.setLayoutData(gd_lst_feature_event);
		this.initFeatureEventTable();
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		btn_clear = new Button(parent, SWT.NONE);
		GridData gd_btnClear = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnClear.widthHint = 66;
		btn_clear.setLayoutData(gd_btnClear);
		btn_clear.setText("Clear");
		new Label(parent, SWT.NONE);
		
		btn_submit = new Button(parent, SWT.NONE);
		
				GridData gd_btn_submit = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
				gd_btn_submit.widthHint = 69;
				btn_submit.setLayoutData(gd_btn_submit);
				btn_submit.setText("Submit");
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		btn_remove = new Button(parent, SWT.NONE);
		btn_remove.setEnabled(false);
		btn_remove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btn_remove.setText("Remove");
		new Label(parent, SWT.NONE);
		
		
	}

	private void initGUIControls() {
		this.initOEM();
		this.initOwner();
		this.initGeneration();
		initEventTable(parent, tbl_eventViewer, 0);
	}
	
	private void initGeneration() {
		this.cbo_gen.removeAll();
		for(int i = 3; i < 5; i++){
			this.cbo_gen.add(String.valueOf(i));
		}
	}

	private void initFeatureTable(final Composite parent, TableViewer viewer){
		model.setFeature();
		for(TableColumn tc :viewer.getTable().getColumns()) tc.dispose();
		String[] titles = { "feature"};
	    int[] bounds = { 350 };
	    
	    TableViewerColumn[] column = new TableViewerColumn[1];
	    column[0] = helper.createTableViewerColumn(this.tbl_featureViewer, titles[0], bounds[0], 0);
	    column[0].setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Feature f = (Feature) element;
				return f.getName() + " (" + f.getDescription() + ")";
			}
	    });
	    
	    String[] menuItems = {"Select Events", "Add a Feature" , "Add an Event on this Feature"};
	    SelectionListener[] listeners = new SelectionListener[menuItems.length];
	    listeners[0] = new FeatureSelectionListener(this.tbl_featureViewer.getTable());
	    listeners[1] = new FeatureAddListener(parent.getShell());
	    listeners[2] = new EventAddListener(parent.getShell());
	    helper.addPopupMenu(this.tbl_featureViewer.getTable(), menuItems, listeners);
	    
	}
	
	private void initFeatureEventTable(){
	    String[] menuItems = {"Remove Events"};
	    SelectionListener[] listeners = new SelectionListener[menuItems.length];
	    listeners[0] = new EventRemoveOfSelectedListener(this.lst_feature_event);
	    helper.addPopupMenu(this.lst_feature_event, menuItems, listeners);
	}
	
	private void initEventTable(final Composite parent, final TableViewer viewer, int featureId){
		viewer.getTable().removeAll();
		for(TableColumn tc :viewer.getTable().getColumns()) tc.dispose();
		model.setEvent(featureId);
	    String[] titles = { "Event", "Category"};
	    int[] bounds = { 350, 100 };
	    
	    TableViewerColumn[] column = new TableViewerColumn[2];
	    column[0] = helper.createTableViewerColumn(this.tbl_eventViewer, titles[0], bounds[0], 0);
	    column[0].setEditingSupport(new ItemEditingSupport(this.tbl_eventViewer));
	    column[0].setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Event e = (Event) element;
				return e.getName() + " (" + e.getDescription() + ")";
			}
	    });

	    column[1] = helper.createTableViewerColumn(this.tbl_eventViewer, titles[1], bounds[1], 1);
	    column[1].setEditingSupport(new OptionEditingSupport(this.tbl_eventViewer));
	    column[1].setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Event e = (Event) element;
				return e.getCategoryName();
			}
	    });
	    
	    String[] menuItems = {"Select Event", "Add Event", "Remove Event"};
	    SelectionListener[] listeners = new SelectionListener[menuItems.length];
	    listeners[0] = new EventSelectionListener(this.tbl_eventViewer.getTable(), this.lst_feature_event, this.tbl_featureViewer.getTable());
	    listeners[1] = new EventAddListener(parent.getShell());
	    listeners[2] = new EventRemoveListener(parent, this.tbl_eventViewer.getTable());
	    
	    helper.addPopupMenu(this.tbl_eventViewer.getTable(), menuItems, listeners);
	}

	private void initOwner() {
		String query = "select id, concat(firstname, ' ', lastname) as name from users where privilege = 2";
		
		Hashtable<String, String> table = initiator.getPairedList(query, "id", "name");
		helper.initCombo(this.cbo_owner, table);
	}
	
	private void initOwner(String ownerId) {
		String query = "select id, concat(firstname, ' ', lastname) as name from users where privilege = 2";
		
		Hashtable<String, String> table = initiator.getPairedList(query, "id", "name");
		helper.initCombo(this.cbo_owner, table);
		String text = Tokenizer.concatWithDelim("-", String.valueOf(ownerId), table.get(ownerId));
		this.cbo_owner.setText(text);
	}
	
	
	private void initOEM(){
		Hashtable<Integer, String> table = initiator.getIdNamePairList("id", "name", "company");
		helper.initCombo(this.cbo_oem, table);
	}
	
	private void initOEM(String oemName){
		Hashtable<Integer, String> table = initiator.getIdNamePairList("id", "name", "company");
		helper.initCombo(this.cbo_oem, table);
		int oemId = -1;
		for(int id: table.keySet()){
			if(table.get(id).equals(oemName)){
				oemId = id;
				break;
			}
		}
		
		String text = Tokenizer.concatWithDelim("-", String.valueOf(oemId), oemName);
		this.cbo_oem.setText(text);
	}
	
	
	private void addListeners() {
		
		lst_feature_event.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				if(lst_feature_event.getSelectionIndex()!=-1)
					lst_feature_event.remove(lst_feature_event.getSelectionIndex());
			}
		});
		
		tbl_feature.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e){
				ProjectManager.selectedFeatureId = String.valueOf(((Feature)tbl_feature.getItem(tbl_feature.getSelectionIndex()).getData()).getId()); 
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				String selectedFeatureID = String.valueOf(((Feature)tbl_feature.getItem(tbl_feature.getSelectionIndex()).getData()).getId());
				ProjectManager.selectedFeatureId = selectedFeatureID;
				
				if(selectedFeature.contains(Integer.valueOf(selectedFeatureID))){
					selectedFeature.remove(Integer.valueOf(selectedFeatureID));
					ArrayList<Integer> removeIndex = new ArrayList<Integer>();
					
					for(int i = 0 ; i < lst_feature_event.getItemCount() ; i++){
						String featureIDOfItem = Tokenizer.getToken(lst_feature_event.getItem(i), "-", 0);
						if(featureIDOfItem.equals(selectedFeatureID)){
							removeIndex.add(i);
						}
					}
					Collections.sort(removeIndex, Collections.reverseOrder());
					for(int index: removeIndex) lst_feature_event.remove(index);
					
				}else{
					TableItem[] events = tbl_event.getItems();
					selectedFeature.add(Integer.valueOf(selectedFeatureID));
					int eventSize = events.length-1;
					for(int i = eventSize ; i >= 0; i--){
						String event = events[i].getText();						
						event = event.replace(" (", "-");
						event = event.replace(")", "");
						Event eventObject = (Event)events[i].getData();
						Table featureTable = tbl_featureViewer.getTable();
						String featureDescription = ((Feature)featureTable.getItem(featureTable.getSelectionIndex()).getData()).getDescription();
						String featureName = ((Feature)featureTable.getItem(featureTable.getSelectionIndex()).getData()).getName();
						
						event = eventObject.getFeatureId() + "-" + featureName + "-" + featureDescription + "-" + eventObject.getId() +"-"+ event + "-" + eventObject.getEventCategory();
						if(!helper.isExistItem(lst_feature_event, event)){
							lst_feature_event.add(event, 0);
						}
					}
				}
			}
		});
		
		btn_submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				createProject();
			}
		});
		
		btn_clear.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseUp(MouseEvent e) {
				clear();
			}
		});
		
		btn_remove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				removeProject();
			}
		});
		
		btn_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				closeProject();
			}
		});
		
	}
	
	
	private void closeProject(){
		String id = txt_id.getText();
		if(this.btn_close.getText().equals("Close")){
			new ProjectCreator().close(id);
		}else{
			new ProjectCreator().open(id);
		}
		this.viewerRefreash();
		this.treeViewerRefreash();
	}
	
	private void removeProject(){
		String id = txt_id.getText();
		new ProjectCreator().remove(id);
		this.viewerRefreash();
		this.treeViewerRefreash();
	}
	
	private void clear(){
		txt_name.setText("");
		initOEM();
		initGeneration();
		initOwner();
		lst_feature_event.removeAll();

		this.btn_close.setEnabled(false);
		this.btn_remove.setEnabled(false);
		this.txt_id.setText("");
		
		initEventTable(parent, tbl_eventViewer, 0);
		String today = helper.getCurrentDate();
		
		int y = Integer.valueOf(Tokenizer.getToken(today, "-", 0));
		int m = Integer.valueOf(Tokenizer.getToken(today, "-", 1))-1;
		int d = Integer.valueOf(Tokenizer.getToken(today, "-", 2));
		
		this.date_start.setDate(y, m, d);
		this.date_end.setDate(y, m, d);
		
		btn_close.setText("Close");
	}
	
	private void createProject(){
		
		
		try {
			new ProgressMonitorDialog(parent.getShell()).run(true, true, new VTProgressBar(new VTJob(){
				private int total = 0;

				@Override
				public void doJob(IProgressMonitor monitor) {
					String name = txt_name.getText();
					String companyid = Tokenizer.getToken(cbo_oem.getText(), "-", 0);
					String ownerid = Tokenizer.getToken(cbo_owner.getText(), "-", 0);
					String generation = cbo_gen.getText();
					int startYear =  date_start.getYear();
					int startMonth =  date_start.getMonth() + 1;
					int startDay =  date_start.getDay();
					int endYear =  date_end.getYear();
					int endMonth =  date_end.getMonth() + 1;
					int endDay =  date_end.getDay();
					String start = helper.convertDateToString(startDay, startMonth, startYear);
					String end = helper.convertDateToString(endDay, endMonth, endYear);
				    
					String[] featureEventList = lst_feature_event.getItems();
					String[] featureList = new String[featureEventList.length];
					String[] featureNameList = new String[featureEventList.length];
					String[] eventList = new String[featureEventList.length];
					String[] eventNameList = new String[featureEventList.length];
					String[] eventCategoryList = new String[featureEventList.length];
					String[] eventDescriptionList = new String[featureEventList.length];
					String[] featureDescriptionList = new String[featureEventList.length];
					
					for(int index = 0; index < featureEventList.length ; index++){
						String featureEvent = featureEventList[index];
						featureList[index] = Tokenizer.getToken(featureEvent, "-", 0);
						featureNameList[index] = Tokenizer.getToken(featureEvent, "-", 1);
						featureDescriptionList[index] = Tokenizer.getToken(featureEvent, "-", 2);
						eventList[index] = Tokenizer.getToken(featureEvent, "-", 3);
						eventNameList[index] = Tokenizer.getToken(featureEvent, "-", 4);
						eventDescriptionList[index] = Tokenizer.getToken(featureEvent, "-", 5);
						eventCategoryList[index] = Tokenizer.getToken(featureEvent, "-", 6);
					}
					
					String[] bottons = {"Confirm"};
					if(name.equals("") || companyid.equals("") || generation.equals("") || ownerid.equals("") || 
							featureEventList.length==0){
						message.showErrorBox(parent, "Warning", "All the fields should be filled in.", MessageDialog.ERROR, bottons);
					}else{
						
						Project newProject = new Project
								(name, companyid, ownerid, generation, start, end, 0, featureList, featureNameList, featureDescriptionList, eventList, eventNameList, eventDescriptionList, eventCategoryList);
						if(txt_id.getText().equals(""))
							new ProjectCreator(newProject);
						else{
							newProject.setId(Integer.valueOf(txt_id.getText()));
							new ProjectUpdator(newProject);
						}
						
						viewerRefreash();
						treeViewerRefreash();
					}
				}

				@Override
				public String getTitle() {
					return "Creating(Updating) Project...";
				}

				@Override
				public int getTotal() {
					return total;
				}
				
			}));
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void treeViewerRefreash() {
		try {
			IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(FeatureTreeViewer.ID);
			((FeatureTreeViewer)view).refresh();
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
	
	private void viewerRefreash() {
		try {
			IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ProjectViewer.ID);
			((ProjectViewer)view).refresh();
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
	
	public void refresh(){
		initGUIControls();
		clear();
	}
	
	public void refresh(final Project project){
		
		try{

			btn_close.setEnabled(true);
			btn_remove.setEnabled(true);
			txt_id.setText(String.valueOf(project.getId()));
			lst_feature_event.removeAll();
			
			new ProgressMonitorDialog(parent.getShell()).run(true, true, new VTProgressBar(new VTJob(){
				private int total = 0;
				@Override
				public void doJob(IProgressMonitor monitor) {
					
					String[] featureIdList = project.getFeatureList();
					total = featureIdList.length + 5;
					int cnt=0;
					for(int i = 0; i < featureIdList.length ; i++) {

						Table featureTable = tbl_featureViewer.getTable();
						for(int k = 0 ; k < featureTable.getItemCount(); k++){
							if(((Feature)featureTable.getItem(k).getData()).getId()==Integer.valueOf(featureIdList[i])){
								tbl_featureViewer.getTable().setSelection(k);
								new FeatureSelectionListener(tbl_featureViewer.getTable()).doEvent();						
								if(((Feature)featureTable.getItem(k).getData()).getId()==Integer.valueOf(featureIdList[i])){
									tbl_featureViewer.getTable().setSelection(k);
									new FeatureSelectionListener(tbl_featureViewer.getTable()).doEvent();
									int[] selectedIndice = getSelectedIndice(project);
									new EventSelectionListener(tbl_eventViewer.getTable(), 
											lst_feature_event, tbl_featureViewer.getTable(), selectedIndice).doEvent();
								}
							}
						}
						
						monitor.worked(cnt++);
					}
					
					txt_name.setText(project.getName());
					initOEM(project.getOem());
					cbo_gen.setText(String.valueOf(project.getGeneration()));
					initOwner(project.getOwnerid());
					
					int s_y = Integer.valueOf(Tokenizer.getToken(project.getStart().toString(), "-", 0));
					int s_m = Integer.valueOf(Tokenizer.getToken(project.getStart().toString(), "-", 1))-1;
					int s_d = Integer.valueOf(Tokenizer.getToken(project.getStart().toString(), "-", 2));
					int e_y = Integer.valueOf(Tokenizer.getToken(project.getEnd().toString(), "-", 0));
					int e_m = Integer.valueOf(Tokenizer.getToken(project.getEnd().toString(), "-", 1))-1;
					int e_d = Integer.valueOf(Tokenizer.getToken(project.getEnd().toString(), "-", 2));
					
					if(project.getIsOpen()==0){
						btn_close.setText("Close");
					}else{
						btn_close.setText("Open");
					}
					date_start.setDate(s_y, s_m, s_d);
					date_end.setDate(e_y, e_m, e_d);
				}

				@Override
				public String getTitle() {
					// TODO Auto-generated method stub
					return "Initialize the Selected Project...";
				}

				@Override
				public int getTotal() {
					return total;
				}
				
			}));
			
			
			
		}catch(NullPointerException | InvocationTargetException | InterruptedException err){
			clear();
		}
		
	}

	private int[] getSelectedIndice(Project project) {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		
		for(int j = 0; j < project.getEventList().length ; j++){
			String savedId = project.getEventList()[j];
			for(int h = 0 ; h < this.tbl_eventViewer.getTable().getItemCount() ; h++){
				int id = ((Event)this.tbl_eventViewer.getTable().getItem(h).getData()).getId();
				if(Integer.valueOf(savedId) == id){
					tmp.add(h);
				}
			}
		}
		
		int[] selectedIndice = new int[tmp.size()];
		for(int i = 0 ;i < tmp.size() ; i++){
			selectedIndice[i] = tmp.get(i);
		}
		
		return selectedIndice;
	}
	
	public void refreashTable(TableViewer tableViewer, String featureId, boolean flg){
		model = new FeatureEventProvider();
		
		tableViewer.setContentProvider(new ArrayContentProvider());
		
		if(flg){
			model.setEvent(Integer.valueOf(featureId));
			tableViewer.setInput(model.getEvent());
		}else{
			model.setFeature();
			tableViewer.setInput(model.getFeature());
		}
		
	    getSite().setSelectionProvider(tableViewer);
	}
	
	
	public void refreashTable(TableViewer tableViewer, int projectId, boolean flg){
		FeatureEventProvider model = new FeatureEventProvider();
		
		tableViewer.setContentProvider(new ArrayContentProvider());
		
		if(flg){
			model.setEvent(Integer.valueOf(ProjectManager.selectedFeatureId));
			tableViewer.setInput(model.getEvent());
		}else{
			model.setFeature(projectId);
			tableViewer.setInput(model.getFeature());
		}
		
	    getSite().setSelectionProvider(tableViewer);
	}
	
	
	@Override
	public void setFocus() {
		
	}
}
