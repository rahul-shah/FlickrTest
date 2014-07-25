package com.example.flickrsampleapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfile
{
	@SerializedName("realname")
	@Expose
	public String realname;
	
	@SerializedName("username")
	@Expose
	public String username;

}