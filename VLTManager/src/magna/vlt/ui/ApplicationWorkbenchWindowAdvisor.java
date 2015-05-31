package magna.vlt.ui;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(400, 300));
        configurer.setShowCoolBar(true);
        configurer.setShowStatusLine(true);
        configurer.setShowPerspectiveBar(true);
        configurer.setTitle("Magna Video Logging Tool");
        
        Bundle bundle = FrameworkUtil.getBundle(configurer.getWindow().getShell().getClass());
    	URL url = FileLocator.find(bundle, new Path("images/magna.png"), null);
    	ImageDescriptor imageDcr = ImageDescriptor.createFromURL(url);
		Image image = new Image(null, imageDcr.getImageData().scaledTo(20, 20));
        configurer.getWindow().getShell().setImage(image);
    }
    
    public boolean isDurableFolder(String perspectiveId, String folderId) {
    	  return true;
    	}
}
