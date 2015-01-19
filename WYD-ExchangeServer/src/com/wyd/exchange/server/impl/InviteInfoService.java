package com.wyd.exchange.server.impl;
import java.util.List;
import org.springframework.context.ApplicationContext;
import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.exchange.bean.GroupInvite;
import com.wyd.exchange.bean.InviteInfo;
import com.wyd.exchange.dao.IInviteInfoDao;
import com.wyd.exchange.server.IInviteInfoService;
/**
 * The service class for the TabExtensionUser entity.
 */
public class InviteInfoService extends UniversalManagerImpl implements IInviteInfoService {
    /**
     * The dao instance injected by Spring.
     */
    private IInviteInfoDao      dao;
    /**
     * The service Spring bean id, used in the applicationContext.xml file.
     */
    private static final String SERVICE_BEAN_ID = "InviteInfoService";

    public InviteInfoService() {
        super();
    }

    /**
     * Returns the singleton <code>IExtensionUserService</code> instance.
     */
    public static IInviteInfoService getInstance(ApplicationContext context) {
        return (IInviteInfoService) context.getBean(SERVICE_BEAN_ID);
    }

    /**
     * Called by Spring using the injection rules specified in the Spring beans file "applicationContext.xml".
     */
    public void setDao(IInviteInfoDao dao) {
        super.setDao(dao);
        this.dao = dao;
    }

    public IInviteInfoDao getDao() {
        return this.dao;
    }

    /**
     * 根据玩家的邀请码获取玩家的邀请信息
     * 
     * @param inviteCode
     * @return
     */
    public InviteInfo getInviteInfoByCode(String inviteCode) {
        return dao.getInviteInfoByCode(inviteCode);
    }

    /**
     * 根据玩家的邀请码获取玩家的邀请玩家列表
     * 
     * @param inviteCode
     * @return
     */
    public PageList getInviteListByCode(String inviteCode, int pageIndex, int pageSize) {
        return dao.getInviteListByCode(inviteCode, pageIndex, pageSize);
    }

    /**
     * 检查两个服务器是否在同一个组
     * @param s1
     * @param s2
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean checkGroup(String s1, String s2) {
        List<GroupInvite> giList = dao.getAll(GroupInvite.class);
        for (GroupInvite gi : giList) {
            String seveices = "," + gi.getServices() + ",";
            s1 = "," + s1 + ",";
            s2 = "," + s2 + ",";
            if (seveices.indexOf(s1) > -1 && seveices.indexOf(s2) > -1) {
                return true;
            }
        }
        return false;
    }
}