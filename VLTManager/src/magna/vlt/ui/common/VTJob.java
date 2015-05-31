package magna.vlt.ui.common;

import org.eclipse.core.runtime.IProgressMonitor;

public interface VTJob {

	public void doJob(IProgressMonitor monitor);
	public String getTitle();
	public int getTotal();
}
