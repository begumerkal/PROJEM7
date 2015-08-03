package com.wyd.empire.protocol.data.square;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GetMhUrlOk extends AbstractData {
    private String submitMh;
    private String listMh;

    public GetMhUrlOk(int sessionId, int serial) {
        super(Protocol.MAIN_SQUARE, Protocol.SQUARE_GetMhUrlOk, sessionId, serial);
    }

    public GetMhUrlOk() {
        super(Protocol.MAIN_SQUARE, Protocol.SQUARE_GetMhUrlOk);
    }

    public String getSubmitMh() {
        return submitMh;
    }

    public void setSubmitMh(String submitMh) {
        this.submitMh = submitMh;
    }

    public String getListMh() {
        return listMh;
    }

    public void setListMh(String listMh) {
        this.listMh = listMh;
    }
}
