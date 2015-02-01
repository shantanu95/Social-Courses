package com.shantanu.coursebook.client;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@SuppressWarnings("serial")
@Entity
public class CourseData implements Serializable{

	public @Parent Key<MyUser> parent;
	public @Id Long id;
	
	public String name, course_code, indexNo, venue;
	public Date startdate;
	public int stTime_HH, stTime_MM, enTime_HH, enTime_MM;
	public int frequency;
	
}
