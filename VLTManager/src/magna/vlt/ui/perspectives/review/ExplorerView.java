package magna.vlt.ui.perspectives.review;

import java.util.Hashtable;
import java.util.Vector;

import magna.vlt.ui.common.GUIInfoRetriever;
import magna.vlt.ui.common.UIHelper;
import magna.vlt.util.MessageBox;
import magna.vlt.util.Tokenizer;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class ExplorerView extends ViewPart {

	private Combo cbo_project;
	private Combo cbo_feature;
	private Button btn_submit;
	private UIHelper helper = new UIHelper();
	private GUIInfoRetriever initiator = new GUIInfoRetriever();
	private MessageBox message;
	private Composite parent;
	
	public ExplorerView() {
		message = new MessageBox();
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		parent.setLayout(new GridLayout(3, true));
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Project");
		
		cbo_project = new Combo(parent, SWT.READ_ONLY);
		cbo_project.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblNewLabel_1 = new Label(parent, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("Feature");
		
		cbo_feature = new Combo(parent, SWT.READ_ONLY);
		cbo_feature.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		initGUI(parent);
		new Label(parent, SWT.NONE);
		
		Button btnNewButton = new Button(parent, SWT.NONE);
		btnNewButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnNewButton.setText("Clear");
		
		btn_submit = new Button(parent, SWT.NONE);
		btn_submit.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btn_submit.setText("Submit");
		addListener();
	}

	private void addListener() {
		cbo_project.addModifyListener(new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent e) {
				if(cbo_project.getSelectionIndex()>-1){
					String projectid = Tokenizer.getToken(cbo_project.getItem(cbo_project.getSelectionIndex()), "-", 0);
					String query = "select b.id, b.name from project_feature_map a, feature_list b where a.featureid = b.id and a.projectid = " + projectid + ";";
					Vector<String> fieldList = new Vector<String>();
					fieldList.add("id");
					fieldList.add("name");
					
					Hashtable<Integer, String> featureTable = initiator.getSeveralList(query, "id", fieldList);
					helper.initCombo(cbo_feature, featureTable);
				}
			}
		});
		
		btn_submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				String[] bottons = {"Confirm"};
				if(cbo_project.getText().equals("") || cbo_feature.getText().equals("")){
					message.showErrorBox(parent, "Warning", "All the fields should be filled in.", MessageDialog.ERROR, bottons);
				}else{
					String projectid = Tokenizer.getToken(cbo_project.getText(), "-", 0);
					String featureid = Tokenizer.getToken(cbo_feature.getText(), "-", 0);
					
					try {
						IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(DashBoardViewer.ID);
						((DashBoardViewer)view).refresh(projectid, featureid);
					} catch (PartInitException e2) {
						e2.printStackTrace();
					}
				}
			}
		});
	}

	private void initGUI(Composite parent) {
		Hashtable<Integer, String> featureTable = initiator.getIdNamePairList("id", "name", "feature_list");
		helper.initCombo(this.cbo_feature, featureTable);
		
		Hashtable<Integer, String> projectTable = initiator.getIdNamePairList("id", "name", "project");
		helper.initCombo(this.cbo_project, projectTable);
	}


	@Override
	public void setFocus() {
		
	}

}
