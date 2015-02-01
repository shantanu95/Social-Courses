package com.shantanu.coursebook.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.tractionsoftware.gwt.user.client.ui.UTCDateBox;
import com.tractionsoftware.gwt.user.client.ui.UTCDateTimeRangeController;
import com.tractionsoftware.gwt.user.client.ui.UTCTimeBox;

public class NewCourse extends DialogBox {

	private TextBox textBox;
	private TextBox textBox_1;
	private TextBox textBox_2;
	private DateBox dateBox;
	private ListBox comboBox_1;
	private UTCTimeBox startTime;
	private UTCTimeBox endTime;
	private TextBox tbvenue;

	public NewCourse(final String userId, final CourseCreateListener ccl) {
		setAnimationEnabled(true);
		setAutoHideEnabled(true);
		setGlassEnabled(true);
		setHTML("Add New Course");
		center();
		
		Grid grid = new Grid(8, 2);
		setWidget(grid);
		grid.setSize("100%", "100%");
		
		textBox = new TextBox();
		grid.setWidget(0, 1, textBox);
		
		HTML htmlCourseCode = new HTML("Course Code", true);
		grid.setWidget(0, 0, htmlCourseCode);
		
		HTML htmlCourseName = new HTML("Course Name", true);
		grid.setWidget(1, 0, htmlCourseName);
		
		textBox_1 = new TextBox();
		grid.setWidget(1, 1, textBox_1);
		
		HTML htmlCourseIndex = new HTML("Course Index", true);
		grid.setWidget(2, 0, htmlCourseIndex);
		
		textBox_2 = new TextBox();
		grid.setWidget(2, 1, textBox_2);
		
		HTML htmlDay = new HTML("Start Date", true);
		grid.setWidget(3, 0, htmlDay);
		
		dateBox = new DateBox();
		dateBox.setFormat(new DefaultFormat(DateTimeFormat.getFormat("dd MMM yyyy")));
		grid.setWidget(3, 1, dateBox);
		
		HTML htmlTime = new HTML("Time", true);
		grid.setWidget(4, 0, htmlTime);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		startTime = new UTCTimeBox(DateTimeFormat.getFormat("HH:mm"));
		endTime = new UTCTimeBox(DateTimeFormat.getFormat("HH:mm"));
		verticalPanel.add(startTime);
		verticalPanel.add(endTime);
		new UTCDateTimeRangeController(new UTCDateBox(), startTime, new UTCDateBox(), endTime);
		grid.setWidget(4, 1, verticalPanel);
		
		HTML htmlFrequency = new HTML("Frequency", true);
		grid.setWidget(5, 0, htmlFrequency);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		grid.setWidget(5, 1, horizontalPanel);
		
		HTML htmlEvery = new HTML("Every", true);
		horizontalPanel.add(htmlEvery);
		
		comboBox_1 = new ListBox();
		horizontalPanel.add(comboBox_1);
		for(int i=1; i<=5; i++)
			comboBox_1.insertItem(""+i, i-1);
		
		HTML htmlWeeks = new HTML("weeks", true);
		horizontalPanel.add(htmlWeeks);
		
		HTML htmlVenue = new HTML("Venue");
		grid.setWidget(6, 0, htmlVenue);
		
		tbvenue = new TextBox();
		grid.setWidget(6, 1, tbvenue);
		
		Button btnCreate = new Button("Create");
		grid.setWidget(7, 1, btnCreate);
		
		btnCreate.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				CourseData cd = createDataObject();
				hide();
				CourseService.Util.getInstance().createCourse(userId, cd, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						ccl.onCourseCreated();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		
		startTime.setValue(UTCTimeBox.getValueForNextHour(), true);
	}
	
	CourseData createDataObject(){
		CourseData cd = new CourseData();
		
		cd.course_code = textBox.getText();
		cd.name = textBox_1.getText();
		cd.indexNo = textBox_2.getText();
		cd.startdate = dateBox.getValue();
		String ar[] = startTime.getText().split(":");
		cd.stTime_HH = Integer.parseInt(ar[0]);
		cd.stTime_MM = Integer.parseInt(ar[1].split(" ")[0]);
		String ar1[] = endTime.getText().split(":");
		cd.enTime_HH = Integer.parseInt(ar1[0]);
		cd.enTime_MM = Integer.parseInt(ar1[1].split(" ")[0]);
		cd.frequency = comboBox_1.getSelectedIndex() + 1;
		cd.venue = tbvenue.getText();
		return cd;
	}

}
