package com.express.controller;

import com.express.bean.BootStrapTableExpress;
import com.express.bean.Express;
import com.express.bean.Message;
import com.express.bean.ResultData;
import com.express.mvc.ResponseBody;
import com.express.service.ExpressService;
import com.express.util.DataFormatUtil;
import com.express.util.JSONUtil;
import com.express.util.UserUtil;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpressController {
    @ResponseBody("/express/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response) {
        //1.得到数据
        List<Map<String, Integer>> data = ExpressService.console();
        //2.创建消息对象
        Message msg = new Message();
        //3.判断数据中是否有值
        if (data.size() == 0) {
            //3.1如果出错里面没有数据，那么就给状态码为-1
            msg.setStatus(-1);
        } else {
            //3.2如果有数据那么设置消息状态为成功
            msg.setStatus(0);
        }
        //4.将控制台的快递数据传入到消息对象中
        msg.setData(data);
        //5.将消息对象中的数据转换为json类型
        String json = JSONUtil.toJSON(msg);
        //6.将转换好的json数据发往前端
        return json;
    }

    @ResponseBody("/express/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        //1.查询数据起始索引值
        int offset = Integer.parseInt(request.getParameter("offset"));
        //2.获取当前页需要查询的数据量
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        //3.进行分页查询
        List<Express> list = ExpressService.findAll(true, offset, pageNumber);
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
        List<Map<String, Integer>> console = ExpressService.console();
        //获取到总条数
        int total = console.get(0).get("data1_size");
        //4.将集合封装为bootStrap-table识别的格式
        ResultData<BootStrapTableExpress> data = new ResultData<>();
        //封装快递数据
        data.setRows(list2);
        //封装总长度
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        return json;
    }

    @ResponseBody("/express/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response) {
        //接收参数
        String number = request.getParameter("number");
        String company = request.getParameter("company");
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        Express e = new Express(number, username, userPhone, company, UserUtil.getUserPhone(request.getSession()));
        boolean flag = ExpressService.insert(e);
        Message msg = new Message();
        if (flag) {
            //录入成功
            msg.setStatus(0);
            msg.setResult("快递录入成功,请注意查收短信！");
        } else {
            //录入失败
            msg.setStatus(-1);
            msg.setResult("快递录入失败！");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/express/find.do")
    public String find(HttpServletRequest request, HttpServletResponse response) {
        //1.接收参数
        String number = request.getParameter("number");
        Express data = ExpressService.findByNumber(number);
        Message msg = new Message();
        if (data == null) {
            //没找到
            msg.setStatus(-1);
            msg.setResult("该快递不存在，请检查输入的快递单号");
        } else {
            //找到了
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(data);
        }

        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/express/update.do")
    public String update(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String number = request.getParameter("number");
        String company = request.getParameter("company");
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        int status = Integer.parseInt(request.getParameter("status"));
        Express newExpress = new Express();
        newExpress.setNumber(number);
        newExpress.setCompany(company);
        newExpress.setUsername(username);
        newExpress.setUserPhone(userPhone);
        newExpress.setStatus(status);
        boolean flag = ExpressService.update(id, newExpress);
        Message msg = new Message();
        if(flag){
            msg.setStatus(0);
            msg.setResult("修改成功");
        }else{
            msg.setStatus(-1);
            msg.setResult("修改失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/express/delete.do")
    public String delete(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean delete = ExpressService.delete(id);
        Message msg = new Message();
        if (delete) {
            msg.setStatus(0);
            msg.setResult("删除成功！");
        } else {
            msg.setStatus(-1);
            msg.setResult("删除失败，请检查！");
        }
        msg.setData(delete);
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}

