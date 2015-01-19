package com.wyd.service.server.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.service.bean.GetItem;
import com.wyd.service.bean.GiftRecord;
import com.wyd.service.bean.Player;
import com.wyd.service.bean.PlayerItemsFromShop;
import com.wyd.service.bean.ShopItem;
import com.wyd.service.bean.ShopItemsPrice;
import com.wyd.service.dao.IPlayerItemsFromShopDao;
import com.wyd.service.server.factory.IPlayerItemsFromShopService;

public class PlayerItemsFromShopService  extends UniversalManagerImpl implements IPlayerItemsFromShopService {
    Logger                          log             = Logger.getLogger(PlayerItemsFromShopService.class);
    /**
     * The dao instance injected by Spring.
     */
    private IPlayerItemsFromShopDao dao;
   

    public PlayerItemsFromShopService() {
        super();
    }

    /**
     * Returns the singleton <code>IPlayeritemsfromshopService</code> instance.
     */
    public static IPlayerItemsFromShopService getInstance(ApplicationContext context) {
        return (IPlayerItemsFromShopService) context.getBean(SERVICE_BEAN_ID);
    }

    /**
     * Called by Spring using the injection rules specified in the Spring beans file "applicationContext.xml".
     */
    public void setDao(IPlayerItemsFromShopDao dao) {
        super.setDao(dao);
        this.dao = dao;
    }

    public IPlayerItemsFromShopDao getDao() {
        return this.dao;
    }
    /** 一天的时间long值 */
    public static final long     ONEDAYLONG                          = 24 * 60 * 60 * 1000;
   
	@Override
	public void playerGetItem(int playerId, int itemId, int priceId, int days, int useNum, int getway, String remark) {
	    	ShopItem si = null;
	        ShopItemsPrice sip = null;
	        PlayerItemsFromShop pifs = null;
	        int dayMark = days;
	        int numMark = useNum;
	        // 通过物品id获得相应物品	        
	        if(priceId != -1){
	        	sip = (ShopItemsPrice) dao.get(ShopItemsPrice.class, priceId);
	        	si = sip.getShopItem();
	        	itemId = sip.getShopItem().getId();
	        }else{
	        	si = (ShopItem) dao.get(ShopItem.class, itemId);
	        }
	        if (null == si) {
	            return;
	        }
	        List<PlayerItemsFromShop> list = dao.checkPlayerHaveItem(playerId, itemId);
	        // 判断是否拥有该装备
	        if (list.size() > 0) {// 有该件装备
	            pifs = list.get(0);
	            Date buyTime = pifs.getBuyTime();
	            // 获得到期时间的long值，用于比较
	            long lastBuyLong = buyTime.getTime() + pifs.getPLastTime() * ONEDAYLONG;
	            // 商品是否到期
	            if (lastBuyLong < System.currentTimeMillis() && pifs.getPLastTime() != -1) {// 到期
	                pifs.setBuyTime(new Date(System.currentTimeMillis()));
	                // 判断是否是购买物品
	                if (priceId != -1) {
	                    pifs.setPLastTime(pifs.getPLastTime() == -1 ? -1 : sip.getDays());
	                    pifs.setPLastNum(pifs.getPLastNum() == -1 ? -1 : sip.getCount());
	                    useNum = sip.getCount();
	                    days = sip.getDays();
	                } else {
	                    pifs.setPLastNum(pifs.getPLastNum() == -1 ? -1 : useNum);
	                    pifs.setPLastTime(pifs.getPLastTime() == -1 ? -1 : days);
	                }
	            } else {// 未到期
	                // 判断是否是购买物品
	                if (priceId != -1) {
	                    sip = (ShopItemsPrice) dao.get(ShopItemsPrice.class, priceId);
	                    if (sip.getCount() != -1) {
	                        useNum = sip.getCount() + pifs.getPLastNum();
	                    } else {
	                        useNum = -1;
	                    }
	                    if (sip.getDays() != -1) {
	                        days = sip.getDays() + pifs.getPLastTime();
	                    } else {
	                        days = -1;
	                    }
	                    pifs.setPLastNum(pifs.getPLastNum() == -1 ? -1 : useNum);
	                    pifs.setPLastTime(pifs.getPLastTime() == -1 ? -1 : days);
	                    days = sip.getDays();
	                    useNum = sip.getCount();
	                } else {
	                    if (useNum != -1) {
	                        useNum = useNum + pifs.getPLastNum();
	                    }
	                    if (days != -1) {
	                        days = days + pifs.getPLastTime();
	                    }
	                    pifs.setPLastNum(pifs.getPLastNum() == -1 ? -1 : useNum);
	                    pifs.setPLastTime(pifs.getPLastTime() == -1 ? -1 : days);
	                }
	            }
	            // 更新物品
	            dao.update(pifs);
	        } else {// 没有该件装备
	            pifs = new PlayerItemsFromShop();
	            Player player = new Player();
	            player.setId(playerId);
	            pifs.setPlayer(player);
	            pifs.setShopItem(si);
	            pifs.setBuyTime(new Date());
	            // 判断是否是购买物品
	            if (priceId != -1) {
	                sip = (ShopItemsPrice) dao.get(ShopItemsPrice.class, priceId);
	                pifs.setPLastNum(sip.getCount());
	                pifs.setPLastTime(sip.getDays());
	                useNum = sip.getCount();
	                days= sip.getDays();
	            } else {
	                pifs.setPLastNum(useNum);
	                pifs.setPLastTime(days);
	            }
	            if (si.getAutoUse() == 1) {
	                pifs.setIsInUsed(true);
	            } else {
	                pifs.setIsInUsed(false);
	            }	            
	            pifs.setPExpExtraRate(0);
	            pifs.setHollNum(si.getHollForStoneId());
	            pifs.setHollUsedNum(0);
	            pifs.setSkillful(0);
	            pifs.setDispearAtOverTime(si.getDispearAtOverTime());
	            pifs.setPUseLastTime(si.getUseLastTime());
	            pifs.setWeapSkill1(0);
	            pifs.setWeapSkill2(0);
	            // 保存PlayerItemsFromShop对象
	            dao.save(pifs);
	        }
	        // 保存获得商品日志
	        if (getway != -10) {
	            saveGetItemRecord(playerId, itemId, dayMark, numMark, getway, 0, remark);
	        }
	        
	           
    }

	@Override
	public void saveGiftRecord(int playerId, int itemId,int num, String code) {
		GiftRecord record=new GiftRecord();
		record.setCode(code);
		record.setItemId(itemId);
		record.setPlayerId(playerId);
		record.setNum(num);
		record.setCreateTime(new Date());
		save(record);
	}

	@Override
	public boolean isCodeInGiftRecord(String code) {
		String hql = "from GiftRecord where code=?";
		@SuppressWarnings("rawtypes")
		List results=dao.getList(hql, new Object[]{code});
		return (results!=null && results.size()>0);
	}
	 /**
     * 保存物品的进销记录
     * 
     * @param playerId
     *            玩家ID
     * @param itemId
     *            物品ID
     * @param days
     *            天数
     * @param countNum
     *            数量
      * @param getway 获得途径 -8喇叭，-7结婚，-6洗练，-5强化合成消耗，-4兑换勋章消耗，-3改名笔，-2公会构造，-1失败清零，0商城，1兑换，2任务，4副本奖励，5是礼包，6合成，7签到，8砸蛋，9VIP，10结婚 11排位赛，12战斗，13兑换码，14爱心许愿，15GM工具，16技能锁定，17打孔，18拆卸，
      * 19镶嵌， 20计费点购买,21抽奖系统，22抽奖消耗，23召唤消耗 24 分包下载
     * @param mark
     *            添加还是使用（0添加，1使用）
     * @param remark  GM工具物品给予时做备注说明          
     */
    public void saveGetItemRecord(int playerId, int itemId, int days, int countNum, int getWay, int mark, String remark) {
        GetItem gi = new GetItem();
        gi.setCountNum(countNum);
        gi.setCreatetime(new Date());
        gi.setDays(days);
        gi.setGetway(getWay);
        gi.setItemId(itemId);
        gi.setMark(mark);
        gi.setRemark(remark);
        gi.setPlayerId(playerId);
        dao.save(gi);
    }
    /**
     * 获取玩家装备的物品属性表
     * @param player
     * @param itemIds
     * @return
     */
    public List<PlayerItemsFromShop> playerFindItem(Integer playerId, List<Integer> itemIds, int sex) {
        List<PlayerItemsFromShop> pifsList = new ArrayList<PlayerItemsFromShop>();
        int itemId;
        for (int i = 0; i < itemIds.size(); i++) {
            itemId = itemIds.get(i);
            PlayerItemsFromShop pifs = dao.getPlayerItemsFromShopByPlayerIdAndItemId(playerId, itemId);
            if (null != pifs) {
                pifsList.add(pifs);
            }
        }
        return pifsList;
    }

	@Override
	/**
     * 获得玩家指定物品对象
     * 
     * @param playerId
     * @param itemId
     * @return
     */
    public PlayerItemsFromShop getPlayerItemsFromShopByPlayerIdAndItemId(Player player, int itemId) {
        PlayerItemsFromShop playerItemsFromShop = dao.getPlayerItemsFromShopByPlayerIdAndItemId(player.getId(), itemId);
        if (null != playerItemsFromShop) playerItemsFromShop.setPlayer(player);
        return playerItemsFromShop;
    }

}
