package com.app.empire.protocol.data.player;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
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
