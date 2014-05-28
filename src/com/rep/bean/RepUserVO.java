
package com.rep.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * 关于erp用户表的实体bean.
 * @author www(水清)
 * 任何人和公司可以传播并且修改本程序，但是不得去掉本段声明以及作者署名.
 * http://www.iteye.com
 */ 
public class RepUserVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public RepUserVO() {

	}
	
	public RepUserVO( int sno , String brandName , String brandType , double area , String address , double masterPrice , int workNum , String workTime , String workTimeNum , int weekendNum , String phone , String password , String param1 , String lng_north , Date inDate , String lat_east , String location , String param2 ) {
		 this.sno = sno;
		 this.brandName = brandName;
		 this.brandType = brandType;
		 this.area = area;
		 this.address = address;
		 this.masterPrice = masterPrice;
		 this.workNum = workNum;
		 this.workTime = workTime;
		 this.workTimeNum = workTimeNum;
		 this.weekendNum = weekendNum;
		 this.phone = phone;
		 this.password = password;
		 this.param1 = param1;
		 this.lng_north = lng_north;
		 this.inDate = inDate;
		 this.lat_east = lat_east;
		 this.location = location;
		 this.param2 = param2;
	}
	
	public RepUserVO(String brandName ,String brandType ,double area ,String address ,double masterPrice ,int workNum ,String workTime ,String workTimeNum ,int weekendNum ,String phone ,String password ,String param1 ,String lng_north ,Date inDate ,String lat_east ,String location ,String param2 ) {
			 this.brandName = brandName;
			 this.brandType = brandType;
			 this.area = area;
			 this.address = address;
			 this.masterPrice = masterPrice;
			 this.workNum = workNum;
			 this.workTime = workTime;
			 this.workTimeNum = workTimeNum;
			 this.weekendNum = weekendNum;
			 this.phone = phone;
			 this.password = password;
			 this.param1 = param1;
			 this.lng_north = lng_north;
			 this.inDate = inDate;
			 this.lat_east = lat_east;
			 this.location = location;
			 this.param2 = param2;
	}
	 
	private Integer sno; 
 	/**
 	 * 获取流水号的属性值.
 	 */
 	public Integer getSno(){
 		return sno;
 	}
 	
 	/**
 	 * 设置流水号的属性值.
 	 */
 	public void setSno(Integer sno){
 		this.sno = sno;
 	}
	private String brandName; 
 	/**
 	 * 获取品牌名称的属性值.
 	 */
 	public String getBrandName(){
 		return brandName;
 	}
 	
 	/**
 	 * 设置品牌名称的属性值.
 	 */
 	public void setBrandName(String brandname){
 		this.brandName = brandname;
 	}
	private String brandType; 
 	/**
 	 * 获取品类的属性值.
 	 */
 	public String getBrandType(){
 		return brandType;
 	}
 	
 	/**
 	 * 设置品类的属性值.
 	 */
 	public void setBrandType(String brandtype){
 		this.brandType = brandtype;
 	}
	private Double area; 
 	/**
 	 * 获取营业面积的属性值.
 	 */
 	public Double getArea(){
 		return area;
 	}
 	
 	/**
 	 * 设置营业面积的属性值.
 	 */
 	public void setArea(Double area){
 		this.area = area;
 	}
	private String address; 
 	/**
 	 * 获取店铺地址的属性值.
 	 */
 	public String getAddress(){
 		return address;
 	}
 	
 	/**
 	 * 设置店铺地址的属性值.
 	 */
 	public void setAddress(String address){
 		this.address = address;
 	}
	private Double masterPrice; 
 	/**
 	 * 获取主力单价的属性值.
 	 */
 	public Double getMasterPrice(){
 		return masterPrice;
 	}
 	
 	/**
 	 * 设置主力单价的属性值.
 	 */
 	public void setMasterPrice(Double masterprice){
 		this.masterPrice = masterprice;
 	}
	private Integer workNum; 
 	/**
 	 * 获取人流量-工作日的属性值.
 	 */
 	public Integer getWorkNum(){
 		return workNum;
 	}
 	
 	/**
 	 * 设置人流量-工作日的属性值.
 	 */
 	public void setWorkNum(Integer worknum){
 		this.workNum = worknum;
 	}
	private String workTime; 
 	/**
 	 * 获取营业时间的属性值.
 	 */
 	public String getWorkTime(){
 		return workTime;
 	}
 	
 	/**
 	 * 设置营业时间的属性值.
 	 */
 	public void setWorkTime(String worktime){
 		this.workTime = worktime;
 	}
	private String workTimeNum; 
 	/**
 	 * 获取营业时间的属性值.
 	 */
 	public String getWorkTimeNum(){
 		return workTimeNum;
 	}
 	
 	/**
 	 * 设置营业时间的属性值.
 	 */
 	public void setWorkTimeNum(String worktimenum){
 		this.workTimeNum = worktimenum;
 	}
	private Integer weekendNum; 
 	/**
 	 * 获取人流量-周末的属性值.
 	 */
 	public Integer getWeekendNum(){
 		return weekendNum;
 	}
 	
 	/**
 	 * 设置人流量-周末的属性值.
 	 */
 	public void setWeekendNum(Integer weekendnum){
 		this.weekendNum = weekendnum;
 	}
	private String phone; 
 	/**
 	 * 获取联系方式的属性值.
 	 */
 	public String getPhone(){
 		return phone;
 	}
 	
 	/**
 	 * 设置联系方式的属性值.
 	 */
 	public void setPhone(String phone){
 		this.phone = phone;
 	}
	private String password; 
 	/**
 	 * 获取密码的属性值.
 	 */
 	public String getPassword(){
 		return password;
 	}
 	
 	/**
 	 * 设置密码的属性值.
 	 */
 	public void setPassword(String password){
 		this.password = password;
 	}
	private String param1; 
 	/**
 	 * 获取额外参数1的属性值.
 	 */
 	public String getParam1(){
 		return param1;
 	}
 	
 	/**
 	 * 设置额外参数1的属性值.
 	 */
 	public void setParam1(String param1){
 		this.param1 = param1;
 	}
	private String lng_north; 
 	/**
 	 * 获取纬度的属性值.
 	 */
 	public String getLng_north(){
 		return lng_north;
 	}
 	
 	/**
 	 * 设置纬度的属性值.
 	 */
 	public void setLng_north(String lng_north){
 		this.lng_north = lng_north;
 	}
	private Date inDate; 
 	/**
 	 * 获取注册时间的属性值.
 	 */
 	public Date getInDate(){
 		return inDate;
 	}
 	
 	/**
 	 * 设置注册时间的属性值.
 	 */
 	public void setInDate(Date indate){
 		this.inDate = indate;
 	}
	private String lat_east; 
 	/**
 	 * 获取经度的属性值.
 	 */
 	public String getLat_east(){
 		return lat_east;
 	}
 	
 	/**
 	 * 设置经度的属性值.
 	 */
 	public void setLat_east(String lat_east){
 		this.lat_east = lat_east;
 	}
	private String location; 
 	/**
 	 * 获取位置的属性值.
 	 */
 	public String getLocation(){
 		return location;
 	}
 	
 	/**
 	 * 设置位置的属性值.
 	 */
 	public void setLocation(String location){
 		this.location = location;
 	}
	private String param2; 
 	/**
 	 * 获取额外参数2的属性值.
 	 */
 	public String getParam2(){
 		return param2;
 	}
 	
 	/**
 	 * 设置额外参数2的属性值.
 	 */
 	public void setParam2(String param2){
 		this.param2 = param2;
 	}
}
