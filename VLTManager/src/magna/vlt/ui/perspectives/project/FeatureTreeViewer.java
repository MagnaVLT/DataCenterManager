package magna.vlt.ui.perspectives.project;

import java.net.URL;
import java.util.Hashtable;

import magna.vlt.ui.perspectives.project.data.FeaturEventRetriever;
import magna.vlt.ui.perspectives.project.data.FeatureEvent;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class FeatureTreeViewer extends ViewPart {
	public final static String ID = "magna.vlt.ui.perspectives.project.FeatureTreeViewer";
	private Hashtable<String, Image> images = new Hashtable<String, Image>();
	private TreeViewer treeViewer;
	public FeatureTreeViewer() {
		// TODO Auto-generated constructor stub
	}

	public void refresh(){
		FeatureEvent.clearRoots(ID);
		new FeaturEventRetriever(ID).getRoot();
		treeViewer.setContentProvider(new FeatureTreeContentProvider());
		treeViewer.setLabelProvider(new FeatureTreeViewLabelProvider());
		treeViewer.setInput("root1");
		treeViewer.expandAll();
	}
	
	@Override
	public void createPartControl(Composite parent) {
		createImage();
		parent.setLayout(new GridLayout(1, false));
		treeViewer = new TreeViewer(parent, SWT.BORDER);
		
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				ITreeSelection selection = ((ITreeSelection)event.getSelection());
//    			System.out.println(((FeatureEvent)selection.getPaths()[0].getLastSegment()).getId());
			}
		});
		
		treeViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		new FeaturEventRetriever(ID).getRoot();
		treeViewer.setContentProvider(new FeatureTreeContentProvider());
		treeViewer.setLabelProvider(new FeatureTreeViewLabelProvider());
		treeViewer.setInput("root");
		treeViewer.expandAll();

	}

	private void createImage() {
		Bundle bundle = FrameworkUtil.getBundle(FeatureTreeViewLabelProvider.class);
		URL[] url = new URL[3]; 
		ImageDescriptor[] imageDcr = new ImageDescriptor[3];
		Image[] image = new Image[3];
		String[] imageName = new String[3];
		
		url[0] = FileLocator.find(bundle, new Path("images/project.png"), null);
		imageDcr[0] = ImageDescriptor.createFromURL(url[0]);
		image[0] = new Image(null, imageDcr[0].getImageData().scaledTo(15, 15));
		this.images.put(FeatureEvent.project, image[0]);
		
		
		url[1] = FileLocator.find(bundle, new Path("images/feature.png"), null);
		imageDcr[1] = ImageDescriptor.createFromURL(url[1]);
		image[1] = new Image(null, imageDcr[1].getImageData().scaledTo(20, 20));
		imageName[1] = "feature";
		this.images.put(FeatureEvent.feature, image[1]);
		
		
		url[2] = FileLocator.find(bundle, new Path("images/event.png"), null);
		imageDcr[2] = ImageDescriptor.createFromURL(url[2]);
		image[2] = new Image(null, imageDcr[2].getImageData().scaledTo(20, 20));
		imageName[2] = "event";
		this.images.put(FeatureEvent.event, image[2]);
	}
	
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	
	
	class FeatureTreeContentProvider implements ITreeContentProvider {

		public Object[] getChildren(Object arg0) {
			return ((FeatureEvent)arg0).listChildren();
		}
		
		public Object getParent(Object arg0) {
			return ((FeatureEvent) arg0).getParent();
		}
		
		public boolean hasChildren(Object arg0) {
			Object[] obj = getChildren(arg0);
			return obj == null ? false : obj.length > 0;
		}
		
		public Object[] getElements(Object arg0) {
			return FeatureEvent.listRoots(ID);
		}
		
		  /**
		   * Disposes any created resources
		   */
		public void dispose() {
		    
		}


		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
			
		}
	}
	
	class FeatureTreeViewLabelProvider extends StyledCellLabelProvider {
		@Override
		public void update(ViewerCell cell) {
			Object element = cell.getElement();
			StyledString text = new StyledString();
			FeatureEvent featureEvent = (FeatureEvent) element;
			text.append(getFileName(featureEvent));
			
			if (featureEvent.getType().equals(FeatureEvent.project)){
				cell.setImage(images.get("project"));
			}else if (featureEvent.getType().equals(FeatureEvent.feature)){
				cell.setImage(images.get("feature"));
			}else if (featureEvent.getType().equals(FeatureEvent.event)){
				cell.setImage(images.get("event"));
			}
			
			cell.setText(text.toString());
			cell.setStyleRanges(text.getStyleRanges());
			super.update(cell);
		}
		
		private String getFileName(FeatureEvent element) {
			if(element.getDescription()==null)
				return element.getId() + "(" + element.getName() + ")";
			else
				return element.getId() + "(" + element.getName() + ")" + "-" + element.getDescription();
		}
	}
}
