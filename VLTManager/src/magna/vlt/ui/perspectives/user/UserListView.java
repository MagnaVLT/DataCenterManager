package magna.vlt.ui.perspectives.user;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Hashtable;

import magna.vlt.ui.common.GUIInfoRetriever;
import magna.vlt.ui.common.UIHelper;
import magna.vlt.ui.common.VTProgressBar;
import magna.vlt.ui.perspectives.project.ProjectViewer;
import magna.vlt.ui.perspectives.user.data.Employee;
import magna.vlt.ui.perspectives.user.data.provider.EmployeeProvider;
import magna.vlt.ui.perspectives.user.data.provider.EmployeeProviderWithCondition;
import magna.vlt.ui.perspectives.user.data.provider.EmployeeProviderWithoutCondition;
import magna.vlt.ui.perspectives.user.table.UserRemoveListener;
import magna.vlt.util.Tokenizer;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class UserListView extends ViewPart {
	private Table table;
	private Text txt_id;
	private Text txt_name;
	private Combo cbo_feature;
	private Button btn_search;
	private TableViewer tableViewer;
	private UIHelper uiHelper = new UIHelper();
	private Composite parent;
	private GUIInfoRetriever initializer;
	private Hashtable<String, String> conditions = new Hashtable<String, String>();
		
	public final static String ID = "magna.vlt.ui.perspectives.user.UserListView";
		
	public UserListView() {
		initializer = new GUIInfoRetriever(); 
	}

	@Override
	public void createPartControl(Composite parent) {
		createGUI(parent);
	}

	private void createGUI(Composite parent) {
		this.parent = parent;
		parent.setLayout(new GridLayout(7, false));
		
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("ID");
		
		txt_id = new Text(parent, SWT.BORDER);
		txt_id.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_1 = new Label(parent, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("Name");
		
		txt_name = new Text(parent, SWT.BORDER);
		txt_name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_2 = new Label(parent, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("Feature");
		
		cbo_feature = new Combo(parent, SWT.READ_ONLY);
		cbo_feature.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btn_search = new Button(parent, SWT.NONE);
		btn_search.setText("Search");
		
		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		GridData gd_table = new GridData(GridData.FILL_BOTH);
		gd_table.horizontalSpan = 7;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setContentProvider(new ArrayContentProvider());
		this.setColumn();
		
		tableViewer.setComparator(new ViewerComparator() {
			public int compare(Viewer viewer, Object e1, Object e2) {
				Employee empl1 = (Employee) e1;
				Employee empl2 = (Employee) e2;
				return empl1.getPrivilege().compareTo(empl2.getPrivilege());
			};
		});
	    getSite().setSelectionProvider(tableViewer);
	    initFeature();
	    addListener();
	}

	private void initFeature(){
		Hashtable<Integer, String> table = initializer.getIdNamePairList("id", "name", "feature_list");
		uiHelper.initCombo(this.cbo_feature, table);
	}
	
	public void refresh(boolean isSearch){
		
		try {
			tableViewer.setContentProvider(new ArrayContentProvider());
			EmployeeProvider provider;
			
			if(isSearch) provider = new EmployeeProviderWithCondition(this.conditions);
			else provider = new EmployeeProviderWithoutCondition();
			
			new ProgressMonitorDialog(parent.getShell()).run(true, true, new VTProgressBar(provider));
			tableViewer.setInput(provider.getEmployees());
			
		    getSite().setSelectionProvider(tableViewer);
		
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private void setColumn() {
		try {
			EmployeeProvider provider = new EmployeeProviderWithoutCondition();
			new ProgressMonitorDialog(parent.getShell()).run(true, true, new VTProgressBar(provider));
			tableViewer.setInput(provider.getEmployees());
			createColumns(parent, tableViewer);
			tableViewer.setInput(provider.getEmployees());
			
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void addListener(){
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
	        public void selectionChanged(final SelectionChangedEvent event) {
	            IStructuredSelection selection = (IStructuredSelection)event.getSelection();
	            Employee employee = ((Employee)selection.getFirstElement());
	    		try {
	    			if(employee!=null){
	    				IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(UserManagerView.ID);
		    			((UserManagerView)view).editingMode(employee);
		    			
		    			IViewPart historyView = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(UserHistoryView.ID);
		    			((UserHistoryView)historyView).refresh(employee.getId());
	    			}
	    		} catch (PartInitException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	        }
	    });
		
		
		btn_search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				conditions.clear();
				if(!txt_id.getText().trim().equals("")) conditions.put("id", txt_id.getText());
				if(!txt_name.getText().trim().equals("")) conditions.put("name", txt_name.getText());
				if(!cbo_feature.getText().equals("")) conditions.put("feature", Tokenizer.getToken(cbo_feature.getText(), "-", 0));
				if(txt_id.getText().equals("") && txt_name.getText().equals("") && cbo_feature.getText().equals("")){
					refresh(false);
				}else{
					refresh(true);
				}
			}

		});
		
	}

	
	
	private void createColumns(Composite parent, TableViewer viewer){
		String[] titles = { "ID", "Name", "Privilege", "Online"};
	    int[] bounds = { 100, 150, 150, 50 };
	    TableViewerColumn[] column = new TableViewerColumn[4];
	    
	    column[0] = uiHelper.createTableViewerColumn(this.tableViewer, titles[0], bounds[0], 0);
	    column[0].setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	Employee e = (Employee) element;
	        return String.valueOf(e.getId());
	      }
	    });
	    
	    column[1] = uiHelper.createTableViewerColumn(this.tableViewer, titles[1], bounds[1], 1);
	    column[1].setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
		    Employee e = (Employee) element;
	        return e.getName();
	      }
	    });
	    
	    column[2] = uiHelper.createTableViewerColumn(this.tableViewer, titles[2], bounds[2], 2);
	    column[2].setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
		    	Employee e = (Employee) element;
	        return e.getPrivilegeType();
	      }
	    });
	    
	    column[3] = uiHelper.createTableViewerColumn(this.tableViewer, titles[3], bounds[3], 3);
	    column[3].setLabelProvider(new ColumnLabelProvider() {

			@Override
	    	public String getText(Object element) {
		    	return "";
			}
			
			@Override
			public Image getImage(Object element) {
				if (((Employee) element).getOnline().equals("1")) {
					Bundle bundle = FrameworkUtil.getBundle(ProjectViewer.class);
					URL url = FileLocator.find(bundle, new Path("images/open.png"), null);
					ImageDescriptor imageDcr = ImageDescriptor.createFromURL(url);
					Image image = new Image(null, imageDcr.getImageData().scaledTo(15, 15));
					return image;
				} else {
					Bundle bundle = FrameworkUtil.getBundle(ProjectViewer.class);
					URL url = FileLocator.find(bundle, new Path("images/close.png"), null);
					ImageDescriptor imageDcr = ImageDescriptor.createFromURL(url);
					Image image = new Image(null, imageDcr.getImageData().scaledTo(15, 15));
					return image;
				}
			}
	    });
	    
	    
	    String[] menuItems = {"Remove the User"};
	    SelectionListener[] listeners = new SelectionListener[menuItems.length];
	    listeners[0] = new UserRemoveListener(this.tableViewer.getTable());
	    uiHelper.addPopupMenu(this.tableViewer.getTable(), menuItems, listeners);
	}

	@Override
	public void setFocus() {
		
	}

}
