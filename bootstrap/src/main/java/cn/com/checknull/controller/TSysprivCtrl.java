/**  
* @Project: xproduct-spmvc
* @Title: TSysprivCtrl.java
* @Package cn.com.checknull.controller
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2016-6-22 下午11:28:54
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
import cn.com.checknull.model.po.TSyspriv;
import cn.com.checknull.model.vo.QueryResult;
import cn.com.checknull.model.vo.ResultData;
import cn.com.checknull.model.vo.ZTree;

/**
 * @ClassName TSysprivCtrl
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2016-6-22   下午11:28:54
 */
@Controller
@RequestMapping("/TSyspriv")
public class TSysprivCtrl extends BaseCtrl{
	
	@RequiresPermissions(value = {MenuCode.SYSPRIV + ":" + PrivCode.LIST})
	@RequestMapping(value = "/list.do")
	public String list() {
		return "TSyspriv/list-success";
	}
	
	@RequestMapping(value = "/getMenu.do")
	@ResponseBody
	public Object getMenu() {
		List<ZTree> menus = new ArrayList<ZTree>();
		try {
			TSysmenu tmpTopQueryTSysmenu = new TSysmenu();
			tmpTopQueryTSysmenu.setLevel(1);
			tmpTopQueryTSysmenu.setStatus(1);
			tmpTopQueryTSysmenu.setOrder("asc");
			tmpTopQueryTSysmenu.setSort("SEQUENCE");
			//查询一级菜单
			QueryResult<TSysmenu> topMenus = tsysmenuService.findPage(-1, -1, tmpTopQueryTSysmenu);
			if (topMenus != null && topMenus.getRecords() > 0){
				ZTree menu;
				ZTree child;
				List<ZTree> children;
				QueryResult<TSysmenu> secondMenus;
				TSysmenu tmpSecondQueryTSysmenu;
				for (TSysmenu topMenu : topMenus.getRows()){
					menu = new ZTree(topMenu.getId(), topMenu.getName());
					menus.add(menu);
					tmpSecondQueryTSysmenu = new TSysmenu();
					tmpSecondQueryTSysmenu.setParentId(topMenu.getId());
					tmpSecondQueryTSysmenu.setStatus(1);
					tmpSecondQueryTSysmenu.setLevel(2);
					tmpSecondQueryTSysmenu.setOrder("asc");
					tmpSecondQueryTSysmenu.setSort("SEQUENCE");
					secondMenus = tsysmenuService.findPage(-1, -1,tmpSecondQueryTSysmenu);
					if (secondMenus != null && secondMenus.getRecords() > 0){
						children = new ArrayList<ZTree>(); 
						for (TSysmenu secondMenu : secondMenus.getRows()){
							child = new ZTree(secondMenu.getId(), secondMenu.getName());
							children.add(child);
						} 
						menu.setChildren(children);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return menus;
	}
	@RequiresPermissions(value = {MenuCode.SYSPRIV + ":" + PrivCode.LIST})
	@RequestMapping(value = "/getData.do")
	@ResponseBody
	public Object getData(@RequestParam(value="name", required=false)String name,
			@RequestParam(value="code", required=false)String code,
			@RequestParam(value="status", required=false)String status,
			@RequestParam(value="menuId", required=false)String menuId,
			@RequestParam(value="page", required=false)String page, 
			@RequestParam(value="rows", required=false)String rows, 
			@RequestParam(value="sidx", required=false)String sidx, 
			@RequestParam(value="sord", required=false)String sord){
		QueryResult<TSyspriv> pages = new QueryResult<TSyspriv>();
		try {
			TSyspriv tsyspriv = new TSyspriv();
			if (!StringUtils.isEmpty(name)){
				tsyspriv.setName(name);
			}
			if (!StringUtils.isEmpty(code)){
				tsyspriv.setCode(code);
			}
			if (!StringUtils.isEmpty(status)){
				int temStatus = NumberUtils.toInt(status,1);
				tsyspriv.setStatus(temStatus);
			}
			if (!StringUtils.isEmpty(menuId)){
				long temMenuId = NumberUtils.toInt(menuId,1);
				tsyspriv.setMenuId(temMenuId);
			}
			tsyspriv.setSort(sidx);
			tsyspriv.setOrder(sord);
			pages = tsysprivService.findPage(NumberUtils.toInt(page, 1), NumberUtils.toInt(rows, 15), tsyspriv);
			if (pages != null && pages.getRecords() > 0){
				for (TSyspriv tmpTSyspriv : pages.getRows()){
					TSysmenu tmpTSysmenu = tsysmenuService.find(tmpTSyspriv.getMenuId());
					tmpTSyspriv.setMenu(tmpTSysmenu);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return pages;
	}
	@RequiresPermissions(value = {MenuCode.SYSPRIV + ":" + PrivCode.VIEW})
	@RequestMapping(value = "/view.do")
	public String view(@RequestParam(value="id")long id, ModelMap model){
		try {
			TSyspriv tsyspriv = tsysprivService.find(id);
			model.addAttribute("tsyspriv", tsyspriv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "TSyspriv/view-success";
	}
	@RequiresPermissions(value = {MenuCode.SYSPRIV + ":" + PrivCode.ADD})
	@RequestMapping(value = "/add.do")
	public String add(@RequestParam(value="menuId")String menuId, ModelMap model){
		try {
			TSysmenu tsysmenu = tsysmenuService.find(NumberUtils.toLong(menuId));
			model.addAttribute("tsysmenu", tsysmenu);
			model.addAttribute("menuId", menuId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "TSyspriv/add-success";
	}
	@RequiresPermissions(value = {MenuCode.SYSPRIV + ":" + PrivCode.ADD})
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public Object save(TSyspriv tsyspriv){
		ResultData result = new ResultData();
		try {
			tsysprivService.insert(tsyspriv);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.ADD, OperateResult.SUCCESS, MenuCode.SYSPRIV, tsyspriv.getName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setIserror(true);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.SYSPRIV + ":" + PrivCode.MODIFY})
	@RequestMapping(value = "/modify.do")
	public String modify(@RequestParam(value="id")long id, ModelMap model){
		try {
			TSyspriv tsyspriv = tsysprivService.find(id);
			model.addAttribute("tsyspriv", tsyspriv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "TSyspriv/modify-success";
	}
	@RequiresPermissions(value = {MenuCode.SYSPRIV + ":" + PrivCode.MODIFY})
	@RequestMapping(value = "/update.do")
	@ResponseBody
	public Object update(TSyspriv tsyspriv){
		ResultData result = new ResultData();
		try {
			tsysprivService.mod(tsyspriv);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.MODIFY, OperateResult.SUCCESS, MenuCode.SYSPRIV, tsyspriv.getName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setIserror(true);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.SYSPRIV + ":" + PrivCode.REMOVE})
	@RequestMapping(value = "/remove.do")
	@ResponseBody
	public Object remove(@RequestParam(value="ids")String ids){
		ResultData result = new ResultData();
		try {
			tsysprivService.bathDelete(ids);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.REMOVE, OperateResult.SUCCESS, MenuCode.SYSPRIV, ids);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setIserror(true);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.SYSPRIV + ":" + PrivCode.ON})
	@RequestMapping(value = "/on.do")
	@ResponseBody
	public Object on(@RequestParam(value="ids")String ids){
		ResultData result = new ResultData();
		try {
			tsysprivService.bathOn(ids);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.ON, OperateResult.SUCCESS, MenuCode.SYSPRIV, ids);
		} catch (Exception e) {
			result.setIserror(true);
			result.setMessage("失败");
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.SYSPRIV + ":" + PrivCode.OFF})
	@RequestMapping(value = "/off.do")
	@ResponseBody
	public Object off(@RequestParam(value="ids")String ids){
		ResultData result = new ResultData();
		try {
			tsysprivService.bathOff(ids);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.OFF, OperateResult.SUCCESS, MenuCode.SYSPRIV, ids);
		} catch (Exception e) {
			result.setIserror(true);
			result.setMessage("失败");
			logger.error(e.getMessage(), e);
		}
		return result;
	}
}

