package com.shantanu.coursebook.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;

public class Notifications {
	private static Label lbl;
	
	static void setLabel(Label l){
		lbl=l;
		lbl.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				hide();
			}
		});
	}
	
	public static void setMessage(String s){
		lbl.setText(s);
		show();
	}
	
	private static void show(){
		lbl.setStyleName("visible");
	}
	
	public static void hide(){
		lbl.setStyleName("hidden");
	}
	
	public static void showError(){
		lbl.setText("An error has occured");
		show();
	}
}
