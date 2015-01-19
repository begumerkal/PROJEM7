package com.wyd.channel.service.impl;

import org.apache.log4j.Logger;

import com.wyd.channel.info.ChannelInfo_CY;
import com.wyd.channel.result.LoginResult;
import com.wyd.channel.utils.Common;
import com.wyd.channel.utils.HexBin;
import com.wyd.channel.utils.HttpClientUtil;

/**
 * 畅游渠道接入基础类
 *
 */
public class Access_CY {
    //畅游渠道号
    public static final int     CHANNEl_CY  = 1107;
    private static Logger log = Logger.getLogger(Access_CY.class);
    /**
     * 获取用户登录结果
     *
     * @param channelInfo   渠道信息
     * @return              用户登录结果
     */
    public LoginResult getUserLoginResult(ChannelInfo_CY channelInfo) {
        LoginResult channelLoginResult = new LoginResult();
        try {
            String[] parameters=channelInfo.getParameter();
            String openId=parameters[0];
            String token=parameters[1];
            String appkey=channelInfo.getAppkey();
            String sign_content = openId+"|"+token+"|"+appkey;
            String sign=HexBin.HashToMD5Hex(sign_content);
            sign=sign.toLowerCase();
            String url = "http://unionlogin.123cw.cn/verifyToken?openid="+openId+"&token="+token+"&sign="+sign;
            System.out.println("url: "+sign_content+" "+url);
            String result=HttpClientUtil.GetData(url);
            //equalsIgnoreCase：客户端传递过来的sign和服务端自行加密的存字母大小写不一致情况。
            if("success".equalsIgnoreCase(result)){
                channelLoginResult.setCode(Common.STATUS_SUCCESS);
                channelLoginResult.setMessage(Common.STATUS_SUCCESS);
            }else{
            	log.error("畅游登陆验证返回失败！");
                channelLoginResult.setCode(Common.STATUS_FAIL);
                channelLoginResult.setMessage("畅游登陆验证返回失败！"+result);
            }
        } catch (Exception e) {
        	log.error("畅游登陆发生异常 ："+e.getMessage());
            channelLoginResult.setCode(Common.STATUS_FAIL);
            channelLoginResult.setMessage(e.getMessage());
        }
        return channelLoginResult;
    }
public static void main(String[] args) {
    	
    	String sign=HexBin.HashToMD5Hex("1402455175598516902223250|1402478997884｜TGbKnguORxc56ND0");
        sign=sign.toLowerCase();
        System.out.println(sign);
	}

}
