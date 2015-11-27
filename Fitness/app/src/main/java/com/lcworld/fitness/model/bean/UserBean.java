package com.lcworld.fitness.model.bean;

import java.io.Serializable;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Transient;

/**
 * data:{ "email": "", "id": 1000113, "lockStatus": 0, "loginName": "",
 * "mobile": "15600606023", "regTime": "2014-07-25 10:07:46", "userType": 100 }
 */
public class UserBean implements Serializable {
	public static final String USERINFOJSONSTRING = "userInfoJsonString";
	private static final long serialVersionUID = 714865000583703054L;
	public static final String UNLOGINUSERID = "0";
	
	// 服务器返回的
	@Id
	public String id;	// userId
	public String loginName;
	public String mobile;
	public String userType;		//用户类型
	public String lockStatus;
	public String email;
	public String regTime;

	// 自己加的
	public String password;
	public boolean isAutoLogin;//是否自动登录
	public long loginTime;// 登录时间
	
	@Override
	public String toString() {
		return "UserBean [id=" + id + ", loginName=" + loginName + ", mobile=" + mobile + ", userType=" + userType + ", lockStatus=" + lockStatus + ", email=" + email + ", regTime=" + regTime + ", password=" + password + ", isAutoLogin=" + isAutoLogin + ", loginTime=" + loginTime + "]";
	}
	

	

	
	
	
	

	
	
	
}
