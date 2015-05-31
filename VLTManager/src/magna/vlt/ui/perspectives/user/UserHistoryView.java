package magna.vlt.ui.perspectives.user;

import magna.vlt.ui.common.UIHelper;
import magna.vlt.ui.perspectives.user.data.Log;
import magna.vlt.ui.perspectives.user.data.provider.UserHistoryProvider;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.part.ViewPart;

public class UserHistoryView extends ViewPart {
	public final static String ID = "magna.vlt.ui.perspectives.user.UserHistory";
	private UIHelper uiHelper = new UIHelper();
	private Table tbl_log;
	private TableViewer tableViewer;
	private Composite parent;
	
	public UserHistoryView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		parent.setLayout(new GridLayout(4, false));
		
		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tbl_log = tableViewer.getTable();
		tbl_log.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 9));
		
		tbl_log.setHeaderVisible(true);
		tbl_log.setLinesVisible(true);
		tableViewer.setContentProvider(new ArrayContentProvider());
		createColumns(parent, tableViewer);
		refresh("");
	}

	public void refresh(String userid) {
		UserHistoryProvider model = new UserHistoryProvider();
		model.setHistory(userid);
		tableViewer.setInput(model.getHistory());
		
		tableViewer.setContentProvider(new ArrayContentProvider());
	    getSite().setSelectionProvider(tableViewer);
	}
	
	
	private void createColumns(Composite parent, TableViewer viewer){
		String[] titles = { "ID", "Name", "Start of Log-in", "End of Log-in", "Duration"};
	    int[] bounds = { 60, 100, 130, 130, 100 };
	    TableViewerColumn[] column = new TableViewerColumn[5];
	    
	    column[0] = uiHelper.createTableViewerColumn(this.tableViewer, titles[0], bounds[0], 0);
	    column[0].setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	  Log e = (Log) element;
	        return String.valueOf(e.getUserid());
	      }
	    });
	    
	    column[1] = uiHelper.createTableViewerColumn(this.tableViewer, titles[1], bounds[1], 1);
	    column[1].setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	  Log e = (Log) element;
		        return String.valueOf(e.getUserName());
	      }
	    });
	    
	    column[2] = uiHelper.createTableViewerColumn(this.tableViewer, titles[2], bounds[2], 2);
	    column[2].setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	  Log e = (Log) element;
		      return e.getStartTime();
	      }
	    });
	    
	    column[3] = uiHelper.createTableViewerColumn(this.tableViewer, titles[3], bounds[3], 3);
	    column[3].setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	  Log e = (Log) element;
		      return e.getEndTime();
	      }
	    });
	    
	    column[4] = uiHelper.createTableViewerColumn(this.tableViewer, titles[4], bounds[4], 4);
	    column[4].setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	  Log e = (Log) element;
		      return e.getDuration();
	      }
	    });
	}
	
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
