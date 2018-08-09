package com.example.springcloudzuul.fallback;

import com.alibaba.fastjson.JSON;
import com.example.springcloudzuul.response.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Component
public class ConsumerFallback implements FallbackProvider {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerFallback.class);

    /**
     * 回退的哪个路由，默认是serviceId
     * 可以用* 或者 null 代表所有服务都过滤
     * @return
     */
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {

        logger.info("ConsumerFallback route:{}",route);

        if (cause != null && cause.getCause() != null) {
            logger.info("ConsumerFallback exception:{}", cause.getCause().getMessage());
        }

        return fallbackResponse(route);
    }

    /**
     * 实例化一个ClientHttpResponse接口的一个实现类
     * @return
     */
    private ClientHttpResponse fallbackResponse(String route) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                ResultMessage resultMessage = new ResultMessage(HttpStatus.BAD_REQUEST.getReasonPhrase(),"The service "+ route +" is unavailable.",new Date(System.currentTimeMillis()));
                return new ByteArrayInputStream(JSON.toJSONString(resultMessage).getBytes("UTF-8"));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
