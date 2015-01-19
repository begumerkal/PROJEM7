package com.wyd.service.server.impl;

import org.apache.log4j.Logger;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.service.bean.MarryRecord;
import com.wyd.service.bean.Player;
import com.wyd.service.bean.PlayerDIYTitle;
import com.wyd.service.bean.PlayerSinConsortia;
import com.wyd.service.bean.PlayerTaskTitle;
import com.wyd.service.bean.PlayerVO;
import com.wyd.service.bean.Title;
import com.wyd.service.dao.ITittleDao;
import com.wyd.service.server.factory.ITitleService;
import com.wyd.service.server.factory.ServiceManager;

public class TitleService extends UniversalManagerImpl implements ITitleService {
    Logger log= Logger.getLogger(TitleService.class);
    public TitleService() {
        super();
    }
    /**
     * The dao instance injected by Spring.
     */
    private ITittleDao dao;
    

    public void setDao(ITittleDao dao) {
		super.setDao(dao);
		this.dao = dao;
    }


    /**
     * 获取婚姻称号
     * 
     * @param player
     * @return
     */
    public String getMarryTitle(Player player) {
        MarryRecord marryRecord = ServiceManager.getManager().getMarryService().getSingleMarryRecordByPlayerId(player.getSex(), player.getId(), 1);
        if (null == marryRecord) {
            return "";
        }
        int mId = 0;
        String title;
        if (0 == player.getSex()) {
            mId = marryRecord.getWomanId();
            if (1 == marryRecord.getStatusMode()) {
                title = "###的未婚夫";
            } else {
                title = "###的老公";
            }
        } else {
            mId = marryRecord.getManId();
            if (1 == marryRecord.getStatusMode()) {
                title = "###的未婚妻";
            } else {
                title = "###的老婆";
            }
        }
        Player p = ServiceManager.getManager().getPlayerService().getById(mId);
        return title.replace("###", p.getName());
    }

    /**
     * 获取公会称号
     * 
     * @return
     */
    public String guildTitle(PlayerVO player) {
        String title = player.getGuildName() + "•";
        PlayerSinConsortia playerSinConsortia = ServiceManager.getManager().getPlayerSinConsortiaService().findPlayerSinConsortiaByPlayerId(player.getId());
        if (null == playerSinConsortia) {
            return "";
        }
        return title+player.getCommunityPosition();
    }

    /**
     * 根据称号ID获取称号对象
     * @param titleId 称号ID
     * @return
     */
    public Title getTitleById(int titleId) {
        return dao.getTitleById(titleId);
    }
    /**
     *  取得玩家称号列表
     * @param playerId
     * @return
     */
    public PlayerTaskTitle getPlayerTaskTitle(int playerId){
    	return dao.getPlayerTaskTitle(playerId);
    }


	@Override
	public PlayerDIYTitle getSelDIYTitle(int playerId) {
		return dao.getSelDIYTitle(playerId);
	}
}
