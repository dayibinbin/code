package cn.com.checknull.model.po;

// Generated 2015-3-17 16:18:12 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import cn.com.checknull.model.serializer.DateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * TLogOperate generated by hbm2java
 */
public class TLogOperate extends QueryEntity implements java.io.Serializable {

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 2476298626711623156L;
	private Long id;
	private String loginName;
	private String operatorName;
	private Long operatorId;
	private String operateType;
	private Integer operateResult;
	private String operateObject;
	private String operateValue;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = DateSerializer.class)
	private Date operateTime;

	public TLogOperate() {
	}

	public TLogOperate(String loginName, String operatorName, Long operatorId,
			String operateType, Integer operateResult, String operateObject,
			String operateValue, Date operateTime) {
		this.loginName = loginName;
		this.operatorName = operatorName;
		this.operatorId = operatorId;
		this.operateType = operateType;
		this.operateResult = operateResult;
		this.operateObject = operateObject;
		this.operateValue = operateValue;
		this.operateTime = operateTime;
	}
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Long getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperateType() {
		return this.operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public Integer getOperateResult() {
		return this.operateResult;
	}

	public void setOperateResult(Integer operateResult) {
		this.operateResult = operateResult;
	}
	public String getOperateObject() {
		return this.operateObject;
	}

	public void setOperateObject(String operateObject) {
		this.operateObject = operateObject;
	}
	public String getOperateValue() {
		return this.operateValue;
	}

	public void setOperateValue(String operateValue) {
		this.operateValue = operateValue;
	}
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TLogOperate other = (TLogOperate) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public void setSort(String sort) {
		
		if (StringUtils.isEmpty(sort) || "id".equals(sort)){
			super.setSort("ID");
		}else if ("loginName".equals(sort)){
			super.setSort("LOGIN_NAME");
		}else if ("operatorName".equals(sort)){
			super.setSort("OPERATOR_NAME");
		}else if ("operatorId".equals(sort)){
			super.setSort("OPERATOR_ID");
		}else if ("operateType".equals(sort)){
			super.setSort("OPERATE_TYPE");
		}else if ("operateResult".equals(sort)){
			super.setSort("OPERATE_RESULT");
		}else if ("operateObject".equals(sort)){
			super.setSort("OPERATE_OBJECT");
		}else if ("operateValue".equals(sort)){
			super.setSort("OPERATE_VALUE");
		}else if ("operateTime".equals(sort)){
			super.setSort("OPERATE_TIME");
		}else{
			super.setSort("ID");
		}
	}
}
