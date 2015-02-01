package com.shantanu.coursebook.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Button;

public class CourseItem extends Composite {

	public CourseItem(final CourseData cd, final CourseCreateListener ccl, boolean admin, final String userId) {
		
		FlowPanel flowPanel = new FlowPanel();
		initWidget(flowPanel);
		
		flowPanel.setStyleName("courseItem");
		
		HTML htmlCzDiscete = new HTML(cd.course_code + " - " + cd.name, true);
		flowPanel.add(htmlCzDiscete);
		htmlCzDiscete.setStyleName("title");
		
		FlowPanel flowPanel_1 = new FlowPanel();
		flowPanel.add(flowPanel_1);
		
		Button btnDelete = new Button("Delete");
		flowPanel_1.add(btnDelete);
		btnDelete.setStyleName("del");
		
		HTML htmlTime = new HTML("From " + cd.stTime_HH + ":" + padNum(cd.stTime_MM) 
				+ " to " + cd.enTime_HH + ":" + padNum(cd.enTime_MM), true);
		
		flowPanel.add(htmlTime);
		
		HTML htmlIndexNo = new HTML("Index No: " + cd.indexNo, true);
		flowPanel.add(htmlIndexNo);
		
		HTML htmlVenue = new HTML("Venue: " + cd.venue, true);
		flowPanel.add(htmlVenue);
		
		HTML htmlFrequency = new HTML("Every " + cd.frequency + " weeks");
		flowPanel.add(htmlFrequency);
		
		if(!admin)
			btnDelete.setVisible(false);
		
		btnDelete.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				CourseService.Util.getInstance().deleteCourse(cd.id, userId, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						Notifications.setMessage("Successfully deleted");
						CourseItem.this.removeFromParent();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
				ccl.onCourseCreated();
			}
		});
	}
	
	private String padNum(int i){
		if(i<10)
			return "0" + i;
		else
			return i + "";
	}

}
