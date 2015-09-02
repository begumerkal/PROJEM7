package com.app.empire.protocol;
public class Protocol {
	public static final byte MAIN_ERROR = -1;
	public static final byte ERROR_ProtocolError = -1;
	/** 服务器间的消息协议 */
	public static final byte MAIN_SERVER = 1;
	public static final byte SERVER_Heartbeat = 1;// 服务器间心跳
	public static final byte SERVER_WorldServerToAccountServer = 2;// world链接账号服务
	public static final byte SERVER_DispatchLogin = 3;// dis链接world
	public static final byte SERVER_UpdateServerInfo = 4;// dis告知ipd 服务器信息-服务已开启
	public static final byte SERVER_NotifyMaxPlayer = 5;// world告知dis人数
	public static final byte SERVER_SyncLoad = 6;// dis告知ipd在线人数等情况
	public static final byte SERVER_AccountLogin = 7;
	public static final byte SERVER_AccountLoginOk = 8;
	public static final byte SERVER_SessionClosed = 9;// dis告知world 用户下线
	public static final byte SERVER_SetClientIPAddress = 10;// 用户链接dis告知world用户ip
	public static final byte SERVER_Kick = 11;// 踢用户下线
	public static final byte SERVER_SyncPlayer = 12;// 玩家角色信息同步(系统内部使用)
	public static final byte SERVER_PlayerLogout = 13; // 玩家登出(主要用这个协议通知账号服务器)
	// 查询用户所在服务区信息
	public static final byte SERVER_SetClientInfo = 14; // 设置客户端信息
	// 用户管理（广播，踢下线等）
	public static final byte SERVER_BroadCast = 15;
	public static final byte SERVER_ForceBroadCast = 16;
	public static final byte SERVER_ShutDown = 17;
	public static final byte SERVER_NotifyMaintance = 18;
	/** 用户帐号相关协议（客户端共享） */
	public static final byte MAIN_ACCOUNT = 2;
	public static final byte ACCOUNT_Heartbeat = 1;// 客户端对dis心跳
	public static final byte ACCOUNT_Login = 2;// 用户登录
	public static final byte ACCOUNT_LoginOk = 3;// 登录成功
	public static final byte ACCOUNT_RepeatLogin = 4;// 主账号重复登录
	public static final byte ACCOUNT_GetRoleList = 5;// 获取角色列表
	public static final byte ACCOUNT_GetRoleListOK = 6;
	public static final byte ACCOUNT_RoleCreate = 7;// 角色创建
	public static final byte ACCOUNT_RoleLogin = 8;// 角色登录
	public static final byte ACCOUNT_RoleLoginOk = 9;// 角色登录成功
	public static final byte ACCOUNT_Move = 10;// 角色移动
	public static final byte ACCOUNT_ReportPlace = 11;// 报告位置
	public static final byte ACCOUNT_ChannelLogin = 12;// 渠道登录
	public static final byte ACCOUNT_ChannelLoginResult = 13;
	public static final byte ACCOUNT_GetRandomName = 14;// name 随机
	public static final byte ACCOUNT_GetRandomNameOk = 15;
	public static final byte ACCOUNT_SetToken = 16;
	public static final byte ACCOUNT_LoginAgain = 17;
	public static final byte ACCOUNT_LoginFail = 18;
	public static final byte ACCOUNT_ModifyPassword = 19;
	public static final byte ACCOUNT_ModifyPasswordOk = 20;
	public static final byte ACCOUNT_FindPassword = 21;
	public static final byte ACCOUNT_FindPasswordOk = 22;
	public static final byte ACCOUNT_ModifyPasswordFail = 23;
	public static final byte ACCOUNT_Verification = 24;
	public static final byte ACCOUNT_VerificationResult = 25;
	public static final byte ACCOUNT_SetNewToken = 26;
	public static final byte ACCOUNT_SetNewTokenOk = 27;
	public static final byte ACCOUNT_GetPushList = 28;
	public static final byte ACCOUNT_GetPushListOk = 29;
	public static final byte ACCOUNT_GetDownloadRewardList = 30;
	public static final byte ACCOUNT_GetDownloadRewardListOK = 31;
	/** 聊天相关协议 */
	public static final byte MAIN_CHAT = 3;
	public static final byte CHAT_SendMessage = 1;
	public static final byte CHAT_ReceiveMessage = 2;
	public static final byte CHAT_ChangeChannel = 3;
	public static final byte CHAT_SyncChannels = 4;
	public static final byte CHAT_GetSpeakerNum = 5;
	public static final byte CHAT_GetSpeakerNumOk = 6;
	/** 系统间通信协议 */
	public static final byte MAIN_SYSTEM = 4;
	public static final byte SYSTEM_NOP = 1;
	public static final byte SYSTEM_SYNC = 2;
	public static final byte SYSTEM_HttpClose = 3;
	public static final byte SYSTEM_ShakeHands = 4;// 客户端对dis心跳
	public static final byte SYSTEM_TopHands = 5;
	public static final byte SYSTEM_GetIslandState = 7;
	public static final byte SYSTEM_GetIslandStateOk = 8;
	public static final byte SYSTEM_GetSystemInfo = 9;
	public static final byte SYSTEM_GetSystemInfoOk = 10;
	public static final byte SYSTEM_GetNoviceRemark = 11;
	public static final byte SYSTEM_GetNoviceRemarkOk = 12;
	public static final byte SYSTEM_GetItemPriceAndVip = 13;
	public static final byte SYSTEM_GetItemPriceAndVipOk = 14;
	public static final byte SYSTEM_EarthPush = 15;
	public static final byte SYSTEM_BattleShakeHands = 16;
	public static final byte SYSTEM_GetPayAppRewardList = 17;
	public static final byte SYSTEM_GetPayAppRewardListOk = 18;
	public static final byte SYSTEM_GetPayAppReward = 19;
	public static final byte SYSTEM_GetPayAppRewardOk = 20;
	public static final byte SYSTEM_GetKeyProcess = 21;
	public static final byte SYSTEM_GetKeyProcessOk = 22;
	/** 弹弹岛邮件相关协议 */
	public static final byte MAIN_MAIL = 5;
	public static final byte MAIL_SendInboxMail = 2;
	public static final byte MAIL_GetMailContent = 3;
	public static final byte MAIL_SendMailContent = 4;
	public static final byte MAIL_SendMail = 5;
	public static final byte MAIL_SendMailOk = 6;
	public static final byte MAIL_DeleteMail = 7;
	public static final byte MAIL_DeleteMailOk = 8;
	public static final byte MAIL_SendOutboxMail = 10;
	public static final byte MAIL_HasNewMail = 11;
	public static final byte MAIL_LoginCheckMail = 12;
	public static final byte MAIL_LoginCheckMailOk = 13;
	public static final byte MAIL_GetInboxMailNew = 14;
	public static final byte MAIL_GetOutboxMailNew = 15;
	/** 公告协议 */
	public static final byte MAIN_BULLETIN = 6;
	public static final byte BULLETIN_GetBulletin = 1;
	public static final byte BULLETIN_GetBulletinOk = 2;
	public static final byte BULLETIN_GetAbout = 3;
	public static final byte BULLETIN_GetAboutOk = 4;
	public static final byte BULLETIN_GetHelp = 5;
	public static final byte BULLETIN_GetHelpOk = 6;
	public static final byte BULLETIN_GetWeiboInfo = 7;
	public static final byte BULLETIN_GetWeiboInfoOk = 8;
	public static final byte BULLETIN_WeiboShare = 9;
	/** 客户端日志上传控制 */
	public static final byte MAIN_ERRORLOG = 7;
	public static final byte ERRORLOG_GetLogList = 1;
	public static final byte ERRORLOG_SendLogList = 2;
	public static final byte ERRORLOG_GetLog = 3;
	public static final byte ERRORLOG_SendLog = 4;
	/** 系统提示(包含错误提示和获取安卓充值价格列表信息) */
	public static final byte MAIN_ERRORCODE = 8;
	public static final byte ERRORCODE_GetList = 1;
	public static final byte ERRORCODE_GetListOk = 2;
	public static final byte ERRORCODE_CheckList = 3;
	public static final byte ERRORCODE_CheckOk = 4;
	public static final byte ERRORCODE_GetSmsCodeList = 12;
	public static final byte ERRORCODE_GetSmsCodeListOk = 13;
	public static final byte ERRORCODE_GetSmsCodeNewList = 14;
	public static final byte ERRORCODE_GetSmsCodeNewListOk = 15;
	/** 弹弹岛充值相关协议 */
	public static final byte MAIN_PURCHASE = 9;
	public static final byte PURCHASE_GetProductIdList = 1;
	public static final byte PURCHASE_SendProductIdList = 2;
	public static final byte PURCHASE_IOSSendProductCheckInfo = 3;
	public static final byte PURCHASE_BuySuccess = 4;
	public static final byte PURCHASE_BuyFailed = 5;
	public static final byte PURCHASE_AndroidSendProductCheckInfo = 6;
	public static final byte PURCHASE_SendProductId = 7;
	public static final byte PURCHASE_GetRuleList = 8;
	public static final byte PURCHASE_GetRuleListOk = 9;
	public static final byte PURCHASE_GetCallBackUri = 10;
	public static final byte PURCHASE_GetCallBackUriOk = 11;
	public static final byte PURCHASE_RequestSmsCodeSerialid = 14;
	public static final byte PURCHASE_RequestSmsCodeSerialidOk = 15;
	public static final byte PURCHASE_SubmitSMSProduct = 16;
	public static final byte PURCHASE_SMSProductBuySuccess = 17;

	// /** 地图协议 */
	// public static final byte MAIN_MAP = 10;
	// public static final byte MAP_GetMapList = 1;
	// public static final byte MAP_GetMapListOk = 2;

}
