package com.lcworld.fitness.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import com.lcworld.fitness.R;
import com.lcworld.fitness.framework.activity.CustomWebActivity;
import com.lcworld.fitness.framework.widget.xlistview.XListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class CommonUtil {

    /**
     * 判断当前是否有可用的网络以及网络类型 0：无网络 1：WIFI 2：CMWAP 3：CMNET
     *
     * @param context
     * @return
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return 0;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return 1;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            String extraInfo = netWorkInfo.getExtraInfo();
                            if ("cmwap".equalsIgnoreCase(extraInfo) || "cmwap:gsm".equalsIgnoreCase(extraInfo)) {
                                return 2;
                            }
                            return 3;
                        }
                    }
                }
            }
        }
        return 0;
    }

    // 获取系统版本号显示
    public static String getSystemVersionName(Context context) {
        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getApplicationInfo().packageName, 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * @param context
     * @return false=有网络 true=无网络，这里面已经做了Toast.makeText(context,
     * R.string.net_error, Toast.LENGTH_SHORT).show();
     * 和ProgressHUD.dismissIfExist();
     */
    private static boolean isNetworkNotAvailable(Context context) {
        if (getNetworkType(context) == 0) {
            Toast.makeText(context, R.string.net_error, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        if (isNetworkNotAvailable(context)) {
            return false;
        }
        return true;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static boolean mkFiledir(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return false;
    }

    /**
     * @param str 被分隔的字符串
     * @param per 分隔符
     * @param len 每隔多长添加一个分隔符
     * @return
     */
    public static String splitString(String str, String per, int len) {
        if (TextUtils.isEmpty(str)) {
            return "";
        } else if (str.length() <= len || len < 1 || TextUtils.isEmpty(per)) {
            return str;
        }
        int in = str.length() / len;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < in; i++) {
            sb.append(str.substring(len * i, len * (i + 1)) + per);
        }
        if (str.length() % len == 0) {
            sb.deleteCharAt(sb.length() - 1);
        } else {
            sb.append(str.substring(len * in, str.length()));
        }
        return sb.toString();
    }

    /**
     * 获取联系人
     *
     * @param context
     * @return
     */
    public static Map<String, String> getContactInfo(Context context) {
        // 获得通讯录信息 ，URI是ContactsContract.Contacts.CONTENT_URI
        Cursor cursor = null;
        Map<String, String> contactInfo = new HashMap<String, String>();
        try {
            ContentResolver cr = context.getContentResolver();
            cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

            while (cursor.moveToNext()) {
                // 获得通讯录中每个联系人的ID
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                // 获得通讯录中联系人的名字
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                // 查看给联系人是否有电话，返回结果是String类型，1表示有，0表是没有
                String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (hasPhone.equalsIgnoreCase("1"))
                    hasPhone = "true";
                else
                    hasPhone = "false";
                // 如果有电话，根据联系人的ID查找到联系人的电话，电话可以是多个
                if (Boolean.parseBoolean(hasPhone)) {
                    Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    while (phones.moveToNext()) {
                        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactInfo.put(name, phoneNumber);
                    }
                    if (phones != null) {
                        phones.close();
                    }
                }
            }
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contactInfo;
    }

    /**
     * @return 格式化最后更新时间
     */
    public static String formatPullToRefreshDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return "今天" + sdf.format(new Date());
    }

    /**
     * 图片按比例大小压缩方法（根据路径获取图片并压缩）
     *
     * @param srcPath
     * @return
     */
    public static Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        if (bitmap != null) {
            return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
        }
        return null;

    }

    private static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return true=全是中文
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 防止按钮重复被点击
     */
    public static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static boolean isFastClick(int clickTime) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < clickTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 防止按钮重复被点击
     */
    private static int lastClickId = 0;

    /**
     * 防止按钮重复被点击
     *
     * @param clickId 被点击view的id
     * @return
     */
    public static boolean isFastDoubleClick(int clickId) {
        if (clickId == lastClickId) {
            return isFastDoubleClick();
        }

        lastClickTime = System.currentTimeMillis();
        lastClickId = clickId;
        return false;
    }

    /**
     * 格式化时间 20140810 格式的 成 2014-08-10
     *
     * @param billDate
     * @return
     */
    public static String formatDate(String billDate) {
        if (TextUtils.isEmpty(billDate)) {
            return "";
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            return date.format(sdf.parse(billDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * money除以100四舍五入，保留两位小数
     *
     * @param money
     * @return
     */
    public static String formatMoney(String money) {
        if (TextUtils.isEmpty(money)) {
            return "";
        }

        try {
            float floatMoney = Float.parseFloat(money);
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.HALF_UP);
            return df.format(floatMoney * 0.01);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * money除以100四舍五入，保留整数
     *
     * @param money
     * @return 发生错误或TextUtils.isEmpty(money)时返回"";
     */
    public static String formatMoneyNoDecimal(String money) {
        if (TextUtils.isEmpty(money)) {
            return "";
        }

        try {
            float floatMoney = Float.parseFloat(money);
            DecimalFormat df = new DecimalFormat("0");
            df.setRoundingMode(RoundingMode.HALF_UP);
            return df.format(floatMoney / 100);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String subDateMounth(String mBillDate) {
        int mounth = 0;
        try {
            String mMounth = mBillDate.substring(4, 6);
            mounth = Integer.parseInt(mMounth);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return mounth + "";
    }

    /**
     * 获取cpu序列号，作为唯一标识
     *
     * @return
     */
    public static String getCPUSerial() {
        String str = "", strCPU = "", cpuAddress = "0000000000000000";
        try {
            // 读取CPU信息
            Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            // 查找CPU序列号
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    // 查找到序列号所在行
                    if (str.indexOf("Serial") > -1) {
                        // 提取序列号
                        strCPU = str.substring(str.indexOf(":") + 1, str.length());
                        // 去空格
                        cpuAddress = strCPU.trim();
                        break;
                    }
                } else {
                    // 文件结尾
                    break;
                }
            }
        } catch (Exception ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return cpuAddress;
    }

    public static void showShortToast(Context context, String msg) {
        Utility.showShortToast(context, msg);
    }

    /**
     * @param context
     */
    public static void showShortToast(Context context, int rstring) {
        if (context != null) {
            String string = context.getResources().getString(rstring);
            Utility.showShortToast(context, string);
        }
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        if (TextUtils.isEmpty(cardId)) {
            return false;
        }

        cardId = cardId.replaceAll(" ", "");
        String nonCheckCodeCardId = cardId.substring(0, cardId.length() - 1);
        if (!nonCheckCodeCardId.matches("\\d+")) {
            return false;
        }

        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0 || !nonCheckCodeCardId.matches("\\d+")) {
            return false;
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return ((((luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0')) == (cardId.charAt(cardId.length() - 1))) ? true : false);
    }

    /**
     * targetFragment.setArguments(bundle);
     *
     * @param targetFragment
     * @param keys
     * @param values
     */
    public static void setFragmentBundle(Fragment targetFragment, String[] keys, Serializable[] values) {
        Bundle bundle = new Bundle();
        if (keys != null) {
            for (int i = 0; i < keys.length; i++) {
                bundle.putSerializable(keys[i], values[i]);
            }
            targetFragment.setArguments(bundle);
        }
    }

    public interface AfterReceiveResponseListener {
        void afterReceiveResponseDo();
    }

    public static void saveStringIntoConfigSP(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(Const.SharedPreferencesConfig, Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static String getStringFromConfigSP(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(Const.SharedPreferencesConfig, Context.MODE_PRIVATE);
        String string = sp.getString(key, defValue);
        return string;
    }

    /**
     * @param whitchType 0="",1="查看",2=“修改”
     */
    public static String CodeToStatusText(int whitchType) {
        String resultString = "";

        switch (whitchType) {
            case 0:
                resultString = "";
                break;
            case 1:
                resultString = "查看";
                break;
            case 2:
                resultString = "修改";
                break;
        }

        return resultString;
    }

    /**
     * @param whitchType 1="提交",2=“下一步”
     */
    public static String CodeToNextText(int whitchType) {
        String resultString = "";

        switch (whitchType) {
            case 1:
                resultString = "提交";
                break;
            case 2:
                resultString = "下一步";
                break;
        }

        return resultString;
    }

    /**
     * @param status
     * @return 1="查看" 2="修改"
     */
    public static int getStatusTextWhenClick(String status) {
        if (status.equals("21") || status.equals("24") || status.equals("44") || status.equals("25")) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * 设置textview中划线
     *
     * @param textView
     */
    public static void setTextViewLineThrough(TextView textView) {
        textView.getPaint().setAntiAlias(true);// 抗锯齿
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
        // textView.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
    }

    /**
     * 设置textview下划线
     *
     * @param textView
     */
    public static void setTextViewLineBottom(TextView textView) {
        textView.getPaint().setAntiAlias(true);// 抗锯齿
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 下划线
    }

    public static boolean isNullOr0(List list) {
        boolean result = false;
        if (list == null) {
            result = true;
        } else {
            if (list.size() == 0) {
                result = true;
            }
        }

        return result;
    }

    public static boolean isNotNull0(List list) {
        return !isNullOr0(list);
    }

    /**
     * @param context
     * @param dismissDialog                是否要使dialog消失
     * @param response
     * @param response_Data
     * @param afterReceiveResponseListener
     */
    public static void filterResponseData(Context context, boolean dismissDialog, Serializable response, Serializable response_Data, AfterReceiveResponseListener afterReceiveResponseListener) {
        if (dismissDialog) {
        }

        if (response != null) {
            if (response_Data != null) {
                if (afterReceiveResponseListener != null) {
                    afterReceiveResponseListener.afterReceiveResponseDo();
                }
            } else {
                CommonUtil.showShortToast(context, R.string.net_error);
            }
        } else {
            CommonUtil.showShortToast(context, R.string.net_error);
        }
    }

    /**
     * @param keys
     * @param values
     * @return keys == null时，返回new HashMap<String, String>();
     */
    public static Map<String, String> getRequestParams(String[] keys, String[] values) {
        Map<String, String> params = new HashMap<String, String>();
        if (keys != null) {
            for (int i = 0; i < keys.length; i++) {
                if (values[i] != null) {
                    params.put(keys[i], values[i]);
                } else {
                    params.put(keys[i], "");
                }
            }
        }

        return params;
    }

    /**
     * 1、2、3
     *
     * @param context
     * @param dialogMsg (dialogMsg != null时showdialog)
     * @param keys      (keys == null时，返回new HashMap<String, String>();)
     * @param values
     * @return 只有无网络时才返回null
     */
    public static Map<String, String> readyForNetwork(Context context, String dialogMsg, String[] keys, String[] values) {
        if (CommonUtil.isNetworkNotAvailable(context)) {
            return null;
        }

        if (dialogMsg != null) {
            // ProgressHUD.showIfNotExist(context, dialogMsg, true, true, null);
        }

        Map<String, String> params = CommonUtil.getRequestParams(keys, values);
        return params;
    }

    /**
     * 1、2、3
     *
     * @param context
     * @param showDefaultDialog (showDefaultDialog为true时showdialog)
     * @param keys              (keys == null时，返回new HashMap<String, String>();)
     * @param values
     * @return 只有无网络时才返回null
     */
    public static Map<String, String> readyForNetwork(Context context, boolean showDefaultDialog, String[] keys, String[] values) {
        if (CommonUtil.isNetworkNotAvailable(context)) {
            return null;
        }

        if (showDefaultDialog) {
            // ProgressHUD.showIfNotExist(context,
            // R.string.default_loading_progress_text, true, true, null);
            // ProgressHUD.dialog.setCanceledOnTouchOutside(false);
        }

        Map<String, String> params = CommonUtil.getRequestParams(keys, values);
        return params;
    }

    public static void showCreditSuccessToast(Context context) {
        Toast toast = new Toast(context);
        // View view = View.inflate(context, R.layout.credit_submit_success,
        // null);
        // toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static String getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String code = "-1";
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            code = String.valueOf(packInfo.versionCode);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return code;
    }

    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String name = "";
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            name = String.valueOf(packInfo.versionName);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static void setXListViewPullLoadEnable(XListView xListView, List obtainData, int pagerSize) {
        if (obtainData != null) {
            try {
                if (obtainData.size() < Integer.parseInt(Const.pageSize)) {
                    xListView.setPullLoadEnable(false);
                } else {
                    xListView.setPullLoadEnable(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static DisplayMetrics getScreenMetrics(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    public static boolean isPhoneNumberAvailable(String phone, Context mActivity) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(mActivity, R.string.input_phone_number, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phone.length() < 11) {
            Toast.makeText(mActivity, R.string.phone_num_erroe, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!p.matcher(phone).matches()) {
            Toast.makeText(mActivity, R.string.phone_num_no_press, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public static String endWithEllipsis(String originString, int showNum) {
        String str = "";
        if (TextUtils.isEmpty(originString) && showNum <= originString.length()) {
            str = originString.substring(0, showNum);
        }

        return str + "...";
    }

    public static void startActivity(Context context, Class clzz) {
        if (context != null) {
            Intent intent = new Intent(context, clzz);
            context.startActivity(intent);
        }
    }

    public static boolean softKeyBoardChange = false;

    public static void openSoftKeyBoard(Context context) {
        if (context != null) {
            softKeyBoardChange = true;
            // Configuration config = context.getResources().getConfiguration();
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void closeSoftKeyBoard(Activity activity) {
        if (activity != null) {
            softKeyBoardChange = true;
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null && activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public static void closeSoftKeyBoard(Activity activity, View view) {
        if (activity != null) {
            softKeyBoardChange = true;
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null && activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static void setRegistAgreementTextStyle(final Context context, TextView tv_info) {

        SpannableString spannable = new SpannableString(context.getResources().getString(R.string.tips_readme));
        spannable.setSpan(new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(context, CustomWebActivity.class);
                intent.putExtra(Const.customWebUrlKey, Const.fuWuXieYiUrl);
                intent.putExtra(Const.customWebTitleKey, "服务条款");
                context.startActivity(intent);
            }
        }, 10, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannable.setSpan(new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(context, CustomWebActivity.class);
                intent.putExtra(Const.customWebUrlKey, Const.privateXieYiUrl);
                intent.putExtra(Const.customWebTitleKey, "隐私协议");
                context.startActivity(intent);
            }
        }, 15, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_info.setText(spannable);
        tv_info.setMovementMethod(LinkMovementMethod.getInstance());
    }


    public static void closeSoftKeyBoard(Window window, Context context) {
        if (window != null && context != null) {
            softKeyBoardChange = true;
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null && window.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(window.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    /**
     * 根据图片大小和imageview的width或者height等比例设置imageview的另一个参数
     *
     * @param context
     * @param iv
     * @param viewWidth   单位px
     * @param viewHeight  单位px
     * @param imageWidth  单位px
     * @param imageHeight 单位px
     */
    public static void setViewHeightWidthByImageLengthWidthRatio(Context context, ImageView iv, int viewWidth, int viewHeight, int imageWidth, int imageHeight) {
        iv.setScaleType(ScaleType.FIT_XY);

        if (viewHeight == -1) {
            // 宽度确定，高度不确定
            int height = viewWidth * imageHeight / imageWidth;
            iv.getLayoutParams().height = height;
            iv.getLayoutParams().width = viewWidth;
        } else if (viewWidth == -1) {
            int width = viewHeight * imageWidth / imageHeight;
            iv.getLayoutParams().height = imageHeight;
            iv.getLayoutParams().width = width;
        }

    }

    public static String ToAllFullWidthString(String input) {
        try {
            if (!TextUtils.isEmpty(input)) {
                char[] c = input.toCharArray();
                for (int i = 0; i < c.length; i++) {
                    if (c[i] == 12288) {
                        c[i] = (char) 32;
                        continue;
                    }
                    if (c[i] > 65280 && c[i] < 65375)
                        c[i] = (char) (c[i] - 65248);
                }
                return new String(c);
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getImageUrl(String userId, String hashValue) {
        return Utility.getImageFilePath(Const.IMAGE_PATH_USERCOVER, userId, hashValue, Const.IMAGE_SIZE_LARGE);
    }


    /**
     * 将有效时间转成2015-06-03
     *
     * @param expiresIn 秒
     * @return
     */
    public static String getExpiresIn(String expiresIn) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        long long1 = 0;
        Date date = null;
        try {
            long1 = Long.parseLong(expiresIn);
            long mills = System.currentTimeMillis() + long1 * 1000;
            date = new Date(mills);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (date != null) {
            return format.format(date);
        } else {
            return "";
        }

    }

  

    public static String getIMEI(Context context) {
        String Imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        return Imei;
    }

    /**
     * @param str it's format is "yyyy-MM-dd HH:mm:ss"
     * @return if cause ParseException return 0; otherwise return millis for the
     * date string
     */
    public static long parseStringToMillis(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = 0;
        try {
            time = format.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

  
    /**
     * @param str it's format is "yyyy-MM-dd HH:mm:ss"
     */
    public static String formatMillisToString(long millis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millis);
        String str = "";
        str = format.format(date);
        return str;
    }

    /**
     * 默认options
     *
     * @return
     */
    public static DisplayImageOptions getImageLoaderDefaultOptions(Drawable emptyDrawable) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(emptyDrawable).showImageForEmptyUri(emptyDrawable).showImageOnFail(emptyDrawable).cacheInMemory(false).cacheOnDisk(true).bitmapConfig(Config.RGB_565)
                // .displayer(new FadeInBitmapDisplayer(500))
                .build();

        return options;
    }

    /**
     * 圆形options
     *
     * @param emptyDrawable
     * @param round
     * @return
     */
    public static DisplayImageOptions getImageLoaderRoundOptions(Drawable emptyDrawable, int round) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(emptyDrawable)
                // set round image
                .showImageForEmptyUri(emptyDrawable).showImageOnFail(emptyDrawable).cacheInMemory(false).cacheOnDisk(true).bitmapConfig(Config.RGB_565).displayer(new RoundedBitmapDisplayer(round)).build();

        return options;
    }

    public static String inputStream2String(InputStream is) {
        String string;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        try {
            while ((i = is.read()) != -1) {
                baos.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
            string = "";
        }
        string = baos.toString();
        return string;
    }

    
    public static void cancelAsyncTask(AsyncTask dataTask) {
        if (dataTask != null && dataTask.getStatus() != AsyncTask.Status.FINISHED)
            dataTask.cancel(true);
    }

    public static void cancelAsyncTaskArray(AsyncTask[] dataTasks) {
        if (dataTasks != null) {
            for (int i = 0; i < dataTasks.length; i++) {
                if (dataTasks[i] != null && dataTasks[i].getStatus() != AsyncTask.Status.FINISHED)
                    dataTasks[i].cancel(true);
            }
            dataTasks = null;
            System.gc();
        }
    }

    public static void recycleBitmap(Bitmap decodeFile) {
        if (decodeFile != null && !decodeFile.isRecycled()) {
            decodeFile.recycle();
            decodeFile = null;
            System.gc();
        }
    }

    public static void clearImageLoaderCache() {
        ImageLoader.getInstance().clearMemoryCache();
        System.gc();
    }

    public static void setOjectsNull(Object[] objects) {
        if (objects != null) {
            for (int i = 0; i < objects.length; i++) {
                objects[i] = null;
            }
        }
        objects = null;
        System.gc();
    }

    public static Pattern getUrlPattern() {
        return Pattern.compile("^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$", Pattern.CASE_INSENSITIVE);
    }

    /**
     * 获取MAC
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }
    
}
