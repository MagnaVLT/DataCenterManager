package magna.vlt.ui.perspectives.user;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class UserPerspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
//        IFolderLayout topViewFolder = layout.createFolder("topViewFolder", IPageLayout.TOP, 0.2f, layout.getEditorArea());
//        topViewFolder.addView("videologgingtool.magna.ui.SQLUploadView");
//        topViewFolder.addView("videologgingtool.magna.ui.DatabaseView");
	}
}