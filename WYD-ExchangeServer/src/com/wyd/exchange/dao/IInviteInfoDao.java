package com.wyd.exchange.dao;
import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.exchange.bean.InviteInfo;
/**
 * The DAO interface for the TabConsortiaright entity.
 */
public interface IInviteInfoDao extends UniversalDao {
    /**
     * 根据玩家的邀请码获取玩家的邀请信息
     * @param inviteCode
     * @return
     */
    public InviteInfo getInviteInfoByCode(String inviteCode);    
    /**
     * 根据玩家的邀请码获取玩家的邀请玩家列表
     * @param inviteCode
     * @return
     */
    public PageList getInviteListByCode(String inviteCode, int pageIndex, int pageSize);
}