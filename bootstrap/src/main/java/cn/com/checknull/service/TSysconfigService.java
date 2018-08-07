/**  
* @Project: xproduct
* @Title: TSysconfigService.java
* @Package cn.com.checknull.service
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-3-17 下午4:32:53
* @Copyright: 2015 check_null Reserved.
* @version v1.0  
*/

package cn.com.checknull.service;

import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.checknull.model.po.TSysconfig;
import cn.com.checknull.model.vo.QueryResult;

/**
 * @ClassName TSysconfigService
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-3-17   下午4:32:53
 */
@Transactional
public class TSysconfigService extends BaseService{

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<TSysconfig>  findPage(int pageIndex, int pageSize, TSysconfig queryEntity){
		return tsysconfigDao.findPage(pageIndex, pageSize, queryEntity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public TSysconfig find(Long id) throws SQLException{
		return tsysconfigDao.find(id);
	}
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public TSysconfig findByParamCode(String paramCode) throws SQLException{
		return tsysconfigDao.findByParamCode(paramCode);
	}
	
	public void insert(TSysconfig entity) throws SQLException{
		if (entity == null) return;
		TSysconfig tmpTSysconfig = tsysconfigDao.findByParamName(entity.getParamName());
		if (tmpTSysconfig != null){
			throw new SQLException("配置名称（" + entity.getParamName() + "）已被使用");
		}
		tmpTSysconfig = tsysconfigDao.findByParamCode(entity.getParamCode());
		if (tmpTSysconfig != null){
			throw new SQLException("配置代码（" + entity.getParamCode() + "）已被使用");
		}
		tsysconfigDao.insert(entity);
	}
	
	public void mod(TSysconfig entity) throws SQLException {
		if (entity == null) return;
		Long id = entity.getId();
		if (find(id) == null) return;
		TSysconfig tmpTSysconfig = tsysconfigDao.findByParamName(entity.getParamName());
		if (tmpTSysconfig != null && tmpTSysconfig.getId().compareTo(id) != 0){
			throw new SQLException("配置名称（" + entity.getParamName() + "）已被使用");
		}
		tmpTSysconfig = tsysconfigDao.findByParamCode(entity.getParamCode());
		if (tmpTSysconfig != null && tmpTSysconfig.getId().compareTo(id) != 0){
			throw new SQLException("配置代码（" + entity.getParamCode() + "）已被使用");
		}
		tsysconfigDao.update(entity);
	}
	
	public void bathDelete(String ids) throws SQLException {
		if (StringUtils.isEmpty(ids)) return;
		String[] array = ids.split(",");
		for (String str : array){
			Long id = NumberUtils.toLong(str, 0L);
			tsysconfigDao.delete(id);
		}
	}
}

