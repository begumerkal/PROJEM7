package com.wyd.empire.world.title;

/**
 * 玩家称号完成情况(GM工具使用)
 * 
 * @author sunzx
 *
 */
public class PlayerTitleInfo extends PlayerTitleVo {
	private static final long serialVersionUID = 1L;
	private String titleName;

	public PlayerTitleInfo(int titleId) {
		super(titleId);
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
}
