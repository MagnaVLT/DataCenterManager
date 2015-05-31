package magna.vlt.ui.perspectives.project;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import magna.vlt.ui.common.UIHelper;
import magna.vlt.ui.common.VTProgressBar;
import magna.vlt.ui.perspectives.project.data.Project;
import magna.vlt.ui.perspectives.project.data.provider.ProjectProvider;

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
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class ProjectViewer extends ViewPart {
	public static String ID = "magna.vlt.ui.project.ProjectViewer";
	private Table tbl_project;
	private TableViewer tableViewer;
	private UIHelper uiHelper = new UIHelper();
	private Composite parent;
	
	public ProjectViewer() {
	}

	public void refresh(){
		initTableViewerModel();
	}
	
	private ProjectProvider initTableViewerModel(){
		ProjectProvider model = new ProjectProvider();
		try {
			new ProgressMonitorDialog(parent.getShell()).run(true, true, new VTProgressBar(model));
			tableViewer.setContentProvider(new ArrayContentProvider());
			tableViewer.setInput(model.getProjects());
		    getSite().setSelectionProvider(tableViewer);
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return model;
	}
	
	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		ProjectProvider model = initTableViewerModel();

	    createColumns(parent, tableViewer);
	    tbl_project = tableViewer.getTable();

	    tbl_project.setHeaderVisible(true);
	    tbl_project.setLinesVisible(true);

		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(model.getProjects());
		
	    getSite().setSelectionProvider(tableViewer);

	    // define layout for the viewer
	    GridData gridData = new GridData();
	    gridData.verticalAlignment = GridData.FILL;
	    gridData.horizontalSpan = 2;
	    gridData.grabExcessHorizontalSpace = true;
	    gridData.grabExcessVerticalSpace = true;
	    gridData.horizontalAlignment = GridData.FILL;
	    tableViewer.getControl().setLayoutData(gridData);
	    addListener();
	}
	
	private void createColumns(final Composite parent, final TableViewer viewer) {
	    String[] titles = { "id", "Name", "OEM", "Owner", "Gen", "Duration", "isOpen"};
	    int[] bounds = { 30, 100, 100, 50, 50, 150, 50 };
	    TableViewerColumn[] column = new TableViewerColumn[7];
	    

	    column[0] = uiHelper.createTableViewerColumn(this.tableViewer, titles[0], bounds[0], 0);
	    column[0].setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	        Project p = (Project) element;
	        return String.valueOf(p.getId());
	      }
	    });
	    
	    column[1] = uiHelper.createTableViewerColumn(this.tableViewer, titles[1], bounds[1], 1);
	    column[1].setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	        Project p = (Project) element;
	        return p.getName();
	      }
	    });

	    column[2] = uiHelper.createTableViewerColumn(this.tableViewer, titles[2], bounds[2], 2);
	    column[2].setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	        Project p = (Project) element;
	        return p.getOem();
	      }
	    });
	    
	    column[3] = uiHelper.createTableViewerColumn(this.tableViewer, titles[3], bounds[3], 3);
	    column[3].setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	        Project p = (Project) element;
	        return p.getOwnerid();
	      }
	    });
	    
	    column[4] = uiHelper.createTableViewerColumn(this.tableViewer, titles[4], bounds[4], 4);
	    column[4].setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	        Project p = (Project) element;
	        return String.valueOf(p.getGeneration());
	      }
	    });
	    
	    column[5] = uiHelper.createTableViewerColumn(this.tableViewer, titles[5], bounds[5], 5);
	    column[5].setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	        Project p = (Project) element;
	        return String.valueOf(p.getStart()) +" ~ " + String.valueOf(p.getEnd());
	      }
	    });
	    
	    column[6] = uiHelper.createTableViewerColumn(this.tableViewer, titles[6], bounds[6], 6);
	    column[6].setLabelProvider(new ColumnLabelProvider() {
		    public String getText(Object element) {
		    	return "";
			}
			@Override
			public Image getImage(Object element) {
				if (((Project) element).getIsOpen()==0) {
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
	  }

	private void addListener(){
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
	        public void selectionChanged(final SelectionChangedEvent event) {
	            IStructuredSelection selection = (IStructuredSelection)event.getSelection();
	            Project project = ((Project)selection.getFirstElement());
	    		try {
	    			IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ProjectManager.ID);
	    			((ProjectManager)view).refresh(project);
	    		} catch (PartInitException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	        }
	    });
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
