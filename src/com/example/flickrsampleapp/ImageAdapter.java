package com.example.flickrsampleapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class ImageAdapter extends BaseAdapter 
{
    private Context mContext;
    List<FlickrActualPhoto> mPhotosList = new ArrayList<FlickrActualPhoto>();
    String urlString = "http://www.flickr.com/photos";
    protected ImageLoader loader;
    Bitmap bitmap;
    
    public ImageAdapter(Context c,List<FlickrActualPhoto> photosList)
    {
        mContext = c;
        mPhotosList.addAll(photosList);
        loader = VolleySingleton.getInstance(c).getImageLoader();
    }

    public int getCount()
    {
        return mPhotosList.size();
    }

    public Object getItem(int position) 
    {
        return null;
    }

    public long getItemId(int position)
    {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	NetworkImageView imageView;
        if (convertView == null)
        {  // if it's not recycled, initialize some attributes
            imageView = new NetworkImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(1000,600));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1,1,1,1);
        } 
        else
        {
            imageView = (NetworkImageView) convertView;
        }
        ImageLoader.getImageListener(imageView, R.drawable.mario, R.drawable.mario);
        imageView.setImageUrl(mPhotosList.get(position).source,loader);
        return imageView;
    }
}