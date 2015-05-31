package magna.vlt.ui.perspectives.review.chart;


import magna.vlt.ui.perspectives.review.chart.plot.PlotConfigure;
import magna.vlt.ui.perspectives.review.chart.query.Query;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.DefaultPieDataset;

public abstract class Chart{
	protected Query query;
	protected JFreeChart jFreeChart;
	protected DefaultPieDataset dataset;
	
	
	public DefaultPieDataset getDataset() {
		return dataset;
	}

	public void setDataset(DefaultPieDataset dataset) {
		this.dataset = dataset;
	}
	
	protected Chart(Query query) {
		this.query = query; 
	}
	
	public JFreeChart getjFreeChart() {
		return jFreeChart;
	}

	public abstract void createChart(String title, PlotConfigure configure);
	public abstract AbstractDataset createDataset();

}