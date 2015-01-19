package com.wyd.serial.bean;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the payments database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name="payments")
public class Payment implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String agent;
	private String approach;
	private Long cardAmt;
	private String cardMedium;
	private String cardNo;
	private String cardPwd;
	private String clientFromHost;
	private String clientReturnPort;
	private java.sql.Timestamp createdAt;
	private Integer executionCount;
	private String orderToken;
	private Long payAmt;
	private Integer priority;
	private Integer providerId;
	private Long realAmt;
	private String remark;
	private String returnCode;
	private String returnMessage;
	private Integer statusCallback;
	private Integer statusNotify;
	private Integer statusResubmit;
	private java.sql.Timestamp updatedAt;
	private String userToken;
    private String channel;
	private App app;
	private String productId;

    public Payment() {
    }

	@Id()
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", unique=true, nullable=false, precision=10)
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Basic()
	@Column(name="agent", length=30)
	public String getAgent() {
		return this.agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}

	@Basic()
	@Column(name="approach", nullable=false, length=15)
	public String getApproach() {
		return this.approach;
	}
	public void setApproach(String approach) {
		this.approach = approach;
	}

	@Basic()
	@Column(name="card_amt", nullable=false, precision=10)
	public Long getCardAmt() {
		return this.cardAmt;
	}
	public void setCardAmt(Long cardAmt) {
		this.cardAmt = cardAmt;
	}

	@Basic()
	@Column(name="card_medium", length=20)
	public String getCardMedium() {
		return this.cardMedium;
	}
	public void setCardMedium(String cardMedium) {
		this.cardMedium = cardMedium;
	}

	@Basic()
	@Column(name="card_no", length=60)
	public String getCardNo() {
		return this.cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Basic()
	@Column(name="card_pwd", length=60)
	public String getCardPwd() {
		return this.cardPwd;
	}
	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}

	@Basic()
	@Column(name="client_from_host", length=50)
	public String getClientFromHost() {
		return this.clientFromHost;
	}
	public void setClientFromHost(String clientFromHost) {
		this.clientFromHost = clientFromHost;
	}

	@Basic()
	@Column(name="client_return_port", length=10)
	public String getClientReturnPort() {
		return this.clientReturnPort;
	}
	public void setClientReturnPort(String clientReturnPort) {
		this.clientReturnPort = clientReturnPort;
	}

	@Basic()
	@Column(name="created_at")
	public java.sql.Timestamp getCreatedAt() {
		return this.createdAt;
	}
	public void setCreatedAt(java.sql.Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	@Basic()
	@Column(name="execution_count", nullable=false, precision=10)
	public Integer getExecutionCount() {
		return this.executionCount;
	}
	public void setExecutionCount(Integer executionCount) {
		this.executionCount = executionCount;
	}

	@Basic()
	@Column(name="order_token", length=80)
	public String getOrderToken() {
		return this.orderToken;
	}
	public void setOrderToken(String orderToken) {
		this.orderToken = orderToken;
	}

	@Basic()
	@Column(name="pay_amt", nullable=false, precision=10)
	public Long getPayAmt() {
		return this.payAmt;
	}
	public void setPayAmt(Long payAmt) {
		this.payAmt = payAmt;
	}

	@Basic()
	@Column(name="priority", nullable=false, precision=10)
	public Integer getPriority() {
		return this.priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Basic()
	@Column(name="provider_id", nullable=false, precision=10)
	public Integer getProviderId() {
		return this.providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	@Basic()
	@Column(name="real_amt", precision=10)
	public Long getRealAmt() {
		return this.realAmt;
	}
	public void setRealAmt(Long realAmt) {
		this.realAmt = realAmt;
	}

	@Basic()
	@Column(name="remark", length=500)
	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Basic()
	@Column(name="return_code", length=30)
	public String getReturnCode() {
		return this.returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	@Basic()
	@Column(name="return_message", length=120)
	public String getReturnMessage() {
		return this.returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	@Basic()
	@Column(name="status_callback", nullable=false, precision=10)
	public Integer getStatusCallback() {
		return this.statusCallback;
	}
	public void setStatusCallback(Integer statusCallback) {
		this.statusCallback = statusCallback;
	}

	@Basic()
	@Column(name="status_notify", nullable=false, precision=10)
	public Integer getStatusNotify() {
		return this.statusNotify;
	}
	public void setStatusNotify(Integer statusNotify) {
		this.statusNotify = statusNotify;
	}

	@Basic()
	@Column(name="status_resubmit", nullable=false, precision=10)
	public Integer getStatusResubmit() {
		return this.statusResubmit;
	}
	public void setStatusResubmit(Integer statusResubmit) {
		this.statusResubmit = statusResubmit;
	}

	@Basic()
	@Column(name="updated_at")
	public java.sql.Timestamp getUpdatedAt() {
		return this.updatedAt;
	}
	public void setUpdatedAt(java.sql.Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Basic()
	@Column(name="user_token", nullable=false, length=80)
	public String getUserToken() {
		return this.userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}


    @Basic()
    @Column(name="channel", length=80)
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    
	//bi-directional many-to-one association to App
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="app_id", referencedColumnName="id", nullable=false)
	public App getApp() {
		return this.app;
	}
	public void setApp(App app) {
		this.app = app;
	}
	
    @Basic()
    @Column(name = "product_id")
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Payment)) {
			return false;
		}
		Payment castOther = (Payment)other;
		return new EqualsBuilder()
			.append(this.getId(), castOther.getId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("id", getId())
			.toString();
	}

   
}