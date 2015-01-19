package com.wyd.exchange.dao.impl;
import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.exchange.bean.InviteInfo;
import com.wyd.exchange.dao.IInviteInfoDao;
/**
 * The DAO class for the TabConsortiaright entity.
 */
public class InviteInfoDao extends UniversalDaoHibernate implements IInviteInfoDao {
    public InviteInfoDao() {
        super();
    }
    /**
     * 根据玩家的邀请码获取玩家的邀请信息
     * @param inviteCode
     * @return
     */
    public InviteInfo getInviteInfoByCode(String inviteCode){
        return (InviteInfo) this.getClassObj("from InviteInfo where inviteCode=?", new Object[]{inviteCode});
    }
    
    /**
     * 根据玩家的邀请码获取玩家的邀请玩家列表
     * @param inviteCode
     * @return
     */
    public PageList getInviteListByCode(String inviteCode, int pageIndex, int pageSize){
        StringBuffer hsql = new StringBuffer();
        hsql.append("from InviteInfo where bindInviteCode=?");
        String hqlc = "SELECT COUNT(*) " + hsql.toString();
        return this.getPageList(hsql.toString(), hqlc, new Object[]{inviteCode}, pageIndex, pageSize);
    }
}