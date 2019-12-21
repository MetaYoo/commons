package com.github.aracwong.commons;

import com.alibaba.fastjson.JSONArray;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author lmm
 * Created by lmm on 2015/11/12.
 */
public class StrKit {

    private static Pattern integerPattern = Pattern.compile("^[-\\+]?[\\d]*$");

    public final static int FORMAT_NUMBER_0  = 1;
    public final static int FORMAT_NUMBER_2  = 2;
    public final static int FORMAT_NUMBER_3  = 3;
    public final static Map<Integer,DecimalFormat>  numberFormat = new HashMap<Integer,DecimalFormat>(){{
        put(FORMAT_NUMBER_0,new DecimalFormat(",###,##0"));
        put(FORMAT_NUMBER_2,new DecimalFormat(",###,##0.00"));
        put(FORMAT_NUMBER_3,new DecimalFormat("#####0.00"));
    }};
    /**
     * 拼接字符串<br />
     * Author:刘明明<br />
     * CreateTime:2015年11月16日17:08:18
     *
     * @param array 侍拼接的数组
     * @param separator  分隔符
     * @return String
     * */
    public static String join(Object[] array, String separator) {
        if (array == null || array.length < 1) {
            return "";
        }
        int arraySize = array.length;
        int bufSize = (arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0].toString().length()) + 1) * arraySize);
        StringBuffer buf = new StringBuffer(bufSize);
        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    /**
     * 替换字符串<br />
     * Author:刘明明<br />
     * CreateTime:2015年11月16日17:08:18
     *
     * @param str 原字符串
     * @param target  要替换的字符
     * @param replaceResult  替换的值
     * @return tempStr 转换结果
     * */
    public static String replaceAll(String str,String target,String replaceResult){
        if(str==null || target==null || replaceResult==null){
            return  str;
        }
        String tempStr ="";
        if(str.contains(target)){
            tempStr = str.replaceAll(target,replaceResult);
        }else{
            tempStr = str;
        }
        return tempStr;
    }

    /**
     * 判断字符串是否为空
     * Author:liangcq
     * CreateTime:2017年4月5日10:21:36
     * @param str 字符串
     * @return
     */
    public static boolean isNull(String str){
        if(str==null || str.isEmpty()){
            return true;
        }else{
            return false;
        }

    }

    /***
     * 将对象转换为安全的字符串对象
     * Author:liangcq
     * CreateTime:2017年4月5日10:21:36
     * @param str  要转换为字符串的对象
     * @return
     */
    public static String safeString(Object str){
        if(str!=null){
            return String.valueOf(str);
        }else {
            return "";
        }
    }

    /***
     * 将对象转换为安全的boolean对象
     * author       朱海强
     * createTime   2017-05-08 10:09:51 528
     * @param str  要转换的对象
     * @return
     */
    public static boolean safeBoolean(Object str){
        if(str != null){
            try {
                return Boolean.valueOf(str.toString());
            } catch (Exception e) {
                return false;
            }
        }else {
            return false;
        }
    }

    /***
     *格式化数字，可以设置前缀和后缀
     * @param number  要格式化的数字
     * @param formatType 格式化类型
     * @param prefix 前缀
     * @param suffix 后缀
     * @return
     */
    public static String  numberFormatToString(String prefix,String suffix,Object number,int formatType){
        if(number==null){
            return "";
        }
        String numberStr;
        if(number instanceof Integer){
            numberStr = numberFormatToString((Integer)number,formatType);
        }else if(number instanceof Long){
            numberStr = numberFormatToString((Long)number,formatType);
        }else if(number instanceof Float){
            numberStr = numberFormatToString((Float)number,formatType);
        }else {
            numberStr = numberFormatToString((Double)number,formatType);
        }
        prefix = prefix==null?"":prefix;
        suffix = suffix==null?"":suffix;
        return prefix+numberStr+suffix;
    }
    /***
     * 数字格式化为字符串
     * @param number  Integer数字
     * @param formatType  格式化类型 1为,###,##0;2为",###,##0.00
     * @return
     */
    public static String  numberFormatToString(Integer number,int formatType){
        if(number==null){
            return "";
        }
        return numberFormat.get(formatType).format(number);
    }

    /***
     * 数字格式化为字符串
     * @param number  Long数字
     * @param formatType  格式化类型 1为,###,##0;2为",###,##0.00
     * @return
     */
    public static String  numberFormatToString(Long number,int formatType){
        if(number==null){
            return "";
        }
        return numberFormat.get(formatType).format(number);
    }
    /***
     * 数字格式化为字符串
     * @param number  Float数字
     * @param formatType  格式化类型 1为,###,##0;2为",###,##0.00
     * @return
     */
    public static String  numberFormatToString(Float number,int formatType){
        if(number==null){
            return "";
        }
        return numberFormat.get(formatType).format(number);
    }

    /***
     * 数字格式化为字符串
     * @param number  Double数字
     * @param formatType  格式化类型 1为,###,##0;2为",###,##0.00
     * @return
     */
    public static String  numberFormatToString(Double number,int formatType){
        if(number==null){
            return "";
        }
        return numberFormat.get(formatType).format(number);
    }

    /**
     * 安全转为long类型
     * author       朱海强
     * createTime   2017-05-08 10:24:22 006
     * @param obj
     * @param defaultValue
     * @return
     */
    public static Long safeToLong(Object obj, Long defaultValue){
        if(obj == null){ return defaultValue; }
        if(obj.toString().isEmpty()) { return defaultValue; }
        try {
            return Long.valueOf(obj.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 安全转为double类型
     * author       朱海强
     * createTime   2017-05-08 10:24:19 086
     * @param obj
     * @return
     */
    public static Double safeToDouble(Object obj){
        return safeToDouble(obj, 0D);
    }

    /**
     * 安全转为double类型
     * author       朱海强
     * createTime   2017-05-08 10:24:19 086
     * @param obj
     * @param defaultValue
     * @return
     */
    public static Double safeToDouble(Object obj, Double defaultValue){
        if(obj == null){ return defaultValue; }
        if(obj.toString().isEmpty()) { return defaultValue; }
        try {
            return Double.valueOf(obj.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 安全转为long类型
     * @param obj
     * @return
     */
    public static Long safeToLong(Object obj){
        return safeToLong(obj, 0L);
    }

    /**
     * 安全转为int类型
     * @param obj
     * @param defaultValue
     * @return
     */
    public static Integer safeToInteger(Object obj, Integer defaultValue){
        if(obj == null){ return defaultValue; }
        if(obj.toString().isEmpty()) { return defaultValue; }
        try {
            return Integer.valueOf(obj.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 安全转为int类型
     * @param obj
     * @return
     */
    public static Integer safeToInteger(Object obj){
        return safeToInteger(obj, 0);
    }

    /**
     * object 转换成bigDecimal
     * @param s
     * Author:邵东伟
     * CreateTime:2016年5月24日11:42:47
     */
    public static BigDecimal safeBigDecimal (Object s){
        if (s == null || "".equals(s)) {
            return new BigDecimal(0);
        }
        try {
            return  new  BigDecimal(s.toString().trim());
        } catch (NumberFormatException num) {
            return new BigDecimal(0);
        }
    }

    /**
     * Object转换成HashSetIntList
     * Author:刘明明
     * CreateTime:2015年11月17日19:22:47
     *
     * @param s         侍转换成HashSetIntList类型的String
     * @param separator 分隔字符串
     * @return HashSet<Integer> 转换后的值
     */
    public static HashSet<Integer> toHashSetIntList(Object s, String separator) {
        if (s == null) return new HashSet<Integer>();
        String[] _list = s.toString().split(separator);
        HashSet<Integer> list = new HashSet<Integer>();
        for (String item : _list) {
            list.add(safeToInteger(item));
        }
        return list;
    }

    /**
     * Object转换成ArrayListIntList
     * Author:刘明明
     * CreateTime:2015年11月30日16:02:40
     *
     * @param s         侍转换成ArrayListIntList类型的String
     * @param separator 分隔字符串
     * @return ArrayList<Integer> 转换后的值
     */
    public static ArrayList<Integer> toArrayListIntList(Object s, String separator) {
        if (s == null) return new ArrayList<Integer>();
        String[] _list = s.toString().split(separator);
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (String item : _list) {
            list.add(safeToInteger(item));
        }
        return list;
    }

    /**
     * Object转换成HashSetLongList
     * Author:刘明明
     * CreateTime:2015年11月17日19:22:47
     *
     * @param s 侍转换成HashSetIntList类型的String
     * @param separator 分隔字符串
     * @return HashSet<Integer> 转换后的值
     * */
    public static HashSet<Long> toHashSetLongList(Object s, String separator){
        if (s == null) return new HashSet<Long>();
        String[] _list = s.toString().split(separator);
        HashSet<Long> list = new HashSet<Long>();
        for(String item : _list){
            if(item == null || item.isEmpty()){
                continue;
            }
            list.add(safeToLong(item.trim()));
        }
        return list;
    }

    /**
     * Object转换成HashSetStringList
     * Author:刘明明
     * CreateTime:2015年11月17日19:22:47
     *
     * @param s 侍转换成HashSetStringList类型的Object
     * @param separator 分隔字符串
     * @return HashSet<String> 转换后的值
     * */
    public static HashSet<String> toHashSetStringList(Object s, String separator){
        if (s == null || "".equals(s.toString())) return new HashSet<String>();
        String[] _list = s.toString().split(separator);
        HashSet<String> list = new HashSet<String>();
        for(String item : _list){
            list.add(item);
        }
        return list;
    }

    /**
     * Object转换成JSONArray
     * Author:刘明明
     * CreateTime:2016年3月15日10:45:40
     * @param s 侍转换成JSONArray类型的String
     * @param separator 分隔字符串
     * @return JSONArray 转换后的值
     * */
    public static JSONArray toJSONArray(Object s, String separator){
        if (s == null) return new JSONArray();
        String[] _list = s.toString().split(separator);
        JSONArray list = new JSONArray();
        for(String item : _list){
            list.add(item);
        }
        return list;
    }

    /**
     * long类型数据转为大小字符串(B/KB/MB修饰)
     * author       朱海强
     * createTime   2017-06-06 17:46:10 507
     * @param size
     * @return
     */
    public static String sizeFormatStr(long size){
        if(size == 0) { return "0B"; }
        double sizeKb = Double.valueOf(size) / 1024;
        if(sizeKb < 0.01) { return new BigDecimal(size).setScale(2, BigDecimal.ROUND_HALF_UP) + "B"; }
        double sizeMb = sizeKb / 1024;
        if(sizeMb < 0.01) { return new BigDecimal(sizeKb).setScale(2, BigDecimal.ROUND_HALF_UP) + "KB"; }
        return new BigDecimal(sizeMb).setScale(2, BigDecimal.ROUND_HALF_UP) + "MB";
    }

    public static boolean isInteger(String str) {
        return integerPattern.matcher(str).matches();
    }

    /*public static void main(String[] args) {
        System.out.println(sizeFormatStr(1313540));
    }*/

}
