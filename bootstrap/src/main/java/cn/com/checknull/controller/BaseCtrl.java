/**  
* @Project: xproduct-spmvc
* @Title: BaseCtrl.java
* @Package cn.com.checknull.controller
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2016-6-19 下午1:54:49
* @version v1.0  
*/

package cn.com.checknull.controller;

import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.checknull.constant.enumer.OperateResult;
import cn.com.checknull.constant.enumer.SessionAttribute;
import cn.com.checknull.model.po.TLogOperate;
import cn.com.checknull.model.po.TOperator;
import cn.com.checknull.service.TLogLoginService;
import cn.com.checknull.service.TLogOperateService;
import cn.com.checknull.service.TOperatorRoleService;
import cn.com.checknull.service.TOperatorService;
import cn.com.checknull.service.TSysconfigService;
import cn.com.checknull.service.TSysmenuService;
import cn.com.checknull.service.TSysprivService;
import cn.com.checknull.service.TSysroleMenuService;
import cn.com.checknull.service.TSysrolePrivService;
import cn.com.checknull.service.TSysroleService;

/**
 * @ClassName BaseCtrl
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2016-6-19   下午1:54:49
 */
public abstract class BaseCtrl {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected HttpServletRequest request;
	@Resource(name="tlogLoginService")
	protected TLogLoginService tlogLoginService;
	@Resource(name="tlogOperateService")
	protected TLogOperateService tlogOperateService;
	@Resource(name="toperatorService")
	protected TOperatorService toperatorService;
	@Resource(name="toperatorRoleService")
	protected TOperatorRoleService toperatorRoleService;
	@Resource(name="tsysroleService")
	protected TSysroleService tsysroleService;
	@Resource(name="tsysroleMenuService")
	protected TSysroleMenuService tsysroleMenuService;
	@Resource(name="tsysmenuService")
	protected TSysmenuService tsysmenuService;
	@Resource(name="tsysprivService")
	protected TSysprivService tsysprivService;
	@Resource(name="tsysrolePrivService")
	protected TSysrolePrivService tsysrolePrivService;
	@Resource(name="tsysconfigService")
	protected TSysconfigService tsysconfigService;
	
	public TLogLoginService getTlogLoginService() {
		return tlogLoginService;
	}

	public void setTlogLoginService(TLogLoginService tlogLoginService) {
		this.tlogLoginService = tlogLoginService;
	}

	public TLogOperateService getTlogOperateService() {
		return tlogOperateService;
	}

	public void setTlogOperateService(TLogOperateService tlogOperateService) {
		this.tlogOperateService = tlogOperateService;
	}

	public TOperatorService getToperatorService() {
		return toperatorService;
	}

	public void setToperatorService(TOperatorService toperatorService) {
		this.toperatorService = toperatorService;
	}

	public TOperatorRoleService getToperatorRoleService() {
		return toperatorRoleService;
	}

	public void setToperatorRoleService(TOperatorRoleService toperatorRoleService) {
		this.toperatorRoleService = toperatorRoleService;
	}

	public TSysroleService getTsysroleService() {
		return tsysroleService;
	}

	public void setTsysroleService(TSysroleService tsysroleService) {
		this.tsysroleService = tsysroleService;
	}

	public TSysroleMenuService getTsysroleMenuService() {
		return tsysroleMenuService;
	}

	public void setTsysroleMenuService(TSysroleMenuService tsysroleMenuService) {
		this.tsysroleMenuService = tsysroleMenuService;
	}

	public TSysmenuService getTsysmenuService() {
		return tsysmenuService;
	}

	public void setTsysmenuService(TSysmenuService tsysmenuService) {
		this.tsysmenuService = tsysmenuService;
	}

	public TSysprivService getTsysprivService() {
		return tsysprivService;
	}

	public void setTsysprivService(TSysprivService tsysprivService) {
		this.tsysprivService = tsysprivService;
	}

	public TSysrolePrivService getTsysrolePrivService() {
		return tsysrolePrivService;
	}

	public void setTsysrolePrivService(TSysrolePrivService tsysrolePrivService) {
		this.tsysrolePrivService = tsysrolePrivService;
	}

	public TSysconfigService getTsysconfigService() {
		return tsysconfigService;
	}

	public void setTsysconfigService(TSysconfigService tsysconfigService) {
		this.tsysconfigService = tsysconfigService;
	}

	public TOperator getCurrentOperator(){
		Object obj = request.getSession().getAttribute(SessionAttribute.OPERATOR.getAttribute());
		if (obj instanceof TOperator){
			return (TOperator)obj;
		}
		return null;
	}
	
	/**
	 * 
	* @Title: saveTLogOperate 
	* @Description: TODO
	* @param privCode
	* @param operateResult
	* @param menuCode
	* @param operateValue 操作值   （一般为每条记录的ID，也可以是其他的可以辨别的唯一标识）
	 */
	public void saveTLogOperate(String privCode, OperateResult operateResult, String menuCode, Object operateValue){
		 TLogOperate tmpTLogOperate= new TLogOperate();
		 tmpTLogOperate.setLoginName(getCurrentOperator().getLoginName());
		 tmpTLogOperate.setOperateObject(menuCode);
		 tmpTLogOperate.setOperateResult(operateResult.getResult());
		 tmpTLogOperate.setOperateTime(new Date());
		 tmpTLogOperate.setOperateType(privCode);
		 tmpTLogOperate.setOperateValue(operateValue + "");
		 tmpTLogOperate.setOperatorId(getCurrentOperator().getId());
		 tmpTLogOperate.setOperatorName(getCurrentOperator().getRealName());
		 try {
			tlogOperateService.insert(tmpTLogOperate);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
}

