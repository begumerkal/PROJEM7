package com.wyd.service.bean;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * The persistent class for the tab_mail database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_mail")
public class Mail implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;
    private Integer           id;
    private Boolean           blackMail;
//    private String            content;
    private Boolean           isRead;
    private Integer           receivedId;
    private Integer           sendId;
    private Date              sendTime;
    private String            theme;
    private byte[]            contentByte;
    // 邮件：0表示个人邮件，1表示系统邮件，2表示公会邮件，3GM工具邮件
    // 意见箱：1建议，8问题反馈，9充值咨询
    private Integer           type;
    private String            remark;
    private String            isHandle;
    private int               deleteMark;
    private Integer           isStick;              // 是否置顶
    private String            receivedName;         // 接收人名称
    private String            sendName;             // 发送人名称

    public Mail() {
    }

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic()
    @Column(name = "blackMail", precision = 3)
    public Boolean getBlackMail() {
        return this.blackMail;
    }

    public void setBlackMail(Boolean blackMail) {
        this.blackMail = blackMail;
    }

    @Basic()
    @Column(name = "isRead", precision = 3)
    public Boolean getIsRead() {
        return this.isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    @Basic()
    @Column(name = "receivedId", precision = 10)
    public Integer getReceivedId() {
        return this.receivedId;
    }

    public void setReceivedId(Integer receivedId) {
        this.receivedId = receivedId;
    }

    @Basic()
    @Column(name = "sendId", precision = 10)
    public Integer getSendId() {
        return this.sendId;
    }

    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }

    @Basic()
    @Column(name = "sendTime")
    public Date getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Basic()
    @Column(name = "theme", length = 60)
    public String getTheme() {
        return this.theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Basic()
    @Column(name = "type", precision = 10)
    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic()
    @Column(name = "contentByte", length = 500)
    public byte[] getContentByte() {
        return contentByte;
    }

    public void setContentByte(byte[] contentByte) {
        this.contentByte = contentByte;
    }

    @Basic()
    @Column(name = "remark", length = 255)
    public String getRemark() {
        if (null == remark) {
            return "";
        } else {
            return remark;
        }
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic()
    @Column(name = "is_handle", length = 1)
    public String getIsHandle() {
        return isHandle;
    }

    public void setIsHandle(String isHandle) {
        this.isHandle = isHandle;
    }

    @Basic()
    @Column(name = "deleteMark", precision = 10)
    public int getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(int deleteMark) {
        this.deleteMark = deleteMark;
    }

    @Basic()
    @Column(name = "is_stick")
    public Integer getIsStick() {
        return isStick;
    }

    public void setIsStick(Integer isStick) {
        this.isStick = isStick;
    }

    @Basic()
    @Column(name = "received_name", length = 20)
    public String getReceivedName() {
        return receivedName;
    }

    public void setReceivedName(String receivedName) {
        this.receivedName = receivedName;
    }

    @Basic()
    @Column(name = "send_name", length = 20)
    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Mail)) {
            return false;
        }
        Mail castOther = (Mail) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }
    
    @Transient
    public String getContent() {
        return null == this.contentByte ? "" : new String(this.contentByte);
    }

    @Transient
    public void setContent(String content) {
        this.contentByte = content.getBytes();
    }
}