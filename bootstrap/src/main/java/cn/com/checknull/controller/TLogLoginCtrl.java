/**  
* @Project: xproduct-spmvc
* @Title: TLogLoginCtrl.java
* @Package cn.com.checknull.controller
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2016-6-22 下午9:28:52
* @version v1.0  
*/

package cn.com.checknull.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.checknull.constant.MenuCode;
import cn.com.checknull.constant.PrivCode;
import cn.com.checknull.model.po.TLogLogin;
import cn.com.checknull.model.vo.QueryResult;
import cn.com.checknull.model.vo.ResultData;

/**
 * @ClassName TLogLoginCtrl
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2016-6-22   下午9:28:52
 */
@Controller
@RequestMapping("/TLogLogin")
public class TLogLoginCtrl extends BaseCtrl{
	
	@RequiresPermissions(value = {MenuCode.LOG_LOGIN + ":" + PrivCode.LIST})
	@RequestMapping(value = "/list.do")
	public String list() {
		return "TLogLogin/list-success";
	}
	@RequiresPermissions(value = {MenuCode.LOG_LOGIN + ":" + PrivCode.LIST})
	@RequestMapping(value = "/getData.do")
	@ResponseBody
	public Object getData(@RequestParam(value="loginName", required=false)String loginName,
			@RequestParam(value="operatorName", required=false)String operatorName,
			@RequestParam(value="result", required=false)String result,
			@RequestParam(value="type", required=false)String type,
			@RequestParam(value="page", required=false)String page, 
			@RequestParam(value="rows", required=false)String rows, 
			@RequestParam(value="sidx", required=false)String sidx, 
			@RequestParam(value="sord", required=false)String sord){
		QueryResult<TLogLogin> pages = new QueryResult<TLogLogin>();
		try {
			TLogLogin tlogLogin = new TLogLogin();
			if (!StringUtils.isEmpty(loginName)){
				tlogLogin.setLoginName(loginName);
			}
			if (!StringUtils.isEmpty(operatorName)){
				tlogLogin.setOperatorName(operatorName);
			}
			if (!StringUtils.isEmpty(result)){
				int temResult = NumberUtils.toInt(result,1);
				tlogLogin.setResult(temResult);
			}
			if (!StringUtils.isEmpty(type)){
				int temType = NumberUtils.toInt(type,1);
				tlogLogin.setType(temType);
			}
			tlogLogin.setSort(sidx);
			tlogLogin.setOrder(sord);
			pages = tlogLoginService.findPage(NumberUtils.toInt(page, 1), NumberUtils.toInt(rows, 15), tlogLogin);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return pages;
	}
	@RequiresPermissions(value = {MenuCode.LOG_LOGIN + ":" + PrivCode.VIEW})
	@RequestMapping(value = "/view.do")
	public String view(@RequestParam(value="id")long id, ModelMap model){
		try {
			TLogLogin tlogLogin = tlogLoginService.find(id);
			model.addAttribute("tlogLogin", tlogLogin);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "TLogLogin/view-success";
	}
	@RequiresPermissions(value = {MenuCode.LOG_LOGIN + ":" + PrivCode.REMOVE})
	@RequestMapping(value = "/remove.do")
	@ResponseBody
	public Object remove(@RequestParam(value="ids")String ids){
		ResultData result = new ResultData();
		try {
			tlogLoginService.bathDelete(ids);
			result.setIserror(false);
			result.setMessage("成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setIserror(true);
			result.setMessage(e.getMessage());
		}
		return result;
	}
}

