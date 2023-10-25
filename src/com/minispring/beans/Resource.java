package com.minispring.beans;

import java.util.Iterator;

/**
 * 外部配置信息都抽象成 Resource
 * 配置信息来源：XML 配置文件、数据库、Web 网络
 */
public interface Resource extends Iterator<Object> {

}
