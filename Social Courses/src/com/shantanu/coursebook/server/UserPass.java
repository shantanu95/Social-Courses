package com.shantanu.coursebook.server;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class UserPass {

	@Id String userId;
	
	String password;
}
