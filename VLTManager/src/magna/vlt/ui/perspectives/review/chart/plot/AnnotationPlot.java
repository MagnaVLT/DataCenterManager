package magna.vlt.ui.perspectives.review.chart.plot;

import java.awt.Color;

import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;

public class AnnotationPlot extends PlotConfigure {

	@Override
	public Plot configure(Plot plot) {
		PiePlot customPlot = (PiePlot) plot;
		
		customPlot.setSectionPaint("Not-Annotated", new Color(255, 102, 102));
		customPlot.setSectionPaint("Free-Annotated", new Color(102, 255, 255));
		
		customPlot.setSectionPaint("TSR_ANNOT_S_MISSED", new Color(160, 160, 255));
		customPlot.setSectionPaint("TSR_ANNOT_S_FALSE_DETECTED", new Color(128, 128, 255 - 32));
		customPlot.setSectionPaint("TSR_ANNOT_S_HOST_REL", new Color(96, 96, 255 - 64));
		customPlot.setSectionPaint("TSR_ANNOT_S_HOST_IRRREL", new Color(64, 64, 255 - 96));
		customPlot.setSectionPaint("TSR_ANNOT_S_EBD_ONTRUCK", new Color(32, 32, 255 - 128));
		customPlot.setSectionPaint("TSR_ANNOT_A_MISSED", new Color(0, 0, 255 - 144));
		
		customPlot.setSectionPaint("TSR_ANNOT_A_FALSE_DETECTED", new Color(51, 153, 255));
		customPlot.setSectionPaint("TSR_ANNOT_A_GEN_DETECTED", new Color(204, 135, 255));
		customPlot.setSectionPaint("TSR_ANNOT_W_MISSED", new Color(255, 204, 229 - 64));
		customPlot.setSectionPaint("TSR_ANNOT_W_FALSE_DETECTED", new Color(204, 255, 229 ));
		customPlot.setSectionPaint("TSR_ANNOT_W_HOST_REL", new Color(204, 255, 204));
		customPlot.setSectionPaint("TSR_ANNOT_W_HOST_IRRREL", new Color(255, 204, 204));
		

		customPlot.setNoDataMessage("No data available");
		customPlot.setExplodePercent("Not-Annotated", 0.20);
		return plot;
	}

}
