package com.shantanu.coursebook.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class ManageFriends extends Composite {

	private FlexTable flexTable_1;
	private FlexTable flexTable;
	private FlexTable flexTable_2;
	private String userId;
	private TextBox textBox;

	public ManageFriends(final String userId) {
		this.userId = userId;
		FlowPanel flowPanel = new FlowPanel();
		initWidget(flowPanel);
		
		FlowPanel flowPanel_1 = new FlowPanel();
		flowPanel.add(flowPanel_1);
		
		textBox = new TextBox();
		flowPanel_1.add(textBox);
		
		Button btnSearch = new Button("Search");
		flowPanel_1.add(btnSearch);
		
		final FlowPanel flowPanel_5 = new FlowPanel();
		flowPanel.add(flowPanel_5);
		final FlexTable searchFT = new FlexTable();
		searchFT.setStyleName("frndTable");
		
		btnSearch.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Notifications.setMessage("Searching...");
				FriendsService.Util.getInstance().getSearch(userId, textBox.getText(), new AsyncCallback<ArrayList<SearchedUser>>() {
					
					@Override
					public void onSuccess(ArrayList<SearchedUser> result) {
						// TODO Auto-generated method stub
						Notifications.hide();
						searchFT.removeAllRows();
						int i = 0;
						for(SearchedUser su : result){
							searchFT.setWidget(i, 0, new HTML(su.name));
							switch(su.type){
							case 1:
								searchFT.setWidget(i, 1, new HTML("Friends")); break;
							case 2:
								searchFT.setWidget(i, 1, new HTML("Friend request recieved")); break;
							case 3:
								searchFT.setWidget(i, 1, new HTML("Friend request sent")); break;
							case 4:
								searchFT.setWidget(i, 1, new MyButton(su.userId, 5, i, "Send request")); break;
							}
							searchFT.getWidget(i, 1).setStyleName("status");
							i++;
						}
						flowPanel_5.setVisible(true);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		
		flowPanel_5.setVisible(false);
		
		Label lblSearchResults = new Label("Search results");
		flowPanel_5.add(lblSearchResults);
		
		flowPanel_5.add(searchFT);
		
		FlowPanel flowPanel_2 = new FlowPanel();
		flowPanel.add(flowPanel_2);
		
		Label lblFriendRequestsReceived = new Label("Friend requests received");
		flowPanel_2.add(lblFriendRequestsReceived);
		
		flexTable = new FlexTable();
		flexTable.setStyleName("frndTable");
		flowPanel_2.add(flexTable);
		
		FlowPanel flowPanel_3 = new FlowPanel();
		flowPanel.add(flowPanel_3);
		
		Label lblFriendRequestsSent = new Label("Friend requests sent");
		flowPanel_3.add(lblFriendRequestsSent);
		
		flexTable_1 = new FlexTable();
		flexTable_1.setStyleName("frndTable");
		flowPanel_3.add(flexTable_1);
		
		FlowPanel flowPanel_4 = new FlowPanel();
		flowPanel.add(flowPanel_4);
		
		Label lblFriends = new Label("Friends");
		flowPanel_4.add(lblFriends);
		
		flexTable_2 = new FlexTable();
		flexTable_2.setStyleName("frndTable");
		flowPanel_4.add(flexTable_2);
		
		FriendsService.Util.getInstance().userFetch(userId, new AsyncCallback<MyUser>() {
			
			@Override
			public void onSuccess(MyUser result) {
				// TODO Auto-generated method stub
				int i = 0;
				for(String s : result.frndReqReceived){
					flexTable.setWidget(i, 0, new HTML(s));
					flexTable.setWidget(i, 1, new MyButton(s, 1, i, "Accept"));
					flexTable.setWidget(i, 2, new MyButton(s, 2, i, "Reject"));
					i++;
				}
				i = 0;
				for(String s : result.frndReqSent){
					flexTable_1.setWidget(i, 0, new HTML(s));
					flexTable_1.setWidget(i, 1, new MyButton(s, 3, i, "Cancel Request"));
					i++;
				}
				i = 0;
				for(String s : result.friends){
					flexTable_2.setWidget(i, 0, new HTML(s));
					flexTable_2.setWidget(i, 1, new MyButton(s, 4, i, "Unfriend"));
					i++;	
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	MyClickHandler mClickHandler = new MyClickHandler();
	
	class MyButton extends Button{
		int type; //1 - accept, 2 - reject, 3 - cancel sent req, 4 - unfriend
		String userId;
		int index;
		
		public MyButton(String u, int i, int i2, String lbl) {
			// TODO Auto-generated constructor stub
			userId = u;
			type = i;
			index = i2;
			this.setText(lbl);
			addClickHandler(mClickHandler);
		}
	}
	
	class MyClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			MyButton obj = (MyButton) event.getSource();
			switch(obj.type){
			case 1:
				FriendsService.Util.getInstance().accept(userId, obj.userId, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						reloadPage();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
				break;
			
			case 2:
				FriendsService.Util.getInstance().reject(userId, obj.userId, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						reloadPage();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						reloadPage();
					}
				});
				break;
				
			case 3:
				FriendsService.Util.getInstance().cancel(userId, obj.userId, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						reloadPage();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
				break;
				
			case 4:
				FriendsService.Util.getInstance().unfriend(userId, obj.userId, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						reloadPage();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
				break;
				
			case 5:
				FriendsService.Util.getInstance().sendRequest(userId, obj.userId, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						reloadPage();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
			}
			
		}
		
		private void reloadPage(){
			Panel p = (Panel) ManageFriends.this.getParent();
			ManageFriends.this.removeFromParent();
			p.add(new ManageFriends(userId));
			
		}
		
	}
	
	
}
