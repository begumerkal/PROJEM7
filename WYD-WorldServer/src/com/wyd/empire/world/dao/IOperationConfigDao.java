package com.wyd.empire.world.dao;

import java.util.List;
import java.util.Map;

import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.bean.ButtonInfo;
import com.wyd.empire.world.bean.OperationConfig;

/**
 * The DAO interface for the TabOperationconfig entity.
 */
public interface IOperationConfigDao extends UniversalDao {
	/**
	 * 获取本服的配置
	 * 
	 * @return
	 */
	public OperationConfig getConfig();

	/**
	 * 初始化数据
	 */
	public void initData();

	/**
	 * 配置表特殊标示
	 **/
	public Map<String, Integer> getSpecialMark();

	/**
	 * 获取所有分区ID和名字
	 * 
	 * @return
	 */
	public List<Object[]> getAreaIdAndName(String areaId);

	/**
	 * 根据分区id（areaId）更新活动广场小金人的url
	 */
	public void updataCfgURL(String url, String areaIds);

	/**
	 * 获取按钮信息
	 * 
	 * @return
	 */
	public List<ButtonInfo> getButtonList();

	/**
	 * 根据id获取按钮信息
	 * 
	 * @param buttonId
	 * @return
	 */
	public ButtonInfo getButtonInfoById(int buttonId);

	/**
	 * 根据areasIds同步配置
	 * 
	 * @param config
	 *            配置类
	 * @param areasIds
	 *            分区ID
	 */
	public void updateCfgByAreasIds(OperationConfig config, String areasIds);

	/**
	 * 获取button列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getButtonInfoList(int pageNum, int pageSize);

	/**
	 * 根据多个ID删除
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteButtonInfo(String ids);

	/**
	 * 获得结婚时间
	 * 
	 * @param id
	 * @return
	 */
	public String getWedTimeById(int id);

	/**
	 * 获得结婚时间Map
	 **/
	public Map<Integer, String> getWedMap();

	/**
	 * 获得每日首充奖励次数（根据充值额度）Map
	 **/
	public Map<Integer, Integer> getRechargeIntervalMap();

	/**
	 * 获得结婚相关配置
	 * 
	 * @param key
	 * @return
	 */
	public Integer getWedConfigByKey(String key);

	/**
	 * 获得战斗场次经验配置
	 **/
	public Map<Integer, Integer> getExpMap();

	/**
	 * 获取button列表
	 * 
	 * @return
	 */
	public List<ButtonInfo> getButtonInfoAllByAreaId();

	/**
	 * 获取某区button总数
	 * 
	 * @return
	 */
	public Long getButtonInfoCountByAreaId(String areaId);

	/**
	 * 获取补签价格列表
	 * 
	 * @return
	 */
	public List<Integer> getSupplPriceList();
}