package com.wyd.exchange.server.impl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.exchange.bean.GroupIntegral;
import com.wyd.exchange.bean.InteRank;
import com.wyd.exchange.bean.Integral;
import com.wyd.exchange.bean.IntegralInfo;
import com.wyd.exchange.dao.IIntegralDao;
import com.wyd.exchange.server.IIntegralService;
/**
 * The service class for the TabExtensionUser entity.
 */
public class IntegralService extends UniversalManagerImpl implements IIntegralService {
    /**
     * The dao instance injected by Spring.
     */
    private IIntegralDao      dao;
    /**
     * The service Spring bean id, used in the applicationContext.xml file.
     */
    private static final String SERVICE_BEAN_ID = "IntegralService";
    
    private static Map<String, GroupIntegral> groupMap;

    public IntegralService() {
        super();
    }
    
    /**
     * Returns the singleton <code>IExtensionUserService</code> instance.
     */
    public static IIntegralService getInstance(ApplicationContext context) {
        return (IIntegralService) context.getBean(SERVICE_BEAN_ID);
    }

    /**
     * Called by Spring using the injection rules specified in the Spring beans file "applicationContext.xml".
     */
    public void setDao(IIntegralDao dao) {
        super.setDao(dao);
        this.dao = dao;
    }

    public IIntegralDao getDao() {
        return this.dao;
    }
    
    /**
     * 初始化弹王分组信息
     */
    public void initData(){
        @SuppressWarnings("unchecked")
        List<GroupIntegral> groupList = this.dao.getAll(GroupIntegral.class);
        Map<String, GroupIntegral> gm = new HashMap<String, GroupIntegral>();
        for(GroupIntegral gi:groupList){
            String[] services = gi.getServices().split(",");
            for(String service:services){
                gm.put(service, gi);
            }
        }
        groupMap = gm;
    }
    
    /**
     * 获取指定服的分组id
     * @param serviceId
     * @return
     */
    public int getGroupIdByServiceId(Integer serviceId) {
        GroupIntegral group = groupMap.get(serviceId.toString());
        if (null != group) {
            return group.getId();
        } else {
            return 0;
        }
    }
    
    /**
     * 获取指定服的组信息
     * @param serviceId
     * @return
     */
    public GroupIntegral getGroupByServiceId(String serviceId) {
        return groupMap.get(serviceId);
    }
    
    /**
     * 更新积分
     * @param integralInfo
     */
    public void updatePlayerIntegral(IntegralInfo integralInfo) {
        Integral integral = getIntegralByPlayerIdAndArea(integralInfo.getPlayerId(), integralInfo.getServiceId());
        if (null == integral) {
            integral = new Integral();
            integral.setPlayerId(integralInfo.getPlayerId());
            integral.setPlayerName(integralInfo.getPlayerName());
            integral.setIntegral(integralInfo.getIntegral());
            integral.setFighting(integralInfo.getFighting());
            integral.setServiceId(integralInfo.getServiceId());
            integral.setServiceName(integralInfo.getServiceName());
            integral.setGroupId(getGroupIdByServiceId(integralInfo.getServiceId()));
            this.dao.save(integral);
        } else {
            integral.setPlayerName(integralInfo.getPlayerName());
            integral.setIntegral(integralInfo.getIntegral());
            integral.setFighting(integralInfo.getFighting());
            integral.setServiceName(integralInfo.getServiceName());
            integral.setGroupId(getGroupIdByServiceId(integralInfo.getServiceId()));
            this.dao.update(integral);
        }
    }
    
    /**
     * 根据分区和玩家Id获取积分记录
     * @param playerId
     * @param serviceId
     * @return
     */
    public Integral getIntegralByPlayerIdAndArea(int playerId, int serviceId) {
        return dao.getIntegralByPlayerIdAndServiceId(playerId, serviceId);
    }
	
	/**
     * 清除挑战赛积分
     * @param area
     */
    public void deleteIntegralOverSeason(int serviceId){
    	dao.deleteIntegralOverSeason(serviceId);
    }
    
    /**
     * 获取指定组的积分排名
     * @param groupId 组id
     * @param maxResults 返回数量
     * @return
     */
    public List<Integral> getIntegralRank(int groupId, int maxResults){
       return  dao.getIntegralRank(groupId, maxResults);
    }
    
    /**
     * 获取指定服的弹王排名
     * @param groupId
     * @param serviceId
     * @return
     */
    public List<InteRank> getRankInfoByService(int groupId, int serviceId) {
        List<Integral> integralList = getIntegralRank(groupId, 999999);
        List<InteRank> rankList = new ArrayList<InteRank>();
        List<Integer> inteList = new ArrayList<Integer>();
        Integral integral;
        for (int i = 0; i < integralList.size(); i++) {
            integral = integralList.get(i);
            if (integral.getServiceId() == serviceId) {
                InteRank rank = new InteRank();
                rankList.add(rank);
                rank.setPlayerId(integral.getPlayerId());
                rank.setRankNum(getRank(i + 1, integral.getIntegral(), inteList));
            }
            inteList.add(integral.getIntegral());
        }
        return rankList;
    }
    
    /**
     * 处理并列排名问题
     * @param rank
     * @param integral
     * @param inteList
     * @return
     */
    public static int getRank(int rank, int integral, List<Integer> inteList){
        for(int i=inteList.size()-1;i>=0;i--){
            if(inteList.get(i)==integral){
                rank--;
            }else{
                return rank;
            }
        }
        return rank;
    }
    
    public static void main(String[] args) throws IOException {
        int serviceId = 0;
        List<Integral> integralList = new ArrayList<Integral>();
        Integral inte = new Integral();
        integralList.add(inte);
        inte.setPlayerId(1);
        inte.setIntegral(10);
        inte = new Integral();
        integralList.add(inte);
        inte.setPlayerId(2);
        inte.setIntegral(10);
        inte = new Integral();
        integralList.add(inte);
        inte.setPlayerId(3);
        inte.setIntegral(10);
        inte = new Integral();
        integralList.add(inte);
        inte.setPlayerId(4);
        inte.setIntegral(8);
        inte = new Integral();
        integralList.add(inte);
        inte.setPlayerId(5);
        inte.setIntegral(8);
        inte = new Integral();
        integralList.add(inte);
        inte.setPlayerId(6);
        inte.setIntegral(7);
        
        
        List<InteRank> rankList = new ArrayList<InteRank>();
        List<Integer> inteList = new ArrayList<Integer>();
        Integral integral;
        for (int i = 0; i < integralList.size(); i++) {
            integral = integralList.get(i);
            if (integral.getServiceId() == serviceId) {
                InteRank rank = new InteRank();
                rankList.add(rank);
                rank.setPlayerId(integral.getPlayerId());
                rank.setRankNum(getRank(i + 1, integral.getIntegral(), inteList));
            }
            inteList.add(integral.getIntegral());
        }
        
        for(InteRank rank:rankList){
            System.out.println("playerId:"+rank.getPlayerId()+"---rank:"+rank.getRankNum());
        }
    }
}