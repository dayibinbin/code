/**  
* @Project: xproduct-spmvc
* @Title: TSysconfigCtrl.java
* @Package cn.com.checknull.controller
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2016-6-22 下午11:02:18
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
import cn.com.checknull.constant.enumer.OperateResult;
import cn.com.checknull.model.po.TSysconfig;
import cn.com.checknull.model.vo.QueryResult;
import cn.com.checknull.model.vo.ResultData;

/**
 * @ClassName TSysconfigCtrl
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2016-6-22   下午11:02:18
 */
@Controller
@RequestMapping("/TSysconfig")
public class TSysconfigCtrl extends BaseCtrl{
	
	@RequiresPermissions(value = {MenuCode.SYSCONFIG + ":" + PrivCode.LIST})
	@RequestMapping(value = "/list.do")
	public String list() {
		return "TSysconfig/list-success";
	}
	@RequiresPermissions(value = {MenuCode.SYSCONFIG + ":" + PrivCode.LIST})
	@RequestMapping(value = "/getData.do")
	@ResponseBody
	public Object getData(@RequestParam(value="paramName", required=false)String paramName,
			@RequestParam(value="paramCode", required=false)String paramCode,
			@RequestParam(value="page", required=false)String page, 
			@RequestParam(value="rows", required=false)String rows, 
			@RequestParam(value="sidx", required=false)String sidx, 
			@RequestParam(value="sord", required=false)String sord){
		QueryResult<TSysconfig> pages = new QueryResult<TSysconfig>();
		try {
			TSysconfig tsysconfig = new TSysconfig();
			if (!StringUtils.isEmpty(paramName)){
				tsysconfig.setParamName(paramName);
			}
			if (!StringUtils.isEmpty(paramCode)){
				tsysconfig.setParamCode(paramCode);
			}
			tsysconfig.setSort(sidx);
			tsysconfig.setOrder(sord);
			pages = tsysconfigService.findPage(NumberUtils.toInt(page, 1), NumberUtils.toInt(rows, 15), tsysconfig);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return pages;
	}
	@RequiresPermissions(value = {MenuCode.SYSCONFIG + ":" + PrivCode.VIEW})
	@RequestMapping(value = "/view.do")
	public String view(@RequestParam(value="id")long id, ModelMap model){
		try {
			TSysconfig tsysconfig = tsysconfigService.find(id);
			model.addAttribute("tsysconfig", tsysconfig);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "TSysconfig/view-success";
	}
	@RequiresPermissions(value = {MenuCode.SYSCONFIG + ":" + PrivCode.ADD})
	@RequestMapping(value = "/add.do")
	public String add(){
		return "TSysconfig/add-success";
	}
	@RequiresPermissions(value = {MenuCode.SYSCONFIG + ":" + PrivCode.ADD})
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public Object save(TSysconfig tsysconfig){
		ResultData result = new ResultData();
		try {
			tsysconfigService.insert(tsysconfig);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.ADD, OperateResult.SUCCESS, MenuCode.SYSCONFIG, tsysconfig.getParamCode());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setIserror(true);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.SYSCONFIG + ":" + PrivCode.MODIFY})
	@RequestMapping(value = "/modify.do")
	public String modify(@RequestParam(value="id")long id, ModelMap model){
		try {
			TSysconfig tsysconfig = tsysconfigService.find(id);
			model.addAttribute("tsysconfig", tsysconfig);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "TSysconfig/modify-success";
	}
	@RequiresPermissions(value = {MenuCode.SYSCONFIG + ":" + PrivCode.MODIFY})
	@RequestMapping(value = "/update.do")
	@ResponseBody
	public Object update(TSysconfig tsysconfig){
		ResultData result = new ResultData();
		try {
			tsysconfigService.mod(tsysconfig);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.MODIFY, OperateResult.SUCCESS, MenuCode.SYSCONFIG, tsysconfig.getParamCode());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setIserror(true);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.SYSCONFIG + ":" + PrivCode.REMOVE})
	@RequestMapping(value = "/remove.do")
	@ResponseBody
	public Object remove(@RequestParam(value="ids")String ids){
		ResultData result = new ResultData();
		try {
			tsysconfigService.bathDelete(ids);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.REMOVE, OperateResult.SUCCESS, MenuCode.SYSCONFIG, ids);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setIserror(true);
			result.setMessage(e.getMessage());
		}
		return result;
	}
}

