package com.tangyongdong.sale.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.tangyongdong.sale.base.exception.BusinessException;
import com.tangyongdong.sale.gateway.config.BusinessErrorCode;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tangyongdong
 * @create 2018-05-04 10:21
 */
@Slf4j
public class UserFilter extends ZuulFilter{
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Object accessToken = request.getParameter("token");
        if(accessToken == null){
            log.info("token is empty");
            throw new BusinessException(BusinessErrorCode.USER_NO_LOGIN);
            //TODO 验证accessToken是否有效
        }
        return null;
    }
}
