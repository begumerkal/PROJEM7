package com.wyd.empire.world.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.StringUtils;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.bean.ButtonInfo;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.dao.IOperationConfigDao;

/**
 * The DAO class for the TabOperationconfig entity.
 */
public class OperationConfigDao extends UniversalDaoHibernate implements IOperationConfigDao {
	private OperationConfig config = null;
	private Map<String, Integer> map = null;
	private List<ButtonInfo> buttonList = null;
	private Map<Integer, ButtonInfo> buttonMap = null;
	// 婚礼时间配置
	private Map<Integer, String> wedTimeMap = null;
	// 婚礼相关参数配置
	private Map<String, Integer> wedConfigMap = null;
	// 战斗场次经验配置
	private Map<Integer, Integer> expMap = null;
	private List<Integer> supplPriceList = null;
	// 每日首充奖励次数（根据充值额度）
	private Map<Integer, Integer> rechargeIntervalMap;

	public OperationConfigDao() {
		super();
	}

	/**
	 * 初始化数据
	 */
	@SuppressWarnings("unchecked")
	public void initData() {
		config = (OperationConfig) getClassObj("from OperationConfig where areaId=?", new Object[]{WorldServer.config.getAreaId()});
		String specialMark = config.getSpecialMark();
		String[] markStr = specialMark.split(",");
		Map<String, Integer> map = new HashMap<String, Integer>();
		String[] str;
		for (String s : markStr) {
			str = s.split("=");
			map.put(str[0], Integer.parseInt(str[1]));
		}
		this.map = map;
		buttonList = getList("from ButtonInfo where areaId=?", new Object[]{WorldServer.config.getAreaId()});
		Map<Integer, ButtonInfo> buttonMap = new HashMap<Integer, ButtonInfo>();
		for (ButtonInfo buttonInfo : buttonList) {
			buttonMap.put(buttonInfo.getButtonId(), buttonInfo);
		}
		this.buttonMap = buttonMap;
		String wedString = config.getWedTime();
		String[] wedmark = wedString.split("\\|");
		Map<Integer, String> wedTimeMap = new HashMap<Integer, String>();
		String[] wedstr;
		for (String s : wedmark) {
			wedstr = s.split("#");
			wedTimeMap.put(Integer.parseInt(wedstr[0]), wedstr[1]);
		}
		this.wedTimeMap = wedTimeMap;
		String wedConfig = config.getWedconfig();
		String[] wedConfigStr = wedConfig.split(",");
		Map<String, Integer> wedConfigMap = new HashMap<String, Integer>();
		String[] wcStr;
		for (String s : wedConfigStr) {
			wcStr = s.split("=");
			wedConfigMap.put(wcStr[0], Integer.parseInt(wcStr[1]));
		}
		this.wedConfigMap = wedConfigMap;
		String expRate = config.getExpRate();
		String[] expRatemark = expRate.split("\\|");
		Map<Integer, Integer> expRateMap = new HashMap<Integer, Integer>();
		String[] expRatestr;
		for (String s : expRatemark) {
			expRatestr = s.split("=");
			expRateMap.put(Integer.parseInt(expRatestr[0]), Integer.parseInt(expRatestr[1]));
		}
		this.expMap = expRateMap;
		this.supplPriceList = new ArrayList<Integer>();
		String supplPrice = config.getSupplPrice();
		if (null == supplPrice || supplPrice.length() < 30) {
			supplPrice = "20,40,60,80,100,120,140,160,180,200,220,240,260,280,300,320,340,360,380,400,420,440,460,480,500,520,540,560,580,600";
		}
		for (String price : supplPrice.split(",")) {
			this.supplPriceList.add(Integer.parseInt(price));
		}
		// 根据充值金额间隔配置每日首充奖励每次
		String rechargeInterval = config.getRechargeInterval();
		String[] rechargeIntervalMark = rechargeInterval.split("\\|");
		Map<Integer, Integer> rechargeIntervalMap = new HashMap<Integer, Integer>();
		String[] rechargeIntervalStr;
		for (String s : rechargeIntervalMark) {
			rechargeIntervalStr = s.split("#");
			rechargeIntervalMap.put(Integer.parseInt(rechargeIntervalStr[0]), Integer.parseInt(rechargeIntervalStr[1]));
		}
		this.rechargeIntervalMap = rechargeIntervalMap;
	}

	/**
	 * 获取本服的配置
	 * 
	 * @return
	 */
	public OperationConfig getConfig() {
		return config;
	}

	/**
	 * 配置表特殊标示
	 **/
	public Map<String, Integer> getSpecialMark() {
		return map;
	}

	/**
	 * 获取所有分区ID和名字
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAreaIdAndName(String areaId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" SELECT o.areaId, o.areaName FROM OperationConfig as o WHERE 1 = 1 ");
		if (StringUtils.hasText(areaId)) {
			hql.append(" AND o.areaId = ? ");
			values.add(areaId);
		}
		return getList(hql.toString(), values.toArray());
	}

	/**
	 * 根据分区id（areaId）更新活动广场小金人的url
	 */
	@Override
	public void updataCfgURL(String url, String areaIds) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" UPDATE OperationConfig SET webUrl = ? WHERE 1 = 1 ");
		values.add(url);
		hql.append(" AND areaId in ( ");
		String[] ids = areaIds.split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append("'");
			sb.append(ids[i]);
			sb.append("'");
		}
		hql.append(sb.toString());
		hql.append(")");
		execute(hql.toString(), values.toArray());
	}

	/**
	 * 根据areasIds同步配置
	 * 
	 * @param config
	 *            配置类
	 * @param areasIds
	 *            分区ID
	 */
	@Override
	public void updateCfgByAreasIds(OperationConfig config, String areasIds) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		// hql.append(" UPDATE OperationConfig SET webUrl = ?, about = ?, " +
		// "notice = ?,  help = ?, pushEndTime = ?, pushStartTime = ?, " +
		// "prices = ?, firstChargeReward = ?, moreChargeReward = ?, " +
		// "discount = ?, strengthenLevel = ?, dhPrice = ?, jhPrice = ?," +
		// " marryDiamond = ?, reDramond = ?, reBadge = ?, specialMark = ?," +
		// " addition = ?, firstChargeRewardType = ?, chargeReward = ?, " +
		// "chargeRewardRemark = ?, wbContent = ?, wbPicUrl = ?, " +
		// "recallDay = ?, rankreward = ?, fundAllocation = ?, rebirthRemark = ?,"
		// +
		// " shopDiscount = ?, shopNoDiscountId = ?, dayTaskNum = ?, noviceRemark = ?,"
		// +
		// " noviceType = ?, firstRankReward = ?, unitPrice = ?, blastLevel = ?, "
		// +
		// "guideLevel = ?, drawDetail = ?, challengeReward = ?, challengeJson = ?,"
		// +
		// " worldBoss = ?, crossLevel = ?, moreGame = ?, bubbleTips = ?, " +
		// "showTipsTimePeriod = ? WHERE 1 = 1 ");
		hql.append(" UPDATE tab_operationconfig SET webUrl = ?,about = ?, notice = ?,  help = ?, push_end_time = ?, push_start_time = ?, prices = ?, first_charge_reward = ?, more_charge_eward = ?, discount = ?, strengthenLevel = ?, dhPrice = ?, jhPrice = ?, marryDiamond = ?, reDramond = ?, reBadge = ?, specialMark = ?, addition = ?, first_chargereward_type = ?, chargereward = ?, chargereward_remark = ?, wb_content = ?, wb_picurl = ?, recall_day = ?, rankreward = ?, fundInfo = ?, rebirth_remark = ?, shop_discount = ?, shop_no_discount_id = ?, day_task_num = ?, novice_remark = ?, novice_type = ?, first_rank_reward = ?, unitPrice = ?, blast_level = ?, guide_level = ?, draw_detail = ?, challenge_reward = ?, challenge_json = ?, world_boss = ?, cross_level = ?, moreGame = ?, bubble_tips = ?, show_tips_time = ? , give_diamond_ratio = ?, level_parabola_range = ? WHERE 1 = 1 ");
		values.add(config.getWebUrl());
		values.add(config.getAbout());
		values.add(config.getNotice());
		values.add(config.getHelp());
		// values.add(config.getPushEndTime());
		// values.add(config.getPushStartTime());
		values.add(config.getPrices());
		// values.add(config.getFirstChargeReward());
		// values.add(config.getMoreChargeReward());
		// values.add(config.getDiscount());
		values.add(config.getStrengthenLevel());
		values.add(config.getDhPrice());
		values.add(config.getJhPrice());
		values.add(config.getMarryDiamond());
		values.add(config.getReDramond());
		values.add(config.getReBadge());
		values.add(config.getSpecialMark());
		values.add(config.getAddition());
		// values.add(config.getFirstChargeRewardType());
		// values.add(config.getChargeReward());
		// values.add(config.getChargeRewardRemark());
		values.add(config.getWbPicUrl());
		values.add(config.getRecallDay());
		values.add(config.getRankreward());
		values.add(config.getFundAllocation());
		values.add(config.getRebirthRemark() == null ? "" : config.getRebirthRemark());
		values.add(config.getShopDiscount());
		values.add(config.getShopNoDiscountId() == null ? "" : config.getShopNoDiscountId());
		// values.add(config.getDayTaskNum());
		// values.add(config.getNoviceRemark());
		values.add(config.getNoviceType());
		values.add(config.getFirstRankReward());
		values.add(config.getUnitPrice());
		values.add(config.getBlastLevel());
		values.add(config.getGuideLevel());
		values.add(config.getDrawDetail() == null ? "" : config.getDrawDetail());
		values.add(config.getChallengeReward() == null ? "" : config.getChallengeReward());
		values.add(config.getChallengeJson() == null ? "" : config.getChallengeJson());
		values.add(config.getWorldBoss() == null ? "" : config.getWorldBoss());
		values.add(config.getCrossLevel());
		values.add(config.getMoreGame() == null ? "" : config.getMoreGame());
		// values.add(config.getBubbleTips() == null ? "" :
		// config.getBubbleTips());
		values.add(config.getShowTipsTimePeriod() == null ? "" : config.getShowTipsTimePeriod());
		values.add(config.getGiveDiamondRatio());
		values.add(config.getLevelParabolaRange() == null ? "" : config.getLevelParabolaRange());
		hql.append(" AND areaId in ( ");
		String[] ids = areasIds.split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append("'");
			sb.append(ids[i]);
			sb.append("'");
		}
		hql.append(sb.toString());
		hql.append(")");

		this.executeByJDBC(hql.toString(), values.toArray());
	}

	public List<ButtonInfo> getButtonList() {
		return buttonList;
	}

	public ButtonInfo getButtonInfoById(int buttonId) {
		return buttonMap.get(buttonId);
	}

	/**
	 * 获取button列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getButtonInfoList(int pageNum, int pageSize) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + ButtonInfo.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" AND areaId = ? ORDER BY buttonId DESC ");
		values.add(WorldServer.config.getAreaId());
		String hqlc = "SELECT COUNT(*) " + hsql.toString();
		return getPageList(hsql.toString(), hqlc, values.toArray(), pageNum, pageSize);
	}

	/**
	 * 获取button列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ButtonInfo> getButtonInfoAllByAreaId() {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + ButtonInfo.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" AND areaId = ? ORDER BY buttonId ASC ");
		values.add(WorldServer.config.getAreaId());
		return getList(hsql.toString(), values.toArray());
	}

	/**
	 * 获取某区button总数
	 * 
	 * @return
	 */
	public Long getButtonInfoCountByAreaId(String areaId) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append("SELECT COUNT(b.id) FROM  " + ButtonInfo.class.getSimpleName() + " AS b WHERE 1 = 1 ");
		hsql.append(" AND areaId = ? ");
		values.add(areaId);
		Long num = (Long) getClassObj(hsql.toString(), values.toArray());
		if (num == null) {
			num = 0l;
		}
		return num;
	}

	/**
	 * 根据多个ID删除
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteButtonInfo(String ids) {
		this.execute(" DELETE ButtonInfo WHERE id in (" + ids + ")", new Object[]{});
	}

	/**
	 * 执行相应SQL语句
	 * 
	 * @param hql
	 *            SQL语句
	 * @param Objct
	 *            [] 参数数组
	 */
	public void executeByJDBC(final String sql, final Object[] values) {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@SuppressWarnings("deprecation")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// Transaction tx = session.beginTransaction();
				Connection conn = session.connection();
				PreparedStatement stme = conn.prepareStatement(sql);
				for (int i = 0; i < values.length; i++) {

					stme.setObject(i + 1, values[i]);
				}
				stme.executeUpdate();
				// tx.commit();
				return null;
			}
		});
	}

	/**
	 * 获得结婚时间
	 * 
	 * @param id
	 * @return
	 */
	public String getWedTimeById(int id) {
		return wedTimeMap.get(id);
	}

	/**
	 * 获得结婚时间Map
	 **/
	public Map<Integer, String> getWedMap() {
		return wedTimeMap;
	}

	/**
	 * 获得结婚相关配置
	 * 
	 * @param key
	 * @return
	 */
	public Integer getWedConfigByKey(String key) {
		return wedConfigMap.get(key);
	}

	/**
	 * 获得战斗场次经验配置
	 **/
	public Map<Integer, Integer> getExpMap() {
		return expMap;
	}

	/**
	 * 获取补签价格列表
	 * 
	 * @return
	 */
	public List<Integer> getSupplPriceList() {
		return this.supplPriceList;
	}

	/**
	 * 获得每日首充奖励次数（根据充值额度）
	 **/
	public Map<Integer, Integer> getRechargeIntervalMap() {
		return rechargeIntervalMap;
	}
}