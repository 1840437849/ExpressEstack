package com.express.wx.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.express.mvc.ResponseView;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.security.MessageDigest;


/**
 * @技术QQ群: 可登录官网https://www.kdniao.com/右侧查看技术群号
 * @see: https://kdniao.com/api-track
 * @copyright: 深圳市快金数据技术服务有限公司
 * ID和Key请到官网申请：https://kdniao.com/reg
 * <p>
 * <p>
 * <p>
 * 即时查询接口
 * 此接口用于向快递公司实时查询物流轨迹信息。该功能支持情况需查看技术文档。
 * 正式地址：https://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx
 * <p>
 * <p>
 * 系统级参数
 * RequestData	   String	R	请求内容为JSON格式 详情可参考接口技术文档：https://www.kdniao.com/documents
 * EBusinessID	   String	R	用户ID
 * RequestType	   String	R	请求接口指令
 * DataSign	       String	R	数据内容签名，加密方法为：把(请求内容(未编码)+ApiKey)进行MD5加密--32位小写，然后Base64编码，最后进行URL(utf-8)编码
 * DataType	       String	R	DataType=2，请求、返回数据类型均为JSON格式
 * <p>
 * <p>
 * 应用级参数
 * R-必填（Required），O-可选（Optional），C-报文中该参数在一定条件下可选（Conditional）
 * OrderCode	  String(30)	O	订单编号
 * ShipperCode	  String(10)	R	快递公司编码  详细编码参考《快递鸟接口支持快递公司编码.xlsx》 https://www.kdniao.com/documents
 * LogisticCode	  String(30)	R	快递单号
 * CustomerName	  String(50)	C	ShipperCode为SF时必填，对应寄件人/收件人手机号后四位；ShipperCode为其他快递时，可不填或保留字段，不可传值
 * <p>
 * 请求示例
 * ZTO请求示例：
 * {
 * "OrderCode": "",
 * "ShipperCode": "ZTO",
 * "LogisticCode": "638650888018",
 * }
 * <p>
 * JD请求示例：
 * {
 * "OrderCode": "",
 * "CustomerName": "",
 * "ShipperCode": "JD",
 * "LogisticCode": "JDVA00003618100",
 * }
 * <p>
 * SF请求示例：
 * {
 * "OrderCode": "",
 * "CustomerName": "1234",
 * "ShipperCode": "SF",
 * "LogisticCode": "SF00003618100",
 * }
 */

public class KdApiSearchDemo {

    //用户ID，快递鸟提供，注意保管，不要泄漏
    private String EBusinessID = "1753753";//即用户ID，登录快递鸟官网会员中心获取 https://www.kdniao.com/UserCenter/v4/UserHome.aspx
    //API key，快递鸟提供，注意保管，不要泄漏
    private String ApiKey = "b6d8fe56-489d-4a4b-89af-f3d21ab6fc66";//即API key，登录快递鸟官网会员中心获取 https://www.kdniao.com/UserCenter/v4/UserHome.aspx
    //请求url, 正式环境地址
    private String ReqURL = "https://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";




/*    public static void main(String[] args) {
        try {
            KdApiSearchDemo api = new KdApiSearchDemo();
            System.out.println(api.orderOnlineByJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    //即时查询接口
    public String orderOnlineByJson(String expCode, String expNo) throws Exception {
        // 组装应用级参数
        String RequestData = "{\'OrderCode\':\'\',\'ShipperCode\':\'" + expCode + "\',\'LogisticCode\':\'" + expNo + "\'}";

        // 组装系统级参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(RequestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "1002");//免费即时查询接口指令1002/在途监控即时查询接口指令8001/地图版即时查询接口指令8003
        String dataSign = encrypt(RequestData, ApiKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");
        // 以form表单形式提交post请求，post请求体中包含了应用级参数和系统级参数
        String result = sendPost(ReqURL, params);

        //根据公司业务处理返回的信息......
        return result;
    }


    /**
     * MD5加密
     * str 内容
     * charset 编码方式
     *
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * base64编码
     * str 内容
     * charset 编码方式
     *
     * @throws UnsupportedEncodingException
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException {
        String encoded = Base64.encode(str.getBytes(charset));
        return encoded;
    }

    @SuppressWarnings("unused")
    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException {
        String result = URLEncoder.encode(str, charset);
        return result;
    }

    /**
     * 电商Sign签名生成
     * content 内容
     * keyValue ApiKey
     * charset 编码方式
     *
     * @return DataSign签名
     * @throws UnsupportedEncodingException ,Exception
     */
    @SuppressWarnings("unused")
    private String encrypt(String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception {
        if (keyValue != null) {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * url 发送请求的 URL
     * params 请求的参数集合
     *
     * @return 远程资源的响应结果
     */
    @SuppressWarnings("unused")
    private String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            if (params != null) {
                StringBuilder param = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (param.length() > 0) {
                        param.append("&");
                    }
                    param.append(entry.getKey());
                    param.append("=");
                    param.append(entry.getValue());
                    System.out.println(entry.getKey() + ":" + entry.getValue());
                }
                System.out.println("param:" + param.toString());
                out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
}

