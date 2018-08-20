package com.example.springcloudzuul.filter;

import com.alibaba.fastjson.JSON;
import com.example.springcloudzuul.feign.AuthService;
import com.example.springcloudzuul.response.ResultMessage;
import com.example.springcloudzuul.zuulenum.FilterEnum;
import com.example.springcloudzuul.zuulenum.HttpParameterEnum;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * token filter
 */
public class TokenFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Autowired
    private AuthService authService;

    /**
     * 定义filter的类型，有pre、route、post、error四种
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 定义filter的顺序，数字越小表示顺序越高，越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 表示是否需要执行该filter，true表示执行，false表示不执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * filter需要执行的具体操作
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();
        logger.info("TokenFilter {},URL:{}，Params:{}",httpServletRequest.getMethod(),httpServletRequest.getRequestURL().toString(),httpServletRequest.getQueryString());

        //获取uri
        String uri = httpServletRequest.getRequestURI();
        logger.info("TokenFilter {},URI:{}",httpServletRequest.getMethod(),uri);

        //如果是auth/*的请求则进行路由
        if(uri.startsWith(FilterEnum.AUTH_URL.getValue())){

            requestContext.setSendZuulResponse(true);
            requestContext.setResponseStatusCode(HttpStatus.OK.value());
            return null;
        }

        //获取参数中的token
        String token = httpServletRequest.getParameter(HttpParameterEnum.TOKEN.getValue());
        logger.info("TokenFilter {},Token:{}",httpServletRequest.getMethod(),token);

        //如果token为空，则不进行路由，返回的data中含有获取token的地址
        if(StringUtils.isBlank(token)){

            getTokenPrepare(httpServletRequest,requestContext,"token is Empty");
            return null;

        }

        //验证token合法性
        Boolean isTokenRightful = authService.checkToken(token);
        if(isTokenRightful){
            //合法，进行路由
            requestContext.setSendZuulResponse(true);
            requestContext.setResponseStatusCode(HttpStatus.OK.value());
            return null;

        }else{
            //不合法，则重新获取token
            getTokenPrepare(httpServletRequest,requestContext,"token is not rightful");
            return null;
        }


    }

    private void getTokenPrepare(HttpServletRequest httpServletRequest,RequestContext requestContext,String message){
        String authUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + httpServletRequest.getServerPort() + FilterEnum.GET_TOKEN.getValue();

        Map<String,Object> data = new HashMap<String,Object>(16){
            {
                put("url",authUrl);
            }
        };
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        ResultMessage resultMessage = new ResultMessage(HttpStatus.METHOD_NOT_ALLOWED.value(),message,new Date(System.currentTimeMillis()),data);
        requestContext.setResponseBody(JSON.toJSONString(resultMessage));
    }
}
