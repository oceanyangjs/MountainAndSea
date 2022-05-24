package com.mountain.sea.core.mybatis.handler.enums;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 16:01
 */
public interface BaseEnum<E extends Enum<?>, T> {
    T getValue();
}