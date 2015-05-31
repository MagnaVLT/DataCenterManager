package magna.vlt.ui.perspectives.project.table;

import magna.vlt.ui.perspectives.project.data.Event;
import magna.vlt.ui.perspectives.project.data.provider.FeatureEventProvider;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;

public class OptionEditingSupport extends EditingSupport 
{
    private ComboBoxCellEditor cellEditor;

    private String[] category;
    public OptionEditingSupport(ColumnViewer viewer) {
        super(viewer);
        this.category = new FeatureEventProvider().getEventCategory();
        cellEditor = new ComboBoxCellEditor(((TableViewer)viewer).getTable(), category, SWT.READ_ONLY);
    }
    
    protected CellEditor getCellEditor(Object element) {
        return cellEditor;
    }
    protected boolean canEdit(Object element) {
        return true;
    }
    protected Object getValue(Object element) {
        return 0;
    }
    
    protected void setValue(Object element, Object value) 
    {
        if((element instanceof Event) && (value instanceof Integer)) {
            Integer choice = (Integer)value;
            ((Event)element).setEventCategory( choice+1);
            ((Event)element).setCategoryName(category[choice]);
            getViewer().update(element, null);
        }
    }
}