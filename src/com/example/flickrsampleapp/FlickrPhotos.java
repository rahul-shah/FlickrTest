package com.example.flickrsampleapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlickrPhotos
{
	@SerializedName("id")
	@Expose
	public String id;
	
	@SerializedName("owner")
	@Expose
	public String owner;
	
	@SerializedName("title")
	@Expose
	public String title;
}