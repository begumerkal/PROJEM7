package com.wyd.empire.protocol.data.admin;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 查询用户 
 * @see AbstractData
 * @author mazheng
 */
public class GetDataResult extends AbstractData {
    private int    selectType; // 0查询玩家，1查询封禁玩家
    private String key;        // 查询关键字
    private int    pageIndex;  // 当前显示页数
    private int    pageCount;  // 每页显示的数据行数
    private int    dataCount;  // 数据总行数
    private String content;    // 数据内容（json格式）
    public GetDataResult(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GetDataResult, sessionId, serial);
    }

    public GetDataResult() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GetDataResult);
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

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
