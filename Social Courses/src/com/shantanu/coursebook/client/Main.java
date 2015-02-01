package com.shantanu.coursebook.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class Main implements EntryPoint {
	
	RootPanel container;

	static String userId;
	
	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		Label lblNotify=Label.wrap(DOM.getElementById("notify"));
		Notifications.setLabel(lblNotify);
		
		container = RootPanel.get("container");
		
		
		//Login code
		Button lg = Button.wrap(DOM.getElementById("loginButton"));
		final TextBox username = TextBox.wrap(DOM.getElementById("username"));
		final PasswordTextBox pass = PasswordTextBox.wrap(DOM.getElementById("password"));
		
		lg.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				LoginService.Util.getInstance().login(username.getText(), pass.getText(), new AsyncCallback<String>() {
					
					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						if(result.equals("OK")){
							Notifications.hide();
							RootPanel.get("logSignContainer").setVisible(false);
							Anchor.wrap(DOM.getElementById("courses")).addClickHandler(new ContentLoader(1));
							Anchor.wrap(DOM.getElementById("manageFriends")).addClickHandler(new ContentLoader(2));
							Anchor.wrap(DOM.getElementById("others")).addClickHandler(new ContentLoader(3));
							RootPanel.get("Nav").setStyleName("visible");
							userId = username.getText();
						}else{
							Notifications.setMessage("Username or password incorrect");
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});

		//Sign up code
		Button su = Button.wrap(DOM.getElementById("signUpBut"));
		final TextBox name = TextBox.wrap(DOM.getElementById("name"));
		final TextBox email = TextBox.wrap(DOM.getElementById("userId"));
		final TextBox year = TextBox.wrap(DOM.getElementById("year"));
		final PasswordTextBox passw = PasswordTextBox.wrap(DOM.getElementById("passwordSign"));
		
		su.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				LoginService.Util.getInstance().signUp(name.getText(), email.getText(), passw.getText(), Integer.parseInt(year.getText()), 
						new AsyncCallback<String>() {
					
					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						Notifications.setMessage("Sign up successful");
						name.setText("");
						email.setText("");
						year.setText("");
						passw.setText("");
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		
		
		
	}
	
	class ContentLoader implements ClickHandler{
		
		int id;
		public ContentLoader(int i) {
			// TODO Auto-generated constructor stub
			id = i;
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			Notifications.hide();
			container.clear();
			switch(id){
			case 1: container.add(new Courses(userId)); break;
			case 2: container.add(new ManageFriends(userId)); break;
			case 3: container.add(new OthersCourses(userId)); break;
			}
		}
		
	}

}
