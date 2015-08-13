package com.app.empire.protocol.data.player;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>GetSkillList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_PLAYER下子命令PLAYER_GetStrengthenInfo(获取武器强化信息)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetStrengthenInfo extends AbstractData {
	private int weaponId;

    public GetStrengthenInfo(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetStrengthenInfo, sessionId, serial);
    }

    public GetStrengthenInfo() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetStrengthenInfo);
    }

	public int getWeaponId() {
		return weaponId;
	}

	public void setWeaponId(int weaponId) {
		this.weaponId = weaponId;
	}

}
