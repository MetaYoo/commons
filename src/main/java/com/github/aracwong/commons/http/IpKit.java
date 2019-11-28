package com.github.aracwong.commons.http;

import com.github.aracwong.commons.ObjectKit;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lmm on 2015/11/9.
 */
public class IpKit {

    private static final Pattern urlIpPattern = Pattern.compile("((http|ftp|https)://)(([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})*(/[a-zA-Z0-9\\&%_\\./-~-]*)*");


    /**
     * 获取用户IP地址<br />
     *
     * @param request request
     */
    public static String getIPAddress(HttpServletRequest request) {
        String clientIP = ObjectKit.toString(request.getHeader("X-Forwarded-For"));
        if (clientIP.length() > 0) {
            return clientIP;
        }
        clientIP = ObjectKit.toString(request.getHeader("X-Real-IP"));
        if (clientIP.length() > 0) {
            return clientIP;
        }
        return request.getRemoteAddr();
    }

    /**
     * 将字符串型ip转成Long类型ip
     *
     * @param strIp ip字符串形式
     * @return Long ip的Long表现形式
     */
    public static Long ip2Long(String strIp) {
        String[] ipList = strIp.split("\\.");
        if (ipList.length != 4) {
            return new Long(0);
        }
        Long n = Long.parseLong(ipList[3]) & 0xFF;
        n |= ((Long.parseLong(ipList[2]) << 8) & 0xFF00);
        n |= ((Long.parseLong(ipList[1]) << 16) & 0xFF0000);
        n |= ((Long.parseLong(ipList[0]) << 24) & 0xFF000000);
        return n;
    }

    /**
     * 将int型ip转成String型ip
     *
     * @param intIp ip的Long形式
     * @return string ip字符串形式
     */
    public static String long2Ip(Long intIp) {
        byte[] byteList = new byte[4];
        byteList[3] = (byte) (0xff & intIp);
        byteList[2] = (byte) ((0xff00 & intIp) >> 8);
        byteList[1] = (byte) ((0xff0000 & intIp) >> 16);
        byteList[0] = (byte) ((0xff000000 & intIp) >> 24);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(byteList[i] & 0xFF);
            if (i < 3) {
                sb.append(".");
            }
        }
        return sb.toString();
    }

    /**
     * 判断是否是IP<br />
     * Author:刘明明<br />
     * CreateTime:2015年12月17日20:33:28
     *
     * @param str 网址或IP字符串
     */
    public static boolean isIp(String str) {
        if (str == null || str.length() < 1) {
            return false;
        }
        int index = str.indexOf("://");
        if (index == -1 || index == str.length() - 1) {
            return false;
        }
        String ip = ObjectUtils.defaultIfNull(str.substring(index + 3, index + 4), "0");
        return Integer.parseInt(ip) > 0;
    }

    /**
     * 判断 url中是否包含 ip
     *
     * @param url
     * @return
     */
    public static boolean isUrlContainsIp(String url) {
        Matcher matcher = urlIpPattern.matcher(url);
        return matcher.matches();
    }


}
