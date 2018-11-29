package com.alpha.self.diagnosis.dao;

import java.util.List;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.diagnosis.pojo.DrugOnSellDetail;

public interface DrugDao extends IBaseDao<DrugOnSellDetail, Long> {

	/**
	 * 根据关键字查询药品
	 * @param keyword
	 * @return
	 */
	List<DrugOnSellDetail> listByKeyword(String keyword);
	
	/**
	 * 根据药品编码查看药品
	 * @param drugCode
	 * @return
	 */
	DrugOnSellDetail getByDrugCode(String drugCode);
	
	/**
     * 根据用户行为获取热门药品
     * @return
     */
    List<DrugOnSellDetail> listUserHotDrug();
    
    /**
     * 根据系统默认排序查询药品
     * @param size 要获取的数量
     * @return
     */
    List<DrugOnSellDetail> listAnyDrugOrderByDefaultOrder(int size);
    
    /**
     * 根据药品名称查询
     * @param diseaseName
     * @return
     */
    List<DrugOnSellDetail> listByDrugName(String drugName);
}
