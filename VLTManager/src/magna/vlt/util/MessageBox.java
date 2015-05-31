package magna.vlt.util;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;

public class MessageBox {

	public void showErrorBox(Composite shell, String title, String message, int type, String[] Buttons){
		MessageDialog dialog = new MessageDialog(shell.getShell(), title, null,
				message, type, Buttons, 0);
			int result = dialog.open();
	}
}
