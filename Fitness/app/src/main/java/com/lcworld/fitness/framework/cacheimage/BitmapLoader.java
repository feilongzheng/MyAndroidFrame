package com.lcworld.fitness.framework.cacheimage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.lcworld.fitness.framework.contant.Constants;

/**
 * TODO 加载图片
 */
public class BitmapLoader {

	private static final int DOWNLOAD_STATE_DONT_NEED_YET_OR_SUCESS = 1000110;
	private static final int DOWNLOAD_STATE_DOWNLOADING = 1000111;
	private static final int DOWNLOAD_STATE_LOAD_FAILED = 1000112;

	private static final int DOWN_LOAD_IMAGE_COMPLETE = 0;// 从网络上下载图片完成
	private ExecutorService executorService = Executors.newFixedThreadPool(Constants.MAX_THREADS);
	private BitmapMemoryCache mBitmapMemoryCache; // 内存缓存图片
	private BitmapFileCache mBitmapFileCache; // 文件缓存图片
	private static BitmapLoader mBitmapLoader = null;

	public static boolean isCanLoadFromNet = true;
	private int currentDownLoadState = DOWNLOAD_STATE_DONT_NEED_YET_OR_SUCESS;

	public BitmapLoader(Context context) {
		mBitmapMemoryCache = BitmapMemoryCache.getInstance(context);
		mBitmapFileCache = BitmapFileCache.getInstance();
	}

	public static BitmapLoader getInstance(Context context) {
		if (null == mBitmapLoader) {
			mBitmapLoader = new BitmapLoader(context);
		}
		return mBitmapLoader;
	}

	/**
	 * 把要下载的图片添加到任务队列中 从缓存，外存或网络上加载到图片
	 * 
	 * @param url
	 * @param img
	 * @param callback
	 */
	public Bitmap loadBitmap(final String url, final OnBitmapLoadFinishedListener onBitmapLoadFinishedListener) {
		Bitmap bitmap;
		// 从内存缓存中获取图片
		bitmap = mBitmapMemoryCache.getBitmapFromCache(url);
		if (bitmap != null) {
			Util.log("从缓存里面获取图片");
			return bitmap;
		} else {
			Util.log("缓存里面没有图片----");
		}
		// 取外部存储上的图片
		if (null == bitmap) {
			bitmap = loadFromFileCache(url);
		}

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case DOWN_LOAD_IMAGE_COMPLETE:
					final Bitmap bitmapFromFileCache = (Bitmap) msg.obj;
					if (null != onBitmapLoadFinishedListener) {
						onBitmapLoadFinishedListener.onBitmapLoadFinished(bitmapFromFileCache, url);
					}
					break;
				}
			}
		};

		// 外部存储获取不到则从网络获取
		if (isCanLoadFromNet == true && null == bitmap) {
			executorService.execute(new Runnable() {

				@Override
				public void run() {
					Bitmap downloadBitmap = BitmapHttpHelper.downloadBitmap(url);
					if (null != downloadBitmap) {
						handler.sendMessage(handler.obtainMessage(DOWN_LOAD_IMAGE_COMPLETE, downloadBitmap));
						// 如果文件缓存不为空就把文件缓存添加到内存缓存
						mBitmapMemoryCache.addBitmapToCache(url, downloadBitmap);
						setCurrentDownLoadState(DOWNLOAD_STATE_DONT_NEED_YET_OR_SUCESS);
					} else {
						setCurrentDownLoadState(DOWNLOAD_STATE_LOAD_FAILED);
					}
				}
			});
			setCurrentDownLoadState(DOWNLOAD_STATE_DOWNLOADING);
		}
		return bitmap;
	}

	/**
	 * 从sd获取图片，并且如果可以获取到就把它加入缓存中
	 * 
	 * @param url
	 * @return
	 */
	private Bitmap loadFromFileCache(final String url) {
		Bitmap bitmapFromSDCard = mBitmapFileCache.getImage(url);
		if (bitmapFromSDCard != null) {
			Util.log("从SD卡里面获取图片");
			// 如果文件缓存不为空就把文件缓存添加到内存缓存
			mBitmapMemoryCache.addBitmapToCache(url, bitmapFromSDCard);
		} else {
			Util.log("SD卡里面没有图片----");
		}
		return bitmapFromSDCard;
	}

	/**
	 * 停止网络下载
	 */
	public void stopLoadFromNetWork() {
		if (null != executorService) {
			executorService.shutdown();
		}
	}

	private synchronized void setCurrentDownLoadState(int downLoadStateCode) {
		currentDownLoadState = downLoadStateCode;
	}

	public synchronized int getCurrentDownLoadState() {
		return currentDownLoadState;
	}

	/**
	 * 删除缓存文件
	 * 
	 * @param url
	 */
	public void deleteImageCache(String url) {
		// 从内存中移除
		mBitmapMemoryCache.deleteBitmapFromCache(url);
		// 从文件中移除
		mBitmapFileCache.deleteImage(url);
	}

	public interface OnBitmapLoadFinishedListener {
		public void onBitmapLoadFinished(Bitmap imageBitmap, String imageUrl);
	}
}
