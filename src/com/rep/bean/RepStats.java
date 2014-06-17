package com.rep.bean;

import java.util.Date;
/**
 * 关于erp统计记录的业务类接口
 * @author www(水清)
 * 任何人和公司可以传播并且修改本程序，但是不得去掉本段声明以及作者署名.
 * http://www.iteye.com
 */ 
public interface RepStats   {  
 	/**
 	 * 获取流水号的属性值.
 	 */
 	public  Integer   getSno();
 	/**
 	 * 获取输入日期的属性值.
 	 */
 	public  Date   getInputDate();
 	/**
 	 * 获取统计数据1的属性值.
 	 */
 	public  double   getStatis1();
 	/**
 	 * 获取统计数据2的属性值.
 	 */
 	public  double   getStatis2();
 	/**
 	 * 获取统计数据3的属性值.
 	 */
 	public  double   getStatis3();
 	/**
 	 * 获取统计数据4的属性值.
 	 */
 	public  double   getStatis4();
 	/**
 	 * 获取统计数据5的属性值.
 	 */
 	public  double   getStatis5();
 	/**
 	 * 获取统计数据6的属性值.
 	 */
 	public  double   getStatis6();
 	/**
 	 * 获取统计用户的属性值.
 	 */
 	public  int   getUserId();
 	/**
 	 * 获取rpi数值的属性值.
 	 */
 	public  double   getRpi();
 	/**
 	 * 获取排名的属性值.
 	 */
 	public  double   getRank();
 	/**
 	 * 获取问题的属性值.
 	 */
 	public  String   getProblem();
 	/**
 	 * 获取额外参数2的属性值.
 	 */
 	public  String   getParam2();
 	/**
 	 * 获取额外参数1的属性值.
 	 */
 	public  String   getParam1();
}
