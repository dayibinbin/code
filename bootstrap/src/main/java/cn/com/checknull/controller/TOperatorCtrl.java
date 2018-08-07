/**  
* @Project: xproduct-spmvc
* @Title: TOperatorCtrl.java
* @Package cn.com.checknull.controller
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2016-6-22 下午9:54:08
* @version v1.0  
*/

package cn.com.checknull.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

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
import cn.com.checknull.model.po.TOperator;
import cn.com.checknull.model.po.TOperatorRole;
import cn.com.checknull.model.po.TSysrole;
import cn.com.checknull.model.vo.QueryResult;
import cn.com.checknull.model.vo.ResultData;
import cn.com.checknull.model.vo.Select2;
import cn.com.checknull.shiro.crypto.MyPasswordService;
import cn.com.checknull.util.Md5Utils;

/**
 * @ClassName TOperatorCtrl
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2016-6-22   下午9:54:08
 */
@Controller
@RequestMapping("/TOperator")
public class TOperatorCtrl extends BaseCtrl{
	@Resource(name="myPasswordService")
	private MyPasswordService myPasswordService;
	
	public MyPasswordService getMyPasswordService() {
		return myPasswordService;
	}
	public void setMyPasswordService(MyPasswordService myPasswordService) {
		this.myPasswordService = myPasswordService;
	}
	@RequiresPermissions(value = {MenuCode.OPERATOR + ":" + PrivCode.LIST})
	@RequestMapping(value = "/list.do")
	public String list() {
		return "TOperator/list-success";
	}
	@RequiresPermissions(value = {MenuCode.OPERATOR + ":" + PrivCode.LIST})
	@RequestMapping(value = "/getData.do")
	@ResponseBody
	public Object getData(@RequestParam(value="loginName", required=false)String loginName,
			@RequestParam(value="realName", required=false)String realName,
			@RequestParam(value="status", required=false)String status,
			@RequestParam(value="page", required=false)String page, 
			@RequestParam(value="rows", required=false)String rows, 
			@RequestParam(value="sidx", required=false)String sidx, 
			@RequestParam(value="sord", required=false)String sord){
		QueryResult<TOperator> pages = new QueryResult<TOperator>();
		try {
			TOperator toperator = new TOperator();
			if (!StringUtils.isEmpty(loginName)){
				toperator.setLoginName(loginName);
			}
			if (!StringUtils.isEmpty(realName)){
				toperator.setRealName(realName);
			}
			if (!StringUtils.isEmpty(status)){
				int temStatus = NumberUtils.toInt(status,1);
				toperator.setStatus(temStatus);
			}
			toperator.setSort(sidx);
			toperator.setOrder(sord);
			pages = toperatorService.findPage(NumberUtils.toInt(page, 1), NumberUtils.toInt(rows, 15), toperator);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return pages;
	}
	@RequiresPermissions(value = {MenuCode.OPERATOR + ":" + PrivCode.VIEW})
	@RequestMapping(value = "/view.do")
	public String view(@RequestParam(value="id")long id, ModelMap model){
		try {
			TOperator toperator = toperatorService.find(id);
			TOperatorRole tmpTOperatorRole = toperatorRoleService.findByOperatorId(id);
			List<String> roleNames = null;
			if (tmpTOperatorRole != null){
				String roleIds = tmpTOperatorRole.getRoleIds();
				if (StringUtils.isEmpty(roleIds)) return "TOperator/view-success";
				List<TSysrole> tmpListTSysrole = tsysroleService.find(roleIds);
			    if (tmpListTSysrole != null && !tmpListTSysrole.isEmpty()){
			    	roleNames = new ArrayList<String>();
			    	for (TSysrole tmpTSysrole : tmpListTSysrole){
			    		if (StringUtils.isEmpty(tmpTSysrole.getName())) continue;
			    		roleNames.add(tmpTSysrole.getName());
			    	}
			    }
			}
			model.addAttribute("toperator", toperator);
			model.addAttribute("roleNames", roleNames);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "TOperator/view-success";
	}
	@RequestMapping(value = "/viewSelf.do")
	public String viewSelf(@RequestParam(value="id")long id, ModelMap model){
		try {
			TOperator toperator = toperatorService.find(id);
			model.addAttribute("toperator", toperator);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "TOperator/viewSelf-success";
	}
	@RequiresPermissions(value = {MenuCode.OPERATOR + ":" + PrivCode.ADD})
	@RequestMapping(value = "/add.do")
	public String add(){
		return "TOperator/add-success";
	}
	
	@RequestMapping(value = "/getRoleTree.do")
	@ResponseBody
	public Object getRoleTree(){
		List<Select2> select2s = new ArrayList<Select2>();
		try {
			TSysrole tmpQueryTSysrole = new TSysrole();
			tmpQueryTSysrole.setStatus(1);
			tmpQueryTSysrole.setOrder("asc");
			tmpQueryTSysrole.setSort("id");
			 QueryResult<TSysrole> tmpQRTSysrole = tsysroleService.findPage(-1, -1, tmpQueryTSysrole);
			 if (tmpQRTSysrole != null && tmpQRTSysrole.getRecords() > 0){
				 Select2 select2;
					for (TSysrole tmpTSysrole : tmpQRTSysrole.getRows()){
						select2 = new Select2(tmpTSysrole.getId(), tmpTSysrole.getName());
						select2s.add(select2);
					}
			 }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return select2s;
	}
	@RequiresPermissions(value = {MenuCode.OPERATOR + ":" + PrivCode.ADD})
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public Object save(TOperator toperator, @RequestParam(value="roleIds", required=false)String roleIds){
		ResultData result = new ResultData();
		try {
			toperatorService.insert(toperator, roleIds);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.ADD, OperateResult.SUCCESS, MenuCode.OPERATOR, toperator.getLoginName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setIserror(true);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.OPERATOR + ":" + PrivCode.MODIFY})
	@RequestMapping(value = "/modify.do")
	public String modify(@RequestParam(value="id")long id, ModelMap model){
		try {
			TOperator toperator = toperatorService.find(id);
			String roleIds = null;
			TOperatorRole tmpTOperatorRole = toperatorRoleService.findByOperatorId(id);
			 if (tmpTOperatorRole != null){
				 roleIds = tmpTOperatorRole.getRoleIds();
			 }
			 model.addAttribute("toperator", toperator);
			 model.addAttribute("roleIds", roleIds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "TOperator/modify-success";
	}
	@RequestMapping(value = "/modifySelf.do")
	public String modifySelf(@RequestParam(value="id")long id, ModelMap model){
		try {
			TOperator toperator = toperatorService.find(id);
			model.addAttribute("toperator", toperator);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "TOperator/modifySelf-success";
	}
	
	@RequestMapping(value = "/updateSelf.do")
	@ResponseBody
	public Object updateSelf(TOperator toperator){
		ResultData result = new ResultData();
		try {
			toperatorService.mod(toperator);
			getCurrentOperator().setPassword(toperator.getPassword());
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.MODIFY, OperateResult.SUCCESS, MenuCode.OPERATOR, toperator.getLoginName());
		} catch (Exception e) {
			result.setIserror(true);
			result.setMessage("失败");
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value = "/modifyPassword.do")
	public String modifyPassword(@RequestParam(value="id")long id, ModelMap model){
		model.addAttribute("id", id);
		return "TOperator/modifyPassword-success";
	}
	@RequestMapping(value = "/updatePassword.do")
	@ResponseBody
	public Object updatePassword(@RequestParam(value="id")long id, @RequestParam(value="password")String password, 
			@RequestParam(value="newPassword")String newPassword){
		ResultData result = new ResultData();
		try {
			TOperator toperator = toperatorService.find(id);
			String encodePass = myPasswordService.encryptPassword(Md5Utils.md5Encode(password));
			if(!encodePass.equals(toperator.getPassword())){
				result.setIserror(true);
				result.setMessage("密码错误");
				return result;
			}
			newPassword = myPasswordService.encryptPassword(Md5Utils.md5Encode(newPassword));
			toperator.setPassword(newPassword);
			toperatorService.mod(toperator);
			getCurrentOperator().setPassword(toperator.getPassword());
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.MODIFY, OperateResult.SUCCESS, MenuCode.OPERATOR, toperator.getLoginName());
		} catch (Exception e) {
			result.setIserror(true);
			result.setMessage("失败");
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequiresPermissions(value = {MenuCode.OPERATOR + ":" + PrivCode.MODIFY})
	@RequestMapping(value = "/update.do")
	@ResponseBody
	public Object update(TOperator toperator, @RequestParam(value="roleIds", required=false)String roleIds){
		ResultData result = new ResultData();
		try {
			toperatorService.mod(toperator,roleIds);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.MODIFY, OperateResult.SUCCESS, MenuCode.OPERATOR, toperator.getLoginName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setIserror(true);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.OPERATOR + ":" + PrivCode.REMOVE})
	@RequestMapping(value = "/remove.do")
	@ResponseBody
	public Object remove(@RequestParam(value="ids")String ids){
		ResultData result = new ResultData();
		try {
			toperatorService.bathDelete(ids);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.REMOVE, OperateResult.SUCCESS, MenuCode.OPERATOR, ids);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setIserror(true);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.OPERATOR + ":" + PrivCode.ON})
	@RequestMapping(value = "/on.do")
	@ResponseBody
	public Object on(@RequestParam(value="ids")String ids){
		ResultData result = new ResultData();
		try {
			toperatorService.bathOn(ids);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.ON, OperateResult.SUCCESS, MenuCode.OPERATOR, ids);
		} catch (Exception e) {
			result.setIserror(true);
			result.setMessage("失败");
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.OPERATOR + ":" + PrivCode.OFF})
	@RequestMapping(value = "/off.do")
	@ResponseBody
	public Object off(@RequestParam(value="ids")String ids){
		ResultData result = new ResultData();
		try {
			toperatorService.bathOff(ids);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.OFF, OperateResult.SUCCESS, MenuCode.OPERATOR, ids);
		} catch (Exception e) {
			result.setIserror(true);
			result.setMessage("失败");
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	@RequiresPermissions(value = {MenuCode.OPERATOR + ":" + PrivCode.RESET_PASSWORD})
	@RequestMapping(value = "/resetPassword.do")
	@ResponseBody
	public Object resetPassword(@RequestParam(value="ids")String ids){
		ResultData result = new ResultData();
		try {
			toperatorService.bathResetPassword(ids);
			result.setIserror(false);
			result.setMessage("成功");
			saveTLogOperate(PrivCode.RESET_PASSWORD, OperateResult.SUCCESS, MenuCode.OPERATOR, ids);
		} catch (Exception e) {
			result.setIserror(true);
			result.setMessage("失败");
			logger.error(e.getMessage(), e);
		}
		return result;
	}
}

