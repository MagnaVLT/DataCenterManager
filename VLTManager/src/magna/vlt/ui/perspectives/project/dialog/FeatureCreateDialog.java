package magna.vlt.ui.perspectives.project.dialog;

import magna.vlt.util.MessageBox;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class FeatureCreateDialog extends TitleAreaDialog {

	private int id;
	private String name;
	private String description;
	private Composite container_1;
	private Text txt_name;
	private Text txt_description;
	
	public FeatureCreateDialog(Shell parentShell) {
	    super(parentShell);
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public void create() {
		super.create();
		setTitle("Feature Creation");
		setMessage("In order to create a new feature, please fill in all the required field", IMessageProvider.INFORMATION);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
	    container_1 = new Composite(area, SWT.NONE);
	    container_1.setLayoutData(new GridData(GridData.FILL_BOTH));
	    GridLayout gl_container_1 = new GridLayout(4, false);
	    container_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    container_1.setLayout(gl_container_1);
	    new Label(container_1, SWT.NONE);
	    new Label(container_1, SWT.NONE);
	    new Label(container_1, SWT.NONE);
	    new Label(container_1, SWT.NONE);
	    new Label(container_1, SWT.NONE);
	    
	    Label lblNewLabel = new Label(container_1, SWT.NONE);
	    lblNewLabel.setText("Name");
	    new Label(container_1, SWT.NONE);
	    
	    txt_name = new Text(container_1, SWT.BORDER);
	    txt_name.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
	    new Label(container_1, SWT.NONE);
	    
	    Label lblNewLabel_1 = new Label(container_1, SWT.NONE);
	    lblNewLabel_1.setText("Description");
	    new Label(container_1, SWT.NONE);
	    
	    txt_description = new Text(container_1, SWT.BORDER);
	    txt_description.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
	    
	    return area;
	}
	
	@Override
	protected boolean isResizable() {
		return false;
	}
	
	private void saveInput() {
	   this.name = txt_name.getText();
	   this.description = txt_description.getText();
	}

	@Override
	protected void okPressed() {
	    saveInput();
	    if(!(name.equals("")||description.equals("")))
	    	super.okPressed();
	    else{
	    	String[] bottons = {"Confirm"};
	    	MessageBox message = new MessageBox();
	    	message.showErrorBox(this.getShell(), "Warning", "All the fields should be filled in.", MessageDialog.ERROR, bottons);
	    }
	}
} 