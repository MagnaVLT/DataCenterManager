package magna.vlt.ui.perspectives.review.chart;

import magna.vlt.ui.perspectives.review.chart.plot.PlotConfigure;
import magna.vlt.ui.perspectives.review.chart.query.Query;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.AbstractDataset;

public class BarChart extends Chart {

	public BarChart(Query query) {
		super(query);
	}

	@Override
	public void createChart(String title, PlotConfigure configure) {
		DefaultCategoryDataset dataset = (DefaultCategoryDataset)this.createDataset();
		super.jFreeChart = ChartFactory.createBarChart(
	            "",         // chart title
	            "",               // domain axis label
	            "",                  // range axis label
	            dataset,                  // data
	            PlotOrientation.VERTICAL, // orientation
	            true,                     // include legend
	            true,                     // tooltips?
	            false                     // URLs?
	        );
		
		CategoryPlot plot = jFreeChart.getCategoryPlot();
		plot.setBackgroundPaint(null);
		plot.getRenderer().setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		plot.getRenderer().setBaseItemLabelsVisible(true);
		
        
	}

	@Override
	public AbstractDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		// row keys...
		for(int i = 0 ; i < super.query.getName().size() ; i++){
			String k = super.query.getName().get(i);
			int v = super.query.getValue().get(i);
			dataset.addValue(v, k, "");
		}
		return dataset;
	}
}
