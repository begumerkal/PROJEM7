package com.wyd.relay.server.http;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class HttpUtils {
    // /private static final String URL_PARAM_CONNECT_FLAG = "&";
    private static Logger log = Logger.getLogger(HttpUtils.class);

    public static String URLGet(String strUrl, Map<String,Object> map) throws IOException {
        String strtTotalURL = "";
        StringBuffer result = new StringBuffer();
        if (strtTotalURL.indexOf("?") == -1)
            strtTotalURL = strUrl + "?" + getUrl(map);
        else {
            strtTotalURL = strUrl + "&" + getUrl(map);
        }
        log.debug("strtTotalURL:" + strtTotalURL);
        URL url = new URL(strtTotalURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        HttpURLConnection.setFollowRedirects(true);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        while (true) {
            String line = in.readLine();
            if (line == null) {
                break;
            }
            result.append(line);
        }
        in.close();
        con.disconnect();
        return result.toString();
    }

    public static String URLPost(String strUrl, Map<String,Object> map) throws IOException {
        String content = "";
        content = getUrl(map);
        String totalURL = null;
        if (strUrl.indexOf("?") == -1)
            totalURL = strUrl + "?" + content;
        else {
            totalURL = strUrl + "&" + content;
        }
        log.debug("requestURL = " + totalURL);
        URL url = new URL(strUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setAllowUserInteraction(false);
        con.setUseCaches(false);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
        BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
        bout.write(content);
        bout.flush();
        bout.close();
        BufferedReader bin = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuffer result = new StringBuffer();
        while (true) {
            String line = bin.readLine();
            if (line == null) {
                break;
            }
            result.append(line);
        }
        con.disconnect();
        return result.toString();
    }

    /**
     * 使用UTF-8编码格式。
     * 
     * @param map
     * @return
     */
    public static String getUrl(Map<String, Object> map) {
        if ((null == map) || (map.keySet().size() == 0)) {
            return "";
        }
        StringBuffer url = new StringBuffer();
        Set<String> keys = map.keySet();
        for (Iterator<String> i = keys.iterator(); i.hasNext();) {
            String key = i.next();
            if (map.containsKey(key)) {
                Object val = map.get(key);
                String str = (val != null) ? val.toString() : "";
                try {
                    str = URLEncoder.encode(str, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                url.append(key).append("=").append(str).append("&");
            }
        }
        String strURL = "";
        strURL = url.toString();
        if ("&".equals("" + strURL.charAt(strURL.length() - 1))) {
            strURL = strURL.substring(0, strURL.length() - 1);
        }
        return strURL;
    }
}
