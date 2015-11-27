package com.lcworld.fitness.framework.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.mynet.FileRequestAgent;
import com.android.volley.mynet.RequestAgent;
import com.android.volley.toolbox.Volley;
import com.lcworld.fitness.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BaseApplication extends Application {
	/**
	 * 存放活动状态的(未被销毁)的Activity列表
	 */
	public static List<Activity> undestroyActivities = new ArrayList<Activity>();
	public static BaseApplication softApplication;
	public static ImageLoader imageLoader = null;
	public static DisplayImageOptions imageLoaderOptions = null;
	 
	private static BaseApplication instance;
	public LruCache lruCache;
	public Picasso picasso;
	private RequestQueue mRequestQueue;
	private RequestAgent requestAgen;
	
	
	
	
    public static BaseApplication getInstance() {
        return instance;
    }
	
    
	@Override
	public void onCreate() {
		super.onCreate();
		softApplication = this;

		initImageLoader();
		initPicasso();
	}


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getInstance());
        }
        return mRequestQueue;
    }

    public RequestAgent getRequestAgen() {
        if (requestAgen == null) {
            requestAgen = new RequestAgent();
        }
        return requestAgen;
    }

    private FileRequestAgent fileRequestAgen;

    public FileRequestAgent getFileRequestAgen() {
        if (fileRequestAgen == null) {
            fileRequestAgen = new FileRequestAgent();
        }
        return fileRequestAgen;
    }
    
	public void initPicasso() {
        Picasso.Builder builder = new Picasso.Builder(this);
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        final int memClass = manager.getMemoryClass();
        final int cacheSize = 1024 * 1024 * memClass / 8;
        lruCache = new LruCache(cacheSize);
        builder.memoryCache(lruCache);
        picasso = builder.build();
    }
	
	private void initImageLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
		.Builder(getApplicationContext())
		.threadPoolSize(5)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		// 		1.5 Mb
		.memoryCacheSize(1500000)
		.denyCacheImageMultipleSizesInMemory()
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);		
		
		imageLoaderOptions = new DisplayImageOptions
				.Builder()
		.showStubImage(R.drawable.empty_photo)
		.showImageForEmptyUri(R.drawable.empty_photo)
		.cacheInMemory()
		.cacheOnDisc()
		.build();
		
		imageLoader = ImageLoader.getInstance();
	}

	/**
	 * 退出应用
	 */
	public static void quit() {
		for (Activity activity : undestroyActivities) {
			if (null != activity) {
				activity.finish();
			}
		}
		undestroyActivities.clear();
	}




	

}
