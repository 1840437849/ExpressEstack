package com.kaikeba.filter;

import com.kaikeba.util.UserUtil;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/admin/index.html","/admin/views/*","/express/*"})
public class AccessControllerFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    //放行条件，用户目前登陆状态
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //先处理乱码
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("utf-8");
        //2.登陆状态放行（判断session中的用户信息是否为空）
        String userName = UserUtil.getUserName(request.getSession());
        if (userName != null) {
            chain.doFilter(req, resp);
        } else {
            //当用户未登陆的时候，跳转到登陆页面
            response.sendError(404,"权限不足！请检查");
        }

    }
}

