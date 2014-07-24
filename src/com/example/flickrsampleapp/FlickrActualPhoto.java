package com.example.flickrsampleapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlickrActualPhoto
{
	@SerializedName("label")
	@Expose
	public String label;
	
	@SerializedName("source")
	@Expose
	public String source;
}