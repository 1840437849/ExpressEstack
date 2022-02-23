package com.kaikeba.bean;

//专门用来发消息的类
public class Message {
    // {status:0,result:"",data:{}}

    //状态码   0:成功 -1：失败
    private int status;
    //消息内容
    private String result;
    //消息所携带的一组数据
    private Object data;    //因为数据可能是任何类型，所以这里用object

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Message(int status) {    //有的时候跟服务器回复的时候是没有数据的，所以这里加入只包含状态码的构造方法
        this.status = status;
    }

    public Message(String result) { //只包含字符串结果的构造方法
        this.result = result;
    }

    public Message(int status, String result) { //有的时候会用到状态码加结果
        this.status = status;
        this.result = result;
    }

    public Message(int status, String result, Object data) {
        this.status = status;
        this.result = result;
        this.data = data;
    }

    public Message() {
    }

}
