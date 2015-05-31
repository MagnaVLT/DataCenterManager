package magna.vlt.ui.perspectives.project.dialog;

import java.util.Hashtable;

import magna.vlt.ui.common.GUIInfoRetriever;
import magna.vlt.ui.common.UIHelper;
import magna.vlt.ui.perspectives.project.ProjectManager;
import magna.vlt.util.MessageBox;
import magna.vlt.util.Tokenizer;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EventCreateDialog extends TitleAreaDialog {

	private String name;
	private String description;
	private String featureId;
	private String subFeatureId;
	private String eventCategory;
	private String requestor;
	private Composite container_1;
	private Text txt_name;
	private Text txt_desc;
	private Combo cbo_feature_sub;
	private Combo cbo_cate;
	private UIHelper helper = new UIHelper();
	private GUIInfoRetriever retriever = new GUIInfoRetriever();
	private Label lblReportor;
	private Combo combo_requestor;
	private Hashtable<Integer, String> table = new Hashtable<Integer, String>();
	
	public EventCreateDialog(Shell parentShell) {
	    super(parentShell);
	}
	
	@Override
	public void create() {
		featureId = ProjectManager.selectedFeatureId;
		if(!(featureId==null || featureId.equals("0"))){
			super.create();
			setTitle("Event Creation");
			setMessage("In order to create a new event, please fill in all the required field", IMessageProvider.INFORMATION);
		}
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		
		Composite area = (Composite) super.createDialogArea(parent);
	    container_1 = new Composite(area, SWT.NONE);
	    container_1.setLayoutData(new GridData(GridData.FILL_BOTH));
	    GridLayout gl_container_1 = new GridLayout(2, false);
	    container_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    container_1.setLayout(gl_container_1);
	    
	    Label lblFeatureSubcategory = new Label(container_1, SWT.NONE);
	    lblFeatureSubcategory.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
	    lblFeatureSubcategory.setText("Feature Sub-Category");
	    
	    cbo_feature_sub = new Combo(container_1, SWT.READ_ONLY);
	    cbo_feature_sub.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	    
	    Label lblNewLabel = new Label(container_1, SWT.NONE);
	    lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
	    lblNewLabel.setText("Event Name");
	    
	    txt_name = new Text(container_1, SWT.BORDER);
	    txt_name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	    
	    Label lblEventDescription = new Label(container_1, SWT.NONE);
	    lblEventDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
	    lblEventDescription.setText("Event Description");
	    
	    txt_desc = new Text(container_1, SWT.BORDER);
	    txt_desc.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	    
	    Label lblEventCategory = new Label(container_1, SWT.NONE);
	    lblEventCategory.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
	    lblEventCategory.setText("Event Category");
	    
	    cbo_cate = new Combo(container_1, SWT.READ_ONLY);
	    cbo_cate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	    
	    lblReportor = new Label(container_1, SWT.NONE);
	    lblReportor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
	    lblReportor.setText("Reportor");
	    
	    combo_requestor = new Combo(container_1, SWT.READ_ONLY);
	    combo_requestor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	    
	    this.initCombo();
	    return area;
	}
	
	private void initCombo(){
		this.initSubFeatureCombo();
		this.initEventCategoryCombo();
		this.initRequestorCombo();
	}

	private void initSubFeatureCombo(){
		if(!featureId.equals("")){
			String query = "select Feature_Sub_Category_ID, name, description from feature_sub_category where featureid = " + featureId + ";";
			table = retriever.getTripledList(query, "Feature_Sub_Category_ID", "name", "description");
			helper.initCombo(this.cbo_feature_sub, table);
		}
	}
	
	private void initEventCategoryCombo() {
		Hashtable<Integer, String> table = retriever.getIdNamePairList("id", "name", "event_category_list");
		helper.initCombo(this.cbo_cate, table);
	}
	
	private void initRequestorCombo() {
		String query = "select id, firstname as name from users where privilege = 2 or privilege = 3;";
		Hashtable<String, String> table = retriever.getPairedList(query, "id", "name");
		helper.initCombo(this.combo_requestor, table);
	}
	
	@Override
	protected boolean isResizable() {
		return false;
	}
	
	private void saveInput() {
	    name = txt_name.getText();
	    description = txt_desc.getText();
	    subFeatureId = cbo_feature_sub.getText();
	    if(!subFeatureId.equals("")) subFeatureId = Tokenizer.getToken(subFeatureId, "-", 0);
	    eventCategory = Tokenizer.getToken(cbo_cate.getText(), "-", 0);
	    requestor = Tokenizer.getToken(combo_requestor.getText(), "-", 0);
	}

	@Override
	protected void okPressed() {
	    saveInput();
	    if(!(name.equals("")||description.equals("") || featureId.equals("")|| eventCategory.equals(""))
	    		&& !(subFeatureId.equals("") && table.size() > 0))
	    	super.okPressed();
	    else{
	    	String[] bottons = {"Confirm"};
	    	MessageBox message = new MessageBox();
	    	message.showErrorBox(this.getShell(), "Warning", "All the fields should be filled in.", MessageDialog.ERROR, bottons);
	    }
	}

	public String getName() {
		return name;
	}
	
	public String getFeatureId() {
		return featureId;
	}

	public String getSubFeatureId() {
		return subFeatureId;
	}

	public String getEventCategory() {
		return eventCategory;
	}

	public String getDescription() {
		return description;
	}

	public String getRequestor() {
		// TODO Auto-generated method stub
		return requestor;
	}

} 