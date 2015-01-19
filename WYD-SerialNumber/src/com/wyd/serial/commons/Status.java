package com.wyd.serial.commons;

public class Status {

	/** 初始状态，即录入状态。*/
	public final static int STATUS_INIT = 0;
	
	/** 支付处理中。*/
	public final static int STATUS_PROCESSING = 101;

	/** 支付处理成功状态。*/
	public final static int STATUS_SUCCESS = 200;
	
	/** 支付处理失败状态。由于通讯原因导致的错误。网络恢复后应该重试。 */
	public final static int STATUS_FAILURE_NETWORK = 411;
	
	/** 支付处理失败状态。可以再重试的错误。*/
	public final static int STATUS_FAILURE_CAN_RETRY = 421;
	
	/** 支付处理失败状态。可不再处理的错误。 */
	public final static int STATUS_FAILURE_FINAL = 431;
    
    /** 定单支付成功*/
    public final static int PAY_STATUS_SUCCESS = 0;

    /** 定单支付等待中*/
    public final static int PAY_STATUS_WAIL = 1;
    
    /** 定单支付失败*/
    public final static int PAY_STATUS_FAIL = 2;
    
    /** 支付成功提示信息*/
    public final static String MESSAGE_SUCCESS = "支付成功";
    
    /** 支付失败提示信息*/
    public final static String MESSAGE_FAIL = "卡号和密码不符合规范。";
    
    /** 支付失败提示信息*/
    public final static String MESSAGE_FAIL2 = "卡号卡密或卡面额不符合规则。";
    
}
