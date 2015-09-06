package com.tangzq.common;

import com.google.common.collect.Maps;
import com.tangzq.utils.PropertiesLoader;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * ȫ�ֹ�����
 */
public class Global {

    /**
     * �����ļ����ض���
     */
    private static PropertiesLoader loader = new PropertiesLoader("config.properties");

    /**
     * ����ȫ������ֵ
     */
    private static Map<String, String> map = Maps.newHashMap();


    /**
     * ��ȡ����
     * @see ${fns:getConfig('adminPath')}
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null){
            value = loader.getProperty(key);
            map.put(key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }

}
