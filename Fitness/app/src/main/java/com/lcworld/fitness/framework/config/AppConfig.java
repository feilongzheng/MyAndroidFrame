package com.lcworld.fitness.framework.config;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;

import com.lcworld.fitness.framework.bean.AppInfo;
import com.lcworld.fitness.framework.contant.Constants;

/**
 * TODO 操作AppInfo
 */
public final class AppConfig {
	private static AppInfo appInfo;

	private AppConfig() {

	}

	/**
	 * 获取AppInfo配置信息
	 * 
	 * @param context
	 * @return AppInfo的bean
	 */
	public static AppInfo getAppConfigInfo(Context context) {
		AssetManager am = context.getAssets();
		try {
			InputStream is = am.open(Constants.APP_CONFIG_FILE_NAME);
			return parse(is);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * appInfo的 JSON流转成bean
	 * 
	 * @param is
	 * @return AppInfo的bean
	 * @throws Exception
	 */
	private static AppInfo parse(InputStream is) throws Exception {
		appInfo = new AppInfo();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		String configString = baos.toString();
		JSONObject appConfig = new JSONObject(configString);
		appInfo.serverAddress = appConfig.getString("server_address");
		appInfo.alipay_address = appConfig.getString("alipay_address");
		appInfo.unionpay_address = appConfig.getString("unionpay_address");

		appInfo.imei = appConfig.getString("imei");
		appInfo.osName = appConfig.getString("osName");
		appInfo.osVersion = appConfig.getString("osVersion");
		appInfo.appVersion = appConfig.getString("appVersion");
		appInfo.timeStamp = appConfig.getString("timeStamp");
		appInfo.uid = appConfig.getString("uid");
		appInfo.authCode = appConfig.getString("authCode");
		baos.close();
		is.close();

		return appInfo;
	}

}
