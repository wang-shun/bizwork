package com.sogou.bizwork.cas.application.dao;

import com.sogou.bizwork.cas.application.model.Attribute;
import com.sogou.bizwork.cas.application.model.AttributeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AttributeMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table uuc_attribute
	 * @mbggenerated
	 */
	int countByExample(AttributeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table uuc_attribute
	 * @mbggenerated
	 */
	int deleteByExample(AttributeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table uuc_attribute
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(Integer attributeId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table uuc_attribute
	 * @mbggenerated
	 */
	int insert(Attribute record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table uuc_attribute
	 * @mbggenerated
	 */
	int insertSelective(Attribute record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table uuc_attribute
	 * @mbggenerated
	 */
	List<Attribute> selectByExample(AttributeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table uuc_attribute
	 * @mbggenerated
	 */
	Attribute selectByPrimaryKey(Integer attributeId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table uuc_attribute
	 * @mbggenerated
	 */
	int updateByExampleSelective(@Param("record") Attribute record,
			@Param("example") AttributeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table uuc_attribute
	 * @mbggenerated
	 */
	int updateByExample(@Param("record") Attribute record,
			@Param("example") AttributeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table uuc_attribute
	 * @mbggenerated
	 */
	int updateByPrimaryKeySelective(Attribute record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table uuc_attribute
	 * @mbggenerated
	 */
	int updateByPrimaryKey(Attribute record);
}