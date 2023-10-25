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
 */
public class ClassPathXmlApplicationContext {
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();

    private Map<String, Object> singletons = new HashMap<>();

    // 构造器获取外部配置，解析出 Bean 的定义，形成内存映像
    public ClassPathXmlApplicationContext(String fileName) {
        this.readXml(fileName);
        this.instanceBeans();
    }

    // 传入 XML 文件的全路径名，获取 XML 内的信息

    /**
     * 解析 Bean 的核心方法：传入 XML 文件的全路径名，获取 XML 内的信息
     * 配置在 XML 内的 Bean 信息都是文本信息，需要解析之后变成内存结构才能注入到容器中。
     *
     * @param fileName
     */
    private void readXml(String fileName) {
        SAXReader saxReader = new SAXReader();
        try {
            URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();
            // 对配置文件中的每一个 <bean> 进行处理
            for (Element element : (List<Element>) rootElement.elements()) {
                // 获取 Bean 的基本信息
                String beanId = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
                // 将 Bean 的定义存放到 beanDefinitions
                beanDefinitions.add(beanDefinition);
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 利用反射创建 Bean 实例，并存储在 singletons 中
     */
    private void instanceBeans() {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                singletons.put(beanDefinition.getId(),
                        Class.forName(beanDefinition.getClassName()).newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 这是对外的一个方法，让外部程序从容器中获取 Bean 实例，会逐步演化成核心方法
    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }
}
