package com.shantanu.coursebook.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	void login(String username, String password, AsyncCallback<String> callback);

	void signUp(String name, String uniAddr, String password, int year,
			AsyncCallback<String> callback);

}
