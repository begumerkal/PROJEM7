package com.wyd.empire.protocol.data.admin;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 查询数据
 * 
 * @see AbstractData
 * @author mazheng
 */
public class GetData extends AbstractData {
    private int    selectType; // 0查询玩家，1查询封禁玩家,2查询禁言玩家,3查询物品,4查询公告,5查询意见箱,6查询GM工具发送的邮件,7查询所有推送,8查询商品价格,9查询版本信息,13获取系统配置,20充值奖励列表,21任务列表
    // 查询关键字，多条件查询查询条件用|分割, 物品查询(单条件为物品id) 上架状态（0所有，1上架，2未上架），性别（0所有，1男，2女，3不限），类型（空所有）
    private String key;        
    private int    pageIndex;  // 当前显示页数
    private int    pageCount;  // 每页显示的数据行数

    public GetData(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GetData, sessionId, serial);
    }

    public GetData() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GetData);
    }

    public int getSelectType() {
        return selectType;
    }

    public void setSelectType(int selectType) {
        this.selectType = selectType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
