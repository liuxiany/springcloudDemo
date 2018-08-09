package com.example.springcloudzuul.filter;

import com.alibaba.fastjson.JSON;
import com.example.springcloudzuul.response.ResultMessage;
import com.example.springcloudzuul.zuulenum.FilterEnum;
import com.example.springcloudzuul.zuulenum.HttpParameterEnum;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * token filter
 */
public class TokenFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    /**
     * 定义filter的类型，有pre、route、post、error四种
     * @return
     */
    @Override
    public String filterType() {
        return FilterEnum.PRE.getValue();
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

        logger.info("TokenFilter {},URL:{}",httpServletRequest.getMethod(),httpServletRequest.getRequestURL().toString());

        //获取参数中的token
        String token = httpServletRequest.getParameter(HttpParameterEnum.TOKEN.getValue());

        if(StringUtils.isNotBlank(token)){
            //对请求进行路由
            requestContext.setSendZuulResponse(true);
            requestContext.setResponseStatusCode(HttpStatus.OK.value());
            return null;
        }else {
            //不对请求进行路由
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.BAD_REQUEST.value());
            ResultMessage resultMessage = new ResultMessage(HttpStatus.BAD_REQUEST.getReasonPhrase(),"token is Empty",new Date(System.currentTimeMillis()));
            requestContext.setResponseBody(JSON.toJSONString(resultMessage));
            return null;
        }


    }
}
