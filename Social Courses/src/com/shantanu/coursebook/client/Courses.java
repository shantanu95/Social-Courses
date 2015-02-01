package com.shantanu.coursebook.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class Courses extends Composite {

	private VerticalPanel verticalPanel;
	String userId;

	public Courses(final String userId) {
		this.userId = userId;
		FlowPanel flowPanel = new FlowPanel();
		initWidget(flowPanel);
		
		FlowPanel flowPanel_3 = new FlowPanel();
		flowPanel.add(flowPanel_3);
		
		Button btnAddNewCourse = new Button("Add New Course");
		btnAddNewCourse.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				new NewCourse(userId, new CourseCreateListener() {
					
					@Override
					public void onCourseCreated() {
						// TODO Auto-generated method stub
						genCourseList(new Date(), true);
					}
				}).show();
			}
		});
		flowPanel_3.add(btnAddNewCourse);
		
		FlowPanel flowPanel_2 = new FlowPanel();
		flowPanel.add(flowPanel_2);
		
		flowPanel_2.setStyleName("head1");
		
		HTML htmlMyCourses = new HTML("My classes: ", true);
		flowPanel_2.add(htmlMyCourses);
		htmlMyCourses.setStyleName("coursesLbl");
		
		final ListBox comboBox_1 = new ListBox();
		comboBox_1.insertItem("Today", 0);
		comboBox_1.insertItem("Custom date", 1);
		flowPanel_2.add(comboBox_1);
		
		comboBox_1.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
				switch(((ListBox)event.getSource()).getSelectedIndex()){
				case 0:
					Date d = new Date();
					genCourseList(d, true); break;
				case 1: 
					final PopupPanel pp = new PopupPanel(true, false);
					DatePicker dp = new DatePicker();
					pp.setWidget(dp);
					dp.addValueChangeHandler(new ValueChangeHandler<Date>() {
						
						@Override
						public void onValueChange(ValueChangeEvent<Date> event) {
							// TODO Auto-generated method stub
							genCourseList(event.getValue(), true);
							pp.removeFromParent();
						}
					});
					pp.showRelativeTo(comboBox_1);
				}
			}
		});
		
		verticalPanel = new VerticalPanel();
		flowPanel.add(verticalPanel);
		
		genCourseList(new Date(), false);
		
	}
	
	void genCourseList(Date d, boolean clear){
		if(clear)
			verticalPanel.clear();
		CourseService.Util.getInstance().getCourseList(userId, d, new AsyncCallback<ArrayList<CourseData>>() {
			
			@Override
			public void onSuccess(ArrayList<CourseData> result) {
				// TODO Auto-generated method stub
				for(CourseData cd : result){
					verticalPanel.add(new CourseItem(cd, new CourseCreateListener() {
						
						@Override
						public void onCourseCreated() {
							// TODO Auto-generated method stub
							
						}
					}, true, userId));
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
