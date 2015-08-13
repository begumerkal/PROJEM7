package com.wyd.service.server.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.app.db.service.impl.UniversalManagerImpl;
import com.wyd.service.bean.WorldCupCode;
import com.wyd.service.bean.WorldCupPoints;
import com.wyd.service.dao.IWorldCupDao;
import com.wyd.service.server.factory.IWorldCupService;

/**
 * 
 * The service class for the TabConsortiaright entity.
 */
public class WorldCupService extends UniversalManagerImpl implements IWorldCupService {
	Logger log = Logger.getLogger(WorldCupService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private IWorldCupDao dao;

	public WorldCupService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IWorldCupService getInstance(ApplicationContext context) {
		return (IWorldCupService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IWorldCupDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	@Override
	public int addPoints(String accountId, String points) {
		return addPoints(accountId, Integer.parseInt(points));
	}

	@Override
	public int addPoints(String accountId, int points) {
		WorldCupPoints worldCupPoints = dao.getByAccountId(accountId);
		if (worldCupPoints != null) {
			points += worldCupPoints.getPoints();
			worldCupPoints.setPoints(points);			
			dao.update(worldCupPoints);
		} else {
			worldCupPoints = new WorldCupPoints();
			worldCupPoints.setAccountId(accountId);
			worldCupPoints.setPoints(points);
			worldCupPoints.setUseDiam(0);
			dao.save(worldCupPoints);
		}
		return worldCupPoints.getPoints();

	}
	@Override
	public int addDiamond(String accountId, String diamond) {
		return addDiamond(accountId, Integer.parseInt(diamond));
	}

	@Override
	public int addDiamond(String accountId, int diamond) {
		WorldCupPoints worldCupPoints = dao.getByAccountId(accountId);
		if (worldCupPoints != null) {
			diamond += worldCupPoints.getUseDiam();
			worldCupPoints.setUseDiam(diamond);			
			dao.update(worldCupPoints);
		} else {
			worldCupPoints = new WorldCupPoints();
			worldCupPoints.setAccountId(accountId);
			worldCupPoints.setUseDiam(diamond);
			worldCupPoints.setPoints(0);
			dao.save(worldCupPoints);
		}
		return worldCupPoints.getUseDiam();

	}

	@Override
	public int getPoints(String accountId) {
		WorldCupPoints worldCupPoints = dao.getByAccountId(accountId);
		return worldCupPoints == null ? 0 : worldCupPoints.getPoints();
	}
	@Override
	public int getUseDiam(String accountId) {
		WorldCupPoints worldCupPoints = dao.getByAccountId(accountId);
		return worldCupPoints == null ? 0 : worldCupPoints.getUseDiam();
	}

	@Override
	public int usePoints(String accountId, int points) {
		WorldCupPoints worldCupPoints = dao.getByAccountId(accountId);
		int totalPoints = worldCupPoints == null ? 0 : worldCupPoints.getPoints();
		if (points > totalPoints) {
			return -1;
		} else {
			worldCupPoints.setPoints(totalPoints - points);
			dao.update(worldCupPoints);
			return worldCupPoints.getPoints();
		}
	}

	@Override
	public String exchangeCode(String accountId, int goals) {
		WorldCupCode code = dao.getCode(goals);
		if (code != null) {
			code.setSendTime(new Date());
			code.setOwner(accountId);
			dao.update(code);
			return code.getCode();
		}
		return "-1";
	}

}