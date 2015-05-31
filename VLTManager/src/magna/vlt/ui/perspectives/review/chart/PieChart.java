package magna.vlt.ui.perspectives.review.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;

import magna.vlt.ui.perspectives.review.chart.plot.PlotConfigure;
import magna.vlt.ui.perspectives.review.chart.query.Query;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart extends Chart {


	public PieChart(Query query) {
		super(query);
	}

	@Override
	public void createChart(String title, PlotConfigure configure) {
		super.dataset = (DefaultPieDataset)this.createDataset();
		
		super.jFreeChart = ChartFactory.createPieChart(title, dataset, false, true, false);
		
        // set a custom background for the chart
		jFreeChart.setBackgroundPaint(new GradientPaint(new Point(0, 0), 
                new Color(240, 240, 240), new Point(400, 200),new Color(240, 240, 240)));
		
		PiePlot plot = (PiePlot) super.jFreeChart.getPlot();  
		plot = (PiePlot) configure.configure(plot);
        plot.setBackgroundPaint(null);
        plot.setInteriorGap(0.04);
        plot.setOutlineVisible(false);
		plot.setOutlineStroke(new BasicStroke(1f));
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})"));
//		plot.setLabelBackgroundPaint(new Color(220, 220, 220));
		
        plot.setSectionOutlinesVisible(true);
        plot.setBaseSectionOutlineStroke(new BasicStroke(1.0f));
        // customise the section label appearance
        plot.setLabelBackgroundPaint(Color.DARK_GRAY);
        plot.setLabelFont(new Font("Calibri", Font.BOLD, 12));
        plot.setLabelLinkPaint(Color.DARK_GRAY);
        plot.setLabelLinkStroke(new BasicStroke(1.0f));
        plot.setLabelOutlineStroke(null);
        plot.setLabelPaint(Color.WHITE);
		plot.setLegendLabelToolTipGenerator(new StandardPieSectionLabelGenerator("Tooltip for legend item {0}"));
		plot.setSimpleLabels(true);
		
	}

	@Override
	public AbstractDataset createDataset() {		
		DefaultPieDataset result = new DefaultPieDataset();
		for(int i = 0 ; i < super.query.getName().size() ; i++){
			String k = super.query.getName().get(i);
			int v = super.query.getValue().get(i);
			result.setValue(k, v);
		}
		
		return result;
	}
}
