package com.shantanu.coursebook.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class OthersCourses extends Composite {

	String userId, userId2;
	private VerticalPanel verticalPanel;
	private FlowPanel flowPanel_4;
	private FlowPanel flowPanel;
	
	public OthersCourses(String userId) {
		this.userId = userId;
		flowPanel = new FlowPanel();
		initWidget(flowPanel);
		
		final FlowPanel flowPanel_1 = new FlowPanel();
		flowPanel.add(flowPanel_1);
		
		HTML htmlSelectFriend = new HTML("Select friend: ", true);
		htmlSelectFriend.setStyleName("coursesLbl");
		flowPanel_1.add(htmlSelectFriend);
		
		CourseService.Util.getInstance().getFriendList(userId, new AsyncCallback<ArrayList<String>>() {
			
			@Override
			public void onSuccess(ArrayList<String> result) {
				// TODO Auto-generated method stub
				MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
				oracle.addAll(result);
			
				final SuggestBox 	suggestBox = new SuggestBox(oracle);
				suggestBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
					
					@Override
					public void onSelection(SelectionEvent<Suggestion> event) {
						// TODO Auto-generated method stub
						userId2 = suggestBox.getText();
						genCourseList(new Date(), false);
					}
				});
				flowPanel_1.add(suggestBox);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		FlowPanel flowPanel_2 = new FlowPanel();
		flowPanel.add(flowPanel_2);
		
		HTML htmlClasses = new HTML("Classes ", true);
		htmlClasses.setStyleName("coursesLbl");
		flowPanel_2.add(htmlClasses);
		flowPanel_2.setStyleName("classesOther");
		
		final ListBox comboBox = new ListBox();
		flowPanel_2.add(comboBox);
		comboBox.addItem("Today");
		comboBox.addItem("Custom date");
		comboBox.setStyleName("comboOther");
		
		comboBox.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
				switch(((ListBox)event.getSource()).getSelectedIndex()){
				case 0:
					genCourseList(new Date(), true); break;
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
					pp.showRelativeTo(comboBox);
				}
			}
		});
		
		FlowPanel flowPanel_3 = new FlowPanel();
		flowPanel.add(flowPanel_3);
		
		HTML htmlCommonFreeTime = new HTML("Common free time on the same day: ", true);
		flowPanel_3.add(htmlCommonFreeTime);
		htmlCommonFreeTime.setStyleName("titleOther");
		
		flowPanel_4 = new FlowPanel();
		flowPanel.add(flowPanel_4);
		
		verticalPanel = new VerticalPanel();
		flowPanel.add(verticalPanel);
		flowPanel_4.setStyleName("freeOther");
		
	}
	
	void genCourseList(final Date d, boolean clear){
		if(clear)
			verticalPanel.clear();
		
		CourseService.Util.getInstance().getCourseList(userId2, d, new AsyncCallback<ArrayList<CourseData>>() {
			
			@Override
			public void onSuccess(ArrayList<CourseData> result) {
				// TODO Auto-generated method stub
				for(CourseData cd : result){
					verticalPanel.add(new CourseItem(cd, new CourseCreateListener() {
						
						@Override
						public void onCourseCreated() {
							// TODO Auto-generated method stub
							
						}
					}, false, userId));
				}
				CourseService.Util.getInstance().computeFreeTime(userId, userId2, d, new AsyncCallback<ArrayList<String>>() {
					
					@Override
					public void onSuccess(ArrayList<String> result) {
						// TODO Auto-generated method stub
						flowPanel_4.clear();
						for(String s:result)
							flowPanel_4.add(new HTML(s));
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}


}
