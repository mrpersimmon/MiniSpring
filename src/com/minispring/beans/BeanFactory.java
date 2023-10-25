package com.minispring.beans;

/**
 * 基础容器：1、获取一个 Bean；2、注册一个 BeanDefinition
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;
    void registerBeanDefinition(BeanDefinition beanDefinition);
}
