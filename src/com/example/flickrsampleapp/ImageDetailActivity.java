package com.example.flickrsampleapp;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageDetailActivity extends Activity 
{
	protected ImageLoader loader;
	 
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
        
        NetworkImageView theImage = (NetworkImageView) findViewById(R.id.imageView1);
        //theImage.setLayoutParams(new GridView.LayoutParams(1000,800));
        theImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        theImage.setImageUrl(imageLink,loader);
        
        TextView imageTitle = (TextView) findViewById(R.id.textView1);
        imageTitle.setText(ImageTitle);
        
        TextView authorTitle = (TextView) findViewById(R.id.textView2);
        authorTitle.setText(AuthorName);
    }
}