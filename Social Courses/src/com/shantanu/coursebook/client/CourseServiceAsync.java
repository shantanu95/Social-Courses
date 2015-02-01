package com.shantanu.coursebook.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CourseServiceAsync {

	void createCourse(String userId, CourseData cd, AsyncCallback<Void> callback);

	void getCourseList(String userId, Date d,
			AsyncCallback<ArrayList<CourseData>> callback);

	void deleteCourse(Long id, String userId, AsyncCallback<Void> callback);

	void getFriendList(String userId, AsyncCallback<ArrayList<String>> callback);

	void computeFreeTime(String u1, String u2, Date d,
			AsyncCallback<ArrayList<String>> callback);

}
