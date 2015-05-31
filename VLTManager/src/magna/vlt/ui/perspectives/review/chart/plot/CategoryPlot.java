package magna.vlt.ui.perspectives.review.chart.plot;

import java.awt.Color;

import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;

public class CategoryPlot extends PlotConfigure {

	@Override
	public Plot configure(Plot plot) {
		PiePlot customPlot = (PiePlot) plot;
		customPlot.setSectionPaint("EYEQ", new Color(160, 160, 255));
		customPlot.setSectionPaint("FCM", new Color(128, 128, 255 - 32));
		customPlot.setSectionPaint("MANUAL", new Color(96, 96, 255 - 64));
		customPlot.setSectionPaint("RADAR", new Color(64, 64, 255 - 96));
//		customPlot.setSectionPaint("MISSED", new Color(32, 32, 255 - 128));
//		customPlot.setSectionPaint("Six", new Color(0, 0, 255 - 144));

		customPlot.setNoDataMessage("No data available");
		customPlot.setExplodePercent("FCM", 0.20);
		
		return plot;
	}

}
