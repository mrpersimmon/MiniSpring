package com.minispring.beans;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作用：解析某个路径下的 XML 来构建应用上下文
 * 按照一定的规则将 XML 文件内容解析出来，获取 Bean 的配置信息
 *
 * 1. 解析 XML 文件中的内容
 * 2. 加载解析的内容，构建 BeanDefinition
 * 3. 读取 BeanDefinition 的配置信息，实例化 Bean，然后把它注入到 BeanFactory 容器中
 */
public class ClassPathXmlApplicationContext implements BeanFactory {
    BeanFactory beanFactory;
    // context 负责整合容器的启动过程，读取外部配置，解析 Bean 定义，创建 BeanFactory
    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        BeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }
    // context 再对外提供一个 getBean 方法，底下就是调用的 BeanFactory 对应的方法
    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }
}
