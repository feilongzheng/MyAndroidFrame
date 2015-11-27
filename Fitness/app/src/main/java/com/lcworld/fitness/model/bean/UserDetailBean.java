package com.lcworld.fitness.model.bean;

import java.io.Serializable;

public class UserDetailBean implements Serializable {

	/**
	 * "id": "1000000", "trueName": "罗金川", "nickName": "kingsun", "email":
	 * "13011102500@163.com", "mobile": "13011102500", "loginName": "macroCYH",
	 * "userType": "100", "lockStatus": "0", "regTime": "2014-07-03 11:11:25",
	 * "headImg": "http://124.205.55.210:9090/upfiles/image/head.jpg", "sex":
	 * "1", "birthDate": "1982-01-01", "address": "北京朝阳", "education": "3",
	 * "workUnit": "嘉鑫XXXX", "zipCode": "100080", "cardNo":
	 * "543000000000000000", "disName": "朝阳区", "tradingName": "常营"
	 */
	private static final long serialVersionUID = 112319078681953510L;
	/**
	 * id
	 */
	public String id;
	/**
	 * 真实姓名
	 */
	public String trueName;
	/**
	 * 昵称
	 */
	public String nickName;
	/**
	 * 电子邮箱
	 */
	public String email;
	/**
	 * 移动电话
	 */
	public String mobile;
	/**
	 * 用户名
	 */
	public String loginName;
	/**
	 * 用户类型:0：无效，100：普通用户，200：教练
	 */
	public String userType;
	/**
	 * 锁定状态
	 */
	public String lockStatus;
	/**
	 * 注册时间
	 */
	public String regTime;
	/**
	 * 头像地址
	 */
	public String headImg;
	/**
	 * 头像背景
	 */
	public String bgImg;
	/**
	 * 性别:1男，2女，0不详
	 */
	public int sex;
	/**
	 * 生日
	 */
	public String birthDate;
	/**
	 * 地址
	 */
	public String address;
	/**
	 * 学历：0、不详；1、高中/中专及以下；2、大专；3、本科；4、研究生；5、博士及以上
	 */
	public String education;
	/**
	 * 工作单位
	 */
	public String workUnit;
	/**
	 * 邮政编码
	 */
	public String zipCode;
	/**
	 * 身份证号
	 */
	public String cardNo;
	/**
	 * 所在地区
	 */
	public String disName;
	/**
	 * 所在商圈
	 */
	public String tradingName;
	/**
	 * 积分
	 */
	public int totalScore;

}
