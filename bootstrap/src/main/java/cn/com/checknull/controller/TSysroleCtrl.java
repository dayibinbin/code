/**  
* @Project: xproduct-spmvc
* @Title: TSysroleCtrl.java
* @Package cn.com.checknull.controller
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2016-6-22 下午11:45:29
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
import cn.com.checknull.model.po.TSysrole;
import cn.com.checknull.model.po.TSysroleMenu;
import cn.com.checknull.model.po.TSysrolePriv;
import cn.com.checknull.model.vo.QueryResult;
import cn.com.checknull.model.vo.ResultData;
import cn.com.checknull.model.vo.ZTree;

/**
 * @ClassName TSysroleCtrl
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2016-6-22   下午11:45:29
 */
@Controller
@RequestMapping("/TSysrole")
public class TSysroleCtrl extends BaseCtrl{
	
	@RequiresPermissions(value = {MenuCode.SYSROLE + ":" + PrivCode.LIST})
	@RequestMapping(value = "/list.do")
	public String list() {
		return "TSysrole/list-success";
	}
	@RequiresPermissions(value = {MenuCode.SYSROLE + ":" + PrivCode.LIST})
	@RequestMapping(value = "/getData.do")
	@ResponseBody
	public Object getData(@RequestParam(value="name", required=false)String name,
			@RequestParam(value="status", required=false)String status,
			@RequestParam(value="page", required=false)String page, 
			@RequestParam(value="rows", required=false)String rows, 
			@RequestParam(value="sidx", required=false)String sidx, 
			@RequestParam(value="sord", required=false)String sord){
		QueryResult<TSysrole> pages = new QueryResult<TSysrole>();
		try {
			TSysrole tsysrole = new TSysrole();
			if (!StringUtils.isEmpty(name)){
				tsysrole.setName(name);
			}
			if (!StringUtils.isEmpty(status)){
				int temStatus = NumberUtils.toInt(status,1);
				tsysrole.setStatus(temStatus);
			}
			tsysrole.setSort(sidx);
			tsysrole.setOrder(sord);
			pages = tsysroleService.findPage(NumberUtils.toInt(page, 1), NumberUtils.toInt(rows, 15), tsysrole);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return pages;
	}
	@RequiresPermissions(value = {MenuCode.SYSROLE + ":" + PrivCode.VIEW})
	@RequestMapping(value = "/view.do")
	public String view(@RequestParam(value="id")long id, ModelMap model){
		try {
			TSysrole tsysrole = tsysroleService.find(id);
			TSysrolePriv tmpTSysrolePriv = tsysrolePrivService.findByRoleId(id);
			String privIds = "";
			if (tmpTSysrolePriv != null){
				privIds = tmpTSysrolePriv.getPrivIds();
			}
			String menuIds = "";
			TSysroleMenu tmpTSysroleMenu = tsysroleMenuService.findByRoleId(id);
			if (tmpTSysroleMenu != null){
				menuIds = tmpTSysroleMenu.getMenuIds();
			}
			String treeIds = "";
			if (!StringUtils.isEmpty(menuIds) && !StringUtils.isEmpty(privIds)){
				String tmpPrivIds = "," + privIds + ",";
				List<TSysmenu> tmpListTSysmenu = tsysmenuService.find(menuIds);
				if (tmpListTSysmenu != null && !tmpListTSysmenu.isEmpty()){
					long tmpMenuId;
					long tmpParentMuneId;
					List<TSyspriv> tmpListTSyspriv;
					String tmpPrivId;
					for (TSysmenu tmpTSysmenu : tmpListTSysmenu){
						if(tmpTSysmenu.getLevel() == 1){
							treeIds += "," + tmpTSysmenu.getId();
						}
						if (tmpTSysmenu.getLevel() == 2 && tmpTSysmenu.getStatus() == 1){
							tmpMenuId = tmpTSysmenu.getId();
							tmpParentMuneId = tmpTSysmenu.getParentMenu().getId();
							treeIds += "," + tmpParentMuneId+"-"+tmpMenuId;
							tmpListTSyspriv = tsysprivService.findByMenuId(tmpMenuId);
							if (tmpListTSyspriv != null && !tmpListTSyspriv.isEmpty()){
								for (TSyspriv tmpTSyspriv : tmpListTSyspriv){
									if (tmpTSyspriv.getStatus() != 1) continue;
									tmpPrivId = "," + tmpTSyspriv.getId() + ",";
									if (tmpPrivIds.indexOf(tmpPrivId) >= 0){
										treeIds += "," + tmpParentMuneId + "-" + tmpMenuId + "-" + tmpTSyspriv.getId();
									}
								}
							}
						}
					}
				}
			}
			if (!StringUtils.isEmpty(treeIds)){
				treeIds = StringUtils.removeStart(treeIds, ",");
				treeIds = StringUtils.removeEnd(treeIds, ",");
			}
			model.addAttribute("tsysrole", tsysrole);
			model.addAttribute("treeIds", treeIds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "TSysrole/view-success";
	}
	
	@RequestMapping(value = "/getPrivTree.do")
	@ResponseBody
	public Object getPrivTree(@RequestParam(value="view", required=false)String view){
		boolean chkDisabled = false;
		if(null != view && view.equals("true")){
			chkDisabled = true;
		}
		List<ZTree> tmpMenus = new ArrayList<ZTree>();
		try {
			TSysmenu tmpTopQueryTSysmenu = new TSysmenu();
			tmpTopQueryTSysmenu.setLevel(1);
			tmpTopQueryTSysmenu.setStatus(1);
			tmpTopQueryTSysmenu.setOrder("asc");
			tmpTopQueryTSysmenu.setSort("sequence");
			  //获取一级菜单
			 QueryResult<TSysmenu>  tmpQRTSysmenu = tsysmenuService.findPage(-1, -1, tmpTopQueryTSysmenu);
			 //获取二级菜单
			 QueryResult<TSysmenu>  tmpQR2TSysmenu = null;
			 //获取权限
			 QueryResult<TSyspriv> tmpQRTSyspriv = null;
			 if (tmpQRTSysmenu != null && tmpQRTSysmenu.getRecords() > 0){
				 ZTree tmpMenu;
				 TSysmenu tmpSecondQueryTSysmenu = new TSysmenu();
				 tmpSecondQueryTSysmenu.setLevel(2);
				 tmpSecondQueryTSysmenu.setStatus(1);
				 tmpSecondQueryTSysmenu.setOrder("asc");
				 tmpSecondQueryTSysmenu.setSort("sequence");
				 
				 TSyspriv tmpQueryTSyspriv = new TSyspriv();
				 tmpQueryTSyspriv.setStatus(1);
				 tmpQueryTSyspriv.setOrder("asc");
				 tmpQueryTSyspriv.setSort("sequence");
				 
				 for (TSysmenu tmpTSysmenu : tmpQRTSysmenu.getRows()){
					 tmpMenu = new ZTree(tmpTSysmenu.getId(), tmpTSysmenu.getName(),chkDisabled);
					 //获取二级菜单
					 tmpSecondQueryTSysmenu.setParentId(tmpTSysmenu.getId());
					 tmpQR2TSysmenu = tsysmenuService.findPage(-1, -1, tmpSecondQueryTSysmenu);
					 if (tmpQR2TSysmenu != null && tmpQR2TSysmenu.getRecords() > 0){
						 List<ZTree> tmp2Menus = new ArrayList<ZTree>();
						 ZTree tmp2Menu;
						 for (TSysmenu tmp2TSysmenu : tmpQR2TSysmenu.getRows()){
							 tmp2Menu = new ZTree(tmpTSysmenu.getId() + "-" + tmp2TSysmenu.getId(), tmp2TSysmenu.getName(), chkDisabled);
							 //获取权限
							 tmpQueryTSyspriv.setMenuId(tmp2TSysmenu.getId());
							 tmpQRTSyspriv = tsysprivService.findPage(-1, -1, tmpQueryTSyspriv);
							 if (tmpQRTSyspriv != null && tmpQRTSyspriv.getRecords() > 0){
								 tmp2Menus.add(tmp2Menu);
								 List<ZTree> tmp3Menus = new ArrayList<ZTree>();
								 tmp2Menu.setChildren(tmp3Menus);
								 ZTree tmp3Menu;
								 for (TSyspriv tmpTSyspriv : tmpQRTSyspriv.getRows()){
									 tmp3Menu = new ZTree(tmpTSysmenu.getId() + "-" + tmp2TSysmenu.getId() + "-" + tmpTSyspriv.getId(), tmpTSyspriv.getName(), chkDisabled);
									 tmp3Menus.add(tmp3Menu);
								 }
							 }
						 }
						 if (tmpQRTSyspriv != null && tmpQRTSyspriv.getRecords() > 0){
							 tmpMenus.add(tmpMenu);
							 tmpMenu.setChildren(tmp2Menus);
						 }
					 }
				 }
			 }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return tmpMenus;
	}
	@RequiresPermissions(value = {MenuCode.SYSROLE + ":" + PrivCode.ADD})
	@RequestMapping(value = "/add.do")
	public String add(){
		return "TSysrole/add-success";
	}
	@RequiresPermissions(value = {MenuCode.SYSROLE + ":" + PrivCode.ADD})
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public Object save(TSysrole tsysrole, @RequestParam(value="menuIds", required=false)String menuIds, 
			@RequestParam(value="privIds", required=false)String privIds){
		ResultData result = new ResultData();
		try {
			tsysroleService.insert(tsysrole, menuIds, privIds);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.ADD, OperateResult.SUCCESS, MenuCode.SYSROLE, tsysrole.getName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setIserror(true);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.SYSROLE + ":" + PrivCode.MODIFY})
	@RequestMapping(value = "/modify.do")
	public String modify(@RequestParam(value="id")long id, ModelMap model){
		try {
			TSysrole tsysrole = tsysroleService.find(id);
			TSysrolePriv tmpTSysrolePriv = tsysrolePrivService.findByRoleId(id);
			String privIds = null;
			if (tmpTSysrolePriv != null){
				 privIds = tmpTSysrolePriv.getPrivIds();
			}
			TSysroleMenu tmpTSysroleMenu = tsysroleMenuService.findByRoleId(id);
			String menuIds = null;
			if (tmpTSysroleMenu != null){
				menuIds = tmpTSysroleMenu.getMenuIds();
			}
			String treeIds = "";
			if (!StringUtils.isEmpty(menuIds) && !StringUtils.isEmpty(privIds)){
				String tmpPrivIds = "," + privIds + ",";
				List<TSysmenu> tmpListTSysmenu = tsysmenuService.find(menuIds);
				if (tmpListTSysmenu != null && !tmpListTSysmenu.isEmpty()){
					long tmpMenuId;
					long tmpParentMuneId;
					List<TSyspriv> tmpListTSyspriv;
					String tmpPrivId;
					for (TSysmenu tmpTSysmenu : tmpListTSysmenu){
						if(tmpTSysmenu.getLevel() == 1){
							treeIds += "," + tmpTSysmenu.getId();
						}
						if (tmpTSysmenu.getLevel() == 2 && tmpTSysmenu.getStatus() == 1){
							tmpMenuId = tmpTSysmenu.getId();
							tmpParentMuneId = tmpTSysmenu.getParentMenu().getId();
							treeIds += "," + tmpParentMuneId+"-"+tmpMenuId;
							tmpListTSyspriv = tsysprivService.findByMenuId(tmpMenuId);
							if (tmpListTSyspriv != null && !tmpListTSyspriv.isEmpty()){
								for (TSyspriv tmpTSyspriv : tmpListTSyspriv){
									if (tmpTSyspriv.getStatus() != 1) continue;
									tmpPrivId = "," + tmpTSyspriv.getId() + ",";
									if (tmpPrivIds.indexOf(tmpPrivId) >= 0){
										treeIds += "," + tmpParentMuneId + "-" + tmpMenuId + "-" + tmpTSyspriv.getId();
									}
								}
							}
						}
					}
				}
			}
			if (!StringUtils.isEmpty(treeIds)){
				treeIds = StringUtils.removeStart(treeIds, ",");
				treeIds = StringUtils.removeEnd(treeIds, ",");
			}
			model.addAttribute("tsysrole", tsysrole);
			model.addAttribute("treeIds", treeIds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "TSysrole/modify-success";
	}
	@RequiresPermissions(value = {MenuCode.SYSROLE + ":" + PrivCode.MODIFY})
	@RequestMapping(value = "/update.do")
	@ResponseBody
	public Object update(TSysrole tsysrole, @RequestParam(value="menuIds", required=false)String menuIds, 
			@RequestParam(value="privIds", required=false)String privIds){
		ResultData result = new ResultData();
		try {
			tsysroleService.mod(tsysrole, menuIds, privIds);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.MODIFY, OperateResult.SUCCESS, MenuCode.SYSROLE, tsysrole.getName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setIserror(true);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.SYSROLE + ":" + PrivCode.REMOVE})
	@RequestMapping(value = "/remove.do")
	@ResponseBody
	public Object remove(@RequestParam(value="ids")String ids){
		ResultData result = new ResultData();
		try {
			tsysroleService.bathDelete(ids);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.REMOVE, OperateResult.SUCCESS, MenuCode.SYSROLE, ids);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setIserror(true);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.SYSROLE + ":" + PrivCode.ON})
	@RequestMapping(value = "/on.do")
	@ResponseBody
	public Object on(@RequestParam(value="ids")String ids){
		ResultData result = new ResultData();
		try {
			tsysroleService.bathOn(ids);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.ON, OperateResult.SUCCESS, MenuCode.SYSROLE, ids);
		} catch (Exception e) {
			result.setIserror(true);
			result.setMessage("失败");
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.SYSROLE + ":" + PrivCode.OFF})
	@RequestMapping(value = "/off.do")
	@ResponseBody
	public Object off(@RequestParam(value="ids")String ids){
		ResultData result = new ResultData();
		try {
			tsysroleService.bathOff(ids);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.OFF, OperateResult.SUCCESS, MenuCode.SYSROLE, ids);
		} catch (Exception e) {
			result.setIserror(true);
			result.setMessage("失败");
			logger.error(e.getMessage(), e);
		}
		return result;
	}
}

