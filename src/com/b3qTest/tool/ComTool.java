package com.b3qTest.tool;

import com.alibaba.fastjson2.JSONObject;

import java.sql.Date;

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
     * @param description 对返回的描述
     * @return 一个json
     */
    public static JSONObject setRetJson(int statusCode,String description){
        JSONObject reJson = new JSONObject();
        reJson.put("status",statusCode);
        reJson.put("data",description);
        return reJson;
    }
}
