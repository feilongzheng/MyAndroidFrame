package com.android.volley.mynet;

import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.lcworld.fitness.framework.util.IOUtils;
import com.lcworld.fitness.framework.util.LogUtil;

public class RequestClass extends JsonRequest<BaseBean> {

	//	http://test.pengsi.cn:7060/service/browser/v2_getuserfisrtconnection.jsp
	private String requestUrl;
	private int requestMethod;
	private Class resultClzz;
	private boolean isDecodeResponse;
	

	public RequestClass(boolean isDecodeResponse, int requestMethod, String requestUrl, Class resultClzz, Listener<BaseBean> listener, ErrorListener errorListener) {
		super(requestMethod, requestUrl, listener, errorListener);

		this.isDecodeResponse = isDecodeResponse;
		this.requestUrl = requestUrl;
		this.requestMethod = requestMethod;
		this.resultClzz = resultClzz;
	}


	@Override
	protected Response<BaseBean> parseNetworkResponse(NetworkResponse response) {
		try {
			String responseString = "";
			if (isDecodeResponse) {
				responseString = IOUtils.toString(response.data);
			}else {
				responseString = new String(response.data);
			}
			
			LogUtil.log("返回="+responseString);
			BaseBean info= (BaseBean) new Gson().fromJson(responseString, resultClzz);
			info.originResultString = responseString;

			if ( !isDecodeResponse && TextUtils.isEmpty(info.resultCode)) {
				info.resultCode = "0";
			}
			try {
				info.resultCode2 = Integer.parseInt(info.resultCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return Response.success(info, HttpHeaderParser.parseCacheHeaders(response));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.error(new VolleyError(response));
	}







}
