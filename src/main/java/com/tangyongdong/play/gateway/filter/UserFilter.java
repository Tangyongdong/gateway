package com.tangyongdong.play.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.tangyongdong.base.common.response.CommonResponse;
import com.tangyongdong.play.gateway.config.GatewayConstant;
import com.tangyongdong.play.user.api.UserApi;
import com.tangyongdong.play.user.request.AccessTokenAuthRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tangyongdong
 * @create 2018-05-04 10:21
 */
@Slf4j
@Component
public class UserFilter extends ZuulFilter {

    @Autowired
    private UserApi userApi;

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
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        if(GatewayConstant.LOGIN_URI.equalsIgnoreCase(request.getRequestURI())){
            return false;
        }
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String userToken =request.getHeader("userToken");
        String accessToken = request.getHeader("accessToken");
        if (accessToken == null || userToken == null) {
            log.info("token is empty,userToken:{},accessToken:{}",userToken,accessToken);
            ctx.setResponseStatusCode(101);
            ctx.setSendZuulResponse(false);
            ctx.setResponseBody("用户认证校验参数错误");
        }
        AccessTokenAuthRequest accessTokenAuthRequest = AccessTokenAuthRequest.builder().userToken(userToken).accessToken(accessToken).build();
        log.info("accessTokenAuthRequest request : {}",JSON.toJSONString(accessTokenAuthRequest));
        CommonResponse<Boolean> response = userApi.accessTokenAuth(accessTokenAuthRequest);
        if(!response.isSuccess() || !response.getData()){
            ctx.setResponseStatusCode(102);
            ctx.setSendZuulResponse(false);
            ctx.setResponseBody("用户认证校验未通过");
        }
        return null;
    }
}
