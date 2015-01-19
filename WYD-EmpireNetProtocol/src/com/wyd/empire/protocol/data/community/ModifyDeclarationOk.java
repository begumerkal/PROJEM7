package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>ModifyDeclarationOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_ModifyDeclarationOk(修改公会宣言成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ModifyDeclarationOk extends AbstractData {
	private String declaration;
	
    public ModifyDeclarationOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ModifyDeclarationOk, sessionId, serial);
    }

    public ModifyDeclarationOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ModifyDeclarationOk);
    }

	public String getDeclaration() {
		return declaration;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}
    
}
