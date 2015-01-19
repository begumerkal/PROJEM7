package com.wyd.empire.world.dao.impl;

import java.util.List;
import java.util.Vector;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.bean.PetBar;
import com.wyd.empire.world.bean.PetCulture;
import com.wyd.empire.world.bean.PetTrain;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.bean.PlayerPetBar;
import com.wyd.empire.world.dao.IPlayerPetDao;

/**
 * The DAO class for the TabPlayeritemsfromshop entity.
 */
public class PlayerPetDao extends UniversalDaoHibernate implements IPlayerPetDao {
	public PlayerPetDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlayerPet> getPetListByPlayer(Integer playerId) {
		String hql = "FROM " + PlayerPet.class.getSimpleName() + " pp WHERE 1 = 1 ";
		hql += " AND playerId = ? ";
		hql += " order by isInUsed desc,pp.level desc";
		List<Object> values = new Vector<Object>();
		values.add(playerId);
		return getList(hql.toString(), values.toArray());
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlayerPet getByPlayerAndPet(Integer playerId, Integer petId) {
		String hql = "FROM " + PlayerPet.class.getSimpleName() + " where playerId=? and pet.id=?";
		Object[] values = new Object[]{playerId, petId};
		List<PlayerPet> list = this.getList(hql.toString(), values);
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlayerPet getInUsePet(int playerId) {
		String hql = "FROM " + PlayerPet.class.getSimpleName() + " where playerId=? and isInUsed=true";
		Object[] values = new Object[]{playerId};
		List<PlayerPet> list = this.getList(hql.toString(), values);
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean isInTrain(int playerId, int petId) {
		PlayerPet playerPet = getByPlayerAndPet(playerId, petId);
		return playerPet.getTrainEndTime() != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PetCulture getCultureByLevel(Integer level) {
		String hql = "FROM " + PetCulture.class.getSimpleName() + " where petLevel=?";
		Object[] values = new Object[]{level};
		// return (PetCulture)getUniqueResult(hql, values);
		// getUniqueResult 转 getList
		List<PetCulture> list = this.getList(hql.toString(), values);
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlayerPet getInTrainPet(int playerId) {
		String hql = "FROM " + PlayerPet.class.getSimpleName() + " where playerId=? and trainEndTime is not null";
		Object[] values = new Object[]{playerId};
		List<PlayerPet> list = this.getList(hql.toString(), values);
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PetTrain getTrainByExp(int quality, int exp) {
		String hql = "FROM " + PetTrain.class.getSimpleName() + " where 1=1";
		if (quality == 0) {
			hql += " and needExp<?";
		} else {
			hql += " and goldNeedExp<?";
		}
		hql += " order by petLevel desc";
		Object[] values = new Object[]{exp};
		List<PetTrain> rs = getList(hql, values, 1);
		if (rs != null && rs.size() > 0) {
			return rs.get(0);
		}
		return null;
	}

	@Override
	public PetBar getPetBar(int id) {
		return (PetBar) get(PetBar.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlayerPetBar getPlayerPetBar(int playerId) {
		String hql = "FROM " + PlayerPetBar.class.getSimpleName() + " where playerId=?";
		Object[] values = new Object[]{playerId};
		// return (PlayerPetBar)getUniqueResult(hql, values);
		// getUniqueResult 转 getList
		List<PlayerPetBar> list = this.getList(hql.toString(), values);
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

}