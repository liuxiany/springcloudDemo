package com.example.eurekaconsumer.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 参考自https://github.com/spring-cloud/spring-cloud-netflix/issues/1952crmky的回答
 */
@Component
public class FeignBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

        if(containsBeanDefinition(configurableListableBeanFactory,"feignContext","eurekaServiceRegistry")){

            BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition("feignContext");
            beanDefinition.setDependsOn("eurekaServiceRegistry");
        }
    }

    private boolean containsBeanDefinition(ConfigurableListableBeanFactory beanFactory, String... beans) {
        return Arrays.stream(beans).allMatch(b -> beanFactory.containsBeanDefinition(b));
    }
}
