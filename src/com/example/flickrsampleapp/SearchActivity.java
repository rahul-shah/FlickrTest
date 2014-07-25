package com.example.flickrsampleapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SearchActivity extends Activity 
{
	static String uri = "";
	RequestQueue mQueue;
	JsonObjectRequest mReq;
	JSONObject jsonObject;
	List<FlickrPhotos> PhotosList = new ArrayList<FlickrPhotos>();
	List<FlickrActualPhoto> ActualPhotosList = new ArrayList<FlickrActualPhoto>();
	GridView gridview;
	Button mBtnSearch;
	EditText mQuery;
	ProgressBar apiProgress;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        //EditText searchText = (EditText)findViewById(R.id.input_search_query);
        
        gridview = (GridView) findViewById(R.id.gridview);
        
        mBtnSearch = (Button) findViewById(R.id.btn_search);
        
        mQuery = (EditText) findViewById(R.id.input_search_query);
        
        apiProgress = (ProgressBar) findViewById(R.id.progressBar1);
        
        mBtnSearch.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Log.d("SearchFragment", "Search Clikced");
				gridview.setAdapter(null);
				PhotosList.clear();
				ActualPhotosList.clear();
				String searchString = mQuery.getText().toString();
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mQuery.getWindowToken(), 0);
				apiProgress.setProgress(TRIM_MEMORY_RUNNING_CRITICAL);
				InitiateSearch(searchString);
			}
		});
    }
    
    public void InitiateSearch(String searchQuery)
    {
    	uri = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=1a3a3ca0ceeb8f7934475f721d4dc2c4&text="+searchQuery+"&format=json&nojsoncallback=1";
        
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
					JSONObject offerObject = response.getJSONObject("photos");
					
					//Due to a access string added to the object which can't be removed now as the website is using it, getting the offers array using this method
					JSONArray offersJsonArray = offerObject.getJSONArray("photo");
					
					for(int i=0;i<offersJsonArray.length();i++)
					{
						JSONObject myObj = offersJsonArray.getJSONObject(i);
						FlickrPhotos fOffer = gson.fromJson(myObj.toString(),FlickrPhotos.class);
						PhotosList.add(fOffer);
						AfterGettingResponse(fOffer.id);
					}
				
			        //gridview.setAdapter(new ImageAdapter(SearchActivity.this,PhotosList));
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
    }
    public void AfterGettingResponse(String picId)
    {
    	uri = "https://api.flickr.com/services/rest/?method=flickr.photos.getSizes&api_key=1a3a3ca0ceeb8f7934475f721d4dc2c4&photo_id=" + picId + "&format=json&nojsoncallback=1";
    	
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
					JSONObject offerObject = response.getJSONObject("sizes");
					
					//Due to a access string added to the object which can't be removed now as the website is using it, getting the offers array using this method
					JSONArray offersJsonArray = offerObject.getJSONArray("size");
					
					for(int i=0;i<offersJsonArray.length();i++)
					{
						JSONObject myObj = offersJsonArray.getJSONObject(i);
						FlickrActualPhoto fOffer = gson.fromJson(myObj.toString(),FlickrActualPhoto.class);
						if(fOffer.label.equals("Large"))
						{
							ActualPhotosList.add(fOffer);
						}
					}
					
					apiProgress.setProgress(TRIM_MEMORY_COMPLETE);
			        gridview.setAdapter(new ImageAdapter(SearchActivity.this,ActualPhotosList));
			        
			        gridview.setOnItemClickListener(new OnItemClickListener()
			        {
			            @Override
			            public void onItemClick(AdapterView<?> parent, View v,int position, long id) 
			            {
			            	Intent intent = new Intent("com.example.flickrsampleapp.ImageDetailActivity");
			            	intent.putExtra("Image",ActualPhotosList.get(position).source);
			            	intent.putExtra("Title",PhotosList.get(position).title);
			            	intent.putExtra("Owner",PhotosList.get(position).owner);
			            	startActivity(intent);
			            }
			        });
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
    }
}