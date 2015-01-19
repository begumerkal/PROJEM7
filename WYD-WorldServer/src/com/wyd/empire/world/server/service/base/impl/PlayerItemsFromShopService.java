package com.wyd.empire.world.server.service.base.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.protocol.data.cache.AddItem;
import com.wyd.empire.protocol.data.cache.PlayerItem;
import com.wyd.empire.protocol.data.cache.RemoveItem;
import com.wyd.empire.protocol.data.cache.UpdateItem;
import com.wyd.empire.protocol.data.wedding.PlayerHaveBless;
import com.wyd.empire.world.bean.Exchange;
import com.wyd.empire.world.bean.GetItem;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.bean.PlayerInfo;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.ShopItemsPrice;
import com.wyd.empire.world.bean.VipRate;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.dao.IPlayerItemsFromShopDao;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerItemsFromShopService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * The service class for the TabPlayeritemsfromshop entity.
 */
public class PlayerItemsFromShopService extends UniversalManagerImpl implements IPlayerItemsFromShopService {
	Logger log = Logger.getLogger(PlayerItemsFromShopService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private IPlayerItemsFromShopDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "PlayeritemsfromshopService";

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
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IPlayerItemsFromShopDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IPlayerItemsFromShopDao getDao() {
		return this.dao;
	}

	/**
	 * 玩家获得物品方法(简化) 根据useType来决定该物品是使用天数还是个数
	 * 
	 * @author zengxc
	 */
	public PlayerItemsFromShop playerGetItem(int playerId, int itemId, int dayOrCount, int getway, String remark, int useDiamond,
			int useGold, int useBadge) {
		ShopItem si = ServiceManager.getManager().getShopItemService().getShopItemById(itemId);
		PlayerItemsFromShop playerItem = null;
		if (null == si) {
			return null;
		}
		if (si.getUseType() == 0) {
			playerItem = playerGetItem(playerId, si, -1, -1, dayOrCount, getway, remark, useDiamond, useGold, useBadge);
		} else {
			playerItem = playerGetItem(playerId, si, -1, dayOrCount, -1, getway, remark, useDiamond, useGold, useBadge);
		}
		return playerItem;
	}

	/**
	 * 玩家获得物品方法
	 * 
	 * @param playerId
	 *            玩家id
	 * @param itemId
	 *            物品id
	 * @param priceId
	 *            物品价格id，如果是购买商品priceId为相应物品的价格id，如果是系统赠送物品则该参数为-1
	 * @param days
	 *            物品使用天数,如果是购买商品，该参数传0
	 * @param userNum
	 *            物品使用次数,如果是购买商品，该参数传0
	 * @param getway
	 *            详见：saveGetItemRecord
	 * @param remark
	 *            GM工具物品给予时做备注说明
	 * @param useDiamond
	 *            消耗钻石
	 * @param useGold
	 *            消耗金币
	 * @param useBadge
	 *            消耗金币
	 */
	public PlayerItemsFromShop playerGetItem(int playerId, int itemId, int priceId, int days, int useNum, int getway, String remark,
			int useDiamond, int useGold, int useBadge) {
		ShopItem si = null;
		ShopItemsPrice sip = null;
		// 通过物品id获得相应物品
		if (priceId != -1) {
			sip = (ShopItemsPrice) dao.get(ShopItemsPrice.class, priceId);
			si = sip.getShopItem();
			itemId = si.getId();
		} else {
			si = ServiceManager.getManager().getShopItemService().getShopItemById(itemId);
		}
		return playerGetItem(playerId, si, priceId, days, useNum, getway, remark, useDiamond, useGold, useBadge);
	}

	public PlayerItemsFromShop playerGetItem(int playerId, ShopItem si, int priceId, int days, int useNum, int getway, String remark,
			int useDiamond, int useGold, int useBadge) {
		if (null == si) {
			return null;
		}
		int itemId = si.getId();
		PlayerItemsFromShop playerItem = null;
		int dayMark = days;
		int numMark = useNum;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ShopItemsPrice sip = null;
		// 通过物品id获得相应物品
		if (priceId != -1) {
			sip = (ShopItemsPrice) dao.get(ShopItemsPrice.class, priceId);
		}
		WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getLoadPlayer(playerId);
		playerItem = dao.uniquePlayerItem(playerId, itemId);
		// 卡牌要特殊处理.卡牌每张都是一个独立的物品
		boolean isCard = si.getType() == Common.SHOP_ITEM_TYPE_ARMS_CARD;
		boolean reAdd = false;// 是否重新购买（数据库有记录）
		if (isCard) {
			playerGetCard(worldPlayer, playerId, si, useNum, getway, remark);
			// 卡牌有可能发多张，所以这里返回null
			return null;
		}
		// 判断是否拥有该装备
		if (playerItem != null) {// 有该件装备
			// 按数量计数的物品，个数为0表示重新购买
			if (playerItem.getPLastNum() < 1 && playerItem.getPLastTime() == -1) {
				reAdd = true;
			}
			// 商品是否到期
			if (playerItem.getPLastTime() != -1 && playerItem.getLastDay() < 1) {// 到期
				playerItem.setBuyTime(new Date(System.currentTimeMillis()));
				// 判断是否是购买物品
				if (sip != null) {
					playerItem.setPLastTime(playerItem.getPLastTime() == -1 ? -1 : sip.getDays());
					playerItem.setPLastNum(playerItem.getPLastNum() == -1 ? -1 : sip.getCount());
					useNum = sip.getCount();
					days = sip.getDays();
				} else {
					playerItem.setPLastNum(playerItem.getPLastNum() == -1 ? -1 : useNum);
					playerItem.setPLastTime(playerItem.getPLastTime() == -1 ? -1 : days);
				}
			} else {// 未到期
				// 判断是否是购买物品
				if (sip != null) {
					sip = (ShopItemsPrice) dao.get(ShopItemsPrice.class, priceId);
					if (sip.getCount() != -1) {
						useNum = sip.getCount() + playerItem.getPLastNum();
					} else {
						useNum = -1;
					}
					if (sip.getDays() != -1) {
						days = sip.getDays() + playerItem.getPLastTime();
					} else {
						days = -1;
					}
					playerItem.setPLastNum(playerItem.getPLastNum() == -1 ? -1 : useNum);
					playerItem.setPLastTime(playerItem.getPLastTime() == -1 ? -1 : days);
					days = sip.getDays();
					useNum = sip.getCount();
				} else {
					if (useNum != -1) {
						useNum = useNum + playerItem.getPLastNum();
					}
					if (days != -1) {
						days = days + playerItem.getPLastTime();
					}
					playerItem.setPLastNum(playerItem.getPLastNum() == -1 ? -1 : useNum);
					playerItem.setPLastTime(playerItem.getPLastTime() == -1 ? -1 : days);
				}
			}
			// 更新物品
			dao.update(playerItem);
			// 更新玩家拥有的物品
			if (reAdd) {
				sendAddItem(worldPlayer, playerItem);
			} else {
				Map<String, String> info = new HashMap<String, String>();
				info.put("lastTime", playerItem.getPLastTime() + "");
				info.put("lastNum", playerItem.getPLastNum() + "");
				info.put("expired", "false");
				sendUpdateItem(worldPlayer, playerItem.getId(), info);
			}
		} else {// 没有该件装备
			playerItem = new PlayerItemsFromShop();
			playerItem.setPlayerId(playerId);
			playerItem.setShopItem(si);
			playerItem.setBuyTime(new Date());
			// 判断是否是购买物品
			if (priceId != -1) {
				sip = (ShopItemsPrice) dao.get(ShopItemsPrice.class, priceId);
				playerItem.setPLastNum(sip.getCount());
				playerItem.setPLastTime(sip.getDays());
				useNum = sip.getCount();
				days = sip.getDays();
			} else {
				playerItem.setPLastNum(useNum);
				playerItem.setPLastTime(days);
			}
			if (si.getAutoUse() == 1) {
				playerItem.setIsInUsed(true);
			} else {
				playerItem.setIsInUsed(false);
			}
			playerItem.setPExpExtraRate(0);
			playerItem.setHollNum(si.getHollForStoneId());
			playerItem.setHollUsedNum(0);
			playerItem.updateStrongLevel(0);
			playerItem.setSkillful(0);
			playerItem.setDispearAtOverTime(si.getDispearAtOverTime());
			playerItem.setPUseLastTime(si.getUseLastTime());
			playerItem.setWeapSkill1(0);
			playerItem.setWeapSkill2(0);
			playerItem.updateAttackStone(-1);
			playerItem.updateDefenseStone(-1);
			playerItem.updateSpecialStone(-1);
			// 保存PlayerItemsFromShop对象
			playerItem = dao.savePlayerItemsFromShop(playerItem);
			// 推送玩家拥有的物品
			sendAddItem(worldPlayer, playerItem);
		}
		// 保存获得商品日志
		if (getway != -10) {
			ServiceManager
					.getManager()
					.getPlayerService()
					.writeLog(
							"玩家获得物品记录：玩家Id=" + playerId + ",物品Id=" + itemId + ",获得方式=" + getway + ",获得数量=" + numMark + ",使用天数=" + dayMark
									+ ",获得时间=" + sdf.format(new Date()));
			saveGetItemRecord(playerId, itemId, dayMark, numMark, getway, 0, remark);
		}
		int level = 0;
		if (null != worldPlayer) {
			level = worldPlayer.getLevel();
			if (Common.BUFFTYPE == si.getType()) {
				worldPlayer.initialBuff();
			}
			if (Common.RING == si.getId()) {
				worldPlayer.setHasring(true);
			}
			if (Common.BADGEID == si.getId()) {
				worldPlayer.setMedalNum(useNum);
			}
			if (Common.STARSOULDEBRISID == si.getId()) {
				worldPlayer.setStarSoulDebrisNum(useNum);
			}
			// add by EricChan 2013-8-25
			if (itemId == Common.LOVEID || itemId == Common.HORNID || itemId == Common.COLOURHORNID) {
				if (itemId == Common.LOVEID && playerItem.getPLastNum() > 0) {// 获得爱心点亮高亮
					worldPlayer.updateButtonInfo(Common.BUTTON_ID_LOVE, true, 0);
				}
			}
			// add by EricChan 2013-8-25
			if (itemId == Common.BLESS) {// 如果购买礼炮，则推送给客户端刷新礼炮数
				PlayerHaveBless playerHaveBless = new PlayerHaveBless();
				playerHaveBless.setBlessingNum(playerItem.getPLastNum());
				worldPlayer.sendData(playerHaveBless);
			}
			if (itemId == Common.ZFSPID) {// 增加祝福碎片要更新领取状态
				int num = playerItem.getPLastNum();
				if (num > 24 && num < 101) {
					ServiceManager.getManager().getLotteryService().sendZflhList(worldPlayer);
				}
				PlayerInfo playerInfo = worldPlayer.getPlayerInfo();
				playerInfo.setZfspUpdateTime(Calendar.getInstance().getTime());
			}

		}
		int num = 0;
		int unit = 1;
		if (dayMark < 0) {
			num = numMark;
			unit = 2;
		} else {
			num = dayMark;
		}
		// 物品获得记录
		GameLogService.getItem(playerId, level, itemId, si.getType(), si.getSubtype(), num, unit, getway, useDiamond, useGold, useBadge,
				remark);
		return playerItem;
	}

	/**
	 * 卡牌要特殊处理.卡牌每张都是一个独立的物品
	 * 
	 * @param worldPlayer
	 * @param playerId
	 * @param si
	 * @param num
	 */
	private void playerGetCard(WorldPlayer worldPlayer, int playerId, ShopItem si, int num, int getway, String remark) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 0; i < num; i++) {
			PlayerItemsFromShop pifs = new PlayerItemsFromShop();
			pifs.setPlayerId(playerId);
			pifs.setShopItem(si);
			pifs.setBuyTime(new Date());
			pifs.setPLastNum(1);
			pifs.setPLastTime(-1);
			pifs.setIsInUsed(false);
			pifs.setPExpExtraRate(0);
			pifs.setHollNum(si.getHollForStoneId());
			pifs.setHollUsedNum(0);
			pifs.updateStrongLevel(0);
			pifs.setSkillful(0);
			pifs.setDispearAtOverTime(si.getDispearAtOverTime());
			pifs.setPUseLastTime(si.getUseLastTime());
			pifs.setWeapSkill1(0);
			pifs.setWeapSkill2(0);
			pifs.updateAttackStone(-1);
			pifs.updateDefenseStone(-1);
			pifs.updateSpecialStone(-1);
			// 保存PlayerItemsFromShop对象
			pifs = dao.savePlayerItemsFromShop(pifs);
			// 推送玩家拥有的物品
			sendAddItem(worldPlayer, pifs);
			// 物品获得记录
			GameLogService.getItem(playerId, 0, si.getId(), si.getType(), si.getSubtype(), num, -1, getway, -1, -1, -1, remark);
			ServiceManager
					.getManager()
					.getPlayerService()
					.writeLog(
							"玩家获得物品记录：玩家Id=" + playerId + ",物品Id=" + si.getId() + ",获得方式=" + getway + ",获得数量=" + 1 + ",使用天数=" + -1
									+ ",获得时间=" + sdf.format(new Date()));
			saveGetItemRecord(playerId, si.getId(), -1, 1, getway, 0, remark);
		}
	}

	public void vipLevelUpReward(int playerId, int oLevel, int nLevel) {
		for (int level = oLevel + 1; level <= nLevel; level++) {
			switch (level) {
				case 1 :
					playerGetItem(playerId, 554, -1, -1, 1, 9, "vip升级奖励 1级", 0, 0, 0);
					break;
				case 2 :
					playerGetItem(playerId, 555, -1, -1, 1, 9, "vip升级奖励 2级", 0, 0, 0);
					break;
				case 3 :
					playerGetItem(playerId, 556, -1, -1, 1, 9, "vip升级奖励 3级", 0, 0, 0);
					break;
				case 4 :
					playerGetItem(playerId, 557, -1, -1, 1, 9, "vip升级奖励 4级", 0, 0, 0);
					break;
				case 5 :
					playerGetItem(playerId, 558, -1, -1, 1, 9, "vip升级奖励 5级", 0, 0, 0);
					break;
				case 6 :
					playerGetItem(playerId, 559, -1, -1, 1, 9, "vip升级奖励 6级", 0, 0, 0);
					break;
				case 7 :
					playerGetItem(playerId, 560, -1, -1, 1, 9, "vip升级奖励 7级", 0, 0, 0);
					break;
				case 8 :
					playerGetItem(playerId, 561, -1, -1, 1, 9, "vip升级奖励 8级", 0, 0, 0);
					break;
				case 9 :
					playerGetItem(playerId, 562, -1, -1, 1, 9, "vip升级奖励 9级", 0, 0, 0);
					break;
				case 10 :
					playerGetItem(playerId, 563, -1, -1, 1, 9, "vip升级奖励 10级", 0, 0, 0);
					break;
			}

			// VIP升级邮件提醒
			Mail mail = new Mail();
			mail.setBlackMail(false);
			mail.setContent(TipMessages.VIP_LEVELUP_NOTICE.replace("{1}", level + ""));
			mail.setIsRead(false);
			mail.setReceivedId(playerId);
			WorldPlayer wp = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
			mail.setReceivedName(wp.getName());
			mail.setSendId(0);
			mail.setSendName(TipMessages.SYSNAME_MESSAGE);
			mail.setSendTime(new Date());
			mail.setTheme(TipMessages.VIP_LEVELUP_THEME);
			mail.setType(1);
			mail.setIsStick(Common.IS_STICK);
			ServiceManager.getManager().getMailService().saveMail(mail, null);
		}
	}

	/**
	 * 根据玩家id获得对象(物品数量不为0)
	 * 
	 * @param playerId
	 * @param
	 * @return
	 */
	public List<PlayerItemsFromShop> getPlayerItemsFromShopByPlayerId(int playerId) {
		return dao.getPlayerItemsFromShopByPlayerId(playerId);
	}

	/**
	 * 获取玩家指定物品的数量
	 * 
	 * @param playerId
	 * @param itemId
	 * @return
	 */
	public int getPlayerItemNum(int playerId, int itemId) {
		return dao.getPlayerItemNum(playerId, itemId);
	}

	/**
	 * 更新玩家指定物品的数量
	 * 
	 * @param playerId
	 * @param itemId
	 * @param speakerCount
	 */
	public void updatePlayerItemNum(int playerId, int itemId, int speakerCount) {
		dao.updatePlayerItemNum(playerId, itemId, speakerCount);
	}

	/**
	 * 获得特殊效果对象（Vip，双倍卡）
	 * 
	 * @param playerId
	 * @return
	 */
	public List<PlayerItemsFromShop> getSpecialEffectId(int playerId) {
		return dao.getSpecialEffectId(playerId);
	}

	/**
	 * 物品使用期到了，删除可删除物品或修改物品状态 true 有物品更改
	 */
	public boolean dispearItemAtOverTime(WorldPlayer player) {
		List<PlayerItemsFromShop> playerItems = this.getPlayerItemsFromShopByPlayerId(player.getId());
		boolean mark = false;
		for (PlayerItemsFromShop playerItem : playerItems) {
			ShopItem item = playerItem.getShopItem();
			if (item.isEquipment()) {
				// 过期物品
				if (playerItem.getLastDay() == 0) {
					if (playerItem.getIsInUsed()) {
						// 脱下
						takeOffEquipment(playerItem, null);
					}
					mark = true;
					boolean after5 = playerItem.getAfterDay() > 5;
					// 判断是否可以删除
					if (after5 && playerItem.getStars() < 1 && playerItem.getStrongLevel() < 1 && playerItem.getDefenseStone() < 1
							&& playerItem.getAttackStone() < 1 && playerItem.getSpecialStone() < 1) {
						dao.remove(playerItem);
					}
				}
				if (playerItem.getPLastNum() == 0) {
					dao.remove(playerItem);
				}
			}
		}
		return mark;
	}

	/**
	 * 获得玩家指定物品对象
	 * 
	 * @param playerId
	 * @param itemId
	 * @return
	 */
	@Override
	public List<PlayerItemsFromShop> getPlayerItemByItemId(int playerId, int itemId) {
		return dao.getPlayerItemByItemId(playerId, itemId);
	}

	/**
	 * 获取玩家指定的物品(唯一)
	 * 
	 * @param playerId
	 * @param itemId
	 * @return
	 */
	@Override
	public PlayerItemsFromShop uniquePlayerItem(int playerId, int itemId) {
		return dao.uniquePlayerItem(playerId, itemId);
	}

	/**
	 * 按品质取碎片
	 * 
	 * @return
	 */
	@Override
	public List<PlayerItemsFromShop> getPlayerDebrisByLevel(int playerId, int level) {
		List<PlayerItemsFromShop> playerItems = dao.getPlayerItemsFromShopByPlayerId(playerId);
		List<PlayerItemsFromShop> debrisList = new ArrayList<PlayerItemsFromShop>();
		for (PlayerItemsFromShop playerItem : playerItems) {
			ShopItem item = playerItem.getShopItem();
			if (item.getType() == Common.SHOP_ITEM_TYPE_ARMS_CARDDEBRIS) {
				if (item.getLevel().intValue() == level) {
					debrisList.add(playerItem);
				}
			}
		}
		return debrisList;
	}

	/**
	 * 获得玩家勋章数量
	 * 
	 * @param playerId
	 * @param itemId
	 * @return
	 */
	public PlayerItemsFromShop getBadgeByPlayerId(int playerId) {
		return dao.getBadgeByPlayerId(playerId);
	}

	/**
	 * 更新玩家武器熟练度
	 * 
	 * @param playerId
	 * @param itemId
	 */
	public void updateItmeSkillful(WorldPlayer player) {
		PlayerItemsFromShop playerItemsFromShop = player.getWeapon();
		if (null != playerItemsFromShop) {
			int skillful = playerItemsFromShop.getSkillful();
			int add = 0;
			if (skillful < Common.SKILLFULLLEVEL1) {
				add = Common.SKILLFULLLEVEL1ADD;
				skillful += Common.SKILLFULLLEVEL1ADD;
			} else if (skillful < Common.SKILLFULLLEVEL2) {
				add = Common.SKILLFULLLEVEL2ADD;
				skillful += Common.SKILLFULLLEVEL2ADD;
			} else if (skillful < Common.SKILLFULLLEVEL3) {
				add = Common.SKILLFULLLEVEL3ADD;
				skillful += Common.SKILLFULLLEVEL3ADD;
			} else if (skillful < Common.SKILLFULLLEVEL4) {
				add = Common.SKILLFULLLEVEL4ADD;
				skillful += Common.SKILLFULLLEVEL4ADD;
			} else if (skillful < Common.SKILLFULLLEVEL5) {
				add = Common.SKILLFULLLEVEL5ADD;
				skillful += Common.SKILLFULLLEVEL5ADD;
				if (skillful > Common.SKILLFULLLEVEL5) {
					skillful = Common.SKILLFULLLEVEL5;
				}
			}
			if (add > 0) {
				player.setProficiency(skillful);
				dao.update(playerItemsFromShop);
				ServiceManager.getManager().getChatService()
						.sendSystemMessage(player, TipMessages.SKILL_Add.replace("***", (add / 100f) + "%"));
				ServiceManager.getManager().getTaskService().wqsldRask(player, skillful);
			}
		}
	}

	/**
	 * 检测装备第N天后过期
	 * 
	 * @param num
	 *            num为0时，表示当天过期
	 * @return 过期物品玩家ID和物品ID
	 */
	public List<Object> checkEquipmentOverTimeAfterNDays(int playerId, Long num) {
		return dao.checkEquipmentOverTimeAfterNDays(playerId, num);
	}

	/**
	 * 玩家脱下装备,并换上新装备
	 * 
	 * @param playerId
	 * @param oldItemId
	 * @param newItem
	 */
	public void takeOffEquipment(PlayerItemsFromShop oldItem, PlayerItemsFromShop newItem) {
		if (oldItem != null && newItem != null) {
			// 如果新旧ID一样，表示脱下（头脸身不能脱）
			if (oldItem.getId().intValue() == newItem.getId().intValue()) {
				if (oldItem.getShopItem().isWing() || oldItem.getShopItem().isRing() || oldItem.getShopItem().isWing()
						|| oldItem.getShopItem().isNecklace()) {
					newItem = null;
				} else {
					return;
				}
			}
		}
		WorldPlayer player = null;
		if (newItem != null) {
			newItem.setIsInUsed(true);
			dao.update(newItem);
			player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(newItem.getPlayerId());
			Map<String, String> info = new HashMap<String, String>();
			info.put("isUse", "true");
			sendUpdateItem(player, newItem.getId(), info);
			// System.out.println(newItem.getShopItem().getName()+"<穿上");
		}
		if (oldItem != null) {
			oldItem.setIsInUsed(false);
			dao.update(oldItem);
			player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(oldItem.getPlayerId());
			Map<String, String> info = new HashMap<String, String>();
			info.put("isUse", "false");
			sendUpdateItem(player, oldItem.getId(), info);
			// System.out.println("id "+oldItem.getPlayerId()+" "+oldItem.getShopItem().getName()+">脱下");
		}
	}

	/**
	 * 根据玩家角色Id，获取所拥有的可以强化或合成的物品列表
	 * 
	 * @param playerId
	 *            玩家角色Id
	 * @return 可以强化或合成的物品列表
	 */
	public List<PlayerItemsFromShop> getStrengthenShopItemList(int playerId) {
		List<PlayerItemsFromShop> list = dao.getStrengthenShopItemList(playerId);
		List<PlayerItemsFromShop> newList = new ArrayList<PlayerItemsFromShop>();
		for (PlayerItemsFromShop item : list) {
			if ((item.getShopItem().getType() == 6 || item.getShopItem().getType() == 7) && item.getPLastNum() <= 0) {
				continue;
			}
			newList.add(item);
		}
		return newList;
	}

	/**
	 * 获得兑换商品列表
	 * 
	 * @param player
	 * @return
	 */
	public List<Exchange> checkExchangeList(WorldPlayer player) {
		return dao.checkExchangeList(player);
	}

	/**
	 * 刷新兑换列表
	 * 
	 * @param player
	 */
	public void refreshExchange(WorldPlayer player, long timeStart) {
		dao.refreshExchange(player, timeStart);
	}

	/**
	 * GM工具-根据玩家ID查询出玩家物品
	 * 
	 * @param key
	 *            玩家ID
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少条
	 * @return
	 */
	public PageList findAllByPlayerId(String key, int pageIndex, int pageSize) {
		return dao.findAllByPlayerId(key, pageIndex, pageSize);
	}

	/**
	 * 更换玩家身上物品的使用状态
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param shopId
	 *            商品ID
	 * @param isInUse
	 *            是否正在使用
	 */
	public void updateInUseByShopId(int playerId, int shopId, boolean isInUse) {
		dao.updateInUseByShopId(playerId, shopId, isInUse);
	}

	/**
	 * 根据玩家ID获取玩家装备列表（其中装备必须是剩余数量不为0，剩余时间不为0）
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 玩家装备列表
	 */
	public PageList getPlayerItemsFromShopByPlayerId(int playerId, int pageNum, int pageSize) {
		PageList pageList = dao.getPlayerItemsFromShopByPlayerId(playerId, pageNum, pageSize);
		return pageList;
	}

	/**
	 * 清除过期记录(每隔N天清理一次)
	 */
	public void deleteOverDateRecord(int days) {
		dao.deleteOverDateRecord(days);
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
	 * @param getway
	 * @param mark
	 *            添加还是使用（0添加，1使用）
	 * @param remark
	 *            GM工具物品给予时做备注说明
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

	@Override
	public int getTopLevelForItemByPlayer(int playerId) {
		return dao.getTopLevelForItemByPlayer(playerId);
	}

	/**
	 * 发送N天后过期的装备提醒
	 */
	public void checkOvertimeAfterNDay(int playerId) {
		List<Object> list;
		Object[] obj;
		// 发送三天后过期的装备提醒
		list = checkEquipmentOverTimeAfterNDays(playerId, (long) 3);
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			obj = (Object[]) list.get(i);
			if (i != list.size() - 1) {
				str += obj[1].toString() + ",";
			} else {
				str += obj[1].toString() + " ";
			}
		}
		if (str.length() > 0) {
			ServiceManager.getManager().getMailService()
					.saveMail(ServiceManager.getManager().getMailService().makeMail(playerId, str, 3), null);
			str = "";
		}
		// 发送今天过期的装备提醒
		list = checkEquipmentOverTimeAfterNDays(playerId, (long) 0);
		for (int i = 0; i < list.size(); i++) {
			obj = (Object[]) list.get(i);
			if (i != list.size() - 1) {
				str += obj[1].toString() + ",";
			} else {
				str += obj[1].toString() + " ";
			}
		}
		if (str.length() > 0) {
			ServiceManager.getManager().getMailService()
					.saveMail(ServiceManager.getManager().getMailService().makeMail(playerId, str, 0), null);
		}
	}

	/**
	 * 保存物品进销记录
	 * 
	 * @param pifs
	 * @param days
	 * @param countNum
	 * @param getWay
	 * @param mark
	 */
	public void saveAndUpdatePifs(PlayerItemsFromShop pifs, int days, int countNum, int getWay, int mark) {
		update(pifs);
		saveGetItemRecord(pifs.getPlayerId(), pifs.getShopItem().getId(), days, countNum, getWay, mark, null);
	}

	/**
	 * 改变装备属性
	 * 
	 * @param pifs
	 * @param stoneId
	 *            宝石ID
	 * @param mark
	 *            1表示添加属性，0表示删减属性
	 * @return
	 */
	public PlayerItemsFromShop changeParm(PlayerItemsFromShop pifs, int stoneId, int mark) {
		ShopItem si = ServiceManager.getManager().getShopItemService().getShopItemById(stoneId);
		if (null != si) {
			if (mark == 1) {
				if (si.getType() == 11) {
					pifs.updateAttackStone(stoneId);
				} else if (si.getType() == 12) {
					pifs.updateDefenseStone(stoneId);
				} else if (si.getType() == 13) {
					pifs.updateSpecialStone(stoneId);
				}
			} else {
				if (si.getType() == 11) {
					pifs.updateAttackStone(0);
				} else if (si.getType() == 12) {
					pifs.updateDefenseStone(0);
				} else if (si.getType() == 13) {
					pifs.updateSpecialStone(0);
				}
			}
		}
		return pifs;
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		dao.initData();
	}

	/**
	 * 根据ID获得viprate
	 * 
	 * @param id
	 * @return
	 */
	public VipRate getVipRateById(int id) {
		return dao.getVipRateById(id);
	}

	/**
	 * 获取玩家的装备信息
	 * 
	 * @param playerId
	 * @param itemIds
	 * @return
	 */
	public List<PlayerItemsFromShop> getPlayerItem(Integer playerId, List<Integer> itemIds) {
		List<PlayerItemsFromShop> playetAllItems = getPlayerItemsFromShopByPlayerId(playerId);
		List<PlayerItemsFromShop> playetItems = new ArrayList<PlayerItemsFromShop>();

		for (Integer itemId : itemIds) {
			for (PlayerItemsFromShop playetItem : playetAllItems) {
				if (playetItem.getShopItem().getId().intValue() == itemId) {
					playetItems.add(playetItem);
				}
			}
		}
		return playetItems;
	}

	/**
	 * 获得玩家物品MAP
	 * 
	 * @param playerId
	 * @param itemIds
	 * @return
	 */
	public Map<Integer, PlayerItemsFromShop> getPlayerItemMap(int playerId, int[] itemIds) {
		List<Integer> list = new ArrayList<Integer>();
		for (int id : itemIds) {
			list.add(id);
		}
		List<PlayerItemsFromShop> itemList = getPlayerItem(playerId, list);

		Map<Integer, PlayerItemsFromShop> map = new HashMap<Integer, PlayerItemsFromShop>();
		for (PlayerItemsFromShop p : itemList) {
			if (p == null)
				continue;
			map.put(p.getShopItem().getId(), p);
		}
		return map;
	}

	public void sendPlayerItem(WorldPlayer player) {
		List<PlayerItemsFromShop> playerItems = getPlayerItemsFromShopByPlayerId(player.getId());
		sendPlayerItem(player, playerItems);
	}

	public void sendPlayerItem(WorldPlayer player, List<PlayerItemsFromShop> playerItems) {
		if (player == null || player.getPlayer() == null || !player.isOnline())
			return;
		PlayerItem playerItem = new PlayerItem();
		int size = playerItems.size();

		int[] itemId = new int[size];
		byte[] maintype = new byte[size];
		byte[] subtype = new byte[size];
		int[] lastTime = new int[size]; // 剩余的天数，如果是-1，就是不限时间使用
		int[] lastNum = new int[size]; // 剩余数量，如果是-1，就是不限数量使用
		boolean[] isUse = new boolean[size]; // 是否装备在身上
		String[] data = new String[size]; // 数据
		int[] recyclePrice = new int[size]; // 回收价格
		boolean[] expired = new boolean[size];
		boolean[] recommended = new boolean[size]; // 是否推荐
		int[] playerItemId = new int[size];
		int i = 0;
		int[] betterIds = getBetterItems(player, playerItems);
		for (PlayerItemsFromShop pitem : playerItems) {

			itemId[i] = pitem.getShopItem().getId();
			lastTime[i] = pitem.getLastDay(); // 剩余的天数，如果是-1，就是不限时间使用
			lastNum[i] = pitem.getPLastNum(); // 剩余数量，如果是-1，就是不限数量使用

			playerItemId[i] = pitem.getId();
			isUse[i] = pitem.getIsInUsed();
			maintype[i] = pitem.getShopItem().getType();
			subtype[i] = pitem.getShopItem().getSubtype();
			recyclePrice[i] = getRecyclePrice(pitem.getShopItem());
			data[i] = getData(pitem);
			expired[i] = false;
			if (pitem.getPLastTime() != -1) {
				if (pitem.getLastDay() == 0) {
					expired[i] = true;
				}
			}
			recommended[i] = isInArray(itemId[i], betterIds);

			i++;
		}
		playerItem.setItemId(itemId);
		playerItem.setLastNum(lastNum);
		playerItem.setLastTime(lastTime);
		playerItem.setExpired(expired);
		playerItem.setMaintype(maintype);
		playerItem.setSubtype(subtype);
		playerItem.setIsUse(isUse);
		playerItem.setRecyclePrice(recyclePrice);
		playerItem.setRecommended(recommended);
		playerItem.setData(data);
		playerItem.setPlayerItemId(playerItemId);
		player.sendData(playerItem);

	}

	public void sendAddItem(WorldPlayer player, List<PlayerItemsFromShop> playerItems) {
		// 缓存没推送完不能推送增加协议
		if (player == null || player.getPlayer() == null || !player.isOnline() || !player.isCacheOk())
			return;
		AddItem playerItem = new AddItem();
		int size = playerItems.size();

		int[] itemId = new int[size];
		byte[] maintype = new byte[size];
		byte[] subtype = new byte[size];
		int[] lastTime = new int[size]; // 剩余的天数，如果是-1，就是不限时间使用
		int[] lastNum = new int[size]; // 剩余数量，如果是-1，就是不限数量使用
		boolean[] isUse = new boolean[size]; // 是否装备在身上
		String[] data = new String[size]; // 数据
		int[] recyclePrice = new int[size]; // 回收价格
		boolean[] expired = new boolean[size];
		boolean[] recommended = new boolean[size]; // 是否推荐
		int[] playerItemId = new int[size];
		int i = 0;
		int[] betterIds = getBetterItems(player, playerItems);
		for (PlayerItemsFromShop pitem : playerItems) {
			itemId[i] = pitem.getShopItem().getId();
			lastTime[i] = pitem.getPLastTime(); // 剩余的天数，如果是-1，就是不限时间使用
			lastNum[i] = pitem.getPLastNum(); // 剩余数量，如果是-1，就是不限数量使用
			playerItemId[i] = pitem.getId();
			isUse[i] = pitem.getIsInUsed();
			maintype[i] = pitem.getShopItem().getType();
			subtype[i] = pitem.getShopItem().getSubtype();
			recyclePrice[i] = getRecyclePrice(pitem.getShopItem());
			data[i] = getData(pitem);
			expired[i] = false;
			recommended[i] = isInArray(itemId[i], betterIds);
			i++;
		}
		playerItem.setItemId(itemId);
		playerItem.setLastNum(lastNum);
		playerItem.setLastTime(lastTime);
		playerItem.setExpired(expired);
		playerItem.setMaintype(maintype);
		playerItem.setSubtype(subtype);
		playerItem.setIsUse(isUse);
		playerItem.setRecyclePrice(recyclePrice);
		playerItem.setRecommended(recommended);
		playerItem.setData(data);
		playerItem.setPlayerItemId(playerItemId);
		player.sendData(playerItem);

	}

	private String getData(PlayerItemsFromShop playerItem) {
		Map<String, Object> data = new HashMap<String, Object>();
		ShopItem item = playerItem.getShopItem();
		// 武器装扮
		if (item.isEquipment()) {
			data.put("strongLevel", playerItem.getStrongLevel());
			data.put("starLevel", playerItem.getStars());
			if (item.isWeapon()) {
				data.put("proficiency", playerItem.getSkillful());
				data.put("skillId1", playerItem.getWeapSkill1());
				data.put("skillId2", playerItem.getWeapSkill2());
				data.put("skillLock", playerItem.getSkillLock());
			}
			data.put("attackStoneId", playerItem.getAttackStone());
			data.put("defendStoneId", playerItem.getDefenseStone());
			data.put("specailStoneId", playerItem.getSpecialStone());
			// 升星经验
			data.put("starExp", playerItem.getStarExp());
			data.put("wreckDefense", playerItem.getpAddWreckDefense());
			data.put("reduceCrit", playerItem.getpAddReduceCrit());
			data.put("force", playerItem.getpAddForce());
			data.put("armor", playerItem.getpAddArmor());
			data.put("agility", playerItem.getpAddAgility());
			data.put("physique", playerItem.getpAddPhysique());
			data.put("luck", playerItem.getpAddLuck());
			data.put("crit", playerItem.getpAddcrit());
			data.put("attack", playerItem.getPAddAttack());
			data.put("defend", playerItem.getPAddDefend());
			data.put("hp", playerItem.getPAddHp());
		} else if (item.getType() == Common.SHOP_ITEM_TYPE_ARMS_CARD) {
			// 卡牌
			data.put("force", playerItem.getpAddForce());
			data.put("armor", playerItem.getpAddArmor());
			data.put("agility", playerItem.getpAddAgility());
			data.put("physique", playerItem.getpAddPhysique());
			data.put("luck", playerItem.getpAddLuck());
			// 熔炼产出碎片的数量
			int meltNum = ServiceManager.getManager().getPlayerCardsService().getMeltNum(playerItem.getShopItem().getId());
			data.put("meltNum", meltNum);
			data.put("groupAddition",
					ServiceManager.getManager().getPlayerCardsService().getGroupAddition(playerItem.getShopItem().getTkId()));
		}
		return JSONObject.fromObject(data).toString();
	}

	public void sendAddItem(WorldPlayer player, PlayerItemsFromShop playerItem) {
		if (player == null)
			return;
		List<PlayerItemsFromShop> playerItems = new ArrayList<PlayerItemsFromShop>();
		playerItems.add(playerItem);
		sendAddItem(player, playerItems);
	}

	@Override
	public void sendUpdateItem(WorldPlayer player, int playerItemId, Map<String, String> info) {
		// 缓存没推送完不能推送更新协议
		if (player == null || !player.isCacheOk())
			return;
		int size = info.size();
		String[] key = new String[size];
		String[] value = new String[size];
		int i = 0;
		Set<String> keyset = info.keySet();
		for (String k : keyset) {
			key[i] = k;
			value[i] = info.get(k);
			i++;
		}
		UpdateItem updateItem = new UpdateItem();
		updateItem.setId(playerItemId);
		updateItem.setKey(key);
		updateItem.setValue(value);
		player.sendData(updateItem);
	}

	public void removeItem(WorldPlayer player, int[] playerItemId, int[] itemId) {
		// 缓存没推送完不能推送删除协议
		if (player == null || !player.isCacheOk())
			return;
		RemoveItem removeItem = new RemoveItem();
		removeItem.setItemId(itemId);
		removeItem.setPlayerItemId(playerItemId);
		player.sendData(removeItem);
	}

	/**
	 * 通知客户端消耗物品 数量为0时发删除协议
	 * 
	 * @param player
	 * @param playerItem
	 */
	public void useItem(WorldPlayer player, PlayerItemsFromShop playerItem) {
		if (playerItem.getPLastNum() == 0) {
			removeItem(player, new int[]{playerItem.getId()}, new int[]{playerItem.getShopItem().getId()});
		} else if (playerItem.getPLastTime() != -1) {
			Map<String, String> info = new HashMap<String, String>();
			if (playerItem.getPLastTime() == 0) {
				if (playerItem.getShopItem().getMove() != 1) {
					info.put("expired", "true");
				}
			}
			info.put("lastTime", playerItem.getPLastTime() + "");
			sendUpdateItem(player, playerItem.getId(), info);
		} else {
			Map<String, String> info = new HashMap<String, String>();
			info.put("lastNum", playerItem.getPLastNum() + "");
			sendUpdateItem(player, playerItem.getId(), info);
		}
	}

	/**
	 * 变更卡阵
	 * 
	 * @param oldCard
	 *            旧卡
	 * @param newCard
	 *            新卡
	 */
	public void changeCards(WorldPlayer player, PlayerItemsFromShop oldCard, PlayerItemsFromShop newCard) {
		Map<String, String> info = new HashMap<String, String>();
		if (oldCard != null) {
			oldCard.setIsInUsed(false);
			info.put("isUse", "false");
			sendUpdateItem(player, oldCard.getId(), info);
			update(oldCard);
		}
		if (newCard != null) {
			newCard.setIsInUsed(true);
			info.put("isUse", "true");
			sendUpdateItem(player, newCard.getId(), info);
			update(newCard);
		}
	}

	private int getRecyclePrice(ShopItem item) {
		try {
			if (item.getCanRecycle() == 1) {
				try {
					return ServiceManager.getManager().getShopItemService().getItemRecycleById(item.getId()).getGold();
				} catch (Exception ex) {

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	@Override
	public void StrongItem(WorldPlayer player, PlayerItemsFromShop playerItem) {
		Map<String, String> info = new HashMap<String, String>();
		info.put("strongLevel", playerItem.getStrongLevel() + "");
		info.put("attack", playerItem.getPAddAttack() + "");
		info.put("defend", playerItem.getPAddDefend() + "");
		info.put("hp", playerItem.getPAddHp() + "");
		if (playerItem.getWeapSkill1() > 0) {
			info.put("skillId1", playerItem.getWeapSkill1() + "");
		}
		if (playerItem.getWeapSkill2() > 0) {
			info.put("skillId2", playerItem.getWeapSkill2() + "");
		}
		if (playerItem.getSkillLock() > 0) {
			info.put("skillLock", playerItem.getSkillLock() + "");
		}
		sendUpdateItem(player, playerItem.getId(), info);
	}

	@Override
	public void changeAttribute(WorldPlayer player, PlayerItemsFromShop source, PlayerItemsFromShop target) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("strongLevel", source.getStrongLevel() + "");
		data.put("starLevel", source.getStars() + "");
		if (source.getShopItem().isWeapon()) {
			data.put("skillId1", source.getWeapSkill1() + "");
			data.put("skillId2", source.getWeapSkill2() + "");
			data.put("skillLock", source.getSkillLock() + "");
		}
		data.put("attackStoneId", source.getAttackStone() + "");
		data.put("defendStoneId", source.getDefenseStone() + "");
		data.put("specailStoneId", source.getSpecialStone() + "");
		data.put("starExp", source.getStarExp() + "");
		data.put("wreckDefense", source.getpAddWreckDefense() + "");
		data.put("reduceCrit", source.getpAddReduceCrit() + "");
		data.put("force", source.getpAddForce() + "");
		data.put("armor", source.getpAddArmor() + "");
		data.put("agility", source.getpAddAgility() + "");
		data.put("physique", source.getpAddPhysique() + "");
		data.put("luck", source.getpAddLuck() + "");
		data.put("crit", source.getpAddcrit() + "");
		data.put("attack", source.getPAddAttack() + "");
		data.put("defend", source.getPAddDefend() + "");
		data.put("hp", source.getPAddHp() + "");
		sendUpdateItem(player, source.getId(), data);

		data.put("strongLevel", target.getStrongLevel() + "");
		data.put("starLevel", target.getStars() + "");
		if (target.getShopItem().isWeapon()) {
			data.put("skillId1", target.getWeapSkill1() + "");
			data.put("skillId2", target.getWeapSkill2() + "");
			data.put("skillLock", target.getSkillLock() + "");
		}
		data.put("attackStoneId", target.getAttackStone() + "");
		data.put("defendStoneId", target.getDefenseStone() + "");
		data.put("specailStoneId", target.getSpecialStone() + "");
		data.put("starExp", target.getStarExp() + "");
		data.put("wreckDefense", target.getpAddWreckDefense() + "");
		data.put("reduceCrit", target.getpAddReduceCrit() + "");
		data.put("force", target.getpAddForce() + "");
		data.put("armor", target.getpAddArmor() + "");
		data.put("agility", target.getpAddAgility() + "");
		data.put("physique", target.getpAddPhysique() + "");
		data.put("luck", target.getpAddLuck() + "");
		data.put("crit", target.getpAddcrit() + "");
		data.put("attack", target.getPAddAttack() + "");
		data.put("defend", target.getPAddDefend() + "");
		data.put("hp", target.getPAddHp() + "");
		sendUpdateItem(player, target.getId(), data);
	}

	@Override
	public void MosaicItem(WorldPlayer player, PlayerItemsFromShop playerItem) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("attackStoneId", playerItem.getAttackStone() + "");
		data.put("defendStoneId", playerItem.getDefenseStone() + "");
		data.put("specailStoneId", playerItem.getSpecialStone() + "");
		data.put("wreckDefense", playerItem.getpAddWreckDefense() + "");
		data.put("reduceCrit", playerItem.getpAddReduceCrit() + "");
		data.put("force", playerItem.getpAddForce() + "");
		data.put("armor", playerItem.getpAddArmor() + "");
		data.put("agility", playerItem.getpAddAgility() + "");
		data.put("physique", playerItem.getpAddPhysique() + "");
		data.put("luck", playerItem.getpAddLuck() + "");
		data.put("crit", playerItem.getpAddcrit() + "");
		data.put("attack", playerItem.getPAddAttack() + "");
		data.put("defend", playerItem.getPAddDefend() + "");
		data.put("hp", playerItem.getPAddHp() + "");
		sendUpdateItem(player, playerItem.getId(), data);
	}

	@Override
	public void PunchItem(WorldPlayer player, PlayerItemsFromShop playerItem) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("attackStoneId", playerItem.getAttackStone() + "");
		data.put("defendStoneId", playerItem.getDefenseStone() + "");
		data.put("specailStoneId", playerItem.getSpecialStone() + "");
		sendUpdateItem(player, playerItem.getId(), data);
	}

	@Override
	public void upstarItem(WorldPlayer player, PlayerItemsFromShop playerItem) {
		Map<String, String> info = new HashMap<String, String>();
		info.put("starLevel", playerItem.getStars() + "");
		info.put("starExp", playerItem.getStarExp() + "");
		info.put("attack", playerItem.getPAddAttack() + "");
		info.put("defend", playerItem.getPAddDefend() + "");
		info.put("hp", playerItem.getPAddHp() + "");
		sendUpdateItem(player, playerItem.getId(), info);
	}

	private int[] getBetterItems(WorldPlayer player, List<PlayerItemsFromShop> playerItems) {
		int n = 7;// 一共七种物品：武器，头，脸，身，翅膀，戒指，项链
		int[] betterItem = new int[n];
		for (int i = 0; i < n; i++) {
			betterItem[i] = -1;
		}
		List<PlayerItemsFromShop> weapons = new ArrayList<PlayerItemsFromShop>();
		List<PlayerItemsFromShop> heads = new ArrayList<PlayerItemsFromShop>();
		List<PlayerItemsFromShop> faces = new ArrayList<PlayerItemsFromShop>();
		List<PlayerItemsFromShop> bodys = new ArrayList<PlayerItemsFromShop>();
		List<PlayerItemsFromShop> wings = new ArrayList<PlayerItemsFromShop>();
		List<PlayerItemsFromShop> rings = new ArrayList<PlayerItemsFromShop>();
		List<PlayerItemsFromShop> necklaces = new ArrayList<PlayerItemsFromShop>();
		// 把物品分门别类放在一起
		for (int i = 0; i < playerItems.size(); i++) {
			PlayerItemsFromShop playerItem = playerItems.get(i);
			ShopItem item = playerItem.getShopItem();
			if (item.isWeapon()) {
				weapons.add(playerItem);
			} else if (item.isHead()) {
				heads.add(playerItem);
			} else if (item.isFace()) {
				faces.add(playerItem);
			} else if (item.isBody()) {
				bodys.add(playerItem);
			} else if (item.isWing()) {
				wings.add(playerItem);
			} else if (item.isRing()) {
				rings.add(playerItem);
			} else if (item.isNecklace()) {
				necklaces.add(playerItem);
			}
		}
		betterItem[0] = getBetterItem(player, weapons);
		betterItem[1] = getBetterItem(player, heads);
		betterItem[2] = getBetterItem(player, faces);
		betterItem[3] = getBetterItem(player, bodys);
		betterItem[4] = getBetterItem(player, wings);
		betterItem[5] = getBetterItem(player, rings);
		betterItem[6] = getBetterItem(player, necklaces);
		return betterItem;
	}

	private int getBetterItem(WorldPlayer player, List<PlayerItemsFromShop> playerItems) {
		int betterFight = -1;
		int betterItem = -1;
		// 少于2个时候不计算
		if (playerItems.size() < 2)
			return betterItem;
		for (PlayerItemsFromShop playerItem : playerItems) {
			int fight = player.getFight(playerItem);
			if (betterFight < fight) {
				betterFight = fight;
				betterItem = playerItem.getShopItem().getId();
			}
		}
		return betterItem;
	}

	/**
	 * 判断目标值是否包含在数组内
	 * 
	 * @param target
	 * @param array
	 * @return
	 */
	private boolean isInArray(int target, int[] array) {
		for (int i : array) {
			if (i == target) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("strongLevel", "2");
		data.put("starLevel", "3");
		// String [] aa ={"1","2"};
		String s = JSONArray.fromObject(data).toString();
		System.out.println(s);
	}

	/**
	 * 玩家卡牌
	 */
	public List<PlayerItemsFromShop> getPlayerCardList(int playerId) {
		List<PlayerItemsFromShop> playerCards = new ArrayList<PlayerItemsFromShop>();
		List<PlayerItemsFromShop> playerItems = dao.getPlayerItemsFromShopByPlayerId(playerId);
		for (PlayerItemsFromShop playerItem : playerItems) {
			ShopItem item = playerItem.getShopItem();
			if (item.getType() == Common.SHOP_ITEM_TYPE_ARMS_CARD) {
				playerCards.add(playerItem);
			}
		}
		return playerCards;
	}

	public int getPlayerCardCount(int playerId) {
		int count = 0;
		List<PlayerItemsFromShop> playerCards = getPlayerCardList(playerId);
		for (PlayerItemsFromShop card : playerCards) {
			count += card.getPLastNum();
		}
		return count;
	}

	@Override
	public PlayerItemsFromShop getPlayerItemById(int playerId, int id) {
		return dao.getPlayerItemById(playerId, id);
	}

	@Override
	public int getTopStarForPlayer(int playerId) {
		return dao.getTopStarForPlayer(playerId);
	}

	@Override
	public List<PlayerItemsFromShop> getInUseItem(int playerId) {
		return dao.getInUseItem(playerId);
	}

	@Override
	public void loadPlayerItem(int playerId) {
		dao.loadPlayerItem(playerId);
	}

	@Override
	public void reloadPlayerItem(int playerId, int itemId) {
		dao.reloadPlayerItem(playerId, itemId);
	}

	@Override
	public void unLoadPlayerItem(int playerId) {
		dao.unLoadPlayerItem(playerId);
	}

	@Override
	public PlayerItemsFromShop savePlayerItemsFromShop(PlayerItemsFromShop pifs) {
		return dao.savePlayerItemsFromShop(pifs);
	}

	@Override
	public void deletePlayerItem(PlayerItemsFromShop pifs) {
		dao.deletePlayerItem(pifs);
	}

	/**
	 * 置换玩家石头
	 * 
	 * @param player
	 */
	@Override
	public void checkPlayerStone(WorldPlayer player) {
		// 力量宝石id,敏捷宝石id,护甲宝石id,体质宝石id,幸运宝石id
		int[] stoneIds1 = {382, 383, 384, 385, 386

		, 392, 393, 394, 395, 396

		, 402, 403, 404, 405, 406

		, 412, 413, 414, 415, 416

		, 422, 423, 424, 425, 426};
		// 攻击宝石id,暴击宝石id,防御宝石id,生命宝石id,破防宝石id
		int[] stoneIds1_1 = {387, 388, 389, 390, 391

		, 397, 398, 399, 400, 401

		, 407, 408, 409, 410, 411

		, 417, 418, 419, 420, 421

		, 427, 428, 429, 430, 431};
		// 先取出来删掉，再补一颗给玩家
		for (int i = 0; i < stoneIds1.length; i++) {
			int stoneId1 = stoneIds1[i];
			int stoneId1_1 = stoneIds1_1[i];
			PlayerItemsFromShop stone1 = uniquePlayerItem(player.getId(), stoneId1);
			int num = stone1 == null ? 0 : stone1.getPLastNum();
			if (num > 0) {
				dao.deletePlayerItem(stone1);
				playerGetItem(player.getId(), stoneId1_1, num, 15, "系统:" + stoneId1 + "置换为" + stoneId1_1, 0, 0, 0);

			}
		}
		// 身上的装备已镶嵌的先拆掉再装一个新的
		List<PlayerItemsFromShop> playerItems = getPlayerItemsFromShopByPlayerId(player.getId());
		for (PlayerItemsFromShop playerItem : playerItems) {
			if (playerItem.getShopItem().isEquipment()) {
				List<Integer> xqstoneList = new ArrayList<Integer>();// 装备上已镶嵌的石头
				xqstoneList.add(playerItem.getAttackStone());
				xqstoneList.add(playerItem.getDefenseStone());
				xqstoneList.add(playerItem.getSpecialStone());
				for (int stoneId : xqstoneList) {
					if (stoneId == 0)
						continue;
					for (int i = 0; i < stoneIds1.length; i++) {
						if (stoneId == stoneIds1[i]) {
							changeParm(playerItem, stoneId, 0);

							changeParm(playerItem, stoneIds1_1[i], 0);

						}
					}
				}
			}
		}
	}
}