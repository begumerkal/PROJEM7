package com.wyd.empire.protocol.data.trate;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>GetShopList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_TRATE下子命令TRATE_GetShopList(获得物品列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetPromotShopList extends AbstractData {

    public GetPromotShopList(int sessionId, int serial) {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_GetPromotShopList, sessionId, serial);
    }

    public GetPromotShopList() {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_GetPromotShopList);
    }

	


}
