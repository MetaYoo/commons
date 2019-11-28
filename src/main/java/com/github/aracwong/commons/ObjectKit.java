package com.github.aracwong.commons;

/**
 * 描述：
 *
 * @author: zpwang
 * @time: 2019/12/18 20:44
 */
public class ObjectKit {

    public static String toString(Object object) {
        if (null == object) {
            return "";
        }
        return object.toString();
    }
}
