package com.wyd.empire.world.server.service.base.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.bean.ButtonInfo;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.dao.IOperationConfigDao;
import com.wyd.empire.world.server.service.base.IOperationConfigService;

/**
 * The service class for the TabOperationconfig entity.
 */
public class OperationConfigService extends UniversalManagerImpl implements IOperationConfigService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IOperationConfigDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "OperationconfigService";

	public OperationConfigService() {
		super();
	}

	/**
	 * Returns the singleton <code>IOperationconfigService</code> instance.
	 */
	public static IOperationConfigService getInstance(ApplicationContext context) {
		return (IOperationConfigService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IOperationConfigDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IOperationConfigDao getDao() {
		return this.dao;
	}

	/**
	 * 获取本服的配置
	 * 
	 * @return
	 */
	public OperationConfig getConfig() {
		return dao.getConfig();
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		dao.initData();
	}

	public Map<String, Integer> getSpecialMark() {
		return dao.getSpecialMark();
	}

	/**
	 * 获取所有分区ID和名字
	 * 
	 * @return
	 */
	@Override
	public List<Object[]> getAllAreaIdAndName() {
		return dao.getAreaIdAndName(null);
	}

	/**
	 * 获取所有当前分区ID和名字
	 * 
	 * @return
	 */
	@Override
	public Map<String, String> getCurAreaIdAndName() {
		Object[] o = dao.getAreaIdAndName(WorldServer.config.getAreaId()).get(0);
		Map<String, String> m = new HashMap<String, String>();
		m.put("areaId", (String) o[0]);
		m.put("areaName", (String) o[1]);
		return m;
	}

	/**
	 * 根据分区id（areaId）更新活动广场小金人的url
	 */
	@Override
	public void updataCfgURL(String url, String areaIds) {
		dao.updataCfgURL(url, areaIds);
	}

	/**
	 * 根据areasIds同步配置
	 * 
	 * @param config
	 *            配置类
	 * @param areasIds
	 *            分区ID
	 */
	public void updateCfgByAreasIds(OperationConfig config, String areasIds) {
		dao.updateCfgByAreasIds(config, areasIds);
	}

	/**
	 * 获取按钮信息
	 * 
	 * @return
	 */
	public List<ButtonInfo> getButtonList() {
		return dao.getButtonList();
	}

	/**
	 * 根据id获取按钮信息
	 * 
	 * @param buttonId
	 * @return
	 */
	public ButtonInfo getButtonInfoById(int buttonId) {
		return dao.getButtonInfoById(buttonId);
	}

	/**
	 * 获取button列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getButtonInfoList(int pageNum, int pageSize) {
		return dao.getButtonInfoList(pageNum, pageSize);
	}

	/**
	 * 根据areasIds同步配置
	 * 
	 * @param config
	 *            配置类
	 * @param areasIds
	 *            分区ID
	 */
	public void copyButtonInfoByAreasIds(String areasIds) {
		if (areasIds.equals(null) || areasIds.equals("")) {
			System.out.println("参数错误！！！");
			return;
		}
		List<ButtonInfo> list = dao.getButtonInfoAllByAreaId();
		if (list.size() > 0) {
			String[] areaIdArray = areasIds.split(",");
			for (String areaId : areaIdArray) {
				if (dao.getButtonInfoCountByAreaId(areaId) > 0) {
					System.out.println("按钮配置同步跳过" + areaId + "区，此区原本已经包含了按钮配置信息！！！");
				} else {
					for (ButtonInfo buttonInfo : list) {
						buttonInfo.setId(0);
						buttonInfo.setAreaId(areaId);
						dao.save(buttonInfo);
					}
					System.out.println(areaId + "区，按钮配置同步成功！！！");
				}
			}
		}
	}

	/**
	 * 根据多个ID删除
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteButtonInfo(String ids) {
		dao.deleteButtonInfo(ids);
	}

	/**
	 * 获得结婚时间
	 * 
	 * @param id
	 * @return
	 */
	public String getWedTimeById(int id) {
		return dao.getWedTimeById(id);
	}

	/**
	 * 获得结婚时间Map
	 **/
	public Map<Integer, String> getWedMap() {
		return dao.getWedMap();
	}

	/**
	 * 获得每日首充奖励次数（根据充值额度）
	 **/
	public Map<Integer, Integer> getRechargeIntervalMap() {
		return dao.getRechargeIntervalMap();
	}

	/**
	 * 获得结婚相关配置
	 * 
	 * @param key
	 * @return
	 */
	public Integer getWedConfigByKey(String key) {
		return dao.getWedConfigByKey(key);
	}

	/**
	 * 根据战斗场次获得相应比例
	 * 
	 * @param battleNum
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int getExpRateByBattleNum(int battleNum) {
		Map<Integer, Integer> map = dao.getExpMap();
		int rate = 100;
		Set<Integer> key = map.keySet();
		for (Iterator it = key.iterator(); it.hasNext();) {
			int keyvalue = (Integer) it.next();
			if (keyvalue < battleNum) {
				rate = map.get(keyvalue);
			}
		}
		return rate;
	}

	/**
	 * 获取补签价格列表
	 * 
	 * @return
	 */
	public List<Integer> getSupplPriceList() {
		return dao.getSupplPriceList();
	}
}