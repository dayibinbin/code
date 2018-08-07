/**  
* @Project: xdata-report
* @Title: PermissionInterceptor.java
* @Package cn.com.checknull.interceptor
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2016-8-16 下午4:17:49
* @version v1.0  
*/

package cn.com.checknull.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.com.checknull.constant.PrivCode;
import cn.com.checknull.constant.enumer.SessionAttribute;
import cn.com.checknull.model.po.TSyspriv;

/**
 * @ClassName PermissionInterceptor
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2016-8-16   下午4:17:49
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView == null){
			super.postHandle(request, response, handler, modelAndView);
			return;
		}
		if (!handler.getClass().isAssignableFrom(HandlerMethod.class)){
			super.postHandle(request, response, handler, modelAndView);
			return;
		}
		RequiresPermissions tmpRequiresPermissions = ((HandlerMethod) handler).getMethodAnnotation(RequiresPermissions.class);
		if (tmpRequiresPermissions == null){
			super.postHandle(request, response, handler, modelAndView);
			return;
		}
		String[] tmpArrayValue = tmpRequiresPermissions.value();
		if (tmpArrayValue == null || tmpArrayValue.length == 0) {
			super.postHandle(request, response, handler, modelAndView);
			return;
		}
		String tmpValue = tmpArrayValue[0];
		if (StringUtils.isEmpty(tmpValue) || tmpValue.indexOf(":") == -1){
			super.postHandle(request, response, handler, modelAndView);
			return;
		}
		String[] tmpArray = tmpValue.split(":");
		if (tmpArray.length != 2){
			super.postHandle(request, response, handler, modelAndView);
			return;
		}
		String tmpMenuCode = tmpArray[0];
		String tmpPrivCode = tmpArray[1];
		if (tmpPrivCode.equals(PrivCode.LIST)){
			HttpSession tmpSession = request.getSession();
			@SuppressWarnings("unchecked")
			Map<String, List<TSyspriv>> tmpMap = (Map<String, List<TSyspriv>>)tmpSession.getAttribute(SessionAttribute.PERMISSION.getAttribute());
			if (tmpMap == null || tmpMap.isEmpty()){
				super.postHandle(request, response, handler, modelAndView);
				return;
			}
			List<TSyspriv> tmpList = tmpMap.get(tmpMenuCode);
			modelAndView.addObject(SessionAttribute.MENU_CODE.getAttribute(), tmpMenuCode);
			modelAndView.addObject(SessionAttribute.PRIV_LIST.getAttribute(), tmpList);
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return super.preHandle(request, response, handler);
	}

}

