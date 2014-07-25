package com.example.flickrsampleapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageDetailActivity extends Activity 
{
	protected ImageLoader loader;
	static String uri = "";
	RequestQueue mQueue;
	JsonObjectRequest mReq;
	JSONObject jsonObject;
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
    	String imageLink;
    	String ImageTitle;
    	String AuthorName;
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail);
        
        loader = VolleySingleton.getInstance(this).getImageLoader();
        
        Intent myIntent = getIntent();
        imageLink = myIntent.getStringExtra("Image");
        ImageTitle = myIntent.getStringExtra("Title");
        AuthorName = myIntent.getStringExtra("Owner");
        
        uri = "https://api.flickr.com/services/rest/?method=flickr.people.getInfo&api_key=7867448012c92463c5de082ba1853689&user_id="+AuthorName+"&format=json&nojsoncallback=1";
        
		mQueue = VolleySingleton.getInstance(this).getRequestQueue();
		
		mReq = new JsonObjectRequest(Request.Method.GET, uri,null,new Response.Listener<JSONObject>() 
		{
			@Override
			public void onResponse(JSONObject response) 
			{
				//Check for errors in API call
				try 
				{
					Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
					
					//Getting the JSONObject Offers which contains the offers
					JSONObject offerObject = response.getJSONObject("person");
					JSONObject offerObject1 = offerObject.getJSONObject("realname");
					String abc = (String) offerObject1.get("_content");
					
					TextView authorTitle = (TextView) findViewById(R.id.textView2);
			        authorTitle.setText(abc);
				} 
				catch (JSONException e) 
				{
					e.printStackTrace();
				}
			}
		},
		new Response.ErrorListener() 
		{

			@Override
			public void onErrorResponse(VolleyError error) 
			{
				Log.d("RAHUL","An error has occured with volley");
			}
		});
        
		mQueue.add(mReq);
        
        NetworkImageView theImage = (NetworkImageView) findViewById(R.id.imageView1);
        //theImage.setLayoutParams(new GridView.LayoutParams(1000,800));
        theImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        theImage.setImageUrl(imageLink,loader);
        
        TextView imageTitle = (TextView) findViewById(R.id.textView1);
        imageTitle.setText(ImageTitle);
    }
}