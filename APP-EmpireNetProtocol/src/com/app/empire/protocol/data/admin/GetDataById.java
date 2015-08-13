package com.app.empire.protocol.data.admin;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 查询数据--根据ID查询单条数据
 * 
 * @see AbstractData
 * @author mazheng
 */
public class GetDataById extends AbstractData {
    /**
     * 0根据活动ID查询出活动广场信息
     * 1根据房间ID查询出房间信息
     * 2根据公告ID查询出公告信息
     * 4根据充值产品ID查询充值奖励信息
     */
    private int selectType; 
    private int id;

    public GetDataById(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GetDataById, sessionId, serial);
    }

    public GetDataById() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GetDataById);
    }

    public int getSelectType() {
        return selectType;
    }

    public void setSelectType(int selectType) {
        this.selectType = selectType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
