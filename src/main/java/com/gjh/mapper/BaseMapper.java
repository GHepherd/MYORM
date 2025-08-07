package com.gjh.mapper;


import com.gjh.annotations.TableName;
import com.gjh.wrapper.LambdaQueryWrapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BaseMapper<T> {

    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public BaseMapper() {
        // 通过反射获取子类定义时传入的实际泛型类型参数
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) genericSuperclass;
            Type[] actualTypeArguments = pt.getActualTypeArguments();
            if (actualTypeArguments.length > 0) {
                this.entityClass = (Class<T>) actualTypeArguments[0];
            }
        }
    }

    public String query(LambdaQueryWrapper<T> queryWrapper){
        TableName annotation = entityClass.getAnnotation(TableName.class);
        String tableName = annotation.value();
        return "SELECT * FROM " + tableName + queryWrapper.getSqlCondition();
    }
}
