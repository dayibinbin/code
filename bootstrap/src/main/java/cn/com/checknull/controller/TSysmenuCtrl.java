/**  
* @Project: xproduct-spmvc
* @Title: TSysmenuCtrl.java
* @Package cn.com.checknull.controller
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2016-6-22 下午11:12:24
* @version v1.0  
*/

package cn.com.checknull.controller;

import java.util.ArrayList;
import java.util.List;

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
import cn.com.checknull.model.po.TSysmenu;
import cn.com.checknull.model.vo.QueryResult;
import cn.com.checknull.model.vo.ResultData;
import cn.com.checknull.model.vo.Select2;

/**
 * @ClassName TSysmenuCtrl
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2016-6-22   下午11:12:24
 */
@Controller
@RequestMapping("/TSysmenu")
public class TSysmenuCtrl extends BaseCtrl{
	
	@RequiresPermissions(value = {MenuCode.SYSMENU + ":" + PrivCode.LIST})
	@RequestMapping(value = "/list.do")
	public String list() {
		return "TSysmenu/list-success";
	}
	@RequiresPermissions(value = {MenuCode.SYSMENU + ":" + PrivCode.LIST})
	@RequestMapping(value = "/getData.do")
	@ResponseBody
	public Object getData(@RequestParam(value="name", required=false)String name,
			@RequestParam(value="code", required=false)String code,
			@RequestParam(value="status", required=false)String status,
			@RequestParam(value="level", required=false)String level,
			@RequestParam(value="page", required=false)String page, 
			@RequestParam(value="rows", required=false)String rows, 
			@RequestParam(value="sidx", required=false)String sidx, 
			@RequestParam(value="sord", required=false)String sord){
		QueryResult<TSysmenu> pages = new QueryResult<TSysmenu>();
		try {
			TSysmenu tsysmenu = new TSysmenu();
			if (!StringUtils.isEmpty(name)){
				tsysmenu.setName(name);
			}
			if (!StringUtils.isEmpty(code)){
				tsysmenu.setCode(code);
			}
			if (!StringUtils.isEmpty(status)){
				int temStatus = NumberUtils.toInt(status,1);
				tsysmenu.setStatus(temStatus);
			}
			if (!StringUtils.isEmpty(level)){
				int temLevel = NumberUtils.toInt(level,1);
				tsysmenu.setLevel(temLevel);
			}
			tsysmenu.setSort(sidx);
			tsysmenu.setOrder(sord);
			pages = tsysmenuService.findPage(NumberUtils.toInt(page, 1), NumberUtils.toInt(rows, 15), tsysmenu);
			if (pages != null && pages.getRecords() > 0){
				TSysmenu tmpParentTSysmenu;
				for (TSysmenu tmpTSysmenu : pages.getRows()){
					if (tmpTSysmenu.getParentId() != null){
						tmpParentTSysmenu = tsysmenuService.find(tmpTSysmenu.getParentId());
						tmpTSysmenu.setParentMenu(tmpParentTSysmenu);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return pages;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getParentMenu.do")
	public Object getParentMenu(){
		List<Select2> select2S = new ArrayList<Select2>();
		try {
			TSysmenu tmpQueryTSysmenu = new TSysmenu();
			tmpQueryTSysmenu.setLevel(1);
			tmpQueryTSysmenu.setSort("id");
			tmpQueryTSysmenu.setOrder("asc");
			QueryResult<TSysmenu> tmpQRTSysMenu = tsysmenuService.findPage(-1, -1, tmpQueryTSysmenu);
			if (tmpQRTSysMenu != null && tmpQRTSysMenu.getRecords() > 0){
				Select2 select2;
				for (TSysmenu tSysmenu : tmpQRTSysMenu.getRows()) {
					select2 = new Select2(tSysmenu.getId(), tSysmenu.getName());
					select2S.add(select2);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return select2S;
	}
	
	@RequiresPermissions(value = {MenuCode.SYSMENU + ":" + PrivCode.VIEW})
	@RequestMapping(value = "/view.do")
	public String view(@RequestParam(value="id")long id, ModelMap model){
		try {
			TSysmenu tsysmenu = tsysmenuService.find(id);
			//查询二级菜单
			TSysmenu tmpQueryTSysmenu = new TSysmenu();
			tmpQueryTSysmenu.setParentId(id);
			tmpQueryTSysmenu.setSort("sequence");
			tmpQueryTSysmenu.setOrder("asc");
			List<TSysmenu> tsysmenus = null;
			QueryResult<TSysmenu> pages = tsysmenuService.findPage(-1, -1, tmpQueryTSysmenu);
			if (pages != null && pages.getRecords() > 0){
				tsysmenus = pages.getRows();
			}
			model.addAttribute("tsysmenu", tsysmenu);
			model.addAttribute("tsysmenus", tsysmenus);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "TSysmenu/view-success";
	}
	@RequiresPermissions(value = {MenuCode.SYSMENU + ":" + PrivCode.ADD})
	@RequestMapping(value = "/add.do")
	public String add(){
		return "TSysmenu/add-success";
	}
	@RequiresPermissions(value = {MenuCode.SYSMENU + ":" + PrivCode.ADD})
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public Object save(TSysmenu tsysmenu){
		ResultData result = new ResultData();
		try {
			tsysmenuService.insert(tsysmenu);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.ADD, OperateResult.SUCCESS, MenuCode.SYSMENU, tsysmenu.getName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setIserror(true);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.SYSMENU + ":" + PrivCode.MODIFY})
	@RequestMapping(value = "/modify.do")
	public String modify(@RequestParam(value="id")long id, ModelMap model){
		try {
			TSysmenu tsysmenu = tsysmenuService.find(id);
			model.addAttribute("tsysmenu", tsysmenu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "TSysmenu/modify-success";
	}
	@RequiresPermissions(value = {MenuCode.SYSMENU + ":" + PrivCode.MODIFY})
	@RequestMapping(value = "/update.do")
	@ResponseBody
	public Object update(TSysmenu tsysmenu){
		ResultData result = new ResultData();
		try {
			tsysmenuService.mod(tsysmenu);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.MODIFY, OperateResult.SUCCESS, MenuCode.SYSMENU, tsysmenu.getName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setIserror(true);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.SYSMENU + ":" + PrivCode.REMOVE})
	@RequestMapping(value = "/remove.do")
	@ResponseBody
	public Object remove(@RequestParam(value="ids")String ids){
		ResultData result = new ResultData();
		try {
			tsysmenuService.bathDelete(ids);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.REMOVE, OperateResult.SUCCESS, MenuCode.SYSMENU, ids);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setIserror(true);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.SYSMENU + ":" + PrivCode.ON})
	@RequestMapping(value = "/on.do")
	@ResponseBody
	public Object on(@RequestParam(value="ids")String ids){
		ResultData result = new ResultData();
		try {
			tsysmenuService.bathOn(ids);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.ON, OperateResult.SUCCESS, MenuCode.SYSMENU, ids);
		} catch (Exception e) {
			result.setIserror(true);
			result.setMessage("失败");
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.SYSMENU + ":" + PrivCode.OFF})
	@RequestMapping(value = "/off.do")
	@ResponseBody
	public Object off(@RequestParam(value="ids")String ids){
		ResultData result = new ResultData();
		try {
			tsysmenuService.bathOff(ids);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.OFF, OperateResult.SUCCESS, MenuCode.SYSMENU, ids);
		} catch (Exception e) {
			result.setIserror(true);
			result.setMessage("失败");
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequiresPermissions(value = {MenuCode.SYSMENU + ":" + PrivCode.SORT})
	@RequestMapping(value = "/sort.do")
	public String sort(ModelMap model){
		try {
			TSysmenu tmpQueryTSysmenu = new TSysmenu();
			tmpQueryTSysmenu.setSort("sequence");
			tmpQueryTSysmenu.setOrder("asc");
			List<TSysmenu> tsysmenus = null;
			QueryResult<TSysmenu> tmpQRTSysmenu = tsysmenuService.findPage(-1, -1, tmpQueryTSysmenu);
			if (tmpQRTSysmenu != null && tmpQRTSysmenu.getRecords() > 0){
				tsysmenus = tmpQRTSysmenu.getRows();
			}
			model.addAttribute("tsysmenus", tsysmenus);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "TSysmenu/sort-success";
	}
	
	@RequiresPermissions(value = {MenuCode.SYSMENU + ":" + PrivCode.SORT})
	@RequestMapping(value = "/sorting.do")
	@ResponseBody
	public Object sorting(@RequestParam(value="sorts")String sorts){
		ResultData result = new ResultData();
		try {
			tsysmenuService.sort(sorts);
			result.setIserror(false);
			result.setMessage("成功");
		} catch (Exception e) {
			result.setIserror(true);
			result.setMessage("失败");
			logger.error(e.getMessage(), e);
		}
		return result;
	}
}

