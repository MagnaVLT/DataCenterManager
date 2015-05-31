package magna.vlt.ui.perspectives.review.chart.plot;

import java.awt.Color;

import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;

public class StatusPlot extends PlotConfigure{

	public Plot configure(Plot plot){
		
		PiePlot customPlot = (PiePlot) plot;
		
		customPlot.setSectionPaint("UNCONFIRMED", new Color(160, 160, 255));
		customPlot.setSectionPaint("ACCEPTED", new Color(128, 128, 255 - 32));
		customPlot.setSectionPaint("ANNOTATED", new Color(96, 96, 255 - 64));
		customPlot.setSectionPaint("DECLINED", new Color(64, 64, 255 - 96));
		customPlot.setSectionPaint("MISSED", new Color(32, 32, 255 - 128));
		customPlot.setSectionPaint("Six", new Color(0, 0, 255 - 144));

		customPlot.setNoDataMessage("No data available");
		customPlot.setExplodePercent("UNCONFIRMED", 0.20);
		
		return plot;
	}
    
}
