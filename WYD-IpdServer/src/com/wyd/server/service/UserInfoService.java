package com.wyd.server.service;
import java.util.Map;
import com.wyd.server.util.CryptionUtil;
/**
 * @author Administrator
 */
public class UserInfoService {
    /**
     * 将服务器信息转换为 字符串
     * 
     * @param serverInfo
     * @param version
     * @return
     */
    public String infoToString(ServerInfo serverInfo, String versionString, String channel) {
        // System.out.println("----"+serverInfo.getConfig().getMachine());
        StringBuffer sb = new StringBuffer();
        LineInfo lineInfo = null;
        Map<Integer, LineInfo> lineMap = serverInfo.getLineMap();
        for (LineInfo info : lineMap.values()) {
            if (info.getMaintance()) {
                continue;
            }
            if (info.getVersion().equals(versionString)) {
                if (null == lineInfo || lineInfo.getCurrOnline() > info.getCurrOnline()) {
                    lineInfo = info;
                }
            } else {
                if (null == lineInfo || lineInfo.getCurrOnline() > info.getCurrOnline()) {
                    lineInfo = info;
                }
            }
        }
        if (null != lineInfo) {
            sb.append("1,");
            sb.append(lineInfo.getAddress());
            sb.append(",");    
            sb.append(versionString);
            if (null == channel) {
                sb.append(",");
                sb.append(lineInfo.getUpdateurl());
                sb.append(",");
                sb.append(lineInfo.getAppraisal());
            } else {
                String channelu = ServiceManager.getManager().getConfigService().getRnuConfig().getString(channel + "update");
                String channelr = ServiceManager.getManager().getConfigService().getRnuConfig().getString(channel + "review");
                sb.append(",");
                if (null == channelu) {
                    sb.append(lineInfo.getUpdateurl());
                } else {
                    sb.append(channelu);
                }
                if (null == channelr) {
                    sb.append(lineInfo.getAppraisal());
                } else {
                    sb.append(channelr);
                }
            }
            sb.append(",");
            sb.append(lineInfo.getArea());
            sb.append(",");
            sb.append(serverInfo.getConfig().getOpenudid());
            for (String value : ServiceManager.getManager().getConfigService().getConfigList()) {
                sb.append(",");
                sb.append(value);
            }
        } else {
            sb.append("0,");
            sb.append(serverInfo.getConfig().getBulletin());
        }
        return sb.toString();
    }

    /**
     * 将服务器信息转换为 字符串
     * 
     * @param serverInfo
     * @param version
     * @return
     */
    public String infoToString(ServerInfo serverInfo, String versionString, String udid, String channel) {
        StringBuffer sb = new StringBuffer();
        LineInfo lineInfo = null;
        boolean isTestUser = false;
        if (serverInfo.getConfig().getIstest() == 1) {
            try {
                if (null != udid) {
                    udid = new String(CryptionUtil.Decrypt(udid, ServiceManager.getManager().getConfiguration().getString("deckey")), "utf-8");
                    if (ServiceManager.getManager().getUdidService().isTestUser(udid)) {
                        isTestUser = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //分配线 （用户量少的）
        if (isTestUser || 0 == serverInfo.getConfig().getIstest()) {
            Map<Integer, LineInfo> lineMap = serverInfo.getLineMap();
            for (LineInfo info : lineMap.values()) {
                if (info.getMaintance()) {
                    continue;
                }
                if (info.getVersion().equals(versionString)) {
                    if (lineInfo == null || info.getCurrOnline() < lineInfo.getCurrOnline() ) 
                        lineInfo = info;
                } else {
                    if (lineInfo == null ||  info.getCurrOnline() <lineInfo.getCurrOnline() ) 
                        lineInfo = info;
                }
            }
        }
        if (null != lineInfo) {
            sb.append("1,");
            sb.append(lineInfo.getAddress());
            sb.append(","); 
            sb.append(versionString);
            if (null == channel) {
                sb.append(",");
                sb.append(lineInfo.getUpdateurl());
                sb.append(",");
                sb.append(lineInfo.getAppraisal());
            } else {
                String channelu = ServiceManager.getManager().getConfigService().getRnuConfig().getString(channel + "update");
                String channelr = ServiceManager.getManager().getConfigService().getRnuConfig().getString(channel + "review");
                if (null == channelu) {
                    sb.append(",");
                    sb.append(lineInfo.getUpdateurl());
                } else {
                    sb.append(",");
                    sb.append(channelu);
                }
                if (null == channelr) {
                    sb.append(",");
                    sb.append(lineInfo.getAppraisal());
                } else {
                    sb.append(",");
                    sb.append(channelr);
                }
            }
            sb.append(",");
            sb.append(lineInfo.getArea());
            sb.append(",");
            sb.append(serverInfo.getConfig().getOpenudid());
            for (String value : ServiceManager.getManager().getConfigService().getConfigList()) {
                sb.append(",");
                sb.append(value);
            }
        } else {
            sb.append("0,");
            sb.append(serverInfo.getConfig().getBulletin());
        }
        return sb.toString();
    }
}
