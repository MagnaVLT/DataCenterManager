package magna.vlt.ui.perspectives.project.table;

import java.util.Arrays;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.List;

public class EventRemoveOfSelectedListener implements SelectionListener {
	private List list;
	
	public EventRemoveOfSelectedListener(List list){
		this.list = list;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		doEvent();
	}

	private void doEvent(){
		int[] selectedIdx = list.getSelectionIndices();
		Arrays.sort(selectedIdx);
		
		for(int i = selectedIdx.length-1; i >= 0; i--)
		{
			list.remove(i);
		}
		
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
