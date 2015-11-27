package com.lcworld.fitness.framework.bean;

import java.io.Serializable;

/**
 * TODO BaseResponse是基类
 */
public abstract class BaseResponse implements Serializable {

	private static final long serialVersionUID = 5375804597574885028L;
	public int errorCode = -10000;
	public String msg;

	@Override
	public String toString() {
		return "BaseResponse [errorCode=" + errorCode + ", msg=" + msg + "]";
	}

}
