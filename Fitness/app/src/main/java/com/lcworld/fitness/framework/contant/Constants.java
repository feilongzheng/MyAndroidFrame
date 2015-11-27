package com.lcworld.fitness.framework.contant;

import android.os.Environment;

/**
 * 
 * @author zhengfeilong TODO 程序通用常量
 */
public class Constants {

	private static final String FILE_PATH_BASE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.lcworld.fitness";
	public static final String FILE_PATH_TEMP = FILE_PATH_BASE + "/temp";
	public static final String FILE_PATH_JPUSH = FILE_PATH_TEMP + "/jpush.file";
	public static final String FILE_PATH_WEATHER_DB = FILE_PATH_TEMP + "/weather.db";
	public static final String JPUSHFILENAME = "JPUSH_TEMP";
	public static final String FILE_NAME_NETWORK_DATA = "network_data.txt";
	public static final String FILE_NAME_REGIST_AGREEMENT = "regist_agreement.txt";//名字要与asset中的一样
	
	public static final String SP_DICTIONARY_DATA = "sp_dictionary_data";//存储字典数据的sharedpreference
	public static final String SP_WELCOM = "SP_WELCOM";
	public static final String SP_USERINFO = "SP_USERINFO";
	
	/**上传头像图片的服务器地址*/
	public static final String uploadHeaderImageServerAddress = "http://124.205.55.210:9090/app/fileUpLoads.do";
	
	
	public static final int ERROR_CODE_OK = 0;// 访问网络成功时的code
	public static final int SINA_AUTHORIZE_CANCEL = 0;
	public static final int SINA_AUTHORIZE_SUCCESS = 1;
	public static final int SINA_AUTHORIZE_ERROR = 2;
	public static final int SINA_AUTHORIZE_EXCEPTION = 3;
	public static final int SINA_SHARE_WEIBO_COMPLETE = 4;
	public static final int SINA_SHARE_WEIBO_WEIBO_EXCEPTION = 5;
	public static final int SINA_SHARE_WEIBO_IOEXCEPTION = 6;

	public static final String APP_CONFIG_FILE_NAME = "AppConfig.json";
	public static final String FROM_PAGE = "fromPage";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String PASSWORD = "password";
	public static final String WEIBO_TYPE_SINA = "新浪";
	public static final String WEIBO_TYPE_TENCENT = "腾讯";

	/** 我是否允许 umeng 的xx业务执行 */
	public static final boolean isUmengNeed = true;

	/**shareSDK默认值*/
	public static final String SHARE_DEFAULT_PHONE_NUBMER = "12345678901";
	public static final String SHARE_DEFAULT_TITLE = "欧德莱尔商城";
	public static final String SHARE_DEFAULT_TITLEURL = "www.jinghuakongqi.com";
	public static final String SHARE_DEFAULT_COMMODITY_TEXT = "我欧德莱尔商城发现了一款不错的商品，赶快来看看吧。";
	public static final String SHARE_DEFAULT_SHOP_TEXT = "我欧德莱尔商城发现了一个不错的店铺，赶快来看看吧。";
	public static final String SHARE_DEFAULT_SITE = "欧德莱尔空气净化网";
	public static final String SHARE_DEFAULT_SITE_URL = "www.jinghuakongqi.com";
	
	
	/**标签类型：0:无效；100:健身中心；200:健身教练；300:课程；400:计划;500:评论;800:用户自定义标签'*/
	public static class TAGTYPE{
		public static String no = "0";
		public static String center = "100";
		public static String coach = "200";
		public static String course = "300";
		public static String plan = "400";
		public static String crowdfunding = "500";
		public static String suite = "600";
		public static String self_define = "800";
	}
	
	/**商品类型:
	100 套餐卡;
	200 课程;
	300 众筹;
	400 私教;*/
	public static class COMMODITY_TYPE{
		public static String suiteCard = "100";
		public static String course = "200";
		public static String crowdfunding = "300";
		public static String personalCoach = "400";
	}
	
	
	public static class SP_DICTIONARY_KEY{
		public static final String project = "1";/**服务项目的jsonArrayString*/
		public static final String sort_center = "comSort";/**健身中心排序的jsonArrayString*/
		public static final String sort_plan = "planSort";/**健身计划的jsonArrayString*/
		public static final String sort_raise = "raiseStopType";/**健身众筹的jsonArrayString*/
		public static final String sort_coach = "traSort";/**健身教练的jsonArrayString*/
		
		/*----计划查询条件-----
		健身目的：下拉【字典：fitnessTarget】
		使用人群：下拉【字典：fitPerson】
		训练类型：下拉【字典：trainType】
		计划周期数：下拉【字典：planCycle】*/
		public static final String fitnessTarget = "fitnessTarget";
		public static final String fitPerson = "fitPerson";
		public static final String trainType = "trainType";
		public static final String planCycle = "planCycle";
		
	}
	
	public static final String  default_sortord= "desc";
	
	
	
	
	
	
	
	
	
	// --------------NetWorkImageView--------------
	/**
	 * TAG
	 */
	public static final String TAG = "NetWorkImageView";

	/**
	 * DEBUG
	 */
	public static final boolean DEBUG = true;
	/**
	 * 存储的文件名后缀
	 */
	public static final String WHOLESALE_CONV = ".png";
	/**
	 * 在外部存储上的地址
	 */
	public static final String IMAGE_CACHE_DIR = FILE_PATH_TEMP + "/pics";

	/**
	 * 网络连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 60;

	/**
	 * 下载图片的最大线程数
	 */
	public final static int MAX_THREADS = 2;

	/**
	 * 图片的二级缓存容量
	 */
	public static final int LEVEL_TWO_CACHE_CAPACITY = 10;
	// --------------NetWorkImageView--------------

}
