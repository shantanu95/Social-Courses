package com.shantanu.coursebook.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.googlecode.objectify.ObjectifyService;
import com.shantanu.coursebook.client.CourseData;
import com.shantanu.coursebook.client.MyUser;

public class MyLifecycleListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		ObjectifyService.register(MyUser.class);
		ObjectifyService.register(UserPass.class);
		ObjectifyService.register(CourseData.class);
	}

}
