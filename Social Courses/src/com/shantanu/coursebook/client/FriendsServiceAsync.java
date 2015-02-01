package com.shantanu.coursebook.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FriendsServiceAsync {
	
	void userFetch(String userId, AsyncCallback<MyUser> callback);

	void accept(String first, String second, AsyncCallback<Void> callback);

	void cancel(String first, String second, AsyncCallback<Void> callback);

	void reject(String first, String second, AsyncCallback<Void> callback);

	void unfriend(String first, String second, AsyncCallback<Void> callback);

	void getSearch(String userId, String in, AsyncCallback<ArrayList<SearchedUser>> callback);

	void sendRequest(String first, String second, AsyncCallback<Void> callback);

}
