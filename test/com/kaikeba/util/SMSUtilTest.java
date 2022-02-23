package com.kaikeba.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class SMSUtilTest {

    @Test
    public void send() {
        boolean send = SMSUtil.send("15350210273", "123456");
        System.out.println(send);
    }
}