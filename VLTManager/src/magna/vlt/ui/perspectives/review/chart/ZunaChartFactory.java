package magna.vlt.ui.perspectives.review.chart;

import java.util.Hashtable;

import magna.vlt.ui.perspectives.review.chart.plot.PlotConfigure;
import magna.vlt.ui.perspectives.review.chart.query.Query;

import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;

public class ZunaChartFactory {
	

	public final static int bar = 0;
	public final static int pie = 1;
	public final static String EVENT = "EVENT";
	public final static String ANNOTATION = "ANNOTATION";
	public final static String CATEGORY = "CATEGORY";
	public final static String STATUS = "STATUS";
	
	public static Hashtable<String, Dataset> chartList = new Hashtable<String, Dataset>();
	
	public JFreeChart createChart(Composite parent, String chartId, int type, Query query, PlotConfigure configure) {
		Chart chart = null;
		
		switch (type)
		{
		case bar:
			chart = new BarChart(query);
			break;
		case pie:
			chart = new PieChart(query);
			break;
		}
		
		chart.createChart("", configure);
		chartList.put(chartId, chart.getDataset());
		return chart.getjFreeChart();
	}
}