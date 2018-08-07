/**  
* @Project: xproduct-spmvc
* @Title: IndexCtrl.java
* @Package cn.com.checknull.controller
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2016-6-19 下午2:29:27
* @version v1.0  
*/

package cn.com.checknull.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.checknull.model.po.TSysmenu;
import cn.com.checknull.shiro.authz.MyAuthorizationInfo;
import cn.com.checknull.shiro.realm.ServiceRealm;

/**
 * @ClassName IndexCtrl
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2016-6-19   下午2:29:27
 */
@Controller
@RequestMapping("/")
public class IndexCtrl extends BaseCtrl{
	
	@Resource(name="serviceRealm")
	private ServiceRealm serviceRealm;
	
	public ServiceRealm getServiceRealm() {
		return serviceRealm;
	}

	public void setServiceRealm(ServiceRealm serviceRealm) {
		this.serviceRealm = serviceRealm;
	}

	@RequiresAuthentication
	@RequestMapping(value = "/index.do")
	public String index(ModelMap model){
		try{
			MyAuthorizationInfo tmpMyAuthorizationInfo = (MyAuthorizationInfo) serviceRealm.getAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
			List<TSysmenu> tsysmenus = tmpMyAuthorizationInfo.getTsysmenus();
			model.addAttribute("tsysmenus", tsysmenus);
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new UnauthorizedException(e);
		}
		return "index-success";
	}
	
	@RequiresAuthentication
	@RequestMapping(value = "/welcome.do")
	public String welcome(){
		return "welcome-success";
	}
}

