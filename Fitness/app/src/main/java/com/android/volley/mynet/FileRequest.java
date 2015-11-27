package com.android.volley.mynet;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.lcworld.fitness.framework.util.IOUtils;
import com.lcworld.fitness.framework.util.LogUtil;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.http.client.multipart.content.StringBody;

/**
 * Created by Administrator on 2015/8/31.
 */
public class FileRequest extends Request<BaseBean> {

    private MultipartEntity entity = new MultipartEntity();
    private int requestMethod = Method.POST;

    private final Response.Listener<BaseBean> mListener;
    private boolean isDecodeResponse;
    private Map<String, File> mFileParts;
    private Map<String, String> mParams;
    private Class resultClzz;


    /**
     * @param url
     * @param errorListener
     * @param listener
     * @param files
     * @param params
     */
    public FileRequest(boolean isDecodeResponse, String url, Map<String, File> files, Map<String, String> params, Class resultClzz, Response.Listener<BaseBean> listener, Response.ErrorListener errorListener
                       ) {

        super(Method.POST, url, errorListener);
        this.isDecodeResponse = isDecodeResponse;
        mListener = listener;
        mFileParts = files;
        mParams = params;
        this.resultClzz = resultClzz;
        buildMultipartEntity();
    }

    private void buildMultipartEntity() {
        try {
            if (mParams != null && mParams.size() > 0) {
                for (Map.Entry<String, String> entry : mParams.entrySet()) {
                    entity.addPart(
                            entry.getKey(),
                            new StringBody(entry.getValue(), Charset
                                    .forName("UTF-8")));
                }
            }
            if (mFileParts != null && mFileParts.size() > 0) {
                for (Map.Entry<String, File> me : mFileParts.entrySet()) {
                    entity.addPart(me.getKey(), new FileBody(me.getValue()));
                }
                long l = entity.getContentLength();
            }
        } catch (UnsupportedEncodingException e) {
            VolleyLog.e("UnsupportedEncodingException");
        }
    }

    @Override
    public String getBodyContentType() {
        return entity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }


    /*
     * (non-Javadoc)
     *
     * @see com.android.volley.Request#getHeaders()
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        VolleyLog.d("getHeaders");
        Map<String, String> headers = super.getHeaders();

        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }


        return headers;
    }

    @Override
    protected Response<BaseBean> parseNetworkResponse(NetworkResponse response) {
        try {
            String responseString = "";
            if (isDecodeResponse) {
                responseString = IOUtils.toString(response.data);
            } else {
                responseString = new String(response.data);
            }

            LogUtil.log("返回值=" + responseString);
            BaseBean info = (BaseBean) new Gson().fromJson(responseString, resultClzz);
            info.originResultString = responseString;

            if (!isDecodeResponse && TextUtils.isEmpty(info.resultCode)) {
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

    @Override
    protected void deliverResponse(BaseBean response) {
        mListener.onResponse(response);
    }
}
