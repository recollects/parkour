package com.alipay.parkour.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月05日 下午9:54
 */
public class SystemUtils extends org.apache.commons.lang3.SystemUtils {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(SystemUtils.class);


    /**
     * 获取机器的hostName
     *
     * @return
     */
    public static String getHostNameNew() {
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();

            String hostName = localHost.getHostName();

            if (StringUtils.isNotEmpty(hostName)) {
                return hostName;
            }

        } catch (UnknownHostException e) {
            logger.error("", e);
        }
        return localHost.getHostAddress();
    }

    /**
     * 获取机器的IP
     * @return
     */
    public static String getIP() {
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("", e);
        }
        return "unknown";
    }

}
