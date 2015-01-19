package com.wyd.empire.nearby.bean;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
/**
 * 附件好友邮件表
 * @author zguoqiu
 */
@Entity()
@Table(name = "tab_mail")
public class Mail extends BaseBean implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;
	private Integer id;
	private int sendId;// 发件人
	private int receivedId;// 收件人
	private String sendName; // 发送人名称
	private String receivedName; // 接收人名称
	private Date sendTime; //邮件创建时间
	private String theme; //邮件主题
	private byte[] content; //邮件内容
	private boolean isRead;//是否已读
	private byte deleteMark;//删除标识 0未删除，1收件人删除，2发件人删除,3双方都已删除

    @Id()
    @GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "assigned") 
    @Column(name = "id", unique = true, nullable = false, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic()
    @Column(name = "send_id", precision = 10)
	public int getSendId() {
		return sendId;
	}

	public void setSendId(int sendId) {
		this.sendId = sendId;
	}

	@Basic()
    @Column(name = "received_id", precision = 10)
	public int getReceivedId() {
		return receivedId;
	}

	public void setReceivedId(int receivedId) {
		this.receivedId = receivedId;
	}

    @Basic()
    @Column(name = "send_name", length = 20)
	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
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
    @Column(name = "send_time")
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

    @Basic()
    @Column(name = "theme", length = 60)
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

    @Basic()
    @Column(name = "content")
	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

    @Basic()
    @Column(name = "is_read", precision = 3)
	public boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(boolean isRead) {
		this.isRead = isRead;
	}

    @Basic()
    @Column(name = "delete_mark", precision = 3)
	public byte getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(byte deleteMark) {
		this.deleteMark = deleteMark;
	}
}