/**  
* @Project: xdata-report
* @Title: Log4jFormatInterceptor.java
* @Package cn.com.checknull.interceptor
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2016-8-16 下午4:10:38
* @version v1.0  
*/

package cn.com.checknull.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.com.checknull.constant.enumer.SessionAttribute;
import cn.com.checknull.model.po.TOperator;
import cn.com.checknull.util.RequestUtils;

/**
 * @ClassName Log4jFormatInterceptor
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2016-8-16   下午4:10:38
 */
public class Log4jFormatInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		if (session == null) {
			MDC.put("user", "anonymous");
		} else {
			TOperator toperator = (TOperator) session.getAttribute(SessionAttribute.OPERATOR.getAttribute());
			if (toperator != null) {
				MDC.put("user", toperator.getLoginName());
			} else {
				MDC.put("user", "anonymous");
			}
		}
		MDC.put("ip", RequestUtils.getRequsetIP(request));
		return super.preHandle(request, response, handler);
	}

}

