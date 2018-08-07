/**  
* @Project: xproduct-shiro
* @Title: ServiceRealm.java
* @Package cn.com.checknull.shiro.realm
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com 273244373
* @date 2015-9-20 下午4:49:45
* @Copyright: 2015 check_null All Rights Reserved.
* @version v1.0  
*/

package cn.com.checknull.shiro.realm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;

import cn.com.checknull.constant.enumer.SessionAttribute;
import cn.com.checknull.model.po.TOperator;
import cn.com.checknull.model.po.TOperatorRole;
import cn.com.checknull.model.po.TSysmenu;
import cn.com.checknull.model.po.TSyspriv;
import cn.com.checknull.model.po.TSysrole;
import cn.com.checknull.model.po.TSysroleMenu;
import cn.com.checknull.model.po.TSysrolePriv;
import cn.com.checknull.service.TOperatorRoleService;
import cn.com.checknull.service.TOperatorService;
import cn.com.checknull.service.TSysmenuService;
import cn.com.checknull.service.TSysprivService;
import cn.com.checknull.service.TSysroleMenuService;
import cn.com.checknull.service.TSysrolePrivService;
import cn.com.checknull.service.TSysroleService;
import cn.com.checknull.shiro.authz.MyAuthorizationInfo;

/**
 * @ClassName ServiceRealm
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-9-20   下午4:49:45
 */
public class ServiceRealm extends AuthorizingRealm {

	@Resource(name="toperatorService")
	private TOperatorService toperatorService;
	@Resource(name="toperatorRoleService")
	private TOperatorRoleService toperatorRoleService;
	@Resource(name="tsysroleService")
	private TSysroleService tsysroleService;
	@Resource(name="tsysroleMenuService")
	private TSysroleMenuService tsysroleMenuService;
	@Resource(name="tsysmenuService")
	private TSysmenuService tsysmenuService;
	@Resource(name="tsysprivService")
	private TSysprivService tsysprivService;
	@Resource(name="tsysrolePrivService")
	private TSysrolePrivService tsysrolePrivService;
	private static final int ERROR_TIMES = 5;

	@Override
	public AuthorizationInfo getAuthorizationInfo(
			PrincipalCollection principals) {
		return super.getAuthorizationInfo(principals);
	}

	/**
	 * (非 Javadoc) 
	* <p>Title: doGetAuthorizationInfo</p> 
	* <p>Description: 获取授权信息</p> 
	* @param principals
	* @return 
	* @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected MyAuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		if (principals == null) {
            throw new AuthorizationException("参数不能为空");
        }
		MyAuthorizationInfo authorizationInfo  = new MyAuthorizationInfo();
		try{
			String username = (String) getAvailablePrincipal(principals);
			TOperator tmpTOperator = toperatorService.findByLoginName(username);
		    if (tmpTOperator == null){
		    	throw new UnknownAccountException("账号错误");
		    }
		    //查询操作员拥有的角色
		    TOperatorRole toperatorRole = toperatorRoleService.findByOperatorId(tmpTOperator.getId());
		    if (toperatorRole == null){
		    	throw new AuthenticationException("无权访问");
		    }
		    //多个角色以逗号分隔
		    String roleIds = toperatorRole.getRoleIds();
		    if (StringUtils.isEmpty(roleIds)){
		    	throw new AuthenticationException("关联角色，数据错误");
		    }
		    String[] arrayRoleIds = roleIds.split(",");
		    
		    TSysrole tsysrole;
			TSysroleMenu tsysroleMenu;
			TSysrolePriv tsysrolePriv;
		    long roleId;
		    StringBuilder sbMenuIds = new StringBuilder();
		    StringBuilder sbPrivIds = new StringBuilder();
		    for (String strRoleId : arrayRoleIds){
		    	roleId = NumberUtils.toLong(strRoleId);
		    	tsysrole = tsysroleService.find(roleId);
		    	if (tsysrole == null){
		    		throw new AuthenticationException("操作员角色，数据错误");
		    	}
		    	//添加角色
		    	authorizationInfo.addRole(roleId + "");
		    	tsysrolePriv = tsysrolePrivService.findByRoleId(roleId);
		    	tsysroleMenu = tsysroleMenuService.findByRoleId(roleId);
		    	if (tsysroleMenu != null){
		    		 if (StringUtils.isEmpty(tsysroleMenu.getMenuIds())){
		    		    	//throw new AuthenticationException("角色菜单，数据错误");
		    			 continue;
		    		 }
		    		 sbMenuIds.append(tsysroleMenu.getMenuIds()).append(",");
		    	}
		    	if (tsysrolePriv != null){
		    		if (StringUtils.isEmpty(tsysrolePriv.getPrivIds())){
	    		    	//throw new AuthenticationException("角色权限，数据错误");
		    			continue;
		    		}
		    		sbPrivIds.append(tsysrolePriv.getPrivIds()).append(",");
		    	}
		    }
		    String menuIds = sbMenuIds.toString();
		    if(!StringUtils.isEmpty(menuIds)){
		    	menuIds = StringUtils.removeEnd(menuIds, ",");
		    }
		    String privIds = sbPrivIds.toString();
		    Set<String> setPrivIds = null;
		    if(!StringUtils.isEmpty(privIds)){
		    	privIds = StringUtils.removeEnd(privIds, ",");
		    	setPrivIds = new HashSet<String>();
		    	for (String privId : privIds.split(",")){
		    		setPrivIds.add(privId);
		    	}
		    }
		    
		    List<TSysmenu> tsysmenus = tsysmenuService.find(menuIds);
		    List<TSyspriv> tsysprivs;
		    String wildcardString = null;
		    //用于前段动态获取权限
			Map<String, List<TSyspriv>> permission = new LinkedHashMap<String, List<TSyspriv>>();
			//单个菜单拥有权限集合
			List<TSyspriv> haveTSyspriv;
		    if (tsysmenus != null && !tsysmenus.isEmpty() && setPrivIds != null){
		    	 for (TSysmenu tsysmenu : tsysmenus){
		    		 tsysprivs = tsysprivService.findByMenuId(tsysmenu.getId());
		    		 if (tsysprivs == null || tsysprivs.isEmpty()){
		    			 continue;
		    		 }
		    		 haveTSyspriv = new ArrayList<TSyspriv>();
		    		 boolean contains = false;
		    		 for (TSyspriv tsyspriv : tsysprivs){
		    			 if (setPrivIds.contains(tsyspriv.getId() + "")){
		    				 contains = true;
		    				 wildcardString = tsysmenu.getCode() + ":" + tsyspriv.getCode();
			    			 authorizationInfo.addObjectPermission(new WildcardPermission(wildcardString));
			    			 haveTSyspriv.add(tsyspriv);
		    			 }
		    		 }
		    		 if (contains){
		    			 wildcardString = tsysmenu.getCode() + ":list";
			    		 authorizationInfo.addObjectPermission(new WildcardPermission(wildcardString));
			    		 permission.put(tsysmenu.getCode(), haveTSyspriv);
		    		 }
		    	 }
		    	 authorizationInfo.setTsysmenus(tsysmenus);
		    	 ServletRequest request = ((WebDelegatingSubject) SecurityUtils.getSubject()).getServletRequest();
		    	 HttpSession httpSession = ((HttpServletRequest)request).getSession();
		    	 httpSession.setAttribute(SessionAttribute.PERMISSION.getAttribute(), permission);
		    }
		}catch (SQLException e){
			throw new AuthenticationException("SQL异常", e);
		}
		return authorizationInfo;
	}

	/**
	 * (非 Javadoc) 
	* <p>Title: doGetAuthenticationInfo</p> 
	* <p>Description: 获取身份验证信息</p> 
	* @param token
	* @return
	* @throws AuthenticationException 
	* @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		if (token == null) {
            throw new AuthorizationException("参数不能为空");
        }
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
	    String username = upToken.getUsername();
	    String password = new String(upToken.getPassword());
	    if (StringUtils.isEmpty(username)) {
            throw new AccountException("账号不允许为空");
        }
	    if (StringUtils.isEmpty(password)) {
            throw new AccountException("密码不允许为空");
        }
	    SimpleAuthenticationInfo info = null;
	    try{
		    TOperator tmpTOperator = toperatorService.findByLoginName(username);
		    if (tmpTOperator == null){
		    	throw new UnknownAccountException("账号/密码错误");
		    }
		    if (tmpTOperator.getErrorTimes() >= ERROR_TIMES){
				throw new IncorrectCredentialsException("密码错误" + ERROR_TIMES + "次,账号被锁定,请联系管理员");
			}
		    info = new SimpleAuthenticationInfo(username, tmpTOperator.getPassword().toCharArray(), getName());
		    boolean matchResult = getCredentialsMatcher().doCredentialsMatch(upToken, info);
			if (!matchResult){
		    	tmpTOperator.setErrorTimes(tmpTOperator.getErrorTimes() + 1);
		    	toperatorService.mod(tmpTOperator);
		    	if (tmpTOperator.getErrorTimes() == ERROR_TIMES){
		    		throw new IncorrectCredentialsException("密码错误" + ERROR_TIMES + "次,账号被锁定,请联系管理员");
				}else{
					throw new IncorrectCredentialsException("密码错误" + tmpTOperator.getErrorTimes() + "次,错误" + ERROR_TIMES + "次,将被锁定");
				}
		    }
		    if (tmpTOperator.getStatus() == 0){
		    	throw new DisabledAccountException("账号不可用");
		    }
	    }catch (SQLException e){
	    	throw new AuthenticationException("SQL异常异常", e);
	    }
	    return info;
	}

	public TOperatorService getToperatorService() {
		return toperatorService;
	}

	public void setToperatorService(TOperatorService toperatorService) {
		this.toperatorService = toperatorService;
	}

	public TOperatorRoleService getToperatorRoleService() {
		return toperatorRoleService;
	}

	public void setToperatorRoleService(TOperatorRoleService toperatorRoleService) {
		this.toperatorRoleService = toperatorRoleService;
	}

	public TSysroleService getTsysroleService() {
		return tsysroleService;
	}

	public void setTsysroleService(TSysroleService tsysroleService) {
		this.tsysroleService = tsysroleService;
	}

	public TSysroleMenuService getTsysroleMenuService() {
		return tsysroleMenuService;
	}

	public void setTsysroleMenuService(TSysroleMenuService tsysroleMenuService) {
		this.tsysroleMenuService = tsysroleMenuService;
	}

	public TSysmenuService getTsysmenuService() {
		return tsysmenuService;
	}

	public void setTsysmenuService(TSysmenuService tsysmenuService) {
		this.tsysmenuService = tsysmenuService;
	}

	public TSysprivService getTsysprivService() {
		return tsysprivService;
	}

	public void setTsysprivService(TSysprivService tsysprivService) {
		this.tsysprivService = tsysprivService;
	}

	public TSysrolePrivService getTsysrolePrivService() {
		return tsysrolePrivService;
	}

	public void setTsysrolePrivService(TSysrolePrivService tsysrolePrivService) {
		this.tsysrolePrivService = tsysrolePrivService;
	}
}

