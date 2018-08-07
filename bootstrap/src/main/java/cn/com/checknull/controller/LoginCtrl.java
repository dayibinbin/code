/**  
* @Project: xproduct-spmvc
* @Title: LoginCtrl.java
* @Package cn.com.checknull.controller
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2016-6-19 下午2:54:42
* @version v1.0  
*/

package cn.com.checknull.controller;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.checknull.constant.enumer.SessionAttribute;
import cn.com.checknull.model.po.TLogLogin;
import cn.com.checknull.model.po.TOperator;
import cn.com.checknull.model.vo.ResultData;
import cn.com.checknull.util.RequestUtils;

/**
 * @ClassName LoginCtrl
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2016-6-19   下午2:54:42
 */
@Controller
@RequestMapping("/")
public class LoginCtrl extends BaseCtrl{
	@RequestMapping(value = "/login.do")
	public String login() {
		return "login-success";
	}
	
	@RequestMapping(value = "/redirect.do")
	public String redirect() {
		return "redirect-success";
	}
	
	@RequestMapping(value = "/unauthorized.do")
	public String unauthorized() {
		return "unauthorized-success";
	}
	
	@RequestMapping(value = "/userLogin.do", method=RequestMethod.POST)
	@ResponseBody
	public Object userLogin(@RequestParam(value="loginName")String loginName, 
			@RequestParam(value="password")String password) {
		ResultData result = new ResultData();
		try{
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
			TLogLogin tmpTLogLogin = new TLogLogin();
			String requestIP = RequestUtils.getRequsetIP(request);
			tmpTLogLogin.setLoginIp(requestIP);
			tmpTLogLogin.setLoginName(loginName);
			tmpTLogLogin.setType(1);//1表示登陆
			try {
				subject.login(token);
				if (subject.isAuthenticated()){
					TOperator tmpTOperator = toperatorService.findByLoginName(loginName);
					tmpTOperator.setLoginIp(requestIP);
					tmpTOperator.setErrorTimes(0);
					tmpTOperator.setLoginTime(new Date());
					toperatorService.mod(tmpTOperator);
					//设置session
					Session session = subject.getSession();
					session.setAttribute(SessionAttribute.OPERATOR.getAttribute(), tmpTOperator);
					tmpTLogLogin.setOperatorId(tmpTOperator.getId());
					tmpTLogLogin.setOperatorName(tmpTOperator.getRealName());
					tmpTLogLogin.setResult(1);//1表示成功
					tmpTLogLogin.setDescription("登陆成功");
					result.setIserror(false);
					result.setMessage("登陆成功");
				}else{
					tmpTLogLogin.setResult(0);//0表示失败
					tmpTLogLogin.setDescription("登陆失败");
					result.setIserror(true);
					result.setMessage("登陆失败");
				}
			} catch (AuthenticationException e){
				tmpTLogLogin.setResult(0);//0表示失败
				tmpTLogLogin.setDescription(e.getMessage());
				result.setIserror(true);
				result.setMessage(e.getMessage());
			}
			tlogLoginService.insert(tmpTLogLogin);
			return result;
		}catch (Exception e){
			logger.error(e.getMessage(),e);
			result.setMessage("系统发生错误");
			result.setIserror(true);
			return result;
		}
	}
	@RequestMapping(value = "/loginOut.do")
	public void loginOut(HttpServletResponse response){
		TLogLogin tmpTLogLogin = new TLogLogin();
		TOperator tmpTOperator = this.getCurrentOperator();
		if (tmpTOperator == null){
			logger.error("退出失败，当前操作者为空");
			return;
		}
		try{
			String requestIP = RequestUtils.getRequsetIP(request);
			tmpTLogLogin.setLoginIp(requestIP);
			tmpTLogLogin.setLoginName(tmpTOperator.getLoginName());
			tmpTLogLogin.setOperatorId(tmpTOperator.getId());
			tmpTLogLogin.setOperatorName(tmpTOperator.getRealName());
			tmpTLogLogin.setResult(1);//成功
			tmpTLogLogin.setType(2);//退出
			tmpTLogLogin.setDescription("退出成功");
			tlogLoginService.insert(tmpTLogLogin);
			
			Subject subject = SecurityUtils.getSubject();
			if (subject != null && subject.isAuthenticated()){
				//退出以后会清理缓存
				subject.logout();
			}
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<script>");
			out.println("window.open ('" + request.getContextPath()
					+ "/login.do','_top')");
			out.println("</script>");
			out.println("</html>");
		}catch (Exception e){
			tmpTLogLogin.setResult(0);//失败
			tmpTLogLogin.setDescription("退出失败");
			try {
				tlogLoginService.insert(tmpTLogLogin);
			} catch (SQLException e1) {
				logger.error(e1.getMessage(), e1);
			}
			logger.error(e.getMessage(), e);
		}
	}
}

