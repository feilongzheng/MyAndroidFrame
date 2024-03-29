package com.lcworld.fitness.framework.cacheimage;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.lcworld.fitness.framework.contant.Constants;

/**
 * TODO 处理缓存中的图片
 */
public class BitmapMemoryCache {

	private static LruCache<String, Bitmap> mBitmapLruCache;
	private static ConcurrentHashMap<String, SoftReference<Bitmap>> mBitmapLevelTwoCache;

	private static BitmapMemoryCache mBitmapMemoryCache;

	/**
	 * 获取BitmapMemoryCache 实例
	 * 
	 * @param context
	 * @return
	 */
	public static BitmapMemoryCache getInstance(Context context) {
		if (null == mBitmapMemoryCache) {
			mBitmapMemoryCache = new BitmapMemoryCache(context);
		}
		return mBitmapMemoryCache;
	}

	private BitmapMemoryCache(Context context) {
		mBitmapLevelTwoCache = new ConcurrentHashMap<String, SoftReference<Bitmap>>(Constants.LEVEL_TWO_CACHE_CAPACITY);

		final int memClass = ((ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		final int cacheSize = 1024 * 1024 * memClass / 8;
		mBitmapLruCache = new LruCache<String, Bitmap>(cacheSize) {

			@Override
			protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
				super.entryRemoved(evicted, key, oldValue, newValue);
				mBitmapLevelTwoCache.put(key, new SoftReference<Bitmap>(oldValue));
			}

			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}

		};
	}

	/**
	 * 从缓存中获取图片
	 */
	public Bitmap getBitmapFromCache(String url) {
		Bitmap bitmap = null;
		// 先从LruCache缓存中获取
		if (mBitmapLruCache != null && !TextUtils.isEmpty(url)) {

			try {
				bitmap = mBitmapLruCache.get(url);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
		}

		if (bitmap == null && mBitmapLevelTwoCache != null && !TextUtils.isEmpty(url)) {
			// 如果LruCache中找不到，到二级缓存中找
			final SoftReference<Bitmap> bitmapReference = mBitmapLevelTwoCache.get(url);

			if (bitmapReference != null) {
				try {

					bitmap = bitmapReference.get();
					if (bitmap != null) {
						// 将图片移回硬缓存
						mBitmapLruCache.put(url, bitmap);
						mBitmapLevelTwoCache.remove(url);
					} else {
						mBitmapLevelTwoCache.remove(url);
					}
				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				}
			}
		}
		return bitmap;
	}

	/**
	 * 添加图片到缓存
	 * 
	 * @param url
	 * @param bitmap
	 */
	public void addBitmapToCache(String url, Bitmap bitmap) {
		if (mBitmapLruCache != null) {
			mBitmapLruCache.put(url, bitmap);
		}
	}

	/**
	 * 删除内存缓存中的图片
	 * 
	 * @param url
	 */
	public void deleteBitmapFromCache(String url) {
		mBitmapLruCache.remove(url);
		mBitmapLevelTwoCache.remove(url);
	}
}
