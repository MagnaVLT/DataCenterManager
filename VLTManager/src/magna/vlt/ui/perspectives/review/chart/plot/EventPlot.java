package magna.vlt.ui.perspectives.review.chart.plot;

import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;

public class EventPlot extends PlotConfigure {

	@Override
	public Plot configure(Plot plot) {
		PiePlot customPlot = (PiePlot) plot;
		customPlot.setNoDataMessage("No data available");
		customPlot.setExplodePercent("UNCONFIRMED", 0.20);
		return plot;
	}
}