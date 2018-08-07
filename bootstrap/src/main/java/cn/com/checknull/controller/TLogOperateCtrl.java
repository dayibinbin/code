/**  
* @Project: xproduct-spmvc
* @Title: TLogOperateCtrl.java
* @Package cn.com.checknull.controller
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2016-6-22 下午9:41:54
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
import cn.com.checknull.model.po.TLogOperate;
import cn.com.checknull.model.vo.QueryResult;
import cn.com.checknull.model.vo.ResultData;

/**
 * @ClassName TLogOperateCtrl
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2016-6-22   下午9:41:54
 */
@Controller
@RequestMapping("/TLogOperate")
public class TLogOperateCtrl extends BaseCtrl{
	
	@RequiresPermissions(value = {MenuCode.LOG_OPERATE + ":" + PrivCode.LIST})
	@RequestMapping(value = "/list.do")
	public String list() {
		return "TLogOperate/list-success";
	}
	@RequiresPermissions(value = {MenuCode.LOG_OPERATE + ":" + PrivCode.LIST})
	@RequestMapping(value = "/getData.do")
	@ResponseBody
	public Object getData(@RequestParam(value="loginName", required=false)String loginName,
			@RequestParam(value="operatorName", required=false)String operatorName,
			@RequestParam(value="operateObject", required=false)String operateObject,
			@RequestParam(value="operateResult", required=false)String operateResult,
			@RequestParam(value="page", required=false)String page, 
			@RequestParam(value="rows", required=false)String rows, 
			@RequestParam(value="sidx", required=false)String sidx, 
			@RequestParam(value="sord", required=false)String sord){
		QueryResult<TLogOperate> pages = new QueryResult<TLogOperate>();
		try {
			TLogOperate tlogOperate = new TLogOperate();
			if (!StringUtils.isEmpty(loginName)){
				tlogOperate.setLoginName(loginName);
			}
			if (!StringUtils.isEmpty(operatorName)){
				tlogOperate.setOperatorName(operatorName);
			}
			if (!StringUtils.isEmpty(operateObject)){
				tlogOperate.setOperateObject(operateObject);
			}
			if (!StringUtils.isEmpty(operateResult)){
				int temOperateResult = NumberUtils.toInt(operateResult,1);
				tlogOperate.setOperateResult(temOperateResult);
			}
			tlogOperate.setSort(sidx);
			tlogOperate.setOrder(sord);
			pages = tlogOperateService.findPage(NumberUtils.toInt(page, 1), NumberUtils.toInt(rows, 15), tlogOperate);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return pages;
	}
	@RequiresPermissions(value = {MenuCode.LOG_OPERATE + ":" + PrivCode.VIEW})
	@RequestMapping(value = "/view.do")
	public String view(@RequestParam(value="id")long id, ModelMap model){
		try {
			TLogOperate tlogOperate = tlogOperateService.find(id);
			model.addAttribute("tlogOperate", tlogOperate);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "TLogOperate/view-success";
	}
	@RequiresPermissions(value = {MenuCode.LOG_OPERATE + ":" + PrivCode.REMOVE})
	@RequestMapping(value = "/remove.do")
	@ResponseBody
	public Object remove(@RequestParam(value="ids")String ids){
		ResultData result = new ResultData();
		try {
			tlogOperateService.bathDelete(ids);
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

