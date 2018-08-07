/**  
* @Project: xproduct-shiro
* @Title: MyAuthorizationInfo.java
* @Package cn.com.checknull.shiro.authz
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com 273244373
* @date 2015-11-24 上午11:42:54
* @Copyright: 2015 check_null All Rights Reserved.
* @version v1.0  
*/

package cn.com.checknull.shiro.authz;

import java.util.List;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

import cn.com.checknull.model.po.TSysmenu;

/**
 * @ClassName MyAuthorizationInfo
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-11-24   上午11:42:54
 */
public class MyAuthorizationInfo extends SimpleAuthorizationInfo {

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 7607077397716606001L;
	
	private List<TSysmenu> tsysmenus;

	public List<TSysmenu> getTsysmenus() {
		return tsysmenus;
	}

	public void setTsysmenus(List<TSysmenu> tsysmenus) {
		this.tsysmenus = tsysmenus;
	}

}

