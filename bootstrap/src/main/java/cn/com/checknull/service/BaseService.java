/**  
* @Project: xproduct
* @Title: BaseService.java
* @Package cn.com.checknull.service
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-2-26 上午11:32:13
* @Copyright: 2015 check_null Reserved.
* @version v1.0  
*/

package cn.com.checknull.service;

import javax.annotation.Resource;

import cn.com.checknull.dao.TLogLoginDao;
import cn.com.checknull.dao.TLogOperateDao;
import cn.com.checknull.dao.TOperatorDao;
import cn.com.checknull.dao.TOperatorRoleDao;
import cn.com.checknull.dao.TSysconfigDao;
import cn.com.checknull.dao.TSysmenuDao;
import cn.com.checknull.dao.TSysprivDao;
import cn.com.checknull.dao.TSysroleDao;
import cn.com.checknull.dao.TSysroleMenuDao;
import cn.com.checknull.dao.TSysrolePrivDao;


/**
 * @ClassName BaseService
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-2-26   上午11:32:13
 */
public abstract class BaseService {
	@Resource(name="tlogLoginDao")
	protected TLogLoginDao tlogLoginDao;
	@Resource(name="tlogOperateDao")
	protected TLogOperateDao tlogOperateDao;
	@Resource(name="toperatorRoleDao")
	protected TOperatorRoleDao toperatorRoleDao;
	@Resource(name="toperatorDao")
	protected TOperatorDao toperatorDao;
	@Resource(name="tsysconfigDao")
	protected TSysconfigDao tsysconfigDao;
	@Resource(name="tsysmenuDao")
	protected TSysmenuDao tsysmenuDao;
	@Resource(name="tsysprivDao")
	protected TSysprivDao tsysprivDao;
	@Resource(name="tsysroleDao")
	protected TSysroleDao tsysroleDao;
	@Resource(name="tsysroleMenuDao")
	protected TSysroleMenuDao tsysroleMenuDao;
	@Resource(name="tsysrolePrivDao")
	protected TSysrolePrivDao tsysrolePrivDao;
	public TLogLoginDao getTlogLoginDao() {
		return tlogLoginDao;
	}
	public void setTlogLoginDao(TLogLoginDao tlogLoginDao) {
		this.tlogLoginDao = tlogLoginDao;
	}
	public TLogOperateDao getTlogOperateDao() {
		return tlogOperateDao;
	}
	public void setTlogOperateDao(TLogOperateDao tlogOperateDao) {
		this.tlogOperateDao = tlogOperateDao;
	}
	public TOperatorRoleDao getToperatorRoleDao() {
		return toperatorRoleDao;
	}
	public void setToperatorRoleDao(TOperatorRoleDao toperatorRoleDao) {
		this.toperatorRoleDao = toperatorRoleDao;
	}
	public TOperatorDao getToperatorDao() {
		return toperatorDao;
	}
	public void setToperatorDao(TOperatorDao toperatorDao) {
		this.toperatorDao = toperatorDao;
	}
	public TSysconfigDao getTsysconfigDao() {
		return tsysconfigDao;
	}
	public void setTsysconfigDao(TSysconfigDao tsysconfigDao) {
		this.tsysconfigDao = tsysconfigDao;
	}
	public TSysmenuDao getTsysmenuDao() {
		return tsysmenuDao;
	}
	public void setTsysmenuDao(TSysmenuDao tsysmenuDao) {
		this.tsysmenuDao = tsysmenuDao;
	}
	public TSysprivDao getTsysprivDao() {
		return tsysprivDao;
	}
	public void setTsysprivDao(TSysprivDao tsysprivDao) {
		this.tsysprivDao = tsysprivDao;
	}
	public TSysroleDao getTsysroleDao() {
		return tsysroleDao;
	}
	public void setTsysroleDao(TSysroleDao tsysroleDao) {
		this.tsysroleDao = tsysroleDao;
	}
	public TSysroleMenuDao getTsysroleMenuDao() {
		return tsysroleMenuDao;
	}
	public void setTsysroleMenuDao(TSysroleMenuDao tsysroleMenuDao) {
		this.tsysroleMenuDao = tsysroleMenuDao;
	}
	public TSysrolePrivDao getTsysrolePrivDao() {
		return tsysrolePrivDao;
	}
	public void setTsysrolePrivDao(TSysrolePrivDao tsysrolePrivDao) {
		this.tsysrolePrivDao = tsysrolePrivDao;
	}
}

