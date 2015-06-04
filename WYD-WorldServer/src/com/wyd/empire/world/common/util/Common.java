package com.wyd.empire.world.common.util;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wyd.empire.world.bean.Successrate;
import com.wyd.empire.world.server.service.factory.ServiceManager;
public class Common {
    /** 喇叭ID */
    public static final int      HORNID                              = 1;
    /** 彩色喇叭ID */
    public static final int      COLOURHORNID                        = 437;
    /** 升星石ID */
    public static final int      STARSID                             = 438;
    /** 改名笔ID */
    public static final int      RENAMEPENID                         = 2;
    /** 公会改名笔ID */
    public static final int      RENAMECOMMUNITYPENID                = 3;
    /** 公会构造券ID */
    public static final int      COMMUNITYBUILDID                    = 4;
    /** 双倍经验卡ID */
    public static final int      DOUBLEEXPID                         = 5;
    /** 财富卡ID */
    public static final int      WEALTHID                            = 6;
    /** 失败清零ID */
    public static final int      RESETZEROID                         = 7;
    /** 会员卡ID */
    public static final int      VIPID                               = 8;
    /** 临时会员卡ID */
    public static final int      TEMPVIPID                           = 485;
    /** 金币ID */
    public static final int      GOLDID                              = 26;
    /** 钻石ID */
    public static final int      DIAMONDID                           = 27;
    /** 勋章ID */
    public static final int      BADGEID                             = 28;
    /** 活跃值ID */
    public static final int      HYZID                               = 1044;
    /** 经验ID */
    public static final int      EXPID                               = 1045;
    /** 高级经验勋章ID  【修炼兑换属性经验值】*/
    public static final int      SENIORBADGEID                       = 892;
    /** 星魂碎片ID */
    public static final int      STARSOULDEBRISID                    = 891;
    /** 公会戒指ID */
    public static final int      RING                                = 29;
    /** 钻戒ID */
    public static final int      MARRYITEMID1                        = 228;
    /** 易拉环ID */
    public static final int      MARRYITEMID2                        = 229;
    /** 玫瑰花束ID */
    public static final int      MARRYITEMID3                        = 230;
    /** 银行卡ID */
    public static final int      MARRYITEMID4                        = 231;
    /** 技能锁ID */
    public static final int      LOCKID                              = 381;
    /** 技能锁ID */
    public static final int      PUNCHID                             = 25;
    /** 冰冻弹ID */
    public static final int      FREEZEID                            = 12;
    /** 一级转生石ID */
    public static final int      REBIRTHLEVEL1ID                     = 380;
    /** 一级转生所需转生石数量 */
    public static final int      REBIRTHLEVEL1NUM                    = 1;
    /** 最多转生次数 */
    public static final int      REBIRTHTOPLEVEL                     = 1;
    /** 最低转生级别 */
    public static final int      REBIRTHLOWLEVEL                     = 50;
    /** 玩家一转后等级 */
    public static final int      REBIRTHCHANGELEVEL1                 = 16;
    /** 武器熟练度等级一 */
    public static final int      SKILLFULLLEVEL1                     = 4900;
    /** 武器熟练度等级一加成 */
    public static final int      SKILLFULLLEVEL1ADD                  = 245;
    /** 武器熟练度等级二 */
    public static final int      SKILLFULLLEVEL2                     = 7600;
    /** 武器熟练度等级二加成 */
    public static final int      SKILLFULLLEVEL2ADD                  = 135;
    /** 武器熟练度等级三 */
    public static final int      SKILLFULLLEVEL3                     = 8900;
    /** 武器熟练度等级三加成 */
    public static final int      SKILLFULLLEVEL3ADD                  = 65;
    /** 武器熟练度等级四 */
    public static final int      SKILLFULLLEVEL4                     = 9600;
    /** 武器熟练度等级四加成 */
    public static final int      SKILLFULLLEVEL4ADD                  = 35;
    /** 武器熟练度等级五 */
    public static final int      SKILLFULLLEVEL5                     = 10000;
    /** 武器熟练度等级五加成 */
    public static final int      SKILLFULLLEVEL5ADD                  = 20;
    /** 副会长个数 */
    public static final int      POSITION1                           = 1;
    /** 长老个数 */
    public static final int      POSITION2                           = 3;
    /** 精英个数 */
    public static final int      POSITION3                           = 10;
    /** 其他类型type */
    public static final int      OTHERTYPE                           = 8;
    /** 喇叭subtype */
    public static final int      HORNSUBTYPE                         = 0;
    /** BUff的type */
    public static final int      BUFFTYPE                            = 9;
    /** 一天的时间long值 */
    public static final long     ONEDAYLONG                          = 24 * 60 * 60 * 1000;
    /** 一小时long值 */
    public static final long     ONEHOURLONG                         = 60 * 60 * 1000;
    /** 一分钟long值 */
    public static final long     ONEMINLONG                          = 60 * 1000;
    /** 显示在线玩家数量 */
    public static final int      ONLINENUM                           = 50;
    /** 副本地地图奖励false表示不显示 */
    public static final boolean  BOSSMAP_REWARD_DISPLAY_NO           = false;
    /** 副本地地图奖励true表示显示 */
    public static final boolean  BOSSMAP_REWARD_DISPLAY_YES          = true;
    /** 是否黑名单-false表示否 */
    public static final boolean  MAIL_BLACK_NO                       = false;
    /** 是否黑名单-true表示是 */
    public static final boolean  MAIL_BLACK_YES                      = true;
    /** 收件人ID为0时表示系统邮件，如建议邮件等 */
    public static final int      MAIL_RECEIVED_ID                    = 0;
    /** 邮件: 0表示个人邮件 */
    public static final int      MAIL_TYPE_SELF                      = 0;
    /** 邮件: 1表示系统邮件 */
    public static final int      MAIL_TYPE_SYSTEM                    = 1;
    /** 邮件: 2表示公会邮件 */
    public static final int      MAIL_TYPE_CONSORTIA                 = 2;
    /** 邮件状态: false表示未读 */
    public static final boolean  MAIL_IS_READ_NO                     = false;
    /** 邮件状态: true表示已读 */
    public static final boolean  MAIL_IS_READ_YES                    = true;
    /** 会员身份: 0表示待审批会员 */
    public static final int      CONSORTIA_IDENTITY_WAIT_APPROVAL    = 0;
    /** 会员身份: 1表示正式会员 */
    public static final int      CONSORTIA_IDENTITY_ALREADY_APPROVAL = 1;
    /** 地图类型: 0表示普通战斗 */
    public static final int      MAP_TYPE_GENERAL                    = 0;
    /** 地图类型: 1表示小副本战斗 */
    public static final int      MAP_TYPE_SMALL                      = 1;
    /** 地图类型: 2表示大副本战斗 */
    public static final int      MAP_TYPE_BIG                        = 2;
    /** 地图类型: 3表示世界BOSS战斗 */
    public static final int      MAP_TYPE_WORLD                      = 3;
    /** 地图类型: 4表示单人副本战斗 */
    public static final int      MAP_TYPE_SINGLE                     = 4;
    /** 玩家状态: 1表示有效 */
    public static final byte     PLAYER_USESTATE_NORMAL              = 1;
    /** 玩家状态: 0表示冻结 */
    public static final byte     PLAYER_USESTATE_DELETE              = 0;
    /** 玩家装备数量为0 */
    public static final int      PLAYER_ITEMS_FROM_SHOP_P_LAST_NUM   = 0;
    /** 玩家装备剩余天数为0 */
    public static final int      PLAYER_ITEMS_FROM_SHOP_P_LAST_TIME  = 0;
    /** 是否在装备中:0表示未装备 */
    public static final boolean  PLAYER_ITEMS_FROM_SHOP_IS_USE_NO    = false;
    /** 是否在装备中:1表示已装备) */
    public static final boolean  PLAYER_ITEMS_FROM_SHOP_IS_USE_YES   = true;
    /** 物品状态：false表示未上架 */
    public static final boolean  SHOP_ITEM_ON_SALE_NO                = false;
    /** 物品状态：true表示已上架 */
    public static final boolean  SHOP_ITEM_ON_SALE_YES               = true;
    /** 物品使用性别限制：1表示只能女的使用 */
    public static final int      SHOP_ITEM_SEX_WOMAN                 = 1;
    /** 物品使用性别限制：0表示只能男的使用 */
    public static final int      SHOP_ITEM_SEX_MAN                   = 0;
    /** 物品使用性别限制：2表示男女都能使用 */
    public static final int      SHOP_ITEM_SEX_ALL                   = 2;
    /** 查询物品动作： 1表示推荐，取销售记录前10名 */
    public static final int      SHOP_ITEM_ACT_TYPE_RECOMMEND        = 1;
    /** 查询物品动作： 2表示武器 */
    public static final int      SHOP_ITEM_ACT_TYPE_ARMS             = 2;
    /** 查询物品动作： 3表示头饰 */
    public static final int      SHOP_ITEM_ACT_TYPE_HEADDRESS        = 3;
    /** 查询物品动作： 4表示脸谱 */
    public static final int      SHOP_ITEM_ACT_TYPE_FACEBOOK         = 4;
    /** 查询物品动作： 5表示衣服 */
    public static final int      SHOP_ITEM_ACT_TYPE_CLOTHES          = 5;
    /** 查询物品动作： 6表示其他 */
    public static final int      SHOP_ITEM_ACT_TYPE_OTHER            = 6;
    /** 查询物品动作： 7表示兑换 */
    public static final int      SHOP_ITEM_ACT_TYPE_EXCHANGE         = 7;
   
    /** 物品类型： 5表示一般道具（只能在战场上使用的道具） */
    public static final byte     SHOP_ITEM_TYPE_ARMS_GENERAL         = 5;
    /** 物品类型： 6表示合成类（合成时使用的） */
    public static final byte     SHOP_ITEM_TYPE_ARMS_FUSION          = 6;
    /** 物品类型：11镶嵌攻击石（镶嵌时使用的） */
    public static final byte     SHOP_ITEM_TYPE_ARMS_MOSAIC1         = 11;
    /** 物品类型： 12镶嵌防御石（镶嵌时使用的） */
    public static final byte     SHOP_ITEM_TYPE_ARMS_MOSAIC2         = 12;
    /** 物品类型：13镶嵌特殊石（镶嵌时使用的） */
    public static final byte     SHOP_ITEM_TYPE_ARMS_MOSAIC3         = 13;
    /** 物品类型： 8表示其它 */
    public static final byte     SHOP_ITEM_TYPE_ARMS_OTHER           = 8;
    /** 物品类型： 14卡牌碎片 */
    public static final byte     SHOP_ITEM_TYPE_ARMS_CARDDEBRIS      = 14;
    /** 物品类型： 15卡牌 */
    public static final byte     SHOP_ITEM_TYPE_ARMS_CARD            = 15;
    /** 副本冷却所需钻石 */
    public static final int      BOSSNEEDTICKET                      = 10;
    /** 默认7天删除系统邮件 */
    public static final int      MAIL_DEL_SYSTEM_MAIL                = 7;
    /** 0表示系统邮件 */
    public static final int      MAIL_TYPE_SYSTEM_MAIL               = 0;
    /** 好友是否黑名单： 0表示否 */
    public static final boolean  FRIEND_BLACK_LIST_NO                = false;
    /** 好友是否黑名单： 1表示是 */
    public static final boolean  FRIEND_BLACK_LIST_YES               = true;
    /** 一级强化石 */
    public static final int      LVL1STONE                           = 9;
    /** 二级强化石 */
    public static final int      LVL2STONE                           = 10;
    /** 三级强化石 */
    public static final int      LVL3STONE                           = 11;
    /** 四级强化石 */
    public static final int      LVL4STONE                           = 12;
    /** 五级强化石 */
    public static final int      LVL5STONE                           = 13;
    /** 一级幸运石 */
    public static final int      LVL1LUCK                            = 14;
    /** 二级幸运石 */
    public static final int      LVL2LUCK                            = 15;
    /** 圣灵石 */
    public static final int      PROTECTSTONE                        = 16;
    /** 强化失败等级界限 */
    public static final int      STRFAILLVL                          = 5;
    /** 强化加成比例 */
    public static final int      ADDRATE                             = 5;
    /** 粘合剂 */
    public static final int      GETIN                               = 24;
    /** 游戏大厅界面 */
    public static final int      GAME_INTERFACE_HALL                 = 2;
    /** 房间界面 */
    public static final int      GAME_INTERFACE_ROOM                 = 3;
    /** 战斗界面 */
    public static final int      GAME_INTERFACE_BATTLE               = 5;
    /** 公会技能经验加成 */
    public static final int      CONEXP                              = 11;
    /** 公会技能经验加成 */
    public static final int      CONGOLD                             = 12;
    /** 公会技能防御加成 */
    public static final int      CONDEF                              = 2;
    /** 公会技能怒气降低加成 */
    public static final int      CONANGRYLOW                         = 3;
    /** 公会技能加血加成 */
    public static final int      CONADDHP                            = 4;
    /** 公会技能治疗加成 */
    public static final int      CONTREAT                            = 5;
    /** 公会技能体力加成 */
    public static final int      CONPOWER                            = 6;
    /** 公会技能暴击加成 */
    public static final int      CONCRIT                             = 7;
    /** 公会技能伤害加成 */
    public static final int      CONHURT                             = 8;
    /** 公会技能生命加成 */
    public static final int      CONHPCAP                            = 9;
    /** 公会技能体力消耗加成 */
    public static final int      CONPOWERLOW                         = 10;
    /** 公会技能强化加成 */
    public static final int      CSTRONG                             = 13;
    /** 公会技能合成加成 */
    public static final int      CGETIN                              = 14;
    /** 公会技能金币消耗加成 */
    public static final int      CGOLDLOW                            = 15;
    /** VIP经验 */
    public static final int[]    VIPEXP                              = { 0, 300, 800, 1800, 4300, 9300, 19300, 44300, 94300, 194300};
    /** 爱心的物品Id */
    public static final int      LOVEID                              = 284;
    /** 记录每页默认条数 */
    public static int            PAGESIZE                            = 30;
    /** 记录每天活得爱心许愿的人数 */
    public static int            WISHNUM                             = 0;
    /** 购买失败原因: 定单验证失败 */
    public static final int      PURCHASE_BUY_TYPE_VERIFY_FAIL       = 0;
    /** 购买失败原因: 增加钻石失败 */
    public static final int      PURCHASE_BUY_TYPE_ADD_TICKET_FAIL   = 1;
    /** 购买失败原因: 定单处理中 */
    public static final int      PURCHASE_BUY_TYPE_DEALING           = 2;
    /** 购买失败原因: 用户取消操作 */
    public static final int      PURCHASE_BUY_TYPE_CANCEL            = 3;
    /** 意见箱是否置顶 */
    public static final int      IS_STICK                            = 0;
    /** 首冲非统一奖励 */
    public static final int      FIRST_CHARGEREWARD_TYPE_0           = 0;
    /** 首冲统一奖励 */
    public static final int      FIRST_CHARGEREWARD_TYPE_1           = 1;
    /** 启用状态 */
    public static final int      STATUS_SHOW                         = 1;
    /** 禁用状态 */
    public static final int      STATUS_HIDE                         = 0;
    /** 领取充值奖励、抽奖--首冲领取 */
    public static final int      RECHARGE_REWARD                     = 0;
    /** 领取充值奖励、抽奖--抽奖领取 */
    public static final int      LOTTERY_REWARD                      = 1;
    /** 每日首充领取 */
    public static final int      EVERYDAY_RECHARGE_REWARD            = 2;
    /** 是否首次充值判断--新版领取 */
    public static final String   PLAYER_BILL_STATUS_N                = "N";
    /** 是否首次充值判断--新版领取 */
    public static final String   PLAYER_BILL_STATUS_Y                = "Y";
    /** 是否已经发放奖励--未发放 */
    public static final String   LOG_AWARD_STATUS_N                  = "N";
    /** 是否已经发放奖励--已发放 */
    public static final String   LOG_AWARD_STATUS_Y                  = "Y";
    /** 活动奖励类型--充值 */
    public static final int      LOG_AWARD_TYPE_0                    = 0;
    /** 活动奖励类型--强化 */
    public static final int      LOG_AWARD_TYPE_1                    = 1;
    /** 活动奖励类型--新品上架 */
    public static final int      LOG_AWARD_TYPE_2                    = 2;
    /** 活动奖励类型--在线时长 */
    public static final int      LOG_AWARD_TYPE_3                    = 3;
    /** 活动奖励类型--商城消费 */
    public static final int      LOG_AWARD_TYPE_4                    = 4;
    /** 活动奖励类型--连续登陆并且在线时长 */
    public static final int      LOG_AWARD_TYPE_5                    = 5;
    /** 活动奖励类型--结婚 */
    public static final int      LOG_AWARD_TYPE_6                    = 6;
    /** 活动奖励类型--升星 */
    public static final int      LOG_AWARD_TYPE_7                    = 7;
    /** 记录排位赛开始关闭开关 */
    public static boolean        rankStart                           = true;
    /** 性别男 */
    public static final int      PLAYER_SEX_M                        = 0;
    /** 性别女 */
    public static final int      PLAYER_SEX_W                        = 1;
    /** 微博--新浪微博 */
    public static final int      WB_XL                               = 0;
    /** 微博--腾讯微博 */
    public static final int      WB_TX                               = 1;
    /** 微博--推特*/
    public static final int      WB_TT                               = 2;
    /** 微博--新浪微博 */
    public static final String   XLWB                                = "xlwb";
    /** 微博--腾讯微博 */
    public static final String   TXWB                                = "txwb";
    /** 微博--推特 */
    public static final String   TTWB                                = "ttwb";
    /** 属性转移--1物品不能转 */
    public static final int      CHANGE_ATTRIBUTE_N                  = 1;
    /** 属性转移--0物品能转 */
    public static final int      CHANGE_ATTRIBUTE_Y                  = 0;
    /** 玩家默认微博值 */
    public static final String   PLAYER_DEFAULT_WB                   = "xlwb=0,txwb=0";
    /** 副本展示奖励物品数量 **/
    public static final int      BOSSMAP_REWARD_SHOW_COUNT           = 5;
    /** 副本奖励物品数量 **/
    public static final int      BOSSMAP_REWARD_COUNT                = 6;
    /** 装备升星 **/
    public static final int      STRONGE_RECORD_STAR                 = 7;
    /** 二级真强化石 */
    public static final int            LVL1REALSTONE           = 375;
    /** 二级真强化石 */
    public static final int            LVL2REALSTONE           = 376;
    /** 三级真强化石 */
    public static final int            LVL3REALSTONE           = 377;
    /** 四级真强化石 */
    public static final int            LVL4REALSTONE           = 378;
    /** 五级真强化石 */
    public static final int            LVL5REALSTONE           = 379;
    /** 主动抛出异常的标识 */
    public static final String         ERRORKEY                = "m_error:";
    /** 商城默认折扣--不打折 */
    public static final int            SHOP_DISCOUNT_DEF       = 100;
    /** 免费拆卸**/
    public static final int            PUNCHFREE               = 0;
    /** 钻石拆卸**/
    public static final int            PUNCHCOST               = 1;
    /** 装备可回收 */
    public static final int            CAN_RECYCLE             = 1;
    /** 结婚提示**/
    public static final String         MARRY_NOTICE            = "MERRY_MARK";
    /** 宠物训练加速消耗钻石系数*/
    public static final int            PET_COMPLETION_DIAMOND  = 100;
    /** 宠物传承需要的钻石*/
    public static final int            PET_INHERITANCE_DIAMOND = 50;
    /** 强化引导的id **/
    public static final int            GUIDE_STRENGTHEN_ID     = 2;
    /** 宠物引导的id **/
    public static final int            GUIDE_PET_ID            = 3;
    /** 20会员卡包月 **/
    public static final int            MONTHLY_20              = 35;
    /** 会员卡包月 **/
    public static final String         MONTHLY                 = "monthly";
    /** 世界BOSS默认地图 */
    public static final int            WORLDBOSS_DEFAULT_MAP   = 45;
    /** 世界BOSS默认CDTime(秒)*/
    public static final int            WORLDBOSS_CDTIME        = 30;
    /** 世界BOSS清除CDTime钻石*/
    public static final int            WORLDBOSS_CLEAR_CDTIME  = 2;
    /** 挑战赛上赛季前三名*/
    public static String               CHALLENGE_RANK          = "";
    /**发送物品类型： 0表示普通物品**/
    public static final int            GIVENITEM_TYPE_PROPS    = 0;
    /**发送物品类型：1表示宠物**/
    public static final int            GIVENITEM_TYPE_PET      = 1;
    /** 结婚礼炮ID*/
    public static final int            BLESS                   = 784;
    /**存放等级，胜利，战力排行数据*/
    public static Map<String, Integer> oldLevelMap             = new HashMap<String, Integer>();
    public static Map<String, Integer> oldWinMap               = new HashMap<String, Integer>();
    public static Map<String, Integer> oldFightMap             = new HashMap<String, Integer>();

    /** 任务状态：0表示未触发*/
    public static final byte   TASK_STATUS_UNTRIGGERED	  	 = 0;
    /** 任务状态：1表示进行中*/
    public static final byte   TASK_STATUS_FINISHING  	  	 = 1;
    /** 任务状态：2表示未提交*/
    public static final byte   TASK_STATUS_SUBMIT    	  	 = 2;
    /** 任务状态：3表示已完成*/
    public static final byte   TASK_STATUS_FINISHED   	 	 = 3;
    /** 称号类型-未获得*/
    public static final byte   TITLE_STATUS_NOT_OBTAIN 		 = 1;
    /** 称号类型-已获得*/
    public static final byte   TITLE_STATUS_OBTAIN      	 = 2;
    /** 称号类型-已显示*/
    public static final byte   TITLE_STATUS_SHOW        	 = 3;
    /** 任务类型:0表示主线任务 */
    public static final byte   TASK_TYPE_MAIN   	         = 0;
    /** 任务类型:1表示活跃度任务 */
    public static final byte   TASK_TYPE_ACTIVE         	 = 1;
    /** 任务类型:2表示每日任务 */
    public static final byte   TASK_TYPE_DAY            	 = 2;
    /** 任务子类型:0表示普通每日任务 */
    public static final byte   TASK_SUBTYPE_ORDINARY         = 0;
    /** 每日任务激活数量 */
    public static final byte   TASK_DAY_NUM                  = 15;
    /** 任务类型-单次*/
    public static final String TASK_TYPE_SINGLE         	 = "yc";
    /** 任务类型-循环*/
    public static final String TASK_TYPE_LOOP           	 = "xh";
    /** 任务奖励提升最高级别*/
    public static final int    TASK_TOP_LEVEL                = 4;
    /** 按钮编号-会员*/
    public static final int    BUTTON_ID_VIP            	 = 28;
    /** 按钮编号-充值*/
    public static final int    BUTTON_ID_RECHARGE       	 = 11;
    /** 按钮编号-基金*/
    public static final int    BUTTON_ID_FUND           	 = 27;
    /** 按钮编号-签到*/
    public static final int    BUTTON_ID_REG            	 = 23;
    /** 按钮编号-爱心*/
    public static final int    BUTTON_ID_LOVE           	 = 20;
    /** 按钮编号-活跃*/
    public static final int    BUTTON_ID_ACTIVE         	 = 22;
    /** 按钮编号-转生*/
    public static final int    BUTTON_ID_REBIRTH        	 = 26;
    /** 按钮编号-世界BOSS*/
    public static final int    BUTTON_ID_BOSS           	 = 32;
    /** 按钮编号-弹王*/
    public static final int    BUTTON_ID_KING           	 = 33;
    /** 界面编号-签到*/
    public static final int    INTERFACE_ID_REG         	 = 20;
    /** 界面编号-活跃*/
    public static final int    INTERFACE_ID_ACTIVE      	 = 103;
    /**弹弹新手称号*/
    public static final byte   TITLE_INIT_ID            	 = 43;
    /** 暴击率（1000倍）key */                                                    
    public static final String DEFENSE			     		 = "defense";      
    /** 攻击力key */                                                              
    public static final String ATTACK			      		 = "attack";       
    /** 防御力key */                                                              
    public static final String DEFEND                   	 = "defend";       
    /** 血量key */                                                                
    public static final String HP			        		 = "hp";           
    /** 免伤key */                                                                
    public static final String INJURYFREE                  	 = "injuryFree";   
    /** 破防key */                                                                
    public static final String WRECK_DEFENSE                 = "wreckDefense"; 
    /** 免暴key */                                                                
    public static final String REDUCE_CRIT                   = "reduceCrit";   
    /** 免坑key */                                                                
    public static final String REDUCE_BURY                   = "reduceBury";   
    /** 力量key */                                                                
    public static final String FORCE                    	 = "force";        
    /** 护甲key */                                                                
    public static final String ARMOR                     	 = "armor";        
    /** 敏捷key */                                                                
    public static final String AGILITY                     	 = "agility";      
    /** 体质key */                                                                
    public static final String PHYSIQUE                      = "physique";     
    /** 幸运key */                                                                
    public static final String LUCK                     	 = "luck";      
    /** 熔炼碎片ID */
    public static final int   MELTITEMID                       = 893;
    /** 首个单人副本ID */
    public static final int   STARTSINGLEMAPID                 = 48;
    /** 神秘格子ID */
    public static final int    MysteriousGrid                  =  1047;
    /** 祝福碎片 */
    public static final int    ZFSPID                          =  1048;
    /** 月卡有效天数 */
    public static final int    MonthCardEffectiveDaysNum       =  30;
    /** 过期提醒数 */
    public static final int    RemainingRemindNum       	   =  3;
    /** 普通月卡*/
    public static final int    NormalMonthCardNumber	       =  30001;
    /** 高级月卡*/
    public static final int    SuperMonthCardNumber	      	   =  30002;
    /** 至尊月卡 */
    public static final int    ExtremeMonthCardNumber	       =  30003;
    /** 结婚最低等级限制 */
    public static final int    MarryMinLeveLimit	       	   =  26;
    /** 战斗大厅低级频道最高等级 */
    public static final int    PLAYER_CHANNEL_LOW_LEVEL        =  35;

 
}