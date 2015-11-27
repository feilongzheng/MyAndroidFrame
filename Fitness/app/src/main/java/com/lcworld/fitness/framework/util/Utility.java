package com.lcworld.fitness.framework.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.mynet.RequestUrls;
import com.lcworld.fitness.R;
import com.lcworld.fitness.R.drawable;
import com.lcworld.fitness.framework.application.BaseApplication;
import com.lcworld.fitness.selfviews.roundedimageview.RoundedTransformationBuilder;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.util.LogUtils;
import com.squareup.picasso.Transformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author HanlionTien
 */
public class Utility {
    /**
     * 判断网络连接
     *
     * @return
     */
    public static boolean isNetWorkConnected() {
        ConnectivityManager cwjManager = (ConnectivityManager) BaseApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != cwjManager.getActiveNetworkInfo())
            return cwjManager.getActiveNetworkInfo().isConnected();

        return false;
    }

    public static int TORANKINGPENGYE = 0;

    public static int REFRESHSTARGROUP = 10;

    public static void showDialog(Activity activity, String content) {
        Builder builder = new Builder(activity);

        builder.setMessage(content);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    /**
     * 清除Cache文件
     *
     * @param ctx
     */
    public static void clearCacheDir(Context ctx) {
        File dir = ctx.getCacheDir();
        if (dir != null && dir.isDirectory()) {
            try {
                File[] children = dir.listFiles();
                if (children.length > 0) {
                    for (int i = 0; i < children.length; i++) {
                        File[] temp = children[i].listFiles();
                        for (int x = 0; x < temp.length; x++) {
                            temp[x].delete();
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("Cache", "failed cache clean");
            }
        }
    }

   

    /**
     * 判断Json中是否有该字符串
     *
     * @param jObj
     * @param key
     * @return
     * @throws JSONException
     */
    public static String parseValueOfJson(JSONObject jObj, String key) throws JSONException {
        if (jObj.isNull(key))
            return "";
        else
            return jObj.getString(key);
    }

    /**
     * 在Json字符串中取值，无值则给默认值
     *
     * @param jObj
     * @param key
     * @return
     * @throws JSONException
     */
    public static String parseValueOfJson(JSONObject jObj, String key, String defaultvalue) throws JSONException {
        if (jObj.isNull(key))
            return defaultvalue;
        else
            return jObj.getString(key);
    }

    /**
     * 在JSON中取Int值
     *
     * @param jObj
     * @param key
     * @return
     * @throws JSONException
     */
    public static int parseIntValueOfJson(JSONObject jObj, String key) throws JSONException {
        if (jObj.isNull(key)) {
            return 0;
        } else {
            return jObj.getInt(key);
        }
    }

    /**
     * 在JSON中取Int值，无值则给默认值
     *
     * @param jObj
     * @param key
     * @return
     * @throws JSONException
     */
    public static int parseIntValueOfJson(JSONObject jObj, String key, int defaultvalue) throws JSONException {
        if (jObj.isNull(key)) {
            return defaultvalue;
        } else {
            return jObj.getInt(key);
        }
    }

    
    /**
     * 4.4用HANGOUT取代原来的MESSAGE 需要用户自己选择发送短信需要使用的APP
     *
     * @param sms
     */
    public static void sendSms(String mobile, String sms) {
        Uri uri = Uri.parse("smsto:".concat(mobile));
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        it.putExtra("sms_body", sms);
        BaseApplication.getInstance().startActivity(it);

		/*
         * Intent sendSmsIntent = new Intent(Intent.ACTION_SEND);
		 * sendSmsIntent.setType ( "text/plain" ); sendSmsIntent.putExtra (
		 * Intent.EXTRA_TEXT, sms);
		 * BaseApplication.getInstance().startActivity ( Intent.createChooser(
		 * sendSmsIntent , "短信" ) );
		 */
    }

    /**
     * 返回图片缓存文件夹
     *
     * @return
     */
    public static File getImageCacheFilePath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "//PengSiCache//imageCache");
            if (!f.isDirectory()) {
                f.mkdirs();
            }
            return f;
        } else {
            Toast.makeText(BaseApplication.getInstance(), "返回图片缓存文件夹错误", Toast.LENGTH_LONG).show();
            return null;
        }
    }

   


    /**
     * 判断是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);

        if (!isNum.matches()) {
            return false;
        }

        return true;
    }

    /**
     * 手机号码匹配
     * <p/>
     * 13x, 14x, 15x, 17x, 18x, 19x
     */
    public static boolean checkMobile(String mobile) {
        String regex = "^1(3[0-9]|4[0-9]|5[0-9]|7[0-9]|8[0-9]|9[0-9])\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mobile);

        return m.find();
    }

    /**
     * 获取本机手机号码
     *
     * @param ctx
     * @return
     */
    public static String getMobile(Context ctx) {
        String mobile = null;

        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        mobile = tm.getLine1Number();

        return mobile;
    }

    /**
     * 判断字符是否全是空格
     *
     * @param str
     * @return
     */
    public static boolean isAllSpace(String str) {
        String strPattern = "^\\s+$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 在Lanuncher上创建图标
     *
     * @param context
     * @param target
     * @param appName
     * @param appIcon
     */
    public static void CreateAPPShortcutOnLauncher(Context context, Class<?> target, String appName, int appIcon) {
        // <uses-permission
        // android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, appIcon));
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
        intent.putExtra("duplicate", false);

        Intent sIntent = new Intent(Intent.ACTION_MAIN);
        sIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        sIntent.setClass(context, target);

        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, sIntent);

        context.sendBroadcast(intent);
    }

    /**
     * 是否在Launcher上创建图标
     *
     * @param ctx
     * @param appName
     * @return
     */
    public static boolean IsInstalledShortcut(Context ctx, String appName) {
        boolean isInstallShortcut = false;

        final ContentResolver cr = ctx.getContentResolver();
        final String AUTHORITY = "com.android.launcher.settings";
        final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");

        Cursor c = cr.query(CONTENT_URI, new String[]{"title", "iconResource"}, "title=?", new String[]{appName}, null);

        if (c != null && c.getCount() > 0) {
            isInstallShortcut = true;
            c.close();
        }

        return isInstallShortcut;
    }

    /**
     * 得到系统中已经安装的应用
     *
     * @param mPackageManager
     * @return
     */
    public static List<PackageInfo> InstalledAppsFetcher(final PackageManager mPackageManager) {

        final List<PackageInfo> installed = new ArrayList<PackageInfo>();

        List<PackageInfo> packages = mPackageManager.getInstalledPackages(PackageManager.GET_META_DATA);
        for (PackageInfo p : packages) {
            // if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM)
            // <= 0)
            if ((p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
                installed.add(p);
        }

        Comparator<PackageInfo> comp = new Comparator<PackageInfo>() {
            @Override
            public int compare(PackageInfo p1, PackageInfo p2) {
                CharSequence appName1 = p1.applicationInfo.loadLabel(mPackageManager);
                CharSequence appName2 = p2.applicationInfo.loadLabel(mPackageManager);

                return appName1.toString().compareTo(appName2.toString());
            }
        };

        Collections.sort(installed, comp);

        return installed;
    }

    /**
     * 服务器图片路径
     *
     * @param dir
     * @param id
     * @return
     */
    public static String getImageFilePath(String dir, String id, String... params) {
        try {
            if (id == null || id.length() == 0) {
                return null;
            }
            StringBuffer buffer = new StringBuffer();
            buffer.append(RequestUrls.URL_ALLIMAGE).append(dir).append("/");
            for (int i = 6; i > 0; i -= 2) {
                buffer.append(id.substring(i - 2, i)).append("/");
            }
            buffer.append(id).append("/");
            for (String s : params) {
                if (s == null || "<null>".equalsIgnoreCase(s)) {//聊天的时候ios的null可能是“<null>”
                    continue;
                }
                buffer.append(s);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 服务器BANNER图片路径
     *
     * @param dir
     * @param id
     * @return
     */
    public static String getBannerImageFilePath(String dir, String id, String... params) {
        if (id == null || id.length() == 0) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(RequestUrls.URL_ALLIMAGE).append(dir).append("/");
        for (int i = 6; i > 0; i -= 2) {
            buffer.append(id.substring(i - 2, i)).append("/");
        }
        buffer.append(id).append("/");

        for (String s : params) {
            if (s == null) {
                continue;
            }
            buffer.append(s);
        }
        return buffer.toString();
    }

    /**
     * 高于10000的数字转换为X.X万
     *
     * @param count
     * @return
     */
    public static String getCharmCount(String count, boolean plus) {

        if (count != null) {
            long charm = 0;
            try {
                charm = Long.parseLong(count);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (charm >= 10000) {
                DecimalFormat df = new DecimalFormat("#.#");
                if (plus) {
                    return "+" + df.format((double) charm / 10000) + "万";
                } else {
                    return df.format((double) charm / 10000) + "万";
                }

            } else {
                if (plus) {
                    return "+" + count;
                } else {
                    return count + "";
                }
            }
        }

        return "0";
    }

    /**
     * 得用反射得到PENGYEICON drawable的int值
     *
     * @param
     * @return
     */
    public static int getPengYeIcon(int level, boolean display) {
        if (level > 0 && level < 82) {
            String name = "ic_pengye_lv" + level;
            Class<drawable> drawable = R.drawable.class;
            Field field = null;
            try {
                field = drawable.getField(name);
                return field.getInt(field.getName());
            } catch (Exception e) {
                return 0;
            }
        }

        if (level < 0) {
            return drawable.ic_launcher;
        }

		/*
         * if (level == 0) { return R.drawable.ic_pengye_lv0; }
		 */
        // if (display) {
        // return R.drawable.ic_pengye_lv0;
        // } else {
        // return 0;
        // }
        //
        // } else {
        // return R.drawable.ic_pengye_lvtop;
        // }
        return 0;
    }

    /**
     * 返回98个Emoji表情
     *
     * @return
     */
    public static int[] getEmojiArray() {
        Class<drawable> drawable = R.drawable.class;
        Field field = null;
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            try {
                field = drawable.getField("chat_emoji_" + i);
                array[i] = field.getInt(field.getName());
            } catch (Exception e) {
                return null;
            }
        }
        return array;
    }

    /**
     * 返回正确的显示时间
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTimeDiffrent(String time) {
        if (Utility.isNull(time)) {
            return "";
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int nowYear = Calendar.getInstance().get(Calendar.YEAR);

        String[] array = time.split("-");
        String mmdd = array[1] + "-" + array[2].split(" ")[0];

        boolean isMatchYear = array[0].equals(String.valueOf(nowYear));

        try {
            Date now = new Date(System.currentTimeMillis());
            Date date = df.parse(time);

            long l = (now.getTime() - date.getTime()) / 1000;

            if (l < 60) {
                return "刚刚";
            }

            if (l < 3600) {
                return l / 60 + "分钟前";
            }

            if (l < 24 * 3600) {
                return l / 3600 + "小时前";
            }

            if (l < 48 * 3600) {
                return "昨天";
            } else if (isMatchYear) {
                return mmdd;
            } else {
                return time.split(" ")[0];
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getTimeDiffrent(String time, String systemTime) {
        if (Utility.isNull(time)) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date now = null;
            if (isNull(systemTime)) {
                now = new Date(System.currentTimeMillis());
            } else {
                now = df.parse(systemTime);
            }
            Date date = df.parse(time);

            Calendar.getInstance().setTime(now);

            int nowYear = Calendar.getInstance().get(Calendar.YEAR);
            String[] array = time.split("-");
            String mmdd = array[1] + "-" + array[2].split(" ")[0];
            boolean isMatchYear = array[0].equals(String.valueOf(nowYear));

            long l = (now.getTime() - date.getTime()) / 1000;
            if (l < 60) {
                return "刚刚";
            }

            if (l < 3600) {
                return l / 60 + "分钟前";
            }

            if (l < 24 * 3600) {
                return l / 3600 + "小时前";
            }

            if (l < 48 * 3600) {
                return "昨天";
            } else if (isMatchYear) {
                return mmdd;
            } else {
                return time.split(" ")[0];
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 返回日期或者时间
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getHourAndMin(String time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = Calendar.getInstance().getTime();
        String dt = df.format(date);

        String ymd = time.split(" ")[0];
        if (ymd.equals(dt)) {
            return time.split(" ")[1];
        } else {
            return ymd;
        }

    }

    /**
     * 返回当前年月日时间
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = Calendar.getInstance().getTime();
        return df.format(date);
    }

    /**
     * 两个时间对比
     *
     * @param context
     * @param timeA
     * @param timeB
     * @return
     */
    public static boolean timeComparison(Context context, String timeA, String timeB) {
        try {
            long aTime = DateFormat.getDateFormat(context).parse(timeA).getTime();
            long bTime = DateFormat.getDateFormat(context).parse(timeB).getTime();

            if (aTime > bTime) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * px转DIP
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dp转px
     *
     * @param context
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 血型
     *
     * @param type
     * @return
     */
    public static String getBloodType(int type) {
        switch (type) {
            case 0:
                return "A型";
            case 1:
                return "B型";
            case 2:
                return "AB型";
            case 3:
                return "O型";
        }
        return null;
    }

    /**
     * 血型
     *
     * @param type
     * @return
     */
    public static int getBloodType(String type) {
        if (type.equals("A型")) {
            return 0;
        }
        if (type.equals("B型")) {
            return 1;
        }
        if (type.equals("AB型")) {
            return 2;
        }
        if (type.equals("O型")) {
            return 3;
        }
        return 12;

    }

    /**
     * 获取版本名
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = BaseApplication.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode() {
        try {
            PackageManager manager = BaseApplication.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取分享地址
     *
     * @param url
     * @return
     */
    public static String getShareUrl(String url) {
        StringBuilder sb = new StringBuilder();
        sb.append(RequestUrls.SHARE_URL);
        sb.append(Base64.encodeToString(url.getBytes(), Base64.DEFAULT));
        return sb.toString();
    }

  
    /**
     * 获取底部蓝高度
     *
     * @return
     */
    public static int getNavigationBarHeight(Activity mActivity) {
        Resources resources = mActivity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        LogUtil.log("Navi height:" + height);
        return height;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     * @date 2013年7月23日
     */
    public static int getWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getTitleWidth(Activity activity) {
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        return outRect.top;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     * @date 2013年7月23日
     */
    public static int getHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 根据屏幕宽度来计算view的高
     *
     * @param context
     * @param px      1920x1080 下对应的view高度
     * @return view的高度
     */
    public static float getViewHeight(Context context, int px) {
        int h = getHeight(context);
        return px * (getWidth(context) / 1080f);
    }

    /**
     * 根据屏幕宽度和图片的宽度比来计算图片的高度
     *
     * @param context
     * @param wPx     图片的宽度
     * @param hPx     图片的高度
     * @return
     */
    public static float getImageLoadHeight(Context context, float wPx, float hPx) {

        return hPx * (getWidth(context) / wPx);
    }

    /**
     * 根据屏幕宽度和图片的宽度比来计算图片的宽度
     *
     * @param context
     * @param wPx     图片的宽度
     * @param hPx     图片的高度
     * @return
     */
    public static float getImageLoadWight(Context context, float wPx, float hPx) {

        return wPx * (getHeight(context) / hPx);
    }

    /**
     * @param string
     * @return
     */
    public static boolean isNotNull(String string) {
        if (null != string && !"".equals(string.trim())) {
            return true;
        }
        return false;
    }

    /**
     * @param string
     * @return
     */
    public static boolean isNull(String string) {
        if (null == string || "".equals(string.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 页面跳转
     *
     * @param context
     * @param clazz
     */
    public static void skip(Context context, Class clazz) {
        Intent it = new Intent(context, clazz);
        context.startActivity(it);
    }

    public static void skip(Context context, Class clazz, Bundle extras) {
        Intent it = new Intent(context, clazz);
        it.putExtras(extras);
        context.startActivity(it);
    }

    public static void skipForResult(Activity context, Class clazz, int requestCode) {
        Intent it = new Intent(context, clazz);
        context.startActivityForResult(it, requestCode);
    }

    public static void skipForResult(Activity context, Class clazz, Bundle extras, int requestCode) {
        Intent it = new Intent(context, clazz);
        it.putExtras(extras);
        context.startActivityForResult(it, requestCode);
    }

    /**
     * fragment
     *
     * @param context
     * @param fragment
     * @param clazz
     * @param requestCode
     */
    public static void skipForResult(Context context, Fragment fragment, Class clazz, int requestCode) {
        Intent it = new Intent(context, clazz);
        fragment.startActivityForResult(it, requestCode);
    }

    public static void skipForResult(Context context, Fragment fragment, Class clazz, Bundle extras, int requestCode) {
        Intent it = new Intent(context, clazz);
        it.putExtras(extras);
        fragment.startActivityForResult(it, requestCode);
    }

    public static <T> boolean isListNull(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static <T> boolean isListNull(Map map) {
        if (map == null || map.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static void showShortToast(Context context, String str) {
        showToast(context, str, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(Context context, int strId) {
        String str = context.getResources().getString(strId);
        showToast(context, str, Toast.LENGTH_SHORT);
    }


    private static void showToast(Context context, String str, int duration) {
        if (context != null && !TextUtils.isEmpty(str)) {
            Toast toast = Toast.makeText(context, str, duration);
//            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public static void showLongToast(Context context, String str) {
        showToast(context, str, Toast.LENGTH_LONG);
    }

    /**
     * 验证是否是手机号码
     *
     * @param phoneNum
     * @return
     */
    public static boolean isPhoneNum(String phoneNum) {
        Pattern p = Pattern.compile("^0?\\d{11}$");
        Matcher m = p.matcher(phoneNum);
        return m.matches();
    }

    /**
     * 验证邮箱格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

   

    public static float cacheSize;

    /**
     * 获取文件大小
     *
     * @param f
     * @return
     */
    public static void getFileSize(File f) {
        try {
            File flist[] = f.listFiles();
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    getFileSize(flist[i]);
                } else {
                    cacheSize = cacheSize + flist[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.log("cacheSize:" + cacheSize);
    }

    /**
     * 清楚缓存
     *
     * @param f
     */
    public static void clearCache(File f) {
        try {
            File flist[] = f.listFiles();
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    clearCache(flist[i]);
                } else {
                    flist[i].delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Utility.cacheSize = 0;
    }

   
    public static String format2(Float value) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(value);
    }

    /**
     * 获取栈顶activity名字
     *
     * @param context
     * @return
     */
    public static String getTopActivityName(Context context) {
        String topActivityName = null;
        ActivityManager activityManager = (ActivityManager) (context.getSystemService(Context.ACTIVITY_SERVICE));
        List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
        if (runningTaskInfos != null) {
            ComponentName f = runningTaskInfos.get(0).topActivity;
            String topActivityClassName = f.getClassName();
            String temp[] = topActivityClassName.split("\\.");
            topActivityName = temp[temp.length - 1];
            LogUtils.i("topActivityName=" + topActivityName);
        }
        return topActivityName;
    }

    public static boolean isTopActivity(Context context, Class clzz) {
        try {
            if (context != null) {
                ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                if (activityManager != null) {
                    List<RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
                    if (!runningTasks.isEmpty()) {
                        RunningTaskInfo runningTaskInfo = runningTasks.get(0);
                        if (runningTaskInfo != null && runningTaskInfo.topActivity != null) {
                            if (clzz == runningTaskInfo.topActivity.getClass()) {
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean isAppBackground(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.processName.equals(context.getPackageName())) {
                /*
                BACKGROUND=400 EMPTY=500 FOREGROUND=100
				GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
				 */
                    Log.i(context.getPackageName(), "此appimportace ="
                            + appProcess.importance
                            + ",context.getClass().getName()="
                            + context.getClass().getName());
                    if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        Log.i(context.getPackageName(), "处于后台" + appProcess.processName);
                        return true;
                    } else {
                        Log.i(context.getPackageName(), "处于前台" + appProcess.processName);
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean isAppClosed(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.processName.equals(context.getPackageName())) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public static String getFragmentName(Fragment fragment) {
        String name = fragment.getClass().getName();
        if (isNotNull(name) && name.contains(".")) {
            name = name.substring(name.lastIndexOf(".") + 1);
        }
        return name;
    }


    /**
     * 隐藏软件盘
     */
    public static void hideSoftKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View focus = context.getCurrentFocus();
        if (focus != null) {
            IBinder binder = focus.getWindowToken();
            if (null != binder) {
                imm.hideSoftInputFromWindow(binder, 0);
            }
        }
    }

    /**
     * 显示键盘
     *
     * @param context
     */
    public static void showSoftKeyboard(Activity context) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View focus = context.getCurrentFocus();
        if (focus != null) {
            IBinder binder = focus.getWindowToken();
            if (null != binder) {
                inputManager.showSoftInputFromInputMethod(binder, 0);
            }
        }
    }

    // 将日志文件写到文件里
    public static void writeLogToFile(String json, String FileName) {
        File file = new File("/sdcard/" + FileName + ".txt");
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(json.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    /**
     * 加载本地图片 centerCrop
     *
     * @param mContext
     * @param file
     * @param errorDrawableId
     * @param intoImageView
     */
    public static void loadLocalImage(Context mContext, File file, int errorDrawableId, ImageView intoImageView) {
        if (file != null && file.exists()) {
            BaseApplication.getInstance().picasso.load(file).centerCrop().fit().error(errorDrawableId).into(intoImageView);
        }
    }

    public static void loadLocalImage(Context mContext, File file, int errorDrawableId, ImageView intoImageView, float cornerRadius) {
        if (file != null && file.exists()) {
            Transformation transformation = new RoundedTransformationBuilder().borderWidthDp(0).cornerRadius(cornerRadius).oval(false).build();
            BaseApplication.getInstance().picasso.load(file).centerCrop().fit().transform(transformation).error(errorDrawableId).into(intoImageView);
        }
    }

    /**
     * 加载网络图片 centerCrop
     *
     * @param mContext
     * @param url
     * @param placeholderDrawableId
     * @param errorDrawableId
     * @param intoImageView
     */
    public static void loadImageCrop(Context mContext, String url, int placeholderDrawableId, int errorDrawableId, ImageView intoImageView) {
        if (!TextUtils.isEmpty(url) && intoImageView != null) {
            BaseApplication.getInstance().picasso.load(url).centerCrop().fit().placeholder(placeholderDrawableId).error(errorDrawableId).into(intoImageView);
        }
    }

    public static void loadImageNoFit(Context mContext, String url, int placeholderDrawableId, int errorDrawableId, ImageView intoImageView) {
        if (!TextUtils.isEmpty(url) && intoImageView != null) {
            BaseApplication.getInstance().picasso.load(url).placeholder(placeholderDrawableId).error(errorDrawableId).into(intoImageView);
        }
    }

    public static void loadImage(Context mContext, String url, int placeholderDrawableId, ImageView intoImageView) {
        if (!TextUtils.isEmpty(url) && intoImageView != null) {
            loadImage(mContext, url, placeholderDrawableId, placeholderDrawableId, intoImageView);
        }
    }

    public static void loadImage(Context mContext, String url, int placeholderDrawableId, int errorDrawableId, ImageView intoImageView) {
        if (!TextUtils.isEmpty(url) && intoImageView != null) {
            if (placeholderDrawableId == Integer.MIN_VALUE) {
                BaseApplication.getInstance().picasso.load(url).fit().into(intoImageView);
            } else {
                BaseApplication.getInstance().picasso.load(url).fit().placeholder(placeholderDrawableId).error(errorDrawableId).into(intoImageView);
            }
        }
    }

    public static void loadImageWithCorner(Context mContext, String url, int placeholderDrawableId, ImageView intoImageView, float cornerRadius) {
        if (!TextUtils.isEmpty(url) && intoImageView != null) {
            loadImageWithCorner(mContext, url, placeholderDrawableId, intoImageView, cornerRadius, true);
        }
    }

    public static void loadImageWithCorner(Context mContext, String url, int placeholderDrawableId, ImageView intoImageView, float cornerRadius, boolean centerCrop) {
        if (!TextUtils.isEmpty(url) && intoImageView != null) {
            if (centerCrop) {
                Transformation transformation = new RoundedTransformationBuilder().borderWidthDp(0).cornerRadius(cornerRadius).oval(false).build();
                BaseApplication.getInstance().picasso.load(url).centerCrop().fit().transform(transformation).placeholder(placeholderDrawableId).error(placeholderDrawableId).into(intoImageView);
            } else {
                loadImageWithCorner(mContext, url, placeholderDrawableId, intoImageView, cornerRadius);
            }
        }
    }

    public static void loadImageWithCorner(Context mContext, String url, int placeholderDrawableId, com.squareup.picasso.Target target) {
        if (!TextUtils.isEmpty(url) && target != null) {
            Transformation transformation = new RoundedTransformationBuilder().borderWidthDp(0).cornerRadius(7.0f).oval(false).build();
            BaseApplication.getInstance().picasso.load(url).transform(transformation).into(target);
        }
    }

    public static void loadImageWithCorner(Context mContext, String url, int placeholderDrawableId, int errorDrawableId, ImageView intoImageView, float cornerRadius) {
        if (!TextUtils.isEmpty(url) && intoImageView != null) {
            Transformation transformation = new RoundedTransformationBuilder().borderWidthDp(0).cornerRadius(cornerRadius).oval(false).build();
            BaseApplication.getInstance().picasso.load(url).centerCrop().fit().transform(transformation).placeholder(placeholderDrawableId).error(errorDrawableId).into(intoImageView);
        }
    }

    /**
     * 关闭activity
     *
     * @param className
     * @param context
     */
    public static void closeActivity(String className, Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 获得当前正在运行的activity
        List<RunningTaskInfo> appList3 = mActivityManager.getRunningTasks(1000);
        for (RunningTaskInfo running : appList3) {
            String name = running.baseActivity.getClassName();
            LogUtil.log("name:" + name);
            if (className.equals(name)) {
                try {
                    Activity selectActivity = (Activity) (Class.forName(name).newInstance());
                    if (selectActivity != null) {
                        selectActivity.finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    
    public static int getViewHeight(View view) {
        int[] mloccation = new int[2];
        view.getLocationOnScreen(mloccation);
        return mloccation[1];
    }

    public static int getAndroidSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
        }
        return version;
    }

    /**
     * 返回json对应
     *
     * @param jsonString
     * @param key
     * @return
     */
    public static String getJsonString4Key(String jsonString, String key) {
        try {
            JSONObject obj = new JSONObject(jsonString);
            if (obj.has(key)) {
                return obj.getString(key);
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    
    public static Bitmap getViewBitmap(View view, int width, int height) {
        view.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static int getTextHeight(TextView textView, SpannableString text, int textSize, int width) {
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    public static int getTextHeight(TextView textView, String text, int textSize, int width) {
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
    }


    /**
     * 通知系统相册
     *
     * @param activity
     * @param newImageFile
     */
    public static void notifySystemGallary(Activity activity, File newImageFile) {
        if (activity == null || newImageFile == null || newImageFile.length() == 0) {
            return;
        }
        String uri;
        try {
            uri = MediaStore.Images.Media.insertImage(activity.getContentResolver(), newImageFile.getAbsolutePath(), newImageFile.getName(), "");
            activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(uri)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void notifySystemGallary(Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void starIt(Context mContext, Class clzz, String[] extraKeys, String[] extraValues) {
        if (mContext != null) {
            Intent intent = new Intent(mContext, clzz);
            if (extraKeys != null && extraValues != null && extraKeys.length <= extraValues.length) {
                for (int i = 0; i < extraKeys.length; i++) {
                    intent.putExtra(extraKeys[i], extraValues[i]);
                }
            }
            mContext.startActivity(intent);
        }
    }

   
   

    /**
     * 调起系统发短信功能
     *
     * @param phoneNumber 发送短信的接收号码
     * @param message     短信内容
     */
    public static void sendSMS(Context context, String phoneNumber, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
        intent.putExtra("sms_body", message);
        context.startActivity(intent);
    }

    public static int selectContract = 173;

    /**
     * 跳转手机联系人界面
     *
     * @param context
     */
    public static void skipContracts(Activity context) {
        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        context.startActivityForResult(i, selectContract);
    }

    /**
     * 获取选择的手机联系人
     *
     * @param context
     * @param resultCode
     * @param data
     * @return
     */
    public static List<String> getSelectContractPhone(Context context, int resultCode, Intent data) {
        List<String> list = new ArrayList<String>();
        if (data == null) {
            return null;
        }
        try {
            Uri uri = data.getData();
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToNext()) {
                // 取得电话号码
                String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phone = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);

                // 不只一个电话号码
                while (phone.moveToNext()) {
                    String phoneNumber = phone.getString(phone.getColumnIndex("data1"));
                    phoneNumber = phoneNumber.replace(" ", "");
                    list.add(phoneNumber);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    
    /**
     * 将view转成bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap view2Bitmap(View view, Context context) {
        if (view == null) {
            return null;
        }
        view.measure(MeasureSpec.makeMeasureSpec(dip2px(context, 80f), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(dip2px(context, 80f), MeasureSpec.EXACTLY));
        // 这个方法也非常重要，设置布局的尺寸和位置
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        // 生成bitmap
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
        // 利用bitmap生成画布
        Canvas canvas = new Canvas(bitmap);
        // 把view中的内容绘制在画布上
        view.draw(canvas);
        return bitmap;
    }



    public static DbUtils getChatUserDbUtils() {
        DbUtils.DbUpgradeListener dbUpgradeListener = new DbUtils.DbUpgradeListener() {
            @Override
            public void onUpgrade(DbUtils dbUtils, int oldVersion, int newVersion) {

            }
        };
        DbUtils dbUtils = DbUtils.create(BaseApplication.getInstance(), "chatUserDbBean", 1, dbUpgradeListener);
        return dbUtils;
    }

   

    public static void setLogEnable(boolean isLog) {
//        if (!Const.ISDEBUG) {
//            Utility.setBooleanToSettingSharedPreference(Const.isLogEnableKey, isLog);
//
//            LogUtils.allowE = LogUtils.allowV = LogUtils.allowI = isLog;
//        }
    }



    private static void clearAllActivityAndKillProcess() {
        for (Activity activity : BaseApplication.getInstance().undestroyActivities) {
            if (activity != null) {
                activity.finish();
            }
        }

        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * IMEI 全称�?International Mobile Equipment Identity，中文翻译为国际移动装备辨识码， 即�?常所说的手机序列号，
     * 用于在手机网络中识别每一部独立的手机，是国际上公认的手机标志序号，相当于移动电话的身份证。序列号共有15位数字，�?位（TAC）是型号核准号码�?
     * 代表手机类型。接�?位（FAC）是�?��装配号，代表产地。后6位（SNR）是串号，代表生产顺序号。最�?位（SP）一般为0，是�?��码，备用�?
     * 国际移动装备辨识码一般贴于机身背面与外包装上，同时也存在于手机记忆体中，通过输入*#06#即可查询�?
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager ts = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ts.getDeviceId();
    }


    public static void addMoreParams(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<String, String>();
        }

        params.put("VERSION_CODE", CommonUtil.getVersionCode(BaseApplication.getInstance()));
        params.put("VERSION_NAME", CommonUtil.getVersionName(BaseApplication.getInstance()));
        params.put("DEVICE_TYPE", "2");//1:IOS :2ANDROID
        params.put("DEVICE_IDENTIFY", NetWorkUtils.getMacAddress(BaseApplication.getInstance()) + "&" + getIMEI(BaseApplication.getInstance()));
        params.put("USER_ID", getUserId());

        int networkType = NetWorkUtils.getCurrentNetworkType();
        if (networkType != -1) {
            params.put("NETWORK_TYPE", String.valueOf(networkType));
        }
    }



    private static String getUserId() {
		return "";
	}


    /**
     * 设置图片包裹的宽高
     *
     * @param rl_pic
     * @param bitmap
     * @param context
     */
    public static void setPicParams(RelativeLayout rl_pic, Bitmap bitmap, Context context) {
        if (rl_pic != null) {
            int bitmapHeight = bitmap.getHeight();
            int bitmapWidth = bitmap.getWidth();

            int rlHeight = (int) (Utility.getHeight(context) / 6f);
            RelativeLayout.LayoutParams rlpicParams = (RelativeLayout.LayoutParams) rl_pic.getLayoutParams();
            rlpicParams.height = rlHeight;

            if (bitmap.getHeight() / bitmap.getWidth() > 3) {
                rlpicParams.width = (int) (rlHeight * 0.7f);
            } else {
                rlpicParams.width = (int) (rlHeight * (bitmapWidth * 1f / bitmapHeight));
            }

            rl_pic.setLayoutParams(rlpicParams);
        }
    }

    /**
     * 设置图片包裹的宽高
     *
     * @param rl_pic
     * @param bitmap
     * @param context
     */
    public static void setVideoParams(RelativeLayout rl_pic, Bitmap bitmap, Context context) {
        if (rl_pic != null) {
            int bitmapHeight = bitmap.getHeight();
            int bitmapWidth = bitmap.getWidth();

            int rlHeight = (int) (Utility.getHeight(context) / 4f);
            RelativeLayout.LayoutParams rlpicParams = (RelativeLayout.LayoutParams) rl_pic.getLayoutParams();
            rlpicParams.height = rlHeight;

            if (bitmap.getHeight() / bitmap.getWidth() > 3) {
                rlpicParams.width = (int) (rlHeight * 0.7f);
            } else {
                rlpicParams.width = (int) (rlHeight * (bitmapWidth * 1f / bitmapHeight));
            }

            rl_pic.setLayoutParams(rlpicParams);
        }
    }

    public static void openUrlWithIntent(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }


    /**
     * 将null转成“”
     */
    public static String nullToEmptyString(String str) {
        return str == null ? "" : str;
    }

    private static Map<String, String> getUrlParams(String action, String clipStr) {
        Map<String, String> params = new HashMap();
        params.put("action", action);
        String[] split1 = clipStr.split(action);
        if (split1 != null && split1.length == 2) {
            if (!TextUtils.isEmpty(split1[1])) {
                if (split1[1].contains("type=")) {
                    String[] split2 = split1[1].split("type=");
                    if (split2.length == 2) {
                        if (!TextUtils.isEmpty(split2[0]) && split2[0].length() >= 1) {
                            params.put("id", split2[0].substring(0, split2[0].length() - 1));
                            params.put("type", split2[1]);
                        }
                    }
                } else {
                    params.put("id", split1[1]);
                }
            }
        }
        return params;
    }

    public static void reportErrorToUmeng(Context mContext, Throwable throwable) {
        if (throwable != null && mContext != null) {
            throwable.printStackTrace();
//            MobclickAgent.reportError(mContext, throwable);
        }
    }


    /**
     * 校验英文和汉子
     *
     * @return
     */
    public static boolean isEngAndChinaese(String content) {
        String regex = "^[a-zA-Z\u4E00-\u9FA5]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(content);
        return match.matches();
    }

}
