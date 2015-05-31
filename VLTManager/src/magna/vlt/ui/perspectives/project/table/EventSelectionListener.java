package magna.vlt.ui.perspectives.project.table;

import magna.vlt.ui.common.UIHelper;
import magna.vlt.ui.perspectives.project.data.Event;
import magna.vlt.util.Tokenizer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;

public class EventSelectionListener implements SelectionListener{
	private Composite source;
	private Table feature;
	private List target;
	private UIHelper helper = new UIHelper();
	private int[] selectedIndice;
	
	public EventSelectionListener(Composite source, List target, Table feature){
		this.source = source;
		this.target = target;
		this.feature = feature;
		selectedIndice = new int[0];
	}
	
	public EventSelectionListener(Composite source, List target, Table feature, int[] selectedIndice){
		this.source = source;
		this.target = target;
		this.feature = feature;
		this.selectedIndice = selectedIndice;
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
		if(selectedIndice.length==0)
			selectedIndice = t.getSelectionIndices();
		
		for(int index: selectedIndice){
			Object o = t.getItem(index).getData();
			if(o instanceof Event){
				String featureDescription = feature.getItem(feature.getSelectionIndex()).getText();
				featureDescription = featureDescription.replace(" (", "-");
				featureDescription = featureDescription.replace(")", "");
				String featureName = Tokenizer.getToken(featureDescription, "-", 0);
				featureDescription = Tokenizer.getToken(featureDescription, "-", 1);
				
				Event event = (Event) o;
				String text = getKey(event.getFeatureId(), featureDescription, featureName, 
						event.getId(), event.getName(), event.getDescription(), event.getEventCategory());
				
				if(!helper.isExistItem(target, text)){
					target.add(text);
				}
				
			}
		}
		
		selectedIndice = new int[0];
	}

	public String getKey(int featureId, String featureDescription, String featureName,
			int eventId, String name, String description, int eventCategory) {
		String text = featureId + "-" + featureName + "-" + featureDescription + "-" + 
				eventId + "-" + name + "-" + description + "-" + eventCategory;
			return text;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
