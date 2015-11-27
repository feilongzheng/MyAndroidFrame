package com.android.volley.mynet;

import java.io.Serializable;

public class BaseBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5331198215023944098L;
	public String resultCode;
	public int resultCode2 = Integer.MAX_VALUE;
	public String message;
	public String originResultString;
}
