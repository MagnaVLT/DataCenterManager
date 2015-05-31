package magna.vlt.ui.common;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

public class VTProgressBar implements IRunnableWithProgress{
	private VTJob job;
	public VTProgressBar(VTJob job){
		this.job = job;
	}
	
	@Override
	public void run(final IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		// TODO Auto-generated method stub

		monitor.beginTask(job.getTitle(), job.getTotal());
		Display.getDefault().asyncExec(new Runnable(){
			@Override
			public void run() {
				job.doJob(monitor);
			}
		});
		monitor.done();
	}
}
