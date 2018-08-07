/**  
* @Project: xproduct
* @Title: TOperatorService.java
* @Package cn.com.checknull.service
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-3-17 上午9:47:19
* @Copyright: 2015 check_null Reserved.
* @version v1.0  
*/

package cn.com.checknull.service;

import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.checknull.model.po.TOperator;
import cn.com.checknull.model.po.TOperatorRole;
import cn.com.checknull.model.po.TSysrole;
import cn.com.checknull.model.vo.QueryResult;
import cn.com.checknull.shiro.crypto.MyPasswordService;
import cn.com.checknull.util.Md5Utils;

/**
 * @ClassName TOperatorService
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-3-17   上午9:47:19
 */
@Transactional
public class TOperatorService extends BaseService{
	@Resource(name="myPasswordService")
	private MyPasswordService myPasswordService;
	
	public void setMyPasswordService(MyPasswordService myPasswordService) {
		this.myPasswordService = myPasswordService;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<TOperator>  findPage(int pageIndex, int pageSize, TOperator queryEntity){
		return toperatorDao.findPage(pageIndex, pageSize, queryEntity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public TOperator find(Long id) throws SQLException{
		return toperatorDao.find(id);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public TOperator findByLoginName(String loginName) throws SQLException{
		return toperatorDao.findByLoginName(loginName);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void insert(TOperator entity, String roleIds) throws SQLException{
		if (entity == null) return;
		TOperator tmpTOperator = toperatorDao.findByLoginName(entity.getLoginName());
		if (tmpTOperator != null){
			throw new SQLException("登录名（" + entity.getLoginName() + "），已被使用");
		}
		String encodePassword = myPasswordService.encryptPassword(Md5Utils.md5Encode(entity.getPassword()));
		entity.setPassword(encodePassword);
		toperatorDao.insert(entity);
		
		if (roleIds == null || StringUtils.isEmpty(roleIds)) return;
		String[] array = roleIds.split(",");
		for (String str : array){
			Long id = NumberUtils.toLong(str, 0L);
			TSysrole tmpTSysrole = tsysroleDao.find(id);
			if(tmpTSysrole == null){
				throw new SQLException("角色ID（" + id + "），已不存在");
			}
		}
		TOperatorRole tmpTOperatorRole = new TOperatorRole();
		tmpTOperatorRole.setOperatorId(entity.getId());
		tmpTOperatorRole.setRoleIds(roleIds);
		toperatorRoleDao.insert(tmpTOperatorRole);
	}
	public void mod(TOperator entity) throws SQLException {
		if (entity == null) return;
		Long id = entity.getId();
		if (find(id) == null) return;
		entity.setUpdateTime(new Date());
		toperatorDao.update(entity);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void mod(TOperator entity, String roleIds) throws SQLException {
		if (entity == null) return;
		Long id = entity.getId();
		if (find(id) == null) return;
		TOperator tmpTOperator = toperatorDao.findByLoginName(entity.getLoginName());
		if (tmpTOperator != null && tmpTOperator.getId().compareTo(id) != 0){
			throw new SQLException("登录名（" + entity.getLoginName() + "），已被使用");
		}
		entity.setUpdateTime(new Date());
		toperatorDao.update(entity);
		if (roleIds != null && !StringUtils.isEmpty(roleIds)) {
			String[] array = roleIds.split(",");
			for (String str : array){
				Long tmpId = NumberUtils.toLong(str, 0L);
				TSysrole tmpTSysrole = tsysroleDao.find(tmpId);
				if(tmpTSysrole == null){
					throw new SQLException("角色ID（" + id + "），已不存在");
				}
			}
		}
		
		TOperatorRole tmpTOperatorRole = toperatorRoleDao.findByOperatorId(id);
		if (tmpTOperatorRole != null){
			tmpTOperatorRole.setRoleIds(roleIds);
			toperatorRoleDao.update(tmpTOperatorRole);
		}else{
			tmpTOperatorRole = new TOperatorRole();
			tmpTOperatorRole.setOperatorId(entity.getId());
			tmpTOperatorRole.setRoleIds(roleIds);
			toperatorRoleDao.insert(tmpTOperatorRole);
		}
	}
	
	public void bathDelete(String ids) throws SQLException {
		if (StringUtils.isEmpty(ids)) return;
		String[] array = ids.split(",");
		for (String str : array){
			Long id = NumberUtils.toLong(str, 0L);
			TOperator entity = find(id);
			if (entity == null) continue;
			TOperatorRole tmpTOperatorRole = toperatorRoleDao.findByOperatorId(id);
			if (tmpTOperatorRole != null){
				toperatorRoleDao.delete(tmpTOperatorRole.getId());
			}
			toperatorDao.delete(id);
		}
	}
	
	public void bathOn(String ids) throws SQLException {
		if (StringUtils.isEmpty(ids)) return;
		String[] array = ids.split(",");
		for (String str : array){
			Long id = NumberUtils.toLong(str, 0L);
			TOperator entity = find(id);
			if (entity == null) continue;
			entity.setStatus(1);
			entity.setUpdateTime(new Date());
			toperatorDao.update(entity);
		}
	}

	public void bathOff(String ids) throws SQLException {
		if (StringUtils.isEmpty(ids)) return;
		String[] array = ids.split(",");
		for (String str : array){
			Long id = NumberUtils.toLong(str, 0L);
			TOperator entity = find(id);
			if (entity == null) continue;
			entity.setStatus(0);
			entity.setUpdateTime(new Date());
			toperatorDao.update(entity);
		}
	}
	
	public void bathResetPassword(String ids) throws SQLException {
		if (StringUtils.isEmpty(ids)) return;
		String[] array = ids.split(",");
		for (String str : array){
			Long id = NumberUtils.toLong(str, 0L);
			TOperator entity = find(id);
			if (entity == null) continue;
			String encodePassword = myPasswordService.encryptPassword(Md5Utils.md5Encode("123456"));
			entity.setPassword(encodePassword);
			entity.setUpdateTime(new Date());
			toperatorDao.update(entity);
		}
	}

}

