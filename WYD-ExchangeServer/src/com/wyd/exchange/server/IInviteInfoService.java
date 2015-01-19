package com.wyd.exchange.server;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.exchange.bean.InviteInfo;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IInviteInfoService extends UniversalManager{
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
    /**
     * 检查两个服务器是否在同一个组
     * @param s1
     * @param s2
     * @return
     */
    public boolean checkGroup(String s1, String s2);
}