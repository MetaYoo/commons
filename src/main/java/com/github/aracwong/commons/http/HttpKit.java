package com.github.aracwong.commons.http;

import com.github.aracwong.commons.ObjectKit;
import com.github.aracwong.commons.SymbolKit;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * desc:
 *
 * @author zpwang
 * @date 2019/11/19 11:26
 * @since 1.0.0
 */
public class HttpKit {

    public static String getUrl(String url, Map<String, Object> parameters) {
        if (parameters != null && parameters.size() > 0) {
            if (url.contains(SymbolKit.QUESTION_MARK)) {
                url = url + SymbolKit.AND + buildQuery(parameters);
            } else {
                url = url + SymbolKit.QUESTION_MARK + buildQuery(parameters);
            }
        }
        return url;
    }

    public static String buildQuery(Map<String, Object> objs) {
        StringBuilder postData = new StringBuilder();
        Boolean hasParam = false;

        for (String strKey : objs.keySet()) {
            // 忽略参数名或参数值为空的参数
            if (StringUtils.isNotEmpty(strKey) && StringUtils.isNotEmpty(ObjectKit.toString(objs.get(strKey)))) {
                if (hasParam) {
                    postData.append(SymbolKit.AND);
                }
                postData.append(strKey);
                postData.append(SymbolKit.EQUAL);
                postData.append(objs.get(strKey));
                hasParam = true;
            }
        }
        return postData.toString();
    }

    /**
     * 获取请求参数，先从 header 取，取不到则从 request 对象里取
     * @param request
     * @param param
     * @return
     */
    public static String getParam(HttpServletRequest request, String param) {
        String value = request.getHeader(param);
        if (StringUtils.isEmpty(value)) {
            return request.getParameter(param);
        }
        return value;
    }
}
