package com.kaikeba.Listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class AttributeListener implements HttpSessionAttributeListener {
    //属性添加
    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("session存值："+httpSessionBindingEvent.getName()+":"+httpSessionBindingEvent.getValue());
    }
    //属性删除
    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("session移除数据："+httpSessionBindingEvent.getName()+","+httpSessionBindingEvent.getValue());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {

    }
}
