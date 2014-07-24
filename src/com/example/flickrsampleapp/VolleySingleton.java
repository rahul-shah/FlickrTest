package com.example.flickrsampleapp;

import java.io.File;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton
{
	private static VolleySingleton mInstance = null;
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
	String zagzigDirectory = extStorageDirectory + "/Android/data/com.zagzig.mobile/cache";
	OutputStream outStream = null;
	File file = new File(zagzigDirectory); // Create the file structure
	
	private VolleySingleton(Context context)
	{
		mRequestQueue = Volley.newRequestQueue(context);
		mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache()
		{
			private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10 * 1024 * 1024); 
			public void putBitmap(String url, Bitmap bitmap) 
			{
				mCache.put(url, bitmap); 
			}
			public Bitmap getBitmap(String url) 
			{
				return mCache.get(url); 
			}
			
			/*
			private final DiskBasedCache mCache = new DiskBasedCache(file, 10 * 1024 * 1024);
			public void putBitmap(String url, Bitmap bitmap)
			{
				Entry entry = new Entry();
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] byteArray = stream.toByteArray();
				entry.data = byteArray;
				mCache.put(url, entry);
			}
			public Bitmap getBitmap(String url)
			{
				if (mCache.get(url) != null)
				{
					Entry entry = new Entry();
					entry.data = mCache.get(url).data;
					Bitmap bitmap = BitmapFactory.decodeByteArray(entry.data, 0, entry.data.length);
					
					Log.d("Toxos", "Have Cashed bitmap! " +  bitmap.toString());
					
					return bitmap;
				}
				return null;
			}
			*/
		});
	}

	public static VolleySingleton getInstance(Context context)
	{
		if (mInstance == null)
		{
			mInstance = new VolleySingleton(context);
		}
		return mInstance;
	}

	public RequestQueue getRequestQueue()
	{
		return this.mRequestQueue;
	}

	public ImageLoader getImageLoader()
	{
		return this.mImageLoader;
	}
}
