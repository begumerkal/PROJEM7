package com.wyd.empire.world.server.service.base.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.BuffRecord;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.bean.ConsortiaBattle;
import com.wyd.empire.world.bean.ConsortiaContribute;
import com.wyd.empire.world.bean.ConsortiaSkill;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.consortia.MaxPrestigeVo;
import com.wyd.empire.world.dao.IConsortiaDao;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IConsortiaService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.ChatService;

/**
 * The service class for the TabConsortia entity.
 */
public class ConsortiaService extends UniversalManagerImpl implements IConsortiaService {

	/**
	 * The dao instance injected by Spring.
	 */
	private IConsortiaDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "ConsortiaService";

	public ConsortiaService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiaService</code> instance.
	 */
	public static IConsortiaService getInstance(ApplicationContext context) {
		return (IConsortiaService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IConsortiaDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IConsortiaDao getDao() {
		return this.dao;
	}

	/**
	 * 获得所有公会列表
	 */
	public PageList getConsortiaList(int pageNum, int mark) {
		return dao.getConsortiaList(pageNum, mark);
	}

	/**
	 * 根据威信大小进行排序，获得公会列表
	 * 
	 * @return 公会列表
	 */
	public List<Consortia> getConsortiaList() {
		return dao.getConsortiaList();
	}

	/**
	 * 根据公会名称获得公会对象
	 * 
	 * @param name
	 *            公会名称
	 * @return 公会对象
	 */
	public Consortia getConsortiaByName(String name) {
		return dao.getConsortiaByName(name);
	}

	public Integer createCommunity(String communityName, int playerId) {
		Player player = new Player();
		player.setId(playerId);
		// 创建公会
		Consortia consortia = new Consortia();
		consortia.setName(communityName);
		consortia.setPresident(player);
		consortia.setPrestige(0);
		consortia.setLevel(1);
		consortia.setTotalMember(50);
		consortia.setIsAcceptMember(true);
		consortia.setMoney(0);
		consortia.setRank(0);
		consortia.setDeclaration(TipMessages.COMMUNITY_DEFAULTDECL_MESSAGE);
		consortia.setInsideNotice(TipMessages.COMMUNITY_DEFAULTNOTICE_MESSAGE);
		consortia.setCreateId(player.getId());
		consortia.setCreateTime(new Date());
		consortia.setHosId(0);
		consortia.setTodayNum(0);
		consortia.setTodayWin(0);
		consortia.setTotalNum(0);
		consortia.setWinNum(0);
		// dao.save(consortia);
		// 获得公会对象
		consortia = (Consortia) dao.save(consortia);

		ConsortiaContribute consortiaContribute = new ConsortiaContribute();
		consortiaContribute.getPlayer().setId(playerId);
		consortiaContribute.getConsortia().setId(consortia.getId());
		consortiaContribute.setContribute(0);
		consortiaContribute.setDiscontribute(0);
		consortiaContribute.setEverydayAdd(0);
		dao.save(consortiaContribute);

		consortiaContribute = ServiceManager.getManager().getPlayerSinConsortiaService()
				.getConsortiaContributeByPlayerId(playerId, consortia.getId());

		// 添加公会成员
		PlayerSinConsortia playerSinConsortia = new PlayerSinConsortia();
		playerSinConsortia.setConsortia(new Consortia());
		playerSinConsortia.getConsortia().setId(consortia.getId());
		playerSinConsortia.setPlayer(player);
		playerSinConsortia.setPosition(0);
		playerSinConsortia.setContribute(consortiaContribute.getContribute());
		playerSinConsortia.setDiscontribute(0);
		playerSinConsortia.setIdentity(1);
		playerSinConsortia.setConsortiaContribute(consortiaContribute);
		playerSinConsortia.setEverydayAdd(0);
		dao.save(playerSinConsortia);
		return consortia.getId();
	}

	/**
	 * 更改新的会长的职位（会长让位）
	 * 
	 * @param consortiaId
	 *            公会ID号
	 * @param newPId
	 *            新会长玩家ID号
	 */
	public void changePresident(int newPId, int oldPId) {
		dao.changePresident(newPId, oldPId);
	}

	/**
	 * 退出公会
	 * 
	 * @param consortiaId
	 * @param newPId
	 */
	public void exitCommunity(int playerId, WorldPlayer executorPlayer) {
		Consortia con = ServiceManager.getManager().getPlayerSinConsortiaService().findPlayerSinConsortia(playerId).getConsortia();
		// 该玩家是会长，则解散工会
		if (playerId == con.getPresident().getId()) {
			PlayerSinConsortia playerSinConsortia = ServiceManager.getManager().getPlayerSinConsortiaService()
					.findPlayerSinConsortia(executorPlayer.getId());
			List<PlayerSinConsortia> list = ServiceManager.getManager().getPlayerSinConsortiaService()
					.getCommunityMemberList(con.getId(), -1);
			for (PlayerSinConsortia psc : list) {
				WorldPlayer player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(psc.getPlayer().getId());
				if (player != null) {
					player.setGuild(null);
					// 退出工会取消称号
					ServiceManager.getManager().getTitleService().leaveConsortiaTask(player);
					if (player.isOnline()) // 离开工会频道
						ServiceManager
								.getManager()
								.getChatService()
								.syncChannels(player.getId(), new String[]{},
										new String[]{ChatService.CHAT_GUILD_CHANNEL + "_" + con.getId()});
				}
			}
			ServiceManager.getManager().getPlayerSinConsortiaService().deleteConsortiaMember(con.getId());
			dao.remove(Consortia.class, con.getId());
			if (null != playerSinConsortia)
				GameLogService.guildChange(executorPlayer.getId(), executorPlayer.getLevel(), con.getId(), con.getLevel(), 4,
						executorPlayer.getId(), executorPlayer.getLevel(), playerSinConsortia.getPosition());
		} else {
			PlayerSinConsortia playerSinConsortia = ServiceManager.getManager().getPlayerSinConsortiaService()
					.findPlayerSinConsortia(executorPlayer.getId());
			// 不是会长，直接退出
			ServiceManager.getManager().getPlayerSinConsortiaService().deletePlayerSinConsortiaByPlayerId(playerId, 1);
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
			if (player != null) {
				player.setGuild(null);
				// 退出工会取消称号
				ServiceManager.getManager().getTitleService().leaveConsortiaTask(player);
				// 离开工会频道
				ServiceManager.getManager().getChatService()
						.syncChannels(player.getId(), new String[]{}, new String[]{ChatService.CHAT_GUILD_CHANNEL + "_" + con.getId()});
				GameLogService.guildChange(player.getId(), player.getLevel(), con.getId(), con.getLevel(), 3, executorPlayer.getId(),
						executorPlayer.getLevel(), playerSinConsortia.getPosition());
			}
		}
	}

	/**
	 * 根据公会ID查询对应公会排名
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @return 对应公会排名
	 */
	public Integer getRankNum(int consortiaId, int mark) {
		return dao.getRankNum(consortiaId, mark);
	}

	/**
	 * 更新公会排名
	 */
	public void updateCommunityRank() {
		dao.updateCommunityRank();
	}

	/**
	 * 申请入会
	 * 
	 * @param consortiaId
	 * @param playerId
	 */
	public void applyJoinCommunity(int consortiaId, int playerId) {
		ConsortiaContribute consortiaContribute = ServiceManager.getManager().getPlayerSinConsortiaService()
				.getConsortiaContributeByPlayerId(playerId, consortiaId);
		if (null == consortiaContribute) {
			// 保存公会贡献度记录
			consortiaContribute = new ConsortiaContribute();
			consortiaContribute.getPlayer().setId(playerId);
			consortiaContribute.getConsortia().setId(consortiaId);
			consortiaContribute.setContribute(0);
			consortiaContribute.setDiscontribute(0);
			consortiaContribute.setEverydayAdd(0);
			dao.save(consortiaContribute);
		}

		consortiaContribute = ServiceManager.getManager().getPlayerSinConsortiaService()
				.getConsortiaContributeByPlayerId(playerId, consortiaId);

		PlayerSinConsortia playerSinConsortia = new PlayerSinConsortia();
		// 添加公会成员对象，审核状态为0（未审核）
		playerSinConsortia.setConsortia(new Consortia());
		playerSinConsortia.getConsortia().setId(consortiaId);
		playerSinConsortia.setContribute(consortiaContribute.getContribute());
		playerSinConsortia.setDiscontribute(0);
		playerSinConsortia.setIdentity(0);
		playerSinConsortia.setPlayer(new Player());
		playerSinConsortia.getPlayer().setId(playerId);
		playerSinConsortia.setPosition(4);
		playerSinConsortia.setConsortiaContribute(consortiaContribute);
		playerSinConsortia.setEverydayAdd(0);
		dao.save(playerSinConsortia);
	}

	/**
	 * 根据公会ID获得此ID以外的所有公会列表
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @return 所有公会列表
	 */
	public List<Consortia> getConsortiaOthers(int consortiaId) {
		return dao.getConsortiaOthers(consortiaId);
	}

	/**
	 * 根据玩家Id获取公会对象
	 * 
	 * @param playerId
	 *            玩家Id
	 * @return 公会对象
	 */
	public Consortia getConsortiaByPlayerId(int playerId) {
		PlayerSinConsortia playerConsortia = ServiceManager.getManager().getPlayerSinConsortiaService().findPlayerSinConsortia(playerId);
		return playerConsortia == null ? null : playerConsortia.getConsortia();
	}

	/**
	 * 根据玩家Id获取公会名称
	 * 
	 * @param playerId
	 *            玩家Id
	 * @return 公会名称
	 */
	public Object[] getConsortiaNameAndIdByPlayerId(int playerId) {
		Object[] result = dao.getConsortiaNameAndIdByPlayerId(playerId);
		if (result == null) {
			result = new Object[]{"", 0};
		}
		return result;
	}

	/**
	 * 根据公会ID，搜索公会信息
	 * 
	 * @param consortiaId
	 *            公司ID
	 * @return 对应公会信息
	 */
	public Consortia getConsortiaById(int consortiaId) {
		return dao.getConsortiaById(consortiaId);
	}

	/**
	 * 获得公会技能列表
	 * 
	 * @return
	 */
	public List<ConsortiaSkill> getConsortiaSkill() {
		return dao.getConsortiaSkill();
	}

	/**
	 * 获得玩家的buff记录
	 * 
	 * @param playerId
	 * @return
	 */
	public List<BuffRecord> getBuffRecordByPlayerId(int playerId) {
		List<BuffRecord> brList = dao.getBuffRecordByPlayerId(playerId);
		for (BuffRecord br : brList) {
			if (null == br.getConsortiaSkill() || 0 == br.getConsortiaSkill().getId()) {
				br.setConsortiaSkill(null);
				continue;
			}
			Hibernate.initialize(br.getConsortiaSkill());
		}
		return brList;
	}

	/**
	 * 删除过期的玩家的buff记录
	 * 
	 * @param playerId
	 * @return
	 */
	public void deleteBuffRecordOverTime(int playerId) {
		dao.deleteBuffRecordOverTime(playerId);
	}

	@Override
	public void updateEverydayAddToZero() {
		dao.updateEverydayAddToZero();
	}

	/**
	 * 获取敌对工会列表
	 */
	public List<Consortia> getHosConsortia(int hosId, int conId) {
		return dao.getHosConsortia(hosId, conId);
	}

	/**
	 * 获得当日工会战参数
	 * 
	 * @param mark
	 * @return
	 */
	public int getTodayConsortiaBattle(int mark) {
		return dao.getTodayConsortiaBattle(mark);
	}

	/**
	 * 判断两个公会是否是敌对工会
	 * 
	 * @param consortiaId1
	 * @param consortiaId2
	 * @return
	 */
	public boolean checkEnemyConsortia(int consortiaId1, int consortiaId2) {
		return dao.checkEnemyConsortia(consortiaId1, consortiaId2);
	}

	/**
	 * 保存公会战日志
	 * 
	 * @param playerId
	 * @param status
	 * @param vsType
	 */
	public void saveConsortiaBattleLog(WorldPlayer player, boolean status, int vsType) {
		ConsortiaBattle cb = new ConsortiaBattle();
		cb.setPlayerId(player.getId());
		if (status) {
			cb.setStatus(1);
		} else {
			cb.setStatus(0);
		}
		cb.setTime(new Date());
		cb.setVsType(vsType);
		cb.setConId(player.getGuildId());
		dao.save(cb);
	}

	/**
	 * 记录公会战斗情况
	 * 
	 * @param consortiaId
	 * @param num
	 */
	public void updateConsortiaBattleNum(int consortiaId, int num) {
		dao.updateConsortiaBattleNum(consortiaId, num);
	}

	/**
	 * 添加一条公会技能数据
	 * 
	 * @param skill
	 */
	public void addConsortiaSkill(ConsortiaSkill skill) {
		dao.save(skill);
	}

	/**
	 * 更新公会技能
	 * 
	 * @param skill
	 */
	public void updateConsortiaSkill(ConsortiaSkill skill) {
		dao.update(skill);
	}

	/**
	 * 获取公会技能分页列表
	 * 
	 * @param pageNum
	 * @param mark
	 * @return
	 */
	public PageList getConsortiaListSkill(int pageNum, int pageSize) {
		return dao.getConsortiaListSkill(pageNum, pageSize);
	}

	/**
	 * 获取公会列表
	 * 
	 * @return 公会列表
	 */
	@Override
	public PageList getConsortiaListByPage(int pageNum, int pageSize) {
		return dao.getConsortiaListByPage(pageNum, pageSize);
	}

	/**
	 * 根据公会id或名称查询公会
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public List<Consortia> getConsortiaByKey(String key) {
		return dao.getConsortiaByKey(key);
	}

	/**
	 * 获取当前服威望最高的公会及会长
	 */
	@Override
	public MaxPrestigeVo getMaxPrestige() {
		return dao.getMaxPrestige();
	}
}