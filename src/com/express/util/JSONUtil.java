package com.express.util;

import com.google.gson.Gson;

public class JSONUtil {
    //当我们有一个对象的时候就可以调用JSONUtil 的toJSON方法 来吧一个对象更快的变成json对象
    private static Gson g=new Gson();
    //json转换工具
    public static String toJSON(Object obj){
        return g.toJson(obj);
    }
}
