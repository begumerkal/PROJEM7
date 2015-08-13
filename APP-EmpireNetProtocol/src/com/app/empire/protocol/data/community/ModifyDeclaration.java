package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>ModifyDeclaration</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_ModifyDeclaration(修改公会宣言)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ModifyDeclaration extends AbstractData {
	private int communityId;
	private String declaration;

    public ModifyDeclaration(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ModifyDeclaration, sessionId, serial);
    }

    public ModifyDeclaration() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ModifyDeclaration);
    }

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public String getDeclaration() {
		return declaration;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}


}
