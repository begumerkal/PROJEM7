package com.wyd.service.dao;

import com.app.db.dao.UniversalDao;
import com.wyd.service.bean.PlayerDIYTitle;
import com.wyd.service.bean.PlayerTaskTitle;
import com.wyd.service.bean.Title;

public interface ITittleDao  extends UniversalDao {
	
    
    public Title getTitleById(int titleId);
    
    /**
     *  取得玩家称号列表
     * @param playerId
     * @return
     */
    public PlayerTaskTitle getPlayerTaskTitle(int playerId);
    /**
     *  取玩家选择的自定义称号
     * @return
     */
    public PlayerDIYTitle getSelDIYTitle(int playerId);
}
