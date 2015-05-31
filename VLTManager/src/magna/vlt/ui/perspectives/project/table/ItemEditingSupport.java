package magna.vlt.ui.perspectives.project.table;

import magna.vlt.ui.perspectives.project.data.Event;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

public class ItemEditingSupport  extends EditingSupport{
	  private final CellEditor editor;

	public ItemEditingSupport(TableViewer viewer) {
		super(viewer);
		this.editor = new TextCellEditor(viewer.getTable());
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		// TODO Auto-generated method stub
		return editor;
	}

	@Override
	protected boolean canEdit(Object element) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		// TODO Auto-generated method stub
		
		return ((Event) element).getDescription();
	}

	@Override
	protected void setValue(Object element, Object value) {
		// TODO Auto-generated method stub
	    ((Event) element).setDescription(String.valueOf(value));
	    super.getViewer().update(element, null);
	}

	
}
