package com.shantanu.coursebook.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@SuppressWarnings("serial")
@Entity
public class MyUser implements Serializable {
	
	public @Id String userId;
	
	public @Index String name;
	public @Index int year;
	public @Index String uniAddr;
	
	public List<String> friends = new ArrayList<String>();
	public List<String> frndReqReceived = new ArrayList<String>();
	public List<String> frndReqSent = new ArrayList<String>();
	

}
