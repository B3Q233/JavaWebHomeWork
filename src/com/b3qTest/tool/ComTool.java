package com.b3qTest.tool;

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
}
