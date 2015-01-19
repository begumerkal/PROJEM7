package com.wyd.empire.protocol.data.player;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class PictureUploadDelete extends AbstractData {
    private String[] deletePicture;

    public PictureUploadDelete(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_PictureUploadDelete, sessionId, serial);
    }

    public PictureUploadDelete() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_PictureUploadDelete);
    }

    public String[] getDeletePicture() {
        return deletePicture;
    }

    public void setDeletePicture(String[] deletePicture) {
        this.deletePicture = deletePicture;
    }
}
