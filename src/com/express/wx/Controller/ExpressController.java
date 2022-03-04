package com.express.wx.Controller;

import com.express.bean.*;
import com.express.mvc.ResponseBody;
import com.express.mvc.ResponseView;
import com.express.service.ExpressService;
import com.express.util.DataFormatUtil;
import com.express.util.JSONUtil;
import com.express.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ExpressController {
    //查询用户所有的快递
    @ResponseBody("/wx/findExpressByUserPhone.do")
    public String findByUserPhone(HttpServletRequest req, HttpServletResponse resp) {
        //拿到用户对象
        Object user = UserUtil.getWxUser(req.getSession());
        String userPhone ="";
        if (user instanceof User) {
            User wxUser = (User) user;
            //获取手机号
            userPhone = wxUser.getUserPhone();
        } else if (user instanceof Courier) {
            Courier wxUser= (Courier) user;
            userPhone = wxUser.getUserPhone();
        }
        //拿到用户信息
        List<Express> list = ExpressService.findByUserPhone(userPhone);
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        for (Express e : list) {
            //调用时间格式化工具类将数据转为String类型并将时间格式为 yyyy-MM-dd HH:mm:ss
            String inTime = DataFormatUtil.format(e.getInTime());
            //如果出库时间为空的话，代表未出库，否则返回出库
            String outTime = e.getOutTime() == null ? "未出库" : DataFormatUtil.format(e.getOutTime());
            String status = e.getStatus() == 0 ? "待取件" : "已取件";
            String code = e.getCode() == null ? "已取件" : e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(), e.getNumber(), e.getUsername(), e.getUserPhone(), e.getCompany(), code, inTime, outTime, status, e.getSysPhone());
            //这样就把上面的list集合转换为了下面的list2，里面的数据都是字符串类型，正好反映了本项目数据类型统一使用字符串的宗旨
            list2.add(e2);
        }
        Message msg = new Message();
        if (list2.size() == 0) {
            msg.setStatus(-1);
        } else {
            msg.setStatus(0);
            // 只保留未取件的快递，并按照入库时间排序
            Stream<BootStrapTableExpress> status0Express = list2.stream().filter(express -> {
                //其实就是对集合中所有的快递对象进行遍历，当status==0时，就在流中保留下来
                if (express.getStatus().equals("待取件")) {
                    //表示未取件的
                    return true;
                } else {
                    return false;
                }
            }).sorted((o1, o2) -> {
                long o1Time = DataFormatUtil.toTime(o1.getInTime());
                long o2Time = DataFormatUtil.toTime(o2.getInTime());
                return (int) (o1Time - o2Time);
            });    //对流中保留的对象进行排序
            Stream<BootStrapTableExpress> status1Express = list2.stream().filter(express -> {
                //其实就是对集合中所有的快递对象进行遍历，当status==1时，就在流中保留下来
                if (express.getStatus().equals("已取件")) {
                    //表示已取件的
                    return true;
                } else {
                    return false;
                }
            }).sorted((o1, o2) -> {
                long o1Time = DataFormatUtil.toTime(o1.getInTime());
                long o2Time = DataFormatUtil.toTime(o2.getInTime());
                return (int) (o1Time - o2Time);
            });
            //因为流中的数据是不直接操作外部集合的，所以这里直接new
            Object[] s0 = status0Express.toArray();
            Object[] s1 = status1Express.toArray();
            Map map = new HashMap();
            map.put("status0",s0);
            map.put("status1",s1);
            msg.setData(map);
        }
        //因为只要数据需要传过去，不必直接将整个msg传过去
        String json = JSONUtil.toJSON(msg.getData());
        return json;
    }

    @ResponseBody("/wx/userExpressList.do")
    public String expressList(HttpServletRequest req,HttpServletResponse resp){
        //当用户访问到的时候，就去获取用户的手机号码
        String userPhone = req.getParameter("userPhone");
        //此时查询到的集合就是未取件的内容
        List<Express> list = ExpressService.findByUserPhoneAndStatus(userPhone, 0);
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        for (Express e : list) {
            //调用时间格式化工具类将数据转为String类型并将时间格式为 yyyy-MM-dd HH:mm:ss
            String inTime = DataFormatUtil.format(e.getInTime());
            //如果出库时间为空的话，代表未出库，否则返回出库
            String outTime = e.getOutTime() == null ? "未出库" : DataFormatUtil.format(e.getOutTime());
            String status = e.getStatus() == 0 ? "待取件" : "已取件";
            String code = e.getCode() == null ? "已取件" : e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(), e.getNumber(), e.getUsername(), e.getUserPhone(), e.getCompany(), code, inTime, outTime, status, e.getSysPhone());
            //这样就把上面的list集合转换为了下面的list2，里面的数据都是字符串类型，正好反映了本项目数据类型统一使用字符串的宗旨
            list2.add(e2);
        }
        Message msg = new Message();
        if (list2.size() == 0) {
            //说明没查到内容
            msg.setStatus(-1);
            msg.setResult("结果未查询到快递");
        } else {
            msg.setStatus(0);
            msg.setResult("查询成功！");
            msg.setData(list2);
        }
        return JSONUtil.toJSON(msg);
    }
}
