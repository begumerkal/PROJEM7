package com.wyd.empire.protocol.data.admin;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 发邮件
 * 
 * @see AbstractData
 * @author mazheng
 */
public class SendMail extends AbstractData {
    private int    mailType; // 0个人，1多人，2全部
    private int[]  playerId; // 接收人id
    private String title;    // 邮件标题
    private String content;  // 邮件内容
    private String sendName; // 发送人名称

    public SendMail(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_SendMail, sessionId, serial);
    }

    public SendMail() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_SendMail);
    }

    public int getMailType() {
        return mailType;
    }

    public void setMailType(int mailType) {
        this.mailType = mailType;
    }

    public int[] getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int[] playerId) {
        this.playerId = playerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }
}
