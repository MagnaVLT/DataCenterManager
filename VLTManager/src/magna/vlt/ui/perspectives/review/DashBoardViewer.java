package magna.vlt.ui.perspectives.review;

import magna.vlt.ui.perspectives.review.chart.BoadRetriever;
import magna.vlt.ui.perspectives.review.chart.ZunaChartFactory;
import magna.vlt.ui.perspectives.review.chart.plot.AnnotationPlot;
import magna.vlt.ui.perspectives.review.chart.plot.CategoryPlot;
import magna.vlt.ui.perspectives.review.chart.plot.EventPlot;
import magna.vlt.ui.perspectives.review.chart.plot.StatusPlot;
import magna.vlt.ui.perspectives.review.chart.query.QueryEvent;
import magna.vlt.ui.perspectives.review.chart.query.QueryEventAnnotation;
import magna.vlt.ui.perspectives.review.chart.query.QueryEventCategory;
import magna.vlt.ui.perspectives.review.chart.query.QueryEventStatus;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

public class DashBoardViewer extends ViewPart {

	private ZunaChartFactory factory = new ZunaChartFactory();
	private ChartComposite eventChart;
	private ChartComposite statusChart;
	private ChartComposite categoryChart;
	private ChartComposite annotationChart;
	public final static String ID = "magna.vlt.ui.perspectives.review.DashBoardViewer";
	private String projectID;
	private Text text;
	
	public DashBoardViewer() {

	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(4, true));
				new Label(parent, SWT.NONE);
				
				Label label = new Label(parent, SWT.NONE);
				label.setText(" ");
				new Label(parent, SWT.NONE);
				new Label(parent, SWT.NONE);
		
				Label lblStatus = new Label(parent, SWT.NONE);
				lblStatus.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
				lblStatus.setText("Events");
		
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		lblNewLabel.setText("Status");
		
		eventChart = new ChartComposite(parent, SWT.NONE, this.createEventChart(parent), true);
		GridData gd_eventChart = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_eventChart.widthHint = 0;
		eventChart.setLayoutData(gd_eventChart);
		
		statusChart = new ChartComposite(parent, SWT.NONE, this.createStatusChart(parent), true);
		GridData gd_statusChart = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_statusChart.heightHint = 0;
		gd_statusChart.widthHint = 0;
		statusChart.setLayoutData(gd_statusChart);
		new Label(parent, SWT.NONE);
		
		
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		
		Label lblNewLabel_1 = new Label(parent, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		lblNewLabel_1.setText("Category");
		
		Label lblNewLabel_2 = new Label(parent, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		lblNewLabel_2.setText("Annotation");
		categoryChart = new ChartComposite(parent, SWT.NONE, this.createCategoryChart(parent), true);
		GridData gd_categoryChart = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_categoryChart.heightHint = 0;
		gd_categoryChart.widthHint = 0;
		categoryChart.setLayoutData(gd_categoryChart);
		
		annotationChart = new ChartComposite(parent, SWT.NONE, this.createAnnotationChart(parent), true);
		GridData gd_annotationChart = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_annotationChart.widthHint = 0;
		gd_annotationChart.heightHint = 0;
		annotationChart.setLayoutData(gd_annotationChart);
		annotationChart.setDisplayToolTips(true);
		new Label(parent, SWT.NONE);
		
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		text = new Text(parent, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Button btnNewButton_1 = new Button(parent, SWT.NONE);
		btnNewButton_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnNewButton_1.setText("Browse");
		
		Button btnNewButton = new Button(parent, SWT.NONE);
		btnNewButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnNewButton.setText("Export");
		
		addListener();
	}
	
	public void refresh(String eventID){
		
		if(projectID != null && !projectID.equals("")){

			String eventName = new QueryEvent().getEventName(eventID);
			((DefaultPieDataset) ZunaChartFactory.chartList.get(ZunaChartFactory.STATUS)).clear();
			new QueryEventStatus().updateRecordByEvent(((DefaultPieDataset) ZunaChartFactory.chartList.get(ZunaChartFactory.STATUS)), projectID, eventID);
			statusChart.getChart().setTitle(eventName + " Event");
			statusChart.getChart().fireChartChanged();
			
			((DefaultPieDataset) ZunaChartFactory.chartList.get(ZunaChartFactory.ANNOTATION)).clear();
			new QueryEventAnnotation().updateRecordByEvent(((DefaultPieDataset) ZunaChartFactory.chartList.get(ZunaChartFactory.ANNOTATION)), projectID, eventID);
			annotationChart.getChart().setTitle(eventName + " Event");
			annotationChart.getChart().fireChartChanged();
			
			((DefaultPieDataset) ZunaChartFactory.chartList.get(ZunaChartFactory.CATEGORY)).clear();
			new QueryEventCategory().updateRecordByEvent(((DefaultPieDataset) ZunaChartFactory.chartList.get(ZunaChartFactory.CATEGORY)), projectID, eventID);
			categoryChart.getChart().setTitle(eventName + " Event");
			categoryChart.getChart().fireChartChanged();
		}
	}
	
	public void refresh(String projectID, String featureID){
		
		this.projectID = projectID;
		String featureName = new QueryEvent().getFeatureName(featureID);
		((DefaultPieDataset) ZunaChartFactory.chartList.get(ZunaChartFactory.EVENT)).clear();
		new QueryEvent().updateRecordByFeature(((DefaultPieDataset) ZunaChartFactory.chartList.get(ZunaChartFactory.EVENT)), projectID, featureID);
		eventChart.getChart().setTitle( featureName + " Feature");
		eventChart.getChart().fireChartChanged();
		
		((DefaultPieDataset) ZunaChartFactory.chartList.get(ZunaChartFactory.STATUS)).clear();
		new QueryEventStatus().updateRecordByFeature(((DefaultPieDataset) ZunaChartFactory.chartList.get(ZunaChartFactory.STATUS)), projectID, featureID);
		statusChart.getChart().setTitle( featureName + "Feature");
		statusChart.getChart().fireChartChanged();
		
		((DefaultPieDataset) ZunaChartFactory.chartList.get(ZunaChartFactory.CATEGORY)).clear();
		new QueryEventCategory().updateRecordByFeature(((DefaultPieDataset) ZunaChartFactory.chartList.get(ZunaChartFactory.CATEGORY)), projectID, featureID);
		categoryChart.getChart().setTitle( featureName + "Feature");
		categoryChart.getChart().fireChartChanged();
		
		((DefaultPieDataset) ZunaChartFactory.chartList.get(ZunaChartFactory.ANNOTATION)).clear();
		new QueryEventAnnotation().updateRecordByFeature(((DefaultPieDataset) ZunaChartFactory.chartList.get(ZunaChartFactory.ANNOTATION)), projectID, featureID);
		annotationChart.getChart().setTitle( featureName + "Feature");
		annotationChart.getChart().fireChartChanged();
	}
	
	
	private void addListener(){

		eventChart.addChartMouseListener(new ChartMouseListener(){

			@Override
			public void chartMouseClicked(ChartMouseEvent arg0) {
				// TODO Auto-generated method stub
				ChartEntity entity = arg0.getEntity();
				if ((entity != null) && (entity instanceof PieSectionEntity)) {
					PieSectionEntity ent = (PieSectionEntity) entity;
					int sindex = ent.getSectionIndex();
					String eventName = ent.getDataset().getKey(sindex).toString();
					String id = new BoadRetriever().getEventID(eventName);
					refresh(id);
				}
				
			}

			@Override
			public void chartMouseMoved(ChartMouseEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	
	private JFreeChart createEventChart(Composite parent){
		return factory.createChart(parent, ZunaChartFactory.EVENT, ZunaChartFactory.pie, new QueryEvent(), new EventPlot());
	}
	
	private JFreeChart createStatusChart(Composite parent){
		return factory.createChart(parent, ZunaChartFactory.STATUS, ZunaChartFactory.pie, new QueryEventStatus(), new StatusPlot());
	}
	
	private JFreeChart createCategoryChart(Composite parent){
		return factory.createChart(parent, ZunaChartFactory.CATEGORY, ZunaChartFactory.pie, new QueryEventCategory(), new CategoryPlot());
	}
	
	private JFreeChart createAnnotationChart(Composite parent){
		return factory.createChart(parent, ZunaChartFactory.ANNOTATION, ZunaChartFactory.pie, new QueryEventAnnotation(), new AnnotationPlot());
	}
	

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
