package com.b3qTest.tool;

import com.alibaba.fastjson2.JSONObject;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  工具类，封装一些共用函数
 * @author b3q
 */
public class ComTool {

    /**
     *  获取当前日期，精确到日
     * @return 返回 sqlDate 对象，表示当前日期
     */
    public static Date getNowDate(){
        Date now = new Date(System.currentTimeMillis());
        return now;
    }

    /**
     *  用于生成Ajax返回json
     * @param statusCode 返回状态码
     * @param description 对 json 的描述
     * @return 一个json
     */
    public static JSONObject setRetJson(int statusCode,String description){
        JSONObject reJson = new JSONObject();
        reJson.put("status",statusCode);
        reJson.put("data",description);
        return reJson;
    }

    /**
     *  用于检查域名格式是否正确
     * @param domain 一个String字符串表示域名
     * @return 一个布尔值表示域名是否符合格式，符合返回真，否则返回假
     */
    public static boolean checkDomain(String domain){
        String regex = "http[s]?:\\/\\/(?:[a-zA-Z]|[0-9]|[$-_@.&+]|[!*\\(\\),]|(?:%[0-9a-fA-F][0-9a-fA-F]))+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(domain);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

}
