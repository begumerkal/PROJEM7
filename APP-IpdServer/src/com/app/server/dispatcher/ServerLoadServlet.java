package com.app.server.dispatcher;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.app.server.service.LineInfo;
import com.app.server.service.ServerInfo;
import com.app.server.service.ServiceManager;
import com.app.server.util.CryptionUtil;
public class ServerLoadServlet extends HttpServlet {
    private static final String CONTENT_TYPE     = "text/html;charset=utf-8";
    private static final long   serialVersionUID = 1911747458628093909L;

    // /private static final String CONTENT_TYPE = "text/html";
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String udid = req.getParameter("id");
        String channel = req.getParameter("channel");
        String group = req.getParameter("group");
        
        if (null == group || !ServiceManager.getManager().getConfigService().exisGroup("",group)) {
            group = ServiceManager.getManager().getConfiguration().getString("defaultgroup");
        }
        
        Map<String, Map<String, Map<Integer, ServerInfo>>> serverInfoMap = ServiceManager.getManager().getServerListService().getServerInfoMap();
        List<Map<String, Map<Integer, ServerInfo>>> smList = new ArrayList<Map<String, Map<Integer, ServerInfo>>>(serverInfoMap.values());
        List<Area> areaList = new ArrayList<Area>();
        boolean isTestUser = false;
        if (null != udid) {
            udid = new String(CryptionUtil.Decrypt(udid, ServiceManager.getManager().getConfiguration().getString("deckey")), "utf-8");
            if (ServiceManager.getManager().getUdidService().isTestUser(udid)) {
                isTestUser = true;
            }
        }
        for (Map<String, Map<Integer, ServerInfo>> sm : smList) {
            List<ServerInfo> serverInfoList = new ArrayList<ServerInfo>(sm.get(group).values());
            for (ServerInfo serverInfo : serverInfoList) {
                List<LineInfo> lineInfoList = new ArrayList<LineInfo>(serverInfo.getLineMap().values());
                if (lineInfoList.size() > 0) {
                    boolean maintain = true;
                    int currOnline = 0;
                    int dayMaxOnline=0;
                    for (LineInfo lineInfo : lineInfoList) {
                        currOnline += lineInfo.getCurrOnline();
                        dayMaxOnline += lineInfo.getDayMaxOnline();
                        if (!lineInfo.getMaintance()) {
                            maintain = false;
                        }
                    }
                    if (maintain) {
                        continue;
                    }
                    if (!isTestUser && 1 == serverInfo.getConfig().getIstest()) {
                        continue;
                    }
                    Area area = new Area();
                    areaList.add(area);
                    area.setName(serverInfo.getConfig().getName());
                    area.setAreaCode(serverInfo.getConfig().getArea());
                    area.setServiceId(serverInfo.getConfig().getServerId());
                    area.setServerId(serverInfo.getConfig().getServerId());
                    area.setCurrOnline(currOnline);
                    area.setDayMaxOnline(dayMaxOnline);
                }
            }
        }
        Comparator<Area> ascComparator = new AreaComparator();
        Collections.sort(areaList, ascComparator);
        JSONArray jsonArray = JSONArray.fromObject(areaList);
        jsonArray.toString();
        resp.setContentType(CONTENT_TYPE);
        resp.setStatus(200);
        ServletOutputStream out = resp.getOutputStream();
        OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
        StringBuffer sb = new StringBuffer();
        sb.append(jsonArray.toString());
        os.write(sb.toString());
        os.flush();
        os.close();
    }
    public class Area {
        private String name;
        private String areaCode;
        private Integer serviceId;
        private int    currOnline;
        private int    serverId;
        private int    dayMaxOnline;//日最高峰时在线人数

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public Integer getServiceId() {
            return serviceId;
        }

        public void setServiceId(Integer serviceId) {
            this.serviceId = serviceId;
        }

        public int getCurrOnline() {
            return currOnline;
        }

        public void setCurrOnline(int currOnline) {
            this.currOnline = currOnline;
        }

        public int getServerId() {
            return serverId;
        }

        public void setServerId(int serverId) {
            this.serverId = serverId;
        }

		public int getDayMaxOnline() {
			return dayMaxOnline;
		}

		public void setDayMaxOnline(int dayMaxOnline) {
			this.dayMaxOnline = dayMaxOnline;
		}
        
        
    }
    public class AreaComparator implements Comparator<Area> {
        public int compare(Area area1, Area area2) {
            int n1 = parseChineseNumber(chineseNumber(area1.getName()), 1);
            int n2 = parseChineseNumber(chineseNumber(area2.getName()), 1);
            return n1 - n2;
        }

        /**
         * 获取中文数字
         * 
         * @param text
         * @return
         */
        private String chineseNumber(String text) {
            String key = "零一二三四五六七八九十百千万亿";
            char[] datas = text.toCharArray();
            for (char data : datas) {
                if (key.indexOf(data) == -1) text = text.replace(data + "", "");
            }
            return text;
        }

        /**
         * 把中文数字解析为阿拉伯数字(Integer)
         * 
         * @param preNumber
         *            第二大的进位
         * @param chineseNumber
         *            中文数字
         * @return 阿拉伯数字(Integer),如果是无法识别的中文数字则返回-1
         */
        private int parseChineseNumber(String chineseNumber, int preNumber) {
            int ret = 0;
            if (chineseNumber.indexOf("零") == 0) {
                int index = 0;
                int end = chineseNumber.length();
                String prefix = chineseNumber.substring(index + 1, end);
                ret = parseChineseNumber(prefix, 1);
            } else if (chineseNumber.indexOf("亿") != -1) {
                int index = chineseNumber.indexOf("亿");
                int end = chineseNumber.length();
                String prefix = chineseNumber.substring(0, index);
                if (prefix.length() == 0) {
                    prefix = "一";
                }
                String postfix = chineseNumber.substring(index + 1, end);
                ret = parseChineseNumber(prefix, 1) * 100000000 + parseChineseNumber(postfix, 10000000);
            } else if (chineseNumber.indexOf("万") != -1) {
                int index = chineseNumber.indexOf("万");
                int end = chineseNumber.length();
                String prefix = chineseNumber.substring(0, index);
                if (prefix.length() == 0) {
                    prefix = "一";
                }
                String postfix = chineseNumber.substring(index + 1, end);
                ret = parseChineseNumber(prefix, 1) * 10000 + parseChineseNumber(postfix, 1000);
            } else if (chineseNumber.indexOf("千") != -1) {
                int index = chineseNumber.indexOf("千");
                int end = chineseNumber.length();
                String prefix = chineseNumber.substring(0, index);
                if (prefix.length() == 0) {
                    prefix = "一";
                }
                String postfix = chineseNumber.substring(index + 1, end);
                ret = parseChineseNumber(prefix, 1) * 1000 + parseChineseNumber(postfix, 100);
            } else if (chineseNumber.indexOf("百") != -1) {
                int index = chineseNumber.indexOf("百");
                int end = chineseNumber.length();
                String prefix = chineseNumber.substring(0, index);
                if (prefix.length() == 0) {
                    prefix = "一";
                }
                String postfix = chineseNumber.substring(index + 1, end);
                ret = parseChineseNumber(prefix, 1) * 100 + parseChineseNumber(postfix, 10);
            } else if (chineseNumber.indexOf("十") != -1) {
                int index = chineseNumber.indexOf("十");
                int end = chineseNumber.length();
                String prefix = chineseNumber.substring(0, index);
                if (prefix.length() == 0) {
                    prefix = "一";
                }
                String postfix = chineseNumber.substring(index + 1, end);
                ret = parseChineseNumber(prefix, 1) * 10 + parseChineseNumber(postfix, 1);
            } else if (chineseNumber.equals("一")) {
                ret = 1 * preNumber;
            } else if (chineseNumber.equals("二")) {
                ret = 2 * preNumber;
            } else if (chineseNumber.equals("三")) {
                ret = 3 * preNumber;
            } else if (chineseNumber.equals("四")) {
                ret = 4 * preNumber;
            } else if (chineseNumber.equals("五")) {
                ret = 5 * preNumber;
            } else if (chineseNumber.equals("六")) {
                ret = 6 * preNumber;
            } else if (chineseNumber.equals("七")) {
                ret = 7 * preNumber;
            } else if (chineseNumber.equals("八")) {
                ret = 8 * preNumber;
            } else if (chineseNumber.equals("九")) {
                ret = 9 * preNumber;
            } else if (chineseNumber.length() == 0) {
                ret = 0;
            } else {
                ret = -1;
            }
            return ret;
        }
    }
}
